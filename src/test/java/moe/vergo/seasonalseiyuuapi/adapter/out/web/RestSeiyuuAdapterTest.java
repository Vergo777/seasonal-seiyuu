package moe.vergo.seasonalseiyuuapi.adapter.out.web;

import io.netty.handler.codec.http.HttpMethod;
import moe.vergo.seasonalseiyuuapi.adapter.out.web.httpinterface.SeiyuuClient;
import moe.vergo.seasonalseiyuuapi.application.exception.GetSeiyuuDetailsException;
import moe.vergo.seasonalseiyuuapi.domain.Role;
import moe.vergo.seasonalseiyuuapi.domain.Seiyuu;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.MediaType;
import org.mockserver.verify.VerificationTimes;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import com.google.common.util.concurrent.RateLimiter;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

// only web reference that currently exists for testing new HTTP Interface in Spring 6 is via MockServer
// https://www.baeldung.com/spring-6-http-interface
public class RestSeiyuuAdapterTest {
    private static final int PORT = 1080;
    private static ClientAndServer mockServer;

    private RestSeiyuuAdapter underTest;

    private RateLimiter rateLimiterMock;

    @BeforeAll
    public static void startServer() {
        mockServer = startClientAndServer(PORT);
    }

    @BeforeEach
    public void setUp() {
        rateLimiterMock = Mockito.mock(RateLimiter.class);
        Mockito.when(rateLimiterMock.acquire()).thenReturn(1d);
    }

    @Test
    public void testGetSeiyuuDetailsReturnsSeiyuuRecordFromEndpoint() throws IOException {
        // Given
        createMockServerClient()
                .when(
                        request()
                                .withPath("/people/1/full")
                                .withMethod(HttpMethod.GET.name()),
                        exactly(1)
                ).respond(
                        response()
                                .withStatusCode(HttpStatus.SC_OK)
                                .withContentType(MediaType.APPLICATION_JSON)
                                .withBody(WebAdapterTestHelper.classpathFileToString("moe/vergo/seasonalseiyuuapi/adapter/out/web/RestSeiyuuAdapterTest_ID1.json"))
                );

        SeiyuuClient seiyuuClient = createTestSeiyuuClient();

        underTest = new RestSeiyuuAdapter(seiyuuClient, rateLimiterMock);

        // When
        Seiyuu actualResult = underTest.getSeiyuuDetails(1);

        // Then
        Seiyuu expectedResult = createExpectedReturnValue(1);

        assertThat(actualResult)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    public void testGetSeiyuuDetailsThrowsGetSeiyuuDetailsExceptionAfterThreeFailedAttempts() {
        // Given
        createMockServerClient()
                .when(
                        request()
                                .withPath("/people/2/full")
                                .withMethod(HttpMethod.GET.name()),
                        exactly(3)
                ).respond(
                        response()
                                .withStatusCode(HttpStatus.SC_SERVICE_UNAVAILABLE)
                );

        SeiyuuClient seiyuuClient = createTestSeiyuuClient();

        underTest = new RestSeiyuuAdapter(seiyuuClient, rateLimiterMock);

        // Then
        assertThrows(
                GetSeiyuuDetailsException.class,
                () -> underTest.getSeiyuuDetails(2)
        );
    }

    @Test
    public void testGetSeiyuuDetailsReturnsSuccessfullyWithinThreeAttempts() throws IOException {
        // Given
        createMockServerClient()
                .when(
                        request()
                                .withPath("/people/3/full")
                                .withMethod(HttpMethod.GET.name()),
                        exactly(2)
                ).respond(
                        response()
                                .withStatusCode(HttpStatus.SC_SERVICE_UNAVAILABLE)
                );

        createMockServerClient()
                .when(
                        request()
                                .withPath("/people/3/full")
                                .withMethod(HttpMethod.GET.name()),
                        exactly(1)
                ).respond(
                        response()
                                .withStatusCode(HttpStatus.SC_OK)
                                .withContentType(MediaType.APPLICATION_JSON)
                                .withBody(WebAdapterTestHelper.classpathFileToString("moe/vergo/seasonalseiyuuapi/adapter/out/web/RestSeiyuuAdapterTest_ID3.json"))
                );

        SeiyuuClient seiyuuClient = createTestSeiyuuClient();

        underTest = new RestSeiyuuAdapter(seiyuuClient, rateLimiterMock);

        // When
        Seiyuu actualResult = underTest.getSeiyuuDetails(3);

        // Then
        Seiyuu expectedResult = createExpectedReturnValue(3);

        assertThat(actualResult)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);

        createMockServerClient()
                .verify(
                        request()
                                .withPath("/people/3/full"),
                        VerificationTimes.exactly(3)
                );
    }


    private MockServerClient createMockServerClient() {
        return new MockServerClient("localhost", PORT);
    }

    private SeiyuuClient createTestSeiyuuClient() {
        return HttpServiceProxyFactory.builder(
                        WebClientAdapter.forClient(WebClient.builder()
                                .baseUrl("http://localhost:" + PORT)
                                .build())
                )
                .build().createClient(SeiyuuClient.class);
    }

    private Seiyuu createExpectedReturnValue(int seiyuuId) {
        return new Seiyuu(
                seiyuuId,
                "https://myanimelist.net/people/64393/Haruka_Aikawa",
                "https://cdn.myanimelist.net/images/voiceactors/3/74098.jpg",
                "Haruka Aikawa",
                List.of(
                        new Role(
                                "Edomae Elf",
                                "https://myanimelist.net/anime/52081/Edomae_Elf",
                                52081,
                                "Sakuraba, Koma",
                                "https://myanimelist.net/character/225458/Koma_Sakuraba",
                                "https://cdn.myanimelist.net/r/84x124/images/characters/16/504208.jpg?s=d8eec3ccad52727de65fba436340c3a2"
                        )
                )
        );
    }

    @AfterAll
    public static void stopServer() {
        mockServer.stop();
    }
}
