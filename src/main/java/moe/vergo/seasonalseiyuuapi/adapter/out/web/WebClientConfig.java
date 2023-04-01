package moe.vergo.seasonalseiyuuapi.adapter.out.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.RateLimiter;
import moe.vergo.seasonalseiyuuapi.adapter.out.web.httpinterface.AnimeClient;
import moe.vergo.seasonalseiyuuapi.adapter.out.web.httpinterface.SeasonClient;
import moe.vergo.seasonalseiyuuapi.adapter.out.web.httpinterface.SeiyuuClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
public class WebClientConfig {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Bean
    public WebClient webClient(ObjectMapper objectMapper) {
        return WebClient.builder()
                .baseUrl(JikanApi.API_BASE_ENDPOINT)
                // https://stackoverflow.com/a/59392022
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer
                                .defaultCodecs()
                                .maxInMemorySize(16 * 1024 * 1024))
                        .build())
                // .filter(retryFilter())
                .build();
    }

    @Bean
    public SeasonClient seasonClient(WebClient webClient) {
        return createHttpServiceProxyFactory(webClient).createClient(SeasonClient.class);
    }

    @Bean
    public AnimeClient animeClient(WebClient webClient) {
        return createHttpServiceProxyFactory(webClient).createClient(AnimeClient.class);
    }

    @Bean
    public SeiyuuClient seiyuuClient(WebClient webClient) {
        return createHttpServiceProxyFactory(webClient).createClient(SeiyuuClient.class);
    }

    private HttpServiceProxyFactory createHttpServiceProxyFactory (WebClient webClient) {
        return HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient))
                // https://stackoverflow.com/a/75527082
                .blockTimeout(Duration.ofSeconds(200))
                .build();
    }

    @Bean
    public RateLimiter rateLimiter() {
        return RateLimiter.create(1);
    }
}
