package com.reactivecontest.ranking.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
public class ScoreRequest {

    @NotEmpty
    private String contestId;

    @NotEmpty
    private UUID userId;

    @NotEmpty
    private Long score;
}
