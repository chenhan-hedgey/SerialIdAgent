package org.chenhan.serialAgent.intercept;

import com.sun.org.apache.xpath.internal.operations.Bool;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.chenhan.serialAgent.exception.AgentException;
import org.chenhan.serialAgent.util.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/1 15:57
 */
public class SerialInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(SerialInterceptor.class);

    /**
     * 拦截器方法，用于在静态方法调用过程中进行拦截和增强。
     *
     * @param allArguments   调用方法时传递的所有参数。
     * @param zuper          代表原始方法调用的可调用对象。
     * @param method         被拦截的方法对象。
     * @return               方法调用的返回值（如果有）。
     * @throws Exception     可能抛出的异常，包括原始方法调用中抛出的异常。
     */
    @RuntimeType
    public static Object interceptForStaticMethod(
            @AllArguments Object[] allArguments,
            @SuperCall Callable<?> zuper,
            @Origin Method method) throws Exception {
        logger.info("进入静态拦截方法");

        /**
         * 调用状态，是指，判断call是否执行成功（过程成功即可）
         * 如：A系统调用B，但是B系统返回一个错误，同样算成功。判断依据为，B是否成功返回结果，而非结果的校验
         */
        Boolean isCallFinish = false;
        /**
         * 重导向状态，是指，本系统调用重导向的方法时，因调用重导向方法出现的错误，
         * 在这种错误条件下，我们将关闭拦截，直接执行原方法,
         * 默认为打开
         */
        Boolean isInsteadSuccess = true;
        Object result = null;
        try {

            Method insteadMethod = findStaticMethod("org.tools.mockAPI.ApiCaller", "call", new Class[]{String[].class, String[].class});
            logger.info("获取对应的class对象以及替代的class方法");
            // 参数处理
            List<Object> newArgs = processArgs(allArguments);
            // 调用替代方法并执行
            result = callInsteadMethod(insteadMethod,newArgs);
            isCallFinish = true;
            return result;
        }
        catch (AgentException e){
            //如果出现了 agent Exception，立即执行原方法
            logger.info("agent执行出错，错误信息为：{}",e);
            logger.info("立即执行原方法，完成原方法的调用");
            isInsteadSuccess = false;
            result = zuper.call();
            isCallFinish = true;
            return result;
        }
        finally {
            logger.info("流水号：{},agent状态：{},调用状态:{},结果为:\n{}","testNo.1",isInsteadSuccess,isCallFinish, result);
            // 其他处理，如果收到异常回复，那么就修改状态为异常，如果收到其他回复，则进行校验判断是否成功并且修改状态
            processAfterIntercept();
        }
    }

    private static Object callInsteadMethod(Method insteadMethod, List<Object> newArgs) throws AgentException {
        try {
            return ReflectionUtils.callStaticMethod(insteadMethod, newArgs);
        } catch (InvocationTargetException e) {
            logger.info("调用目标函数失败，失败方法：{}#{}",insteadMethod.getName(),insteadMethod.getGenericParameterTypes());
            throw new AgentException("调用替代方法失败",e);
        } catch (IllegalAccessException e) {
            logger.info("调用目标函数失败，modifiers:{},失败方法：{}#{}",insteadMethod.getModifiers(),insteadMethod.getName(),insteadMethod.getGenericParameterTypes());
            throw new AgentException("调用替代方法访问失败",e);
        }
    }

    /**
     * 寻找导向的静态方法
     * @param clazzName
     * @param call
     * @param classes
     * @return
     */
    private static Method findStaticMethod(String clazzName, String call, Class[] classes) throws AgentException {
        try {
            Class apiClazz = Class.forName(clazzName);
            Method insteadMethod = ReflectionUtils.getStaticMethodFromClass(apiClazz, "call", new Class[]{String[].class, String[].class});
            return insteadMethod;
        } catch (NoSuchMethodException e) {
            logger.info("没有找到对应的静态方法:{}#{}",clazzName,call);
            throw new AgentException("未找到重定向的静态方法", e);
        } catch (ClassNotFoundException e) {
            logger.info("没有找到对应的重定向类:{}",clazzName);
            throw new AgentException("未找到对应的导向类",e);
        }
    }

    /**
     * todo: 执行基本操作
     */
    private static void processAfterIntercept() {
    }

    /**
     * 参数处理
     * @param args 原参数
     * @return 修改后的参数
     * @throws AgentException 自定义异常
     */
    private static List<Object> processArgs(Object[] args)  throws AgentException {
        String className = "org.tools.serialNumber.SerialThreadData";
        String fieldName = "threadData";
        List<Object> newArgs = new ArrayList<>();
        newArgs.addAll(Arrays.asList(args));
        Class<?> dataClass = null;
        try {
            dataClass = Class.forName(className);
            Field threadData = ReflectionUtils.getFieldFromClass(dataClass, fieldName);
            String value = ((String) ReflectionUtils.getValueFromStaticField(threadData, ThreadLocal.class).get());
            newArgs.add(new String[]{value});
            return newArgs;
        } catch (ClassNotFoundException e) {
            logger.info("未找到数据类：{}",className);
            throw new AgentException("没有找到对应的数据加载类:"+className,e);
        } catch (NoSuchFieldException e) {
            logger.info("未找到数据字段：{}#{}",className,fieldName);
            throw new AgentException("没有找到对应数据类的字段:"+fieldName,e);
        } catch (IllegalAccessException e) {
            throw new AgentException("非法访问",e);
        }
    }

}
