package com.example.KaplatC.formats;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

import java.util.ArrayList;

@Getter @Setter
public class IndependentRequest {
    private List<Double> arguments = new ArrayList<>();
    private String operation;
}
