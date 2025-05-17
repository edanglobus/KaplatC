package com.example.KaplatC.apis;


import com.example.KaplatC.service.Calculator;
import com.example.KaplatC.formats.IndependentRequest;
import com.example.KaplatC.formats.ResponseJsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/calculator/independent")
public class IndependentCalculatorController {
    private final Calculator calculator;

    @Autowired
    public IndependentCalculatorController(@Qualifier("calc") Calculator calculator) {
        this.calculator = calculator;
    }

    @PostMapping("/calculate")
    public ResponseEntity<ResponseJsonFormat> calculate(@RequestBody IndependentRequest reqBody) {
        ResponseJsonFormat response = new ResponseJsonFormat();
        String opStr = reqBody.getOperation();
        List<Double> args = reqBody.getArguments();
        try{
            Double result = calculator.independentValue(opStr, args);
            response.setResult(result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setErrorMessage(e.getMessage());
            return ResponseEntity.status(409).body(response);
        }
    }


}
