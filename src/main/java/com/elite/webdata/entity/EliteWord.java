package com.elite.webdata.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EliteWord {
    private Long id;

    private String word;

    private String phoneticSymbol;

    private String comment;
}