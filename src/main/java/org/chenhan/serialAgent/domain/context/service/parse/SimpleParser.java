package org.chenhan.serialAgent.domain.context.service.parse;

import lombok.Data;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import org.chenhan.serialAgent.domain.support.ReflectionCache;
import org.chenhan.serialAgent.exception.AgentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: chenhan
 * @Description: 简单转换器
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/6 9:21
 **/
@Data
public class SimpleParser implements Parser{
    private static final Logger logger = LoggerFactory.getLogger(SimpleParser.class);
    /**
     * 待拦截的方法
     */
    private String interceptMethod;

    public SimpleParser(String interceptMethod) {
        this.interceptMethod = interceptMethod;
    }


    /**
     * 转化配置文件为 Junction
     * 格式：XXX.XXX.XXX.class#method(String.class,String[].class,int.class)
     * @return Junction 实例
     */
    @Override
    public ElementMatcher.Junction parse() throws AgentException {
        String[] split = interceptMethod.split("#");

        if (split.length != 2) {
            throw new AgentException("分离拦截方法失败");
        }

        String className = split[0];
        String methodInfo = split[1];

        // 1. 解析方法信息
        String[] methodComponents = methodInfo.split("[()]");
        if (methodComponents.length==0) {
            throw new AgentException("获取函数名失败");
        }

        // 3. 取出 method 的信息记录为 methodName
        String methodName = methodComponents[0];

        // 4. 抽出 Args 字符串，记录为 String 数组 args
        String[] args = methodComponents[1].split(",");

        // 5. 根据 args 数组的每一项调用 ReflectionCache 生成 class 实例数组 argsClasses
        Class<?>[] argsClasses = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            try {
                argsClasses[i] = ReflectionCache.loadClass(args[i]);
            } catch (ClassNotFoundException e) {
                logger.info("加载方法的匹配类型失败，失败类型:{}",args[i]);
                throw new AgentException("加载配置文件中的类型失败",e);
            }
        }

        // 6. 调用 ElementMatchers 中的方法，依据如下规则生成 Junction matcher
        ElementMatcher.Junction matcher = ElementMatchers.named(methodName)
                .and(ElementMatchers.takesArguments(argsClasses));

        // 7. 返回 matcher
        return matcher;
    }
}
