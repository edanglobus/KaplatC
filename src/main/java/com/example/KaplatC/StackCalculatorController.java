package com.example.KaplatC;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/calculator/stack")
public class StackCalculatorController {
    private final Calculator calculator;

    public StackCalculatorController(Calculator calculator) {
        this.calculator = calculator;
    }

    @GetMapping("/size")
    public ResponseEntity<ResponseJsonFormat> getStackSize() {
        ResponseJsonFormat response = new ResponseJsonFormat();
        response.setResult(calculator.getStackSize());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/arguments")
    public ResponseEntity<ResponseJsonFormat> pushArgsToStack(@RequestBody StackRequest reqBody) {
        ResponseJsonFormat response = new ResponseJsonFormat();
        List<Double> argumentsList = reqBody.getArguments();
        Double result = calculator.pushArgsToStack(argumentsList);
        response.setResult(result);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/arguments")
    public ResponseEntity<ResponseJsonFormat> stackRemoveArgs(@RequestParam int count) {
        ResponseJsonFormat response = new ResponseJsonFormat();
        try {
            Double result = calculator.popStackByCount(count);
            response.setResult(result);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            response.setErrorMessage(e.getMessage());
            return  ResponseEntity.status(409).body(response);
        }
    }


    @GetMapping("/operate")
    public ResponseEntity<ResponseJsonFormat> stackOperation(@RequestParam String operation) {
        ResponseJsonFormat response = new ResponseJsonFormat();
        try {
            Double result = calculator.performeStackOperation(operation);
            response.setResult(result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setErrorMessage(e.getMessage());
            return ResponseEntity.status(409).body(response);
        }
    }

}
