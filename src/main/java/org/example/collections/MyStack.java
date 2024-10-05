package org.example.collections;

import java.util.Stack;

import static org.example.collections.PalindromeChecker.checkPalindrome;

public class MyStack {

    public static void main(String[] args) {
        // Parenthesis checking
        System.out.println("Parenthesis checking:");
        System.out.println(ParenthesisChecker.checkParenthesis("(a + b) * (c + d)")); // true
        System.out.println(ParenthesisChecker.checkParenthesis("(a + b] * (c + d)")); // false
        System.out.println(ParenthesisChecker.checkParenthesis("(a + b) * (c + d"));  // false
        System.out.println(ParenthesisChecker.checkParenthesis("((a + b) * (c + d))")); // true
        System.out.println(ParenthesisChecker.checkParenthesis("({[]})")); // true
        System.out.println(ParenthesisChecker.checkParenthesis("({[}]")); // false

        // Palindrome checking
        System.out.println("Parenthesis checking:");
        System.out.println("madam: " + checkPalindrome("madam")); // true
        System.out.println("hello:" + checkPalindrome("hello")); // false
        System.out.println("12321:" + checkPalindrome("12321")); // true
        System.out.println("WasItACarOrACatISaw:" + checkPalindrome("WasItACarOrACatISaw")); // true
        System.out.println("NotPalindrome: " + checkPalindrome("NotPalindrome")); // false

    }
}


class ParenthesisChecker {
    public static boolean checkParenthesis(String expression) {
        System.out.print(expression + ":");
        Stack<Character> stack = new Stack<>();

        // Iterate through each character in the expression
        for (char c : expression.toCharArray()) {
            // If opening parenthesis, push onto stack
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            }
            // If closing parenthesis, check if matching opening parenthesis
            else if (c == ')' || c == ']' || c == '}') {
                if (stack.isEmpty()) {
                    return false; // Unbalanced: closing parenthesis without opening
                }

                char opening = stack.pop();
                if ((c == ')' && opening != '(') ||
                        (c == ']' && opening != '[') ||
                        (c == '}' && opening != '{')) {
                    return false; // Unbalanced: mismatched parentheses
                }
            }
        }

        // If stack is empty, parentheses are balanced
        return stack.isEmpty();
    }
}

class PalindromeChecker {
    public static boolean checkPalindrome(String input) {
        Stack<Character> stack = new Stack<>();
        input = input.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

        // Push characters onto stack
        for (char c : input.toCharArray()) {
            stack.push(c);
        }

        // Compare characters from input and stack
        for (char c : input.toCharArray()) {
            if (c != stack.pop()) {
                return false; // Not a palindrome
            }
        }

        return true; // Palindrome
    }
}
