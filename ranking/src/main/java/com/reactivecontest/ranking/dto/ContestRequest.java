package com.reactivecontest.ranking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContestRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

}
