package com.example.KaplatC;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter @Setter
public class AppHistoryManager {
    ObjectMapper mapper = new ObjectMapper();
    private List<JsonFormatForOperation> OperationHistory = new ArrayList<>();
    private JsonFormatForOperation currentOperation = new JsonFormatForOperation();


    public static class JsonFormatForOperation {
        private String flavor;
        private String operation;
        private List<Double> arguments = new ArrayList<>();
        private Double result;
    }

    public void writeFlavor(String flavor) {
        switch(flavor) {
            case "STACK":
            case "INDEPENDENT":
                currentOperation.flavor = flavor;
                break;
            case "":
                currentOperation.flavor = "BOTH";
                break;
            default:
                throw new IllegalArgumentException("Error: Query parameter \"flavor\" wasn't supplied right");
        }
    }

    public void writeOperation(Operator operator) {
        currentOperation.operation = operator.getStrOp().toLowerCase();
    }

    public void writeArgument(Double arg) {
        currentOperation.arguments.addLast(arg);
    }

    public void writeResult(Double result) {
        currentOperation.result = result;
    }

    public String ConvertCurrentOpToJson() throws JsonProcessingException {
        return mapper.writeValueAsString(currentOperation);
    }

    public void addToHistory() throws JsonProcessingException {
        OperationHistory.addLast(currentOperation);
        currentOperation.arguments.clear();
    }

}
