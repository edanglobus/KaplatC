package com.example.KaplatC;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository("history")
@Getter @Setter
public class AppHistoryManager {
    private List<JsonFormatForOperation> SOperationHistory = new ArrayList<>();
    private List<JsonFormatForOperation> IOperationHistory = new ArrayList<>();
    private JsonFormatForOperation currentOperation = new JsonFormatForOperation();

    public List<JsonFormatForOperation> getStackHistory() {
        return SOperationHistory;
    }
    public List<JsonFormatForOperation> getIndependentHistory() {
        return IOperationHistory;
    }
    public List<JsonFormatForOperation> getAllHistory() {
        List<JsonFormatForOperation> all = new ArrayList<>(SOperationHistory);
        all.addAll(IOperationHistory);
        return all;
    }


    public void writeOperation(Operator operator) {
        currentOperation.setOperation(operator.getStrOp().toLowerCase());
    }

    public void writeArgList(List<Double> args) {
        currentOperation.setArguments(args);
    }

    public void writeResult(Double result) {
        currentOperation.setResult(result);
    }

    public void writeAll(Operator op, List<Double> args, Double result) {
        writeOperation(op);
        writeArgList(args);
        writeResult(result);
    }

    public void addToHistory(String sORi) {
        switch(sORi) {
            case "s":
                currentOperation.setFlavor("STACK");
                SOperationHistory.add(currentOperation);
                break;
            case "i":
                currentOperation.setFlavor("INDEPENDENT");
                IOperationHistory.add(currentOperation);
                break;
        }
        currentOperation = new JsonFormatForOperation();
    }

}
