package com.example.KaplatCalculatorApp.service;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Operator {
    private String strOp;
    private String Kind;
    private char Operator;
    String reqCount;

    public Operator(String strOp) {
        strOp = strOp.toLowerCase();
        this.strOp = strOp;
        switch (strOp) {
            case "plus":
                this.Kind = "binary";
                this.Operator = '+';
                this.reqCount = "two";
                break;
            case "minus":
                this.Kind = "binary";
                this.Operator = '-';
                this.reqCount = "two";
                break;
            case "times":
                this.Kind = "binary";
                this.Operator = '*';
                this.reqCount = "two";
                break;
            case "divide":
                this.Kind = "binary";
                this.Operator = '/';
                this.reqCount = "two";
                break;
            case "pow":
                this.Kind = "binary";
                this.Operator = '^';
                this.reqCount = "two";
                break;
            case "abs":
                this.Kind = "unary";
                this.Operator = '|';
                this.reqCount = "one";
                break;
            case "fact":
                this.Kind = "unary";
                this.Operator = '!';
                this.reqCount = "one";
                break;
            default:
                this.Kind = "none";
                this.Operator = ' ';
        }
    }
}
