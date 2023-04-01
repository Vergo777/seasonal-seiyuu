package moe.vergo.seasonalseiyuuapi.adapter.out.web;

import com.google.common.collect.MoreCollectors;
import com.google.common.util.concurrent.RateLimiter;
import moe.vergo.seasonalseiyuuapi.adapter.out.web.dto.AnimeDto;
import moe.vergo.seasonalseiyuuapi.adapter.out.web.dto.AnimeTitlesDto;
import moe.vergo.seasonalseiyuuapi.adapter.out.web.dto.SeasonalAnimeResponseDto;
import moe.vergo.seasonalseiyuuapi.adapter.out.web.helper.WebOutputAdapterHelper;
import moe.vergo.seasonalseiyuuapi.adapter.out.web.httpinterface.SeasonClient;
import moe.vergo.seasonalseiyuuapi.application.port.out.SeasonalAnimePort;
import moe.vergo.seasonalseiyuuapi.domain.Anime;
import moe.vergo.seasonalseiyuuapi.domain.Season;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RestSeasonalAnimeAdapter implements SeasonalAnimePort {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private static final String QUERY_PARAM_PAGE = "page";
    // filter out One Piece and Detective Conan etc.
    private static final Set<Integer> LONG_RUNNING_ANIME = Set.of(
            21, 235, 1199, 6149, 1960
    );

    private final SeasonClient seasonClient;
    private final RateLimiter rateLimiter;

    public RestSeasonalAnimeAdapter(SeasonClient seasonClient, RateLimiter rateLimiter) {
        this.seasonClient = seasonClient;
        this.rateLimiter = rateLimiter;
    }

    @Override
    @Cacheable("seasonalAnimeCache")
    public List<Anime> getSeasonalAnime(int year, Season season) {
        return getSeasonalAnimeFromEndpoint(year, season)
                .filter(dto -> !LONG_RUNNING_ANIME.contains(dto.getMalId()))
                .map(seasonalAnime -> new Anime(
                        seasonalAnime.getMalId(),
                        getDefaultTitleForAnime(seasonalAnime.getTitles()),
                        seasonalAnime.getUrl()
                ))
                .collect(Collectors.toList());
    }

    private Stream<AnimeDto> getSeasonalAnimeFromEndpoint(int year, Season season) {
        LOGGER.info("Making request to seasonal anime endpoint for year {} and season {}", year, season);
        int totalPages = getTotalNumberOfPages(year, season);
        LOGGER.info("Total number of requests to make for fetching all paginated data is {}", totalPages);
        int currentPage = 1;
        Stream<AnimeDto> fullResults = Stream.empty();

        while(currentPage <= totalPages) {
            LOGGER.info("Making request {} of {}", currentPage, totalPages);
            rateLimiter.acquire();
            SeasonalAnimeResponseDto response = seasonClient.getByYearAndSeason(year, season.toString(), Map.of(QUERY_PARAM_PAGE, currentPage));
            fullResults = Stream.concat(fullResults, response.getData().stream());
            currentPage++;
        }

        return fullResults;
    }

    private int getTotalNumberOfPages(int year, Season season) {
        rateLimiter.acquire();
        SeasonalAnimeResponseDto response = seasonClient.getByYearAndSeason(year, season.toString(), Map.of(QUERY_PARAM_PAGE, 1));
        return WebOutputAdapterHelper.getTotalNumberOfPages(response.getPagination());
    }

    private String getDefaultTitleForAnime(List<AnimeTitlesDto> animeTitles) {
        return animeTitles.stream()
                .filter(t -> "Default".equals(t.getType()))
                .map(AnimeTitlesDto::getTitle)
                .collect(MoreCollectors.onlyElement());
    }
}
