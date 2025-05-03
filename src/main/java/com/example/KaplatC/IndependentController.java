package com.example.KaplatC;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/calculator/independent")
public class IndependentController {




    @PostMapping("/calculate")
    public ResponseEntity<ResponseJsonFormat> calculate(@RequestBody IndependentRequest reqObj) {
        ResponseJsonFormat response = new ResponseJsonFormat();
        List<Double> args = reqObj.getArguments();

        try{
            Calculator result = new Calculator(reqObj.getOperation(), args);
            response.setResult(result.value());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setErrorMessage(e.getMessage());
            return ResponseEntity.status(409).body(response);
        }


    }
}
