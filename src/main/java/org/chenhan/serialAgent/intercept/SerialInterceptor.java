package org.chenhan.serialAgent.intercept;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
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

        try {
            Object result = zuper.call();
            return result;
        } catch (Exception e) {
            // 反射执行异常
            logger.error("反射执行错误，被拦截的方法为:{}，抛出的异常信息:\n{}",method.getName(),e);
            // 原异常要原封不动的外抛
            throw e;
        }

    }

}
