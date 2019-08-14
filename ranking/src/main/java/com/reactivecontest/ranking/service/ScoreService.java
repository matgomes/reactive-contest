package com.reactivecontest.ranking.service;

import com.reactivecontest.ranking.dto.ScoreRequest;
import com.reactivecontest.ranking.repository.RedisRankRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class ScoreService {

    private RedisRankRepository repository;

    public Mono<Boolean> saveScore(ScoreRequest request){
        return repository.add(request.getContestId(), request.getUserId().toString(), request.getScore());
    }

    public Flux<TypedTuple<String>> getScoreRank(String key){
        return repository.revRangeWithScores(key, 0L, 10L);
    }


}
