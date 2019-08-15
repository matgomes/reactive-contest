package com.reactivecontest.ranking.service;

import com.reactivecontest.ranking.dto.ScoreRequest;
import com.reactivecontest.ranking.repository.RedisRankRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@AllArgsConstructor
@Service
public class ScoreService {

    private RedisRankRepository repository;

    public Mono<Boolean> saveScore(ScoreRequest request){
        return repository.add(request.getContestId(), request.getUserId().toString(), request.getScore());
    }

    public Flux<TypedTuple<String>> getScores(String key){
        return repository.reverseRangeWithScores(key, 0L, 9L);
    }

    private Mono<Long> getPosition(String key, String userId){
        return repository.reverseRank(key, userId);
    }

    private Mono<Double> getScore(String key, String userId){
        return repository.score(key, userId);
    }

    public Mono<Tuple2<Double, Long>> getSingleScore(String key, String userId){

        Mono<Double> flux = getScore(key, userId);
        Mono<Long> mono = getPosition(key, userId);

        return Mono.zip(flux, mono);
    }


}
