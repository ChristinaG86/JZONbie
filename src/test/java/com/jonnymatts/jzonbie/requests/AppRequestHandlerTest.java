package com.jonnymatts.jzonbie.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flextrade.jfixture.annotations.Fixture;
import com.flextrade.jfixture.rules.FixtureRule;
import com.google.common.collect.Multimap;
import com.jonnymatts.jzonbie.model.ZombiePriming;
import com.jonnymatts.jzonbie.model.ZombieRequest;
import com.jonnymatts.jzonbie.model.ZombieRequestFactory;
import com.jonnymatts.jzonbie.model.ZombieResponse;
import com.jonnymatts.jzonbie.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppRequestHandlerTest {

    @Rule public FixtureRule fixtureRule = FixtureRule.initFixtures();

    @Rule public ExpectedException expectedException = ExpectedException.none();

    @Mock private Multimap<ZombieRequest, ZombieResponse> primingContext;

    @Mock private List<ZombiePriming> callHistory;

    @Mock private ZombieRequestFactory zombieRequestFactory;

    @Mock private Request request;

    @Fixture private ZombiePriming ZombiePriming;

    private AppRequestHandler appRequestHandler;

    private ZombieRequest zombieRequest;

    private ZombieResponse zombieResponse;

    @Before
    public void setUp() throws Exception {
        appRequestHandler = new AppRequestHandler(primingContext, callHistory, zombieRequestFactory);

        zombieRequest = ZombiePriming.getZombieRequest();
        zombieResponse = ZombiePriming.getZombieResponse();

        when(zombieRequestFactory.create(request)).thenReturn(zombieRequest);
        when(primingContext.get(zombieRequest))
                .thenReturn(singletonList(zombieResponse));
    }

    @Test
    public void handleReturnsPrimedResponseIfPrimingKeyExistsInPrimingContext() throws JsonProcessingException {
        final Response got = appRequestHandler.handle(request);

        assertThat(got.getStatusCode()).isEqualTo(zombieResponse.getStatusCode());
        assertThat(got.getHeaders()).containsAllEntriesOf(zombieResponse.getHeaders());
        assertThat(got.getBody()).isEqualTo(zombieResponse.getBody());

        verify(primingContext).remove(zombieRequest, zombieResponse);
    }

    @Test
    public void handleAddsPrimingRequestToCallHistory() throws JsonProcessingException {
        appRequestHandler.handle(request);

        verify(callHistory).add(ZombiePriming);
    }

    @Test
    public void handleReturnsErrorResponseIfPrimingIsNotFound() throws Exception {
        when(primingContext.get(zombieRequest)).thenReturn(emptyList());

        expectedException.expect(PrimingNotFoundException.class);
        expectedException.expect(Matchers.hasProperty("request", equalTo(zombieRequest)));

        appRequestHandler.handle(request);
    }
}