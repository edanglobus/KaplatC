package com.example.KaplatC;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class JsonFormatForOperation {
    private String flavor;
    private String operation;
    private List<Double> arguments = new ArrayList<>();
    private Double result;
}
