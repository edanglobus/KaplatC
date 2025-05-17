package com.example.KaplatC.service;

import com.example.KaplatC.historydb.AppHistoryManager;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

@Repository("calc")
@Getter @Setter
public class Calculator {
    private final AppHistoryManager history;
    private Stack<Double> argsStack = new Stack<>();
    private Operator operator;
    private List<Double> args = new ArrayList<>();

    public Calculator(@Qualifier("history") AppHistoryManager history) {
        this.history = history;
    }


    public Double getStackSize() {
            return (double) argsStack.size();
    }

    public Double pushArgsToStack(List<Double> args) {
        while(!args.isEmpty()) {
            argsStack.push(args.remove(args.size() - 1));
        }
        return this.getStackSize();
    }

    public Double popStackByCount(int count) {
        if (argsStack.size() >= count) {
            for (int i = 0; i < count; ++i) {
                argsStack.pop();
            }
            return this.getStackSize();
        }
        throw new ArrayIndexOutOfBoundsException("Error: cannot remove " + count + " from the stack. It has only " +
                argsStack.size() + " arguments");

    }

    public Double performeStackOperation(String operation) {
        this.operator = new Operator(operation);
        this.args.clear();
        if (Objects.equals(operator.getKind(), "none")) {
            throw new IllegalArgumentException("Error: unknown operation: " + operator.getStrOp());
        }
        final int stackSize = argsStack.size();
        String opKind = operator.getKind();

        if(Objects.equals(opKind, "binary") && stackSize >= 2) {
            args.add(argsStack.pop());
            args.add(argsStack.pop());
            List<Double> cpyArgs = new ArrayList<>(args);
            Double op1 = args.get(0), op2 = args.get(1);
            Double result = BinaryOperation.value(operator, op1 , op2);
            history.writeAll(operator, cpyArgs,result);
            history.addToHistory("s");
            return result;
        }
         else if(Objects.equals(opKind, "unary") && stackSize >= 1) {
            args.add(argsStack.pop());
            List<Double> cpyArgs = new ArrayList<>(args);
            Double op1 = cpyArgs.get(0);
            Double result = UnaryOperation.value(operator, op1);
            history.writeAll(operator, cpyArgs,result);
            history.addToHistory("s");
            return result;
         }

         throw new IllegalArgumentException("Error: cannot implement operation " + operator.getStrOp().toLowerCase() + ". It requires "
                 + operator.getReqCount() + " arguments and the stack has only " + argsStack.size() + " arguments.");

    }

    public Double independentValue(String opStr, List<Double> argumentsList) {
        //this.args.clear();
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
                Double op1 = args.get(0);
                Double result = UnaryOperation.value(operator, op1);
                List<Double> cpyArgs = new ArrayList<>(args);
                history.writeAll(operator, cpyArgs,result);
                history.addToHistory("i");
                return result;
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
                Double op1 = args.get(0), op2 = args.get(1);
                Double result = BinaryOperation.value(operator, op1 , op2);
                List<Double> cpyArgs = new ArrayList<>(args);
                history.writeAll(operator, cpyArgs,result);
                history.addToHistory("i");
                return result;
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
