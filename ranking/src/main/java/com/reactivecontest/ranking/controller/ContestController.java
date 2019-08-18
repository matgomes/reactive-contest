package com.reactivecontest.ranking.controller;

import com.github.dozermapper.core.Mapper;
import com.reactivecontest.ranking.document.Contest;
import com.reactivecontest.ranking.dto.ContestRequest;
import com.reactivecontest.ranking.dto.ContestResponse;
import com.reactivecontest.ranking.exception.NotFoundException;
import com.reactivecontest.ranking.service.ContestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
public class ContestController {

    private Mapper mapper;
    private ContestService service;

    @GetMapping("/contest/{id}")
    public Mono<ContestResponse> get(@PathVariable String id){

        return service.findById(id).map(contest -> mapper.map(contest, ContestResponse.class))
                                   .switchIfEmpty(Mono.error(NotFoundException::new));
    }

    @GetMapping("/contest")
    public Flux<ContestResponse> getAll(){

        return service.findAll().map(contest -> mapper.map(contest, ContestResponse.class));
    }

    @PostMapping("/contest")
    public Mono<Contest> save(@Valid @RequestBody Mono<ContestRequest> contestRequestMono){

        return contestRequestMono.map(body -> mapper.map(body, Contest.class))
                                 .flatMap(contest -> service.save(contest));
    }

}
