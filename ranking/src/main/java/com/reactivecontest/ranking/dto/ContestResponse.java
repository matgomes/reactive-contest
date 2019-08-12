package com.reactivecontest.ranking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContestResponse {

    private String id;
    private String name;
    private String description;
}
