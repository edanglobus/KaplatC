package com.example.KaplatC.service;

public class BinaryOperation {

    public static Double value(Operator op, Double operand1, Double operand2) {
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

