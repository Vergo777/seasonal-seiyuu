package moe.vergo.seasonalseiyuuapi.application;

import moe.vergo.seasonalseiyuuapi.adapter.in.web.cache.CacheManager;
import moe.vergo.seasonalseiyuuapi.adapter.in.web.cache.CacheService;
import moe.vergo.seasonalseiyuuapi.adapter.in.web.dto.CurrentSeasonSummaryItemDto;
import moe.vergo.seasonalseiyuuapi.adapter.in.web.dto.RolesDto;
import moe.vergo.seasonalseiyuuapi.application.port.in.GenerateCurrentSeasonSummaryUseCase;
import moe.vergo.seasonalseiyuuapi.application.port.out.AnimeCharactersPort;
import moe.vergo.seasonalseiyuuapi.application.port.out.SeasonalAnimePort;
import moe.vergo.seasonalseiyuuapi.application.port.out.SeiyuuPort;
import moe.vergo.seasonalseiyuuapi.domain.Character;
import moe.vergo.seasonalseiyuuapi.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GenerateCurrentSeasonSummaryService implements GenerateCurrentSeasonSummaryUseCase {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private static final int CURRENT_SEASON_DB_CACHE_KEY = 0;

    private final AnimeCharactersPort animeCharactersPort;
    private final SeasonalAnimePort seasonalAnimePort;
    private final SeiyuuPort seiyuuPort;

    private final CacheManager<List<CurrentSeasonSummaryItemDto>> seasonSummaryCacheManager;

    public GenerateCurrentSeasonSummaryService(AnimeCharactersPort animeCharactersPort, SeasonalAnimePort seasonalAnimePort, SeiyuuPort seiyuuPort, CacheManager<List<CurrentSeasonSummaryItemDto>> seasonSummaryCacheManager) {
        this.animeCharactersPort = animeCharactersPort;
        this.seasonalAnimePort = seasonalAnimePort;
        this.seiyuuPort = seiyuuPort;
        this.seasonSummaryCacheManager = seasonSummaryCacheManager;
    }

    @Override
    public List<CurrentSeasonSummaryItemDto> generateCurrentSeasonSummary(int year, Season season, boolean useDbCachedValueIfAvailable) {
        CacheService<List<CurrentSeasonSummaryItemDto>> cacheService = seasonSummaryCacheManager.initialiseCache("seasonSummaryCache");

        if (cacheService.isPresentInCache(CURRENT_SEASON_DB_CACHE_KEY) && useDbCachedValueIfAvailable) {
            return cacheService.getValueFromCache(CURRENT_SEASON_DB_CACHE_KEY);
        }

        LOGGER.info("Generating current season summary for year {} and season {}", year, season);

        List<Anime> seasonalAnime = seasonalAnimePort.getSeasonalAnime(year, season);
        List<Seiyuu> seasonalSeiyuus = fetchSeasonalSeiyuus(seasonalAnime);
        Set<Integer> seasonalAnimeIds = getAllSeasonalAnimeIds(seasonalAnime);

        List<CurrentSeasonSummaryItemDto> result = generateCurrentSeasonSummaryFromSeasonalSeiyuus(seasonalSeiyuus, seasonalAnimeIds);

        cacheService.addValueToCache(CURRENT_SEASON_DB_CACHE_KEY, result);
        return result;
    }

    private List<CurrentSeasonSummaryItemDto> generateCurrentSeasonSummaryFromSeasonalSeiyuus(List<Seiyuu> seasonalSeiyuus, Set<Integer> seasonalAnimeIds) {
        return seasonalSeiyuus.stream()
                .map(seiyuu -> new CurrentSeasonSummaryItemDto(
                        seiyuu.name(),
                        seiyuu.imageUrl(),
                        seiyuu.id(),
                        generateRolesDtoList(seiyuu.roles(), seasonalAnimeIds, true),
                        generateRolesDtoList(seiyuu.roles(), seasonalAnimeIds, false)
                ))
                .collect(Collectors.toList());
    }

    private List<Seiyuu> fetchSeasonalSeiyuus(List<Anime> seasonalAnime) {
        List<Character> allSeasonalCharacters = getAllSeasonalCharacters(seasonalAnime);
        Set<Integer> allSeasonalSeiyuuIds = getAllSeasonalSeiyuuIds(allSeasonalCharacters);

        LOGGER.info("Total number of seiyuus for this season are {}", allSeasonalSeiyuuIds.size());

        return getAllSeasonalSeiyuuDetails(allSeasonalSeiyuuIds);
    }

    private List<Character> getAllSeasonalCharacters(List<Anime> seasonalAnime) {
        return seasonalAnime.stream()
                .flatMap(anime -> animeCharactersPort.getAnimeCharacters(anime.id()).stream())
                .collect(Collectors.toList());
    }

    private Set<Integer> getAllSeasonalSeiyuuIds(List<Character> allSeasonalCharacters) {
        return allSeasonalCharacters.stream()
                .flatMap(c -> c.seiyuuIds().stream())
                .collect(Collectors.toSet());
    }

    private List<Seiyuu> getAllSeasonalSeiyuuDetails(Set<Integer> allSeasonalSeiyuuIds) {
        return allSeasonalSeiyuuIds.stream()
                .map(seiyuuPort::getSeiyuuDetails)
                .collect(Collectors.toList());
    }

    private Set<Integer> getAllSeasonalAnimeIds(List<Anime> seasonalAnime) {
        return seasonalAnime.stream()
                .map(Anime::id)
                .collect(Collectors.toSet());
    }

    private List<RolesDto> generateRolesDtoList(List<Role> roles, Set<Integer> seasonalAnimeIds, boolean applyCurrentSeasonFilter) {
        return roles.stream()
                .filter(role -> applyCurrentSeasonFilter ? seasonalAnimeIds.contains(role.animeId()) : true)
                .map(this::toRolesDto)
                .collect(Collectors.toList());
    }

    private RolesDto toRolesDto(Role role) {
        return new RolesDto(
                role.characterName(),
                role.characterImageUrl(),
                role.animeName(),
                role.animeId()
        );
    }
}
