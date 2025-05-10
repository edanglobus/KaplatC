package com.example.KaplatC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/calculator")
public class CalculatorController {
    AppHistoryManager history;

    @Autowired
    public CalculatorController(@Qualifier("history") AppHistoryManager history) {
        this.history = history;
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("OK"); // Returns 200 OK with the string "OK"
    }

    @GetMapping("/history")
    public ResponseEntity<List<JsonFormatForOperation>> fetchHistory(@RequestParam String flavor) {
        if(flavor.equals("STACK")) {
            return ResponseEntity.ok(history.getStackHistory());
        }
        else if (flavor.equals("INDEPENDENT")) {
            return ResponseEntity.ok(history.getIndependentHistory());
        }
        else {
            return ResponseEntity.ok(history.getAllHistory());
        }

    }



}
