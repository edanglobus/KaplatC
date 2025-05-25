package com.example.KaplatC.apis;
import com.example.KaplatC.service.Calculator;
import com.example.KaplatC.formats.ResponseJsonFormat;
import com.example.KaplatC.formats.StackRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/calculator/stack")
public class StackCalculatorController extends BaseController{
    private final Calculator calculator;

    public StackCalculatorController(Calculator calculator) {
        this.calculator = calculator;
    }

    @GetMapping("/size")
    public ResponseEntity<ResponseJsonFormat> getStackSize() {
        long startTime = writeLoggerInfo("/calculator/stack/size", "GET");

        ResponseJsonFormat response = new ResponseJsonFormat();
        response.setResult(calculator.getStackSize());

        writeLoggerDebug(startTime);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/arguments")
    public ResponseEntity<ResponseJsonFormat> pushArgsToStack(@RequestBody StackRequest reqBody) {
        long startTime = writeLoggerInfo("/calculator/stack/arguments", "PUT");

        ResponseJsonFormat response = new ResponseJsonFormat();
        List<Double> argumentsList = reqBody.getArguments();
        Double result = calculator.pushArgsToStack(argumentsList);
        response.setResult(result);

        writeLoggerDebug(startTime);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/arguments")
    public ResponseEntity<ResponseJsonFormat> stackRemoveArgs(@RequestParam int count) {
        long startTime = writeLoggerInfo("/calculator/stack/arguments", "DELETE");

        ResponseJsonFormat response = new ResponseJsonFormat();
        try {
            Double result = calculator.popStackByCount(count);
            response.setResult(result);

            writeLoggerDebug(startTime);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            response.setErrorMessage(e.getMessage());
            writeLoggerError(e.getMessage());
            return  ResponseEntity.status(409).body(response);
        }
    }


    @GetMapping("/operate")
    public ResponseEntity<ResponseJsonFormat> stackOperation(@RequestParam String operation) {
        long startTime = writeLoggerInfo("/calculator/stack/operate", "GET");

        ResponseJsonFormat response = new ResponseJsonFormat();
        try {
            Double result = calculator.performeStackOperation(operation);
            response.setResult(result);

            writeLoggerDebug(startTime);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setErrorMessage(e.getMessage());
            writeLoggerError(e.getMessage());
            return ResponseEntity.status(409).body(response);
        }
    }

}
