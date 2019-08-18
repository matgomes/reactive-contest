package com.reactivecontest.ranking.service;

import com.reactivecontest.ranking.document.Contest;
import com.reactivecontest.ranking.repository.ContestRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@Service
public class ContestService {

    private ContestRepository repository;

    public Flux<Contest> findAll(){
        log.info("Getting all contests");
        return repository.findAll();
    }

    public Mono<Contest> findById(String id){
        log.info("Getting contests by id, [id={}]", id);
        return repository.findById(id);
    }

    public Mono<Contest> save(Contest contest){
        log.info("Saving contest [{}]", contest);
        return repository.save(contest);
    }

}
