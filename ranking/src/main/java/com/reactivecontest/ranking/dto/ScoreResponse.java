package com.reactivecontest.ranking.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ScoreResponse {

    private String userId;
    private Double score;
}
