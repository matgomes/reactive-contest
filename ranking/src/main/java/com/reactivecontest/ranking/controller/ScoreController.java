package com.reactivecontest.ranking.controller;

import com.reactivecontest.ranking.dto.ScoreRequest;
import com.reactivecontest.ranking.dto.ScoreResponse;
import com.reactivecontest.ranking.service.ScoreService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
public class ScoreController {

    private ScoreService service;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "/score")
    public void save(@RequestBody @Valid ScoreRequest request){
        service.saveScore(request).subscribe();
    }

    @GetMapping(value = "/score/{contestId}", produces = {MediaType.APPLICATION_STREAM_JSON_VALUE})
    public Flux<ScoreResponse> get(@PathVariable String contestId){
        return service.getScoreRank(contestId).map(tuple -> ScoreResponse.builder()
                                                                            .score(tuple.getScore())
                                                                            .userId(tuple.getValue())
                                                                         .build());
    }

}
