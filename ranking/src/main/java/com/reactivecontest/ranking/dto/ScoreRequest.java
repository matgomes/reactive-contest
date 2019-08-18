package com.reactivecontest.ranking.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ScoreRequest {

    @NotEmpty
    private String contestId;

    @NotEmpty
    private String userId;

    @NotNull
    private Long score;
}
