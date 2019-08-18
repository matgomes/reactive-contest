package com.reactivecontest.ranking.service;

import com.reactivecontest.ranking.dto.ScoreRequest;
import com.reactivecontest.ranking.repository.RedisRankRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Slf4j
@AllArgsConstructor
@Service
public class ScoreService {

    private RedisRankRepository repository;

    public Mono<Boolean> saveScore(ScoreRequest request){
        log.info("Saving score [user={}, score={}]", request.getUserId(), request.getScore());

        return getScore(request.getContestId(), request.getUserId())
                .map(current -> request.getScore() > current).defaultIfEmpty(true).flatMap(higher -> {

                    if(higher) {
                        return repository.add(request.getContestId(), request.getUserId(), request.getScore());
                    }

                    log.info("Failed saving score, requested is not higher than current [user={}]", request.getUserId());
                    return Mono.empty();
                });
    }

    public Flux<TypedTuple<String>> getScores(String key){
        log.info("Getting scores [key={}]", key);
        return repository.reverseRangeWithScores(key, 0L, 9L);
    }

    private Mono<Long> getPosition(String key, String userId){
        return repository.reverseRank(key, userId);
    }

    private Mono<Double> getScore(String key, String userId){
        return repository.score(key, userId);
    }

    public Mono<Tuple2<Double, Long>> getSingleScore(String key, String userId){
        log.info("Getting single score [key={}, userId={}]", key, userId);

        Mono<Double> flux = getScore(key, userId);
        Mono<Long> mono = getPosition(key, userId);

        return Mono.zip(flux, mono);
    }


}
