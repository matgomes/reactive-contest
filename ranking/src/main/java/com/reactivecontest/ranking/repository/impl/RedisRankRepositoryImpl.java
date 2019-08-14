package com.reactivecontest.ranking.repository.impl;

import com.reactivecontest.ranking.repository.RedisRankRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.core.ReactiveZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.domain.Range.Bound.exclusive;
import static org.springframework.data.domain.Range.Bound.inclusive;

@AllArgsConstructor
@Repository
public class RedisRankRepositoryImpl implements RedisRankRepository {

    private ReactiveZSetOperations<String, String> zSetOps;

    @Override
    public Mono<Boolean> add(String key, String value, double score) {
        return zSetOps.add(key, value, score);
    }

    @Override
    public Flux<TypedTuple<String>> revRangeWithScores(String key, Long rangeFrom, Long rangeTo) {
        return zSetOps.reverseRangeWithScores(key, Range.from(inclusive(rangeFrom)).to(exclusive(rangeTo)));
    }

    @Override
    public Mono<Long> revRank(String key, String userId) {
        return zSetOps.reverseRank(key, userId);
    }
}
