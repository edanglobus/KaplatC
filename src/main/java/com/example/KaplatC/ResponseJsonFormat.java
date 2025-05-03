package com.example.KaplatC;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseJsonFormat {
    private Double result;
    private String errorMessage;

    @JsonIgnore
    private int code = 200;

    public ResponseJsonFormat(int code, double result, String errorMessage) {
        this.result = result;
        this.errorMessage = errorMessage;
        this.code = code;
    }
    public ResponseJsonFormat(double result) {
        this.result = result;
    }
}
