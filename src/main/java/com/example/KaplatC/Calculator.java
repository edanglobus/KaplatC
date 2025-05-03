package com.example.KaplatC;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter @Setter
public class Calculator {
    private Operator operator;
    private List<Double> args;



    public Calculator(String opStr, List<Double> args) {
        this.operator = new Operator(opStr);
        this.args = args;
    }

    public Double value() {
        int argsSize = args.size();
        //Double result;
        if(Objects.equals(operator.getKind(), "unary")) {
            if(argsSize == 1) {
                return UnaryOperation.value(operator, args.get(0));
            }
            else if (argsSize < 1) {
                throw new IllegalArgumentException("Error: Not enough arguments to perform the operation: " + operator.getStrOp());
            }
            else {
                throw new IllegalArgumentException("Error: Too many arguments to perform the operation: " + operator.getStrOp());
            }
        }
        else if (Objects.equals(operator.getKind(), "binary")) {
            if(argsSize == 2) {
                return BinaryOperation.value(operator, args.get(0), args.get(1));
            }
            else if (argsSize < 2) {
                throw new IllegalArgumentException("Error: Not enough arguments to perform the operation: " + operator.getStrOp());
            }
            else {
                throw new IllegalArgumentException("Error: Too many arguments to perform the operation: " + operator.getStrOp());
            }
        }
        else {
            throw new IllegalArgumentException("Error: unknown operation: " + operator.getStrOp());
        }
    }



}
