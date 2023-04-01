package moe.vergo.seasonalseiyuuapi.adapter.out.web;

import com.google.common.util.concurrent.RateLimiter;
import moe.vergo.seasonalseiyuuapi.adapter.out.web.httpinterface.AnimeClient;
import moe.vergo.seasonalseiyuuapi.adapter.out.web.httpinterface.SeasonClient;
import moe.vergo.seasonalseiyuuapi.adapter.out.web.httpinterface.SeiyuuClient;
import moe.vergo.seasonalseiyuuapi.application.port.out.AnimeCharactersPort;
import moe.vergo.seasonalseiyuuapi.application.port.out.SeasonalAnimePort;
import moe.vergo.seasonalseiyuuapi.application.port.out.SeiyuuPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebAdapterConfig {
    @Bean
    public SeasonalAnimePort seasonalAnimePort(SeasonClient seasonClient, RateLimiter rateLimiter) {
        return new RestSeasonalAnimeAdapter(seasonClient, rateLimiter);
    }

    @Bean
    public AnimeCharactersPort animeCharactersPort(AnimeClient animeClient, RateLimiter rateLimiter) {
        return new RestAnimeCharactersAdapter(animeClient, rateLimiter);
    }

    @Bean
    public SeiyuuPort seiyuuPort(SeiyuuClient seiyuuClient, RateLimiter rateLimiter) {
        return new RestSeiyuuAdapter(seiyuuClient, rateLimiter);
    }
}
