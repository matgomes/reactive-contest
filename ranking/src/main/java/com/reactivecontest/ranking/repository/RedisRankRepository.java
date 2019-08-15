package com.reactivecontest.ranking.repository;

import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RedisRankRepository {

    Mono<Boolean> add(String key, String value, double score);

    Flux<TypedTuple<String>> reverseRangeWithScores(String key, Long rangeFrom, Long rangeTo);

    Mono<Long> reverseRank(String key, String member);

    Mono<Double> score(String key, String member);
}
