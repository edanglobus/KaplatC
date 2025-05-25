package com.example.KaplatC.service;

import com.example.KaplatC.historydb.AppHistoryManager;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

@Repository("calc")
@Getter @Setter
public class Calculator {
    private final Logger loggerStack = LoggerFactory.getLogger("stack-logger");
    private final Logger loggerIndependent = LoggerFactory.getLogger("independent-logger");
    private final AppHistoryManager history;
    private Stack<Double> argsStack = new Stack<>();
    private Operator operator;
    private List<Double> args = new ArrayList<>();

    public Calculator(@Qualifier("history") AppHistoryManager history) {
        this.history = history;
    }


    public Double getStackSize() {
            loggerStack.info("Stack size is {}", (double) argsStack.size());
            loggerStack.debug("Stack content (first == top): {}", argsStack);
            return (double) argsStack.size();
    }

    public Double pushArgsToStack(List<Double> args) {
        int addSize = args.size();
        int beforeSize = argsStack.size();
        int afterAddSize = beforeSize + addSize;

        loggerStack.info("Adding total of {} argument(s) to the stack | Stack size: {}",
                addSize,
                afterAddSize
                );
        loggerStack.debug("Adding arguments: {} | Stack size before {} | stack size after {}",
                args,
                beforeSize,
                afterAddSize
        );

        while(!args.isEmpty()) {
            argsStack.push(args.remove(args.size() - 1));
        }
        return (double) afterAddSize;
    }

    public Double popStackByCount(int count) {
        if (argsStack.size() >= count) {
            for (int i = 0; i < count; ++i) {
                argsStack.pop();
            }
            loggerStack.info("Removing total {} argument(s) from the stack | Stack size: {}",
                    count,
                    argsStack.size()
                    );
            return this.getStackSize();
        }
        String eMessage = "Error: cannot remove " + count + " from the stack. It has only " +
                argsStack.size() + " arguments";

        loggerStack.error("Server encountered an error ! message: {}", eMessage);
        throw new ArrayIndexOutOfBoundsException(eMessage);

    }

    public Double performeStackOperation(String operation) {
        this.operator = new Operator(operation);
        this.args.clear();
        Double result;
        if (Objects.equals(operator.getKind(), "none")) {
            throw new IllegalArgumentException("Error: unknown operation: " + operator.getStrOp());
        }
        final int stackSize = argsStack.size();
        String opKind = operator.getKind();

        if(Objects.equals(opKind, "binary") && stackSize >= 2) {
            args.add(argsStack.pop());
            args.add(argsStack.pop());
            Double op1 = args.get(0), op2 = args.get(1);
            result = BinaryOperation.value(operator, op1 , op2);
        }
         else if(Objects.equals(opKind, "unary") && stackSize >= 1) {
            args.add(argsStack.pop());
            Double op1 = args.get(0);
            result = UnaryOperation.value(operator, op1);
         }
         else {
             String eMessage = "Error: cannot implement operation " + operator.getStrOp().toLowerCase() + ". It requires "
                     + operator.getReqCount() + " arguments and the stack has only " + argsStack.size() + " arguments.";
            loggerStack.error("Server encountered an error ! message: {}", eMessage);
            throw new IllegalArgumentException(eMessage);
        }


        List<Double> cpyArgs = new ArrayList<>(args);
        history.writeAll(operator, cpyArgs,result);
        history.addToHistory("s");

        loggerStack.info("Performing operation {}. Result is {} | stack size: {}",
                this.operator.getStrOp(),
                result,
                argsStack.size()
                );
        int lineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();
        loggerStack.debug(" LINE:{} | Performing operation: {}({}) = {}",
                lineNumber,
                this.operator.getStrOp(),
                args,
                result
                );
        return result;

    }

    public Double independentValue(String opStr, List<Double> argumentsList) {
        this.operator = new Operator(opStr);
        this.args = argumentsList;
        return this.valueIndependents();
    }

    public Double valueIndependents() {
        int argsSize = args.size();
        Double result;

        if(Objects.equals(operator.getKind(), "unary")) {
            if(argsSize == 1) {
                // changed to Remove from get**************
                Double op1 = args.get(0);
                result = UnaryOperation.value(operator, op1);
            }
            else if (argsSize < 1) {
                loggerIndependent.error("Server encountered an error ! message: Not enough arguments to perform the operation(unary): {}", operator.getStrOp());
                throw new IllegalArgumentException("Not enough arguments to perform the operation: " + operator.getStrOp());
            }
            else {
                loggerIndependent.error("Server encountered an error ! message: Too many arguments to perform the operation(unary): {}", operator.getStrOp());
                throw new IllegalArgumentException("Too many arguments to perform the operation: " + operator.getStrOp());
            }
        }
        else if (Objects.equals(operator.getKind(), "binary")) {
            if(argsSize == 2) {
                // changed to Remove from get**************
                Double op1 = args.get(0), op2 = args.get(1);
                result = BinaryOperation.value(operator, op1 , op2);
            }
            else if (argsSize < 2) {
                loggerIndependent.error("Server encountered an error ! message: Not enough arguments to perform the operation(binary): {}", operator.getStrOp());
                throw new IllegalArgumentException("Not enough arguments to perform the operation: " + operator.getStrOp());
            }
            else {
                loggerIndependent.error("Server encountered an error ! message: Too many arguments to perform the operation(binary): {}", operator.getStrOp());
                throw new IllegalArgumentException("Too many arguments to perform the operation: " + operator.getStrOp());
            }
        }
        else {
            loggerIndependent.error("Server encountered an error ! message: unknown operation: {}", operator.getStrOp());
            throw new IllegalArgumentException("Error: unknown operation: " + operator.getStrOp());
        }

        List<Double> cpyArgs = new ArrayList<>(args);
        history.writeAll(operator, cpyArgs,result);
        history.addToHistory("i");
        loggerIndependent.info("Performing operation {}. Result is {}", operator.getStrOp(), result);
        loggerIndependent.debug("Performing operation: {}({}) = {}", operator.getStrOp(), args, result);
        return result;
    }
}
