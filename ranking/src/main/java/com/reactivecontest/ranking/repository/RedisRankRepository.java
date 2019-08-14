package com.reactivecontest.ranking.repository;

import org.springframework.data.redis.core.ZSetOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RedisRankRepository {

    Mono<Boolean> add(String key, String value, double score);

    Flux<ZSetOperations.TypedTuple<String>> revRangeWithScores(String key, Long rangeFrom, Long rangeTo);

    Mono<Long> revRank(String key, String userId);
}
