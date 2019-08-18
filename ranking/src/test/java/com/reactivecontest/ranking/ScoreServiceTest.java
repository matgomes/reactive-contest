package com.reactivecontest.ranking;


import com.reactivecontest.ranking.dto.ScoreRequest;
import com.reactivecontest.ranking.repository.RedisRankRepository;
import com.reactivecontest.ranking.service.ScoreService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ScoreServiceTest {

    @Mock
    RedisRankRepository repository;

    @InjectMocks
    ScoreService service;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSaveScoreIfNotExists(){

        ScoreRequest request = new ScoreRequest();
        request.setContestId("contestId");
        request.setUserId("userId");
        request.setScore(10L);

        when(repository.score(anyString(), anyString())).thenReturn(Mono.empty());
        when(repository.add(anyString(), anyString(), anyDouble())).thenReturn(Mono.just(true));

        Mono<Boolean> mono = service.saveScore(request);

        StepVerifier.create(mono).expectSubscription().expectNext(true).verifyComplete();

        verify(repository, times(1)).add(anyString(), anyString(), anyDouble());
    }

    @Test
    public void shouldSaveScoreIfRequestedScoreIsHigher(){

        ScoreRequest request = new ScoreRequest();
        request.setContestId("contestId");
        request.setUserId("userId");
        request.setScore(10L);

        when(repository.score(anyString(), anyString())).thenReturn(Mono.just(1.0));
        when(repository.add(anyString(), anyString(), anyDouble())).thenReturn(Mono.just(true));

        Mono<Boolean> mono = service.saveScore(request);

        StepVerifier.create(mono).expectSubscription().expectNext(true).verifyComplete();

        verify(repository, times(1)).add(anyString(), anyString(), anyDouble());

    }

    @Test
    public void shouldNotSaveScoreIfRequestedScoreIsLower(){

        ScoreRequest request = new ScoreRequest();
        request.setContestId("contestId");
        request.setUserId("userId");
        request.setScore(1L);

        when(repository.score(anyString(), anyString())).thenReturn(Mono.just(10.0));

        Mono<Boolean> mono = service.saveScore(request);

        StepVerifier.create(mono).expectSubscription().verifyComplete();

        verify(repository, never()).add(anyString(), anyString(), anyDouble());

    }

}
