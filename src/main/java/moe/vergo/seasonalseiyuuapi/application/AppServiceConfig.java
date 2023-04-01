package moe.vergo.seasonalseiyuuapi.application;

import moe.vergo.seasonalseiyuuapi.adapter.in.web.cache.CacheManager;
import moe.vergo.seasonalseiyuuapi.adapter.in.web.dto.CurrentSeasonSummaryItemDto;
import moe.vergo.seasonalseiyuuapi.application.port.in.GenerateCurrentSeasonSummaryUseCase;
import moe.vergo.seasonalseiyuuapi.application.port.in.GenerateSeiyuuDetailsUseCase;
import moe.vergo.seasonalseiyuuapi.application.port.out.AnimeCharactersPort;
import moe.vergo.seasonalseiyuuapi.application.port.out.SeasonalAnimePort;
import moe.vergo.seasonalseiyuuapi.application.port.out.SeiyuuPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AppServiceConfig {
    @Bean
    public GenerateCurrentSeasonSummaryUseCase generateCurrentSeasonSummaryUseCase(AnimeCharactersPort animeCharactersPort, SeasonalAnimePort seasonalAnimePort, SeiyuuPort seiyuuPort, CacheManager<List<CurrentSeasonSummaryItemDto>> seasonSummaryCacheManager) {
        return new GenerateCurrentSeasonSummaryService(animeCharactersPort, seasonalAnimePort, seiyuuPort, seasonSummaryCacheManager);
    }

    @Bean
    public GenerateSeiyuuDetailsUseCase generateSeiyuuDetailsUseCase(GenerateCurrentSeasonSummaryUseCase generateCurrentSeasonSummaryUseCase) {
        return new GenerateSeiyuuDetailsService(generateCurrentSeasonSummaryUseCase);
    }
}
