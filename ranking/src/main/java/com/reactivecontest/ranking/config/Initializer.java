package com.reactivecontest.ranking.config;

import com.reactivecontest.ranking.document.Contest;
import com.reactivecontest.ranking.repository.ContestRepository;
import com.reactivecontest.ranking.repository.RedisRankRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;

@Slf4j
@Component
@Qualifier
@Profile("!test")
public class Initializer implements CommandLineRunner {

    @Autowired
    ContestRepository repository;

    @Autowired
    RedisRankRepository rankRepository;

    @Override
    public void run(String... args) {
        setup();
    }

    private void setup() {

        Contest contest = new Contest();
        contest.setName("Contest");
        contest.setDescription("Description");

        Mono<Contest> mono = repository.save(contest);

        mono.map(Contest::getId).flatMapMany(a -> Flux.interval(Duration.ofMillis(5))
                                                      .take(10)
                                                      .flatMap(b -> rankRepository.add(a, UUID.randomUUID().toString(), 10 + b))
        ).subscribe();

    }

}
