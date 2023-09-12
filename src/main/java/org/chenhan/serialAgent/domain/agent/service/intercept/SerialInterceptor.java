package org.chenhan.serialAgent.domain.agent.service.intercept;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.chenhan.serialAgent.domain.agent.model.MethodDescription;
import org.chenhan.serialAgent.domain.context.service.config.SysConfig;
import org.chenhan.serialAgent.domain.support.ReflectionCache;
import org.chenhan.serialAgent.exception.AgentException;
import org.chenhan.serialAgent.util.ReflectionUtils;
import org.chenhan.serialAgent.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import static org.chenhan.serialAgent.domain.support.ReflectionCache.getCallMethod;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/1 15:57
 */
public class SerialInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(SerialInterceptor.class);

    /**
     * 替代的方法
     */
     public static MethodDescription methodDescription;
    
    
    

    /**
     * 拦截器方法，用于在静态方法调用过程中进行拦截和增强。
     *
     * @param originArguments   调用方法时传递的所有参数。
     * @param zuper          代表原始方法调用的可调用对象。
     * @param method         被拦截的方法对象。
     * @return               方法调用的返回值（如果有）。
     * @throws Exception     可能抛出的异常，包括原始方法调用中抛出的异常。
     */
    @RuntimeType
    public static Object interceptForStaticMethod(
            @AllArguments Object[] originArguments,
            @SuperCall Callable<?> zuper,
            @Origin Method method) throws Exception {
        logger.info("进入静态拦截方法");
        Object result = null;
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
        try{
            // 1. 从agentContext中获取Method方法
            SysConfig singleton = SysConfig.getSingleton();
            String rebaseMethod = singleton.getAgentConfig().getRebaseMethod();
            Method callMethod = getCallMethod(rebaseMethod);
            // 2. 重新制作参数
            List<Object> newArgs = processArgs(originArguments);
            // 3. 执行新方法
            result = callInsteadMethod(callMethod, newArgs);
            isCallFinish = true;
            // 4. 捕获异常，执行原方法
            // 其他处理，如果收到异常回复，那么就修改状态为异常，如果收到其他回复，则进行校验判断是否成功并且修改状态
            processAfterIntercept();
            return result;
        }catch (AgentException | RuntimeException e){
            //如果出现了 agent Exception，立即执行原方法
            logger.info("agent执行出错，错误信息为：{}",e.toString());
            logger.info("立即执行原方法，完成原方法的调用");
            isInsteadSuccess = false;
            result = zuper.call();
            isCallFinish = true;
            return result;
        }
        finally {
            logger.info("流水号：{},agent状态：{},调用状态:{},结果为:\n{}","testNo.1",isInsteadSuccess,isCallFinish, result);
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
     * 在这里执行操作，需要在响应的请求header设置
     * todo: 执行基本操作
     */
    private static void processAfterIntercept() {
        // 1. 获取执行Map
        // 2. 获取对应的id号
        // 3. 更新对应的表
    }

    /**
     * 参数处理
     * @param args 原参数
     * @return 修改后的参数
     * @throws AgentException 自定义异常
     */
    public static List<Object> processArgs(Object[] args)  throws AgentException {
        SysConfig singleton = SysConfig.getSingleton();
        String infoObject = singleton.getAgentConfig().getInfoObject();
        String[] classAndFiledFormString = StringUtils.getClassAndFiledFormString(infoObject);
        Class klass = null;
        try {
            klass = ReflectionCache.loadClass(classAndFiledFormString[0]);
        } catch (ClassNotFoundException e) {
            throw new AgentException("加载数据类失败");
        }
        Field field = ReflectionCache.loadField(klass,classAndFiledFormString[1]);
        List<Object> newArgs = new ArrayList<>();
        newArgs.addAll(Arrays.asList(args));
        try {
            Map<String,String> map = ((Map<String,String>) ReflectionUtils.getValueFromStaticField(field, ThreadLocal.class).get());
            System.out.println(map);
            newArgs.add(new String[]{map.get("chenhan")});
            return newArgs;
        } catch (IllegalAccessException e) {
            throw new AgentException("没有对应的代理",e);
        }
    }

}
