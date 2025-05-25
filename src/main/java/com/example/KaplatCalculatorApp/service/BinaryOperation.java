package com.example.KaplatCalculatorApp.service;

public class BinaryOperation {

    public static Double value(Operator op, Double operand1, Double operand2) {
        if(op.getOperator() == '/' && operand2 == 0) {
            throw new IllegalArgumentException("Error while performing operation Divide: division by 0");
        }

        char opSign = op.getOperator();
        return switch (opSign) {
            case '+' -> operand1 + operand2;
            case '-' -> operand1 - operand2;
            case '*' -> operand1 * operand2;
            case '/' -> operand1 / operand2;
            case '^' -> Math.pow(operand1, operand2);
            default -> throw new IllegalArgumentException("Invalid Operator(Binary): " + op);
        };
    }
}

