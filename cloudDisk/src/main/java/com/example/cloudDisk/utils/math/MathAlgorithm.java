package com.example.cloudDisk.utils.math;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author 成大事
 * @since 2022/5/26 9:39
 */
public class MathAlgorithm {

    /**
     *
     * @param exp 字符串形式的四则表达式
     * @return  结果
     */
    public static String getResult(String exp) {

        String result = null;
        List<String> list = Arrays.asList("+", "-", "*", "/", "(", ")");
        // 定义一个字符串，里面包含标准的四则运算；
        // 如果使用参数的方式，还需另外校验四则运算的合法性
        // 定义一个栈
        Stack<String> stack = new Stack<String>();
        // 第一步去除括号，即先计算括号里面的
        for(int i = 0; i<exp.length(); i++){
            String character = String.valueOf(exp.charAt(i));
            if (character.equals(" ")){
                continue;
            }
            if (character.equals(")")){
                // 如果遇到右括号，把栈中的数据一个一个取出来放入另外一个栈中，直到遇见左括号
                Stack<String> sb = new Stack();
                while (!stack.isEmpty() && !stack.peek().equals("(")){
                    sb.push(stack.pop());
                }
                stack.pop();// 删除栈中的左括号
                stack.push(calculate(sb));// 将括号里面的内容计算之后重新放入栈中

            }else {
                // 没有遇到右括号
                if (stack.isEmpty()){
                    stack.push(character);
                    continue;
                }
                if (list.contains(character)){
                    stack.push(character); // 如果是运算符或者左括号，直接入栈
                }else {
                    // 如果是数字或者小数点，需要和之前入栈的数字拼接
                    if (list.contains(stack.peek())){
                        stack.push(character); // 如果上一个入栈的不是数字或者小数点，直接入栈
                    }else {
                        // 如果上一个入栈的是数字或者小数点，先出栈，再拼接，最后入栈
                        StringBuilder sb = new StringBuilder();
                        sb.append(stack.pop());
                        sb.append(character);
                        stack.push(sb.toString());
                    }
                }
            }
        }
        // 计算最后的结果
        if (stack.size() == 1){// 计算完括号里面的，如果栈中只剩下一个元素
            System.out.println("???"+stack.pop());
        }else {// 计算完括号里面的，栈中还有多个元素，继续计算
            // 但是此时栈中的元素是正序排列的，需要变成倒序再计算
            Stack<String> last = new Stack<String>();
            while (!stack.isEmpty()){
                last.push(stack.pop());
            }
            result =  calculate(last);

        }
        return result;
    }
    // 计算不带括号的字符串，但是参数栈中的数据 是倒序的，因为栈的原理是先进后出
    private static String calculate(Stack<String> exp) {
        ArrayList<String> arrayList = new ArrayList<String>();
        // 先算乘除法
        while (!exp.isEmpty()){
            if ("*".equals(exp.peek()) || "/".equals(exp.peek())){
                String fuhao = exp.pop();
                String number = exp.pop();
                String last = arrayList.remove(arrayList.size()-1);
                if (fuhao.equals("*")){
                    arrayList.add(new BigDecimal(last).multiply(new BigDecimal(number)).toString());
                }else {
                    arrayList.add(new BigDecimal(last).divide(new BigDecimal(number), 12, BigDecimal.ROUND_HALF_EVEN).toString());
                }
            }else {
                arrayList.add(exp.pop());
            }
        }
        // 再算加减法
        BigDecimal b = new BigDecimal(arrayList.remove(0));
        Iterator<String> iterator = arrayList.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            if (next.equals("+")){
                b = b.add(new BigDecimal(iterator.next()));
            }else if (next.equals("-")){
                b = b.subtract(new BigDecimal(iterator.next()));
            }
        }
        return b.toString();
    }

    /**
     * 通过 一个jar包里面的方法
     * @param exp  字符串形式的四则表达式
     * @return  结果
     */
    public static String getValue(String exp){
        // jdk自带的方法计算，用于比较结果
        ScriptEngine se = new ScriptEngineManager().getEngineByName("JavaScript");
        try {
            Object eval = se.eval(exp);
            return String.valueOf(eval);
        } catch (ScriptException e) {
            e.printStackTrace();
            return null;
        }
    }
}
