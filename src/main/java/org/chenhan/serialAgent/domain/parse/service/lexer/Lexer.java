package org.chenhan.serialAgent.domain.parse.service.lexer;


import org.chenhan.serialAgent.domain.parse.model.Token;
import org.chenhan.serialAgent.domain.parse.model.TokenIndex;

import java.util.ArrayList;
import java.util.List;

import static org.chenhan.serialAgent.domain.parse.model.TokenType.*;

/**
 * &#064;Author:  chenhan
 * @author Chenhan
 * @Description: 词法分析器
 * &#064;ProjectName:  devtools
 * &#064;Date:  2023/9/5 9:33
 */
public class Lexer {


    public static List<Token> tokenize(String expression){
        return tokenize(expression, 0);
    }
    /**
     * 拆分表达式字符串为 token 列表。
     * 支持的 token 类型有：
     * 1. 操作符：&&、||、！
     * 2. 左括号和右括号
     * 3. 函数：包括函数名称及参数 named（String.class）算一个token
     *
     * @param expression 要拆分的表达式字符串
     * @return 包含 token 的列表
     */
    public static List<Token> tokenize(String expression, int position) {
        List<Token> tokens = new ArrayList<>();

        // 1. 检查位置position的有效性
        if (position < 0 || position >= expression.length()) {
            throw new IllegalArgumentException("Invalid starting position");
        }

        // 2. 从position开始逐个往后读取
        while (position < expression.length()) {
            TokenIndex ti = generateToken(expression, position);
            position = ti.getNextIndex();
            if (ti.getToken() != null) {
                tokens.add(ti.getToken());
            }
        }

        return tokens;
    }

    private static TokenIndex generateToken(String expression, int position) {
        StringBuilder sb = new StringBuilder();
        /**
         * 首字母
         */
        Character firstLetter = expression.charAt(position);
        // 遍历获取该参数
        // 括号
        if (firstLetter=='('||firstLetter==')'){
            return TokenIndex.builder().token(new Token(PARENTHESIS.getType(), firstLetter.toString())).nextIndex(position + 1).build();
        }
        // 取反
        else if (firstLetter.equals('!')) {
            return TokenIndex.builder()
                    .token(new Token(OPERATOR.getType(), firstLetter.toString()))
                    .nextIndex(position + 1)
                    .build();
        }
        // 二元操作符
        else if (judgeBinaryOperator(expression,position)) {
            return TokenIndex.builder()
                    .token(new Token(OPERATOR.getType(), firstLetter.toString() + firstLetter))
                    .nextIndex(position + 2)
                    .build();
        }
        //  函数和其他
        // 如果是函数记录，如果是其他抛出参数异常
        // 函数的特征，字符串开头且一定会存在一个括号，括号中可能存在值也可能不存在
        // 将数据读取到sb中，当遇见不满足的时候，抛出异常
        else{
            if(!isFunctionNameFirstLetter(firstLetter)){
                throw new IllegalArgumentException("表达式不合法");
            }
            // 记录正反括号出现的次数
            sb.append(firstLetter);
            /**
             * 1- 正在读取函数名
             * 2- 读取左括号完毕
             * 3- 正在读取函数参数
             * 4- 读取右括号完毕
             * [a-zA-Z_$][a-zA-Z_$0-9]*\([^)]*\)
             */
            int state = 1;
            int i = position + 1;
            for (; i < expression.length(); i++) {
                Character character = expression.charAt(i);
                if (isFunctionNameNormalLetter(character)&&state==1){
                    sb.append(character);
                }
                // 判断括号
                else if (isParenthesis(character)){
                    if (character.equals('(')&&state==1) {
                        state = 3;
                    }
                    else if (character.equals(')')&&state==3){
                        state = 4;
                    }
                    sb.append(character);
                }
                // read args
                else if (state==3){
                    sb.append(character);
                }
                else {
                    throw new IllegalArgumentException("非法的表达式");
                }
                if (state==4){
                    i++;
                    break;
                }
            }
            return TokenIndex.builder().token(new Token(FUNCTION.getType(), sb.toString())).nextIndex(i).build();
        }

    }

    /**
     * 判断是否为括号
     * @param character 待判断的字符
     * @return 是否为括号
     */
    private static boolean isParenthesis(Character character) {
        return character == '(' || character == ')';
    }

    /**
     * 校验是否为函数名的非头部字母
     * @param character 待验证的字符
     * @return 是否为有效的非头部字符
     */
    private static boolean isFunctionNameNormalLetter(Character character) {
        // 非头部字母可以是字母、数字或下划线
        return Character.isLetterOrDigit(character) || character == '_';
    }

    /**
     * 校验是否为函数的头部字符
     * @param firstLetter 函数名的首字母
     * @return 是否为有效的函数名首字母
     */
    private static boolean isFunctionNameFirstLetter(Character firstLetter) {
        // 函数名的首字母必须是字母或下划线
        return Character.isLetter(firstLetter) || firstLetter == '_';
    }


    /**
     * 判断是否是二元操作符
     * @param expression 表达式
     * @param position
     * @return
     */
    private static boolean judgeBinaryOperator(String expression, int position) {
        Character firstLetter = expression.charAt(position);
        return
                (firstLetter.equals('&') || firstLetter.equals('|'))
                        && position + 1 < expression.length()
                        && firstLetter.equals(expression.charAt(position + 1));
    }

    private static String readFunction(String expression, int position) {
        StringBuilder functionWithArgs = new StringBuilder();
        char currentChar = expression.charAt(position);

        // 首先检查是否是字母或下划线，符合函数命名规范的起始字符
        if (!Character.isLetter(currentChar) && currentChar != '_') {
            return "";
        }

        // 继续检查后续字符是否是字母、数字或下划线，构建函数名
        while (position < expression.length() && (Character.isLetterOrDigit(currentChar) || currentChar == '_')) {
            functionWithArgs.append(currentChar);
            position++;
            if (position < expression.length()) {
                currentChar = expression.charAt(position);
            }
        }

        // 继续检查是否存在一个左括号，表示这是一个函数
        if (position < expression.length() && currentChar == '(') {
            // 继续读取整个函数及其参数的字符串
            int nestingLevel = 1;
            // 添加左括号
            functionWithArgs.append(currentChar);
            position++; // 移动到左括号之后的字符
            while (position < expression.length() && nestingLevel > 0) {
                currentChar = expression.charAt(position);
                if (currentChar == '(') {
                    nestingLevel++;
                } else if (currentChar == ')') {
                    nestingLevel--;
                }
                functionWithArgs.append(currentChar);
                position++;
            }

            // 如果存在与右括号匹配的左括号，将整个函数及其参数作为一个字符串返回
            if (nestingLevel == 0) {
                return functionWithArgs.toString();
            }
        }

        // 如果没有正确匹配的左括号，返回空字符串表示不是函数
        return "";
    }


}