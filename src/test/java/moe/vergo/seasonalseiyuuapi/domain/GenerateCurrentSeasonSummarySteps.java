package moe.vergo.seasonalseiyuuapi.domain;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import moe.vergo.seasonalseiyuuapi.adapter.in.web.cache.CacheManager;
import moe.vergo.seasonalseiyuuapi.adapter.in.web.cache.CacheService;
import moe.vergo.seasonalseiyuuapi.adapter.in.web.dto.CurrentSeasonSummaryItemDto;
import moe.vergo.seasonalseiyuuapi.adapter.in.web.dto.RolesDto;
import moe.vergo.seasonalseiyuuapi.application.GenerateCurrentSeasonSummaryService;
import moe.vergo.seasonalseiyuuapi.application.port.in.GenerateCurrentSeasonSummaryUseCase;
import moe.vergo.seasonalseiyuuapi.application.port.out.AnimeCharactersPort;
import moe.vergo.seasonalseiyuuapi.application.port.out.SeasonalAnimePort;
import moe.vergo.seasonalseiyuuapi.application.port.out.SeiyuuPort;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class GenerateCurrentSeasonSummarySteps {
    private static final int CURRENT_YEAR = 2023;
    private static final Season CURRENT_SEASON = Season.spring;

    private SeasonalAnimePort seasonalAnimePortMock;

    private AnimeCharactersPort animeCharactersPortMock;

    private SeiyuuPort seiyuuPortMock;

    private CacheManager<List<CurrentSeasonSummaryItemDto>> seasonSummaryCacheManagerMock;

    private CacheService<List<CurrentSeasonSummaryItemDto>> cacheServiceMock;

    private GenerateCurrentSeasonSummaryUseCase underTest;

    private List<CharacterForCucumber> seasonalCharacters;

    private List<SeiyuuForCucumber> seiyuuDetails;

    private List<CurrentSeasonSummaryItemDto> actualResult;

    private List<CurrentSeasonSummaryItemForCucumber> currentSeasonSummary;

    private Map<Integer, List<RolesDtoForCucumber>> seiyuuIdToCurrentSeasonRolesSummary;

    @Before
    public void setupPort(){
        seasonalAnimePortMock = Mockito.mock(SeasonalAnimePort.class);
        animeCharactersPortMock = Mockito.mock(AnimeCharactersPort.class);
        seiyuuPortMock = Mockito.mock(SeiyuuPort.class);
        // casting required as follows: https://stackoverflow.com/a/1652738
        seasonSummaryCacheManagerMock = (CacheManager<List<CurrentSeasonSummaryItemDto>>) Mockito.mock(CacheManager.class);
        cacheServiceMock = (CacheService<List<CurrentSeasonSummaryItemDto>>) Mockito.mock(CacheService.class);

        Mockito.when(seasonSummaryCacheManagerMock.initialiseCache(Mockito.anyString())).thenReturn(cacheServiceMock);
        Mockito.doNothing().when(cacheServiceMock).addValueToCache(Mockito.anyInt(), Mockito.any());

        underTest = new GenerateCurrentSeasonSummaryService(
                animeCharactersPortMock, seasonalAnimePortMock, seiyuuPortMock, seasonSummaryCacheManagerMock
        );
    }

    @Given("the following seasonal anime fetched from the seasonal anime port")
    public void theFollowingSeasonalAnimeFetchedFromTheEndpoint(List<Anime> seasonalAnime) {
        Mockito.when(seasonalAnimePortMock.getSeasonalAnime(CURRENT_YEAR, CURRENT_SEASON)).thenReturn(seasonalAnime);
    }

    @And("the following characters returned from the anime characters port")
    public void theFollowingCharactersReturnedFromTheAnimeCharactersPort(List<CharacterForCucumber> seasonalCharacters) {
        this.seasonalCharacters = seasonalCharacters;
    }

    @And("the following seiyuu IDs for each character returned from the anime characters port")
    public void theFollowingSeiyuuIDsForEachCharacterReturnedFromTheAnimeCharactersPort(List<CharToSeiyuuIdForCucucmber> charToSeiyuuIds) {
        Map<Integer, List<Integer>> charToSeiyuuIdsMap = charToSeiyuuIds.stream()
                .collect(Collectors.groupingBy(CharToSeiyuuIdForCucucmber::charId, Collectors.mapping(CharToSeiyuuIdForCucucmber::seiyuuId, Collectors.toList())));

        List<Character> characters = seasonalCharacters.stream()
                .map(c -> new Character(
                        c.charId,
                        c.name,
                        c.role,
                        c.url,
                        c.imageUrl,
                        charToSeiyuuIdsMap.get(c.charId)
                ))
                .collect(Collectors.toList());

        Map<Integer, Character> charIdToCharRecordMap = characters.stream()
                        .collect(Collectors.toMap(Character::id, Function.identity()));

        Map<Integer, List<Integer>> animeToCharIdsMap = seasonalCharacters.stream()
                        .collect(Collectors.groupingBy(CharacterForCucumber::animeId, Collectors.mapping(CharacterForCucumber::charId, Collectors.toList())));

        for(int animeId: animeToCharIdsMap.keySet()) {
            Mockito.when(animeCharactersPortMock.getAnimeCharacters(animeId)).thenReturn(
                    animeToCharIdsMap.get(animeId).stream()
                            .map(charIdToCharRecordMap::get)
                            .collect(Collectors.toList())
            );
        }
    }

    @And("the following seiyuu details returned from the seiyuu port")
    public void theFollowingSeiyuuDetailsReturnedFromTheSeiyuuPort(List<SeiyuuForCucumber> seiyuuDetails) {
        this.seiyuuDetails = seiyuuDetails;
    }

    @And("the following seiyuu roles returned from the seiyuu port")
    public void theFollowingSeiyuuRolesReturnedFromTheSeiyuuPort(List<RoleForCucumber> seiyuuRoles) {
        Map<Integer, List<RoleForCucumber>> seiyuuIdToRoles = seiyuuRoles.stream()
                .collect(Collectors.groupingBy(RoleForCucumber::seiyuuId, Collectors.mapping(Function.identity(), Collectors.toList())));

        for(SeiyuuForCucumber seiyuu: seiyuuDetails) {
            Mockito.when(seiyuuPortMock.getSeiyuuDetails(seiyuu.seiyuuId())).thenReturn(
                    new Seiyuu(
                            seiyuu.seiyuuId,
                            seiyuu.url,
                            seiyuu.imageUrl,
                            seiyuu.name,
                            seiyuuIdToRoles.get(seiyuu.seiyuuId).stream()
                                    .map(r -> new Role(
                                            r.animeName,
                                            r.animeUrl,
                                            r.animeId,
                                            r.characterName,
                                            r.characterUrl,
                                            r.characterImageUrl
                                    ))
                                    .collect(Collectors.toList())
                    )
            );
        }
    }

    @When("the generate current season summary use case is executed")
    public void theGenerateCurrentSeasonSummaryUseCaseIsExecuted() {
        actualResult = underTest.generateCurrentSeasonSummary(CURRENT_YEAR, CURRENT_SEASON, false);
    }

    @Then("the following current season summary is obtained")
    public void theFollowingCurrentSeasonSummaryIsObtained(List<CurrentSeasonSummaryItemForCucumber> currentSeasonSummary) {
        this.currentSeasonSummary = currentSeasonSummary;
    }

    @And("the following current season roles are obtained for each seiyuu")
    public void theFollowingCurrentSeasonRolesAreObtainedForEachSeiyuu(List<RolesDtoForCucumber> currentSeasonRolesSummary) {
        seiyuuIdToCurrentSeasonRolesSummary = currentSeasonRolesSummary.stream()
                .collect(Collectors.groupingBy(RolesDtoForCucumber::seiyuuId, Collectors.mapping(Function.identity(), Collectors.toList())));
    }

    @And("the following all time roles are obtained for each seiyuu")
    public void theFollowingAllTimeRolesAreObtainedForEachSeiyuu(List<RolesDtoForCucumber> overallRolesSummary) {
        Map<Integer, List<RolesDtoForCucumber>> seiyuuIdToOverallSeasonRolesSummary = overallRolesSummary.stream()
                .collect(Collectors.groupingBy(RolesDtoForCucumber::seiyuuId, Collectors.mapping(Function.identity(), Collectors.toList())));

        List<CurrentSeasonSummaryItemDto> expectedResult = currentSeasonSummary.stream()
                .map(item -> new CurrentSeasonSummaryItemDto(
                        item.name,
                        item.image,
                        item.id,
                        seiyuuIdToCurrentSeasonRolesSummary.get(item.id).stream().map(this::toRolesDto).collect(Collectors.toList()),
                        seiyuuIdToOverallSeasonRolesSummary.get(item.id).stream().map(this::toRolesDto).collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        assertThat(actualResult)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedResult);
    }

    private RolesDto toRolesDto(RolesDtoForCucumber rolesDtoForCucumber) {
        return new RolesDto(
                rolesDtoForCucumber.characterName,
                rolesDtoForCucumber.characterThumbnail,
                rolesDtoForCucumber.seriesName,
                rolesDtoForCucumber.seriesId
        );
    }

    record CharacterForCucumber(int animeId, int charId, String name, String role, String url, String imageUrl) {}

    record CharToSeiyuuIdForCucucmber(int charId, int seiyuuId) {}

    record SeiyuuForCucumber(int seiyuuId, String url, String imageUrl, String name) {}

    record RoleForCucumber(int seiyuuId, String animeName, String animeUrl, int animeId, String characterName, String characterUrl, String characterImageUrl) {}

    record CurrentSeasonSummaryItemForCucumber(String name, String image, int id) {}

    record RolesDtoForCucumber(int seiyuuId, String characterName, String characterThumbnail, String seriesName, int seriesId) {}
}
