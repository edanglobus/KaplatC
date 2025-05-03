package com.example.KaplatC;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

import java.util.ArrayList;

@Getter @Setter
public class IndependentRequest {
    private List<Double> arguments = new ArrayList<Double>();
    private String operation;
}
