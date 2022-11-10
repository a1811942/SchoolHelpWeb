package com.example.demo.test;

import java.util.Stack;

public class Test {

    @org.junit.Test
    public void test() {
        Test test = new Test();
        test.isValid();
    }

    @org.junit.Test
    public void isValid() {
        String s = "(){}[]";
        Stack<Character> stack = new Stack<Character>();
        int len = s.length();
        int i = 0;
        char[] chars = s.toCharArray();
        while (i < len) {
            if (!stack.empty()) {
                if ('(' == stack.peek() && chars[i] == ')') {
                    stack.pop();
                    i++;

                }
                if ('{' == stack.peek() && chars[i] == '}') {
                    stack.pop();
                    i++;

                }
                if ('[' == stack.peek() && chars[i] == ']') {
                    stack.pop();
                    i++;

                }
            }

            stack.push(chars[i]);

            i++;
        }


        System.out.println(stack.isEmpty());
    }

    @org.junit.Test
    public void isValid1() {
        String s = "{[]}";
        Stack<Character> stack = new Stack<Character>();
        for (char c : s.toCharArray()) {
            if (c == '(') stack.push(')');
            else if (c == '[') stack.push(']');
            else if (c == '{') stack.push('}');
            else if (stack.isEmpty() || c != stack.pop()) {
                System.out.println(false);
                break;
            }

        }
        System.out.println(stack.isEmpty());
    }
}
