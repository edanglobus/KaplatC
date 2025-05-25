package com.example.KaplatC.apis;


import com.example.KaplatC.service.LogsService;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/logs")
public class LogsController {

    @GetMapping("/level")
    public ResponseEntity<Map<String, String>> getLogLevel(@RequestParam String logger) {
        Map<String, String> response = LogsService.getLoggerLevel(logger);
        if(response.containsKey("Success")) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(404).body(response);
    }

    @PutMapping("/level")
    public ResponseEntity<Map<String, String>> setLogLevel(@RequestParam String logger, @RequestParam String level ) {
        Map<String, String> response = LogsService.setLoggerLevel(logger, level);
        if(response.containsKey("Success")) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(404).body(response);
    }
}
