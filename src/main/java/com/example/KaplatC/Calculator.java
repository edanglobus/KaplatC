package com.example.KaplatC;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

@Component
@Getter @Setter
public class Calculator {
    private Stack<Double> argsStack = new Stack<>();
    private Operator operator;
    private List<Double> args;


    public Double getStackSize() {
            return (double) argsStack.size();
    }

    public Double pushArgsToStack(List<Double> args) {
        while(!args.isEmpty()) {
            argsStack.push(args.remove(0));
        }
        return this.getStackSize();
    }

    public Double performeStackOperation(String operation) {
        this.operator = new Operator(operation);
        if (Objects.equals(operator.getKind(), "none")) {
            throw new IllegalArgumentException("Error: unknown operation: " + operator.getStrOp());
        }
        final int stackSize = argsStack.size();
        String opKind = operator.getKind();

        if(Objects.equals(opKind, "binary") && stackSize >= 2) {
                return BinaryOperation.value(operator, argsStack.pop(), argsStack.pop());
        }
         else if(Objects.equals(opKind, "unary") && stackSize >= 1) {
                 return UnaryOperation.value(operator, argsStack.pop());
         }

         throw new IllegalArgumentException("Error: cannot implement operation " + operator.getStrOp().toLowerCase() + ". It requires "
                 + operator.getReqCount() + " arguments and the stack has only " + argsStack.size() + " arguments.");

    }

    public Double independentValue(String opStr, List<Double> argumentsList) {
        this.operator = new Operator(opStr);
        this.args = argumentsList;
        return this.value();
    }

    public Double value() {
        int argsSize = args.size();
        //Double result;
        if(Objects.equals(operator.getKind(), "unary")) {
            if(argsSize == 1) {
                // changed to Remove from get**************
                return UnaryOperation.value(operator, args.remove(0));
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
                // changed to Remove from get**************
                return BinaryOperation.value(operator, args.remove(0), args.remove(0));
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
