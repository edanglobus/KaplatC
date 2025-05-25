package com.example.KaplatCalculatorApp.service;

public class UnaryOperation {

    private static double factorial(double num) {
        if(num < 0) {
            throw new IllegalArgumentException("Error while performing operation Factorial: not supported for the negative number");
        }
        double res = 1;
        while(num > 0) {
            res *= num;
            --num;
        }
        return res;
    }

    public static Double value(Operator op, Double operand) {
        char opSign = op.getOperator();
        return switch (opSign) {
            case '|' -> Math.abs(operand);
            case '!' -> factorial(operand);
            default -> throw new IllegalArgumentException("Invalid Operator(Unary): " + op);
        };
    }


}
