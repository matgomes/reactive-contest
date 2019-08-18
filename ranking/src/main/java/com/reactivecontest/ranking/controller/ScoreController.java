package com.reactivecontest.ranking.controller;

import com.reactivecontest.ranking.dto.ScoreRequest;
import com.reactivecontest.ranking.dto.ScoreResponse;
import com.reactivecontest.ranking.exception.NotFoundException;
import com.reactivecontest.ranking.service.ScoreService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Flux<ScoreResponse> getContestScores(@PathVariable String contestId){
        return service.getScores(contestId).index((index, typedTuple) -> ScoreResponse.builder()
                                                                                          .score(typedTuple.getScore())
                                                                                          .userId(typedTuple.getValue())
                                                                                          .position(index)
                                                                                      .build())
                      .switchIfEmpty(Flux.error(NotFoundException::new));
    }

    @GetMapping(value = "/score/{contestId}/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<ScoreResponse> getUserScore(@PathVariable String contestId, @PathVariable String userId){
        return service.getSingleScore(contestId, userId).map(tuple -> ScoreResponse.builder()
                                                                                       .score(tuple.getT1())
                                                                                       .userId(userId)
                                                                                       .position(tuple.getT2())
                                                                                   .build())
                      .switchIfEmpty(Mono.error(NotFoundException::new));
    }

}
