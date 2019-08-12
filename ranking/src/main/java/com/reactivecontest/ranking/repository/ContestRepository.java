package com.reactivecontest.ranking.repository;

import com.reactivecontest.ranking.document.Contest;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ContestRepository extends ReactiveMongoRepository<Contest, String> {


}
