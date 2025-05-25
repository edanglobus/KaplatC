package com.example.KaplatC.apis;

import com.example.KaplatC.historydb.AppHistoryManager;
import com.example.KaplatC.formats.JsonFormatForOperation;
import jakarta.servlet.http.HttpServlet;
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
public class CalculatorController extends BaseController {
    AppHistoryManager history;

    @Autowired
    public CalculatorController(@Qualifier("history") AppHistoryManager history) {
        this.history = history;
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        long startTime = writeLoggerInfo("/calculator/health", "GET");
        writeLoggerDebug(startTime);
        return ResponseEntity.ok("OK"); // Returns 200 OK with the string "OK"
    }

    @GetMapping("/history")
    public ResponseEntity<List<JsonFormatForOperation>> fetchHistory(@RequestParam String flavor) {
        long startTime = writeLoggerInfo("/calculator/history", "GET");

        List<JsonFormatForOperation> answer;
        flavor = flavor.toUpperCase();
        if(flavor.equals("STACK")) {
            answer = history.getStackHistory();
        }
        else if (flavor.equals("INDEPENDENT")) {
            answer = history.getIndependentHistory();
        }
        else {
            answer = history.getAllHistory();
        }

        writeLoggerDebug(startTime);
        return ResponseEntity.ok(answer);

    }



}
