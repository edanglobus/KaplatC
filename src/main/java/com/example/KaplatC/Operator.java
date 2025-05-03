package com.example.KaplatC;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Operator {
    private String Kind;
    private String strOp;
    private char Operator;

    public Operator(String strOp) {
        strOp = strOp.toLowerCase();
        this.strOp = strOp;
        switch (strOp) {
            case "plus":
                this.Kind = "binary";
                this.Operator = '+';
                break;
            case "minus":
                this.Kind = "binary";
                this.Operator = '-';
                break;
            case "times":
                this.Kind = "binary";
                this.Operator = '*';
                break;
            case "divide":
                this.Kind = "binary";
                this.Operator = '/';
                break;
            case "pow":
                this.Kind = "binary";
                this.Operator = '^';
                break;
            case "abs":
                this.Kind = "unary";
                this.Operator = '|';
                break;
            case "fact":
                this.Kind = "unary";
                this.Operator = '!';
                break;
            default:
                this.Kind = "none";
                this.Operator = ' ';
        }
    }
}
