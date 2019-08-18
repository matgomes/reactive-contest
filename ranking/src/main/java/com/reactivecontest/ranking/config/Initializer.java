package com.reactivecontest.ranking.config;

import com.reactivecontest.ranking.document.Contest;
import com.reactivecontest.ranking.repository.ContestRepository;
import com.reactivecontest.ranking.repository.RedisRankRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
@Qualifier
@Profile("!test")
@AllArgsConstructor
public class Initializer implements CommandLineRunner {

    private ContestRepository contestRepository;
    private RedisRankRepository rankRepository;

    @Override
    public void run(String... args) {
        setup();
    }

    private void setup() {

        Contest contest = new Contest();
        contest.setName("Contest");
        contest.setDescription("Description");

        Mono<Contest> mono = contestRepository.save(contest);

        mono.map(Contest::getId)
                .flatMapMany(id -> Flux.range(1, 10)
                        .flatMap(index -> rankRepository.add(id, UUID.randomUUID().toString(), 10 + index)))
                .subscribe();
    }

}
