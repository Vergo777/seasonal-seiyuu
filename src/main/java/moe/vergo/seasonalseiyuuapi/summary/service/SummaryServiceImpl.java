package moe.vergo.seasonalseiyuuapi.summary.service;

import moe.vergo.seasonalseiyuuapi.anime.model.Anime;
import moe.vergo.seasonalseiyuuapi.anime.model.Character;
import moe.vergo.seasonalseiyuuapi.anime.model.CharacterSeiyuuDetails;
import moe.vergo.seasonalseiyuuapi.anime.model.FullAnimeDetails;
import moe.vergo.seasonalseiyuuapi.anime.service.AnimeService;
import moe.vergo.seasonalseiyuuapi.cache.CacheManager;
import moe.vergo.seasonalseiyuuapi.cache.CacheService;
import moe.vergo.seasonalseiyuuapi.seiyuu.model.Role;
import moe.vergo.seasonalseiyuuapi.seiyuu.model.Seiyuu;
import moe.vergo.seasonalseiyuuapi.seiyuu.service.SeiyuuService;
import moe.vergo.seasonalseiyuuapi.summary.model.SeasonalSeiyuu;
import moe.vergo.seasonalseiyuuapi.summary.model.SummaryRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static moe.vergo.seasonalseiyuuapi.summary.model.SeasonalSeiyuu.SeasonalSeiyuuBuilder;
import static moe.vergo.seasonalseiyuuapi.summary.model.SummaryRole.SummaryRoleBuilder;

@Service
public class SummaryServiceImpl implements SummaryService {
    private final static String SEIYUU_LANGUAGE = "Japanese";
    private final static Integer SEIYUU_DETAILS_MAP_KEY = 1;

    @Autowired
    private AnimeService animeService;

    @Autowired
    private SeiyuuService seiyuuService;

    @Autowired
    private CacheManager<Map<Integer, SeasonalSeiyuu>> seiyuuDetailsMapCacheManager;

    public List<SeasonalSeiyuu> getCurrentSeasonSummary() {
        Map<Integer, SeasonalSeiyuu> seiyuuDetailsMap = getSeiyuuDetailsMap();
        return new ArrayList<>(seiyuuDetailsMap.values());
    }

    public Map<Integer, SeasonalSeiyuu> getSeiyuuDetailsMap() {
        CacheService<Map<Integer, SeasonalSeiyuu>> cacheService = seiyuuDetailsMapCacheManager.initialiseCache("seiyuuDetailsMapCache");

        if(cacheService.isPresentInCache(SEIYUU_DETAILS_MAP_KEY)) {
            return cacheService.getValueFromCache(SEIYUU_DETAILS_MAP_KEY);
        }

        Map<Integer, SeasonalSeiyuu> summaryMap = new HashMap<>();
        List<FullAnimeDetails> currentlyAiringAnimeDetails = animeService.getDetailsForCurrentlyAiringAnime();

        for(FullAnimeDetails fullAnimeDetails : currentlyAiringAnimeDetails) {
            Anime anime = fullAnimeDetails.getAnime();
            List<Character> animeCharacters = fullAnimeDetails.getAnimeCharacters().getCharacters();

            for(Character character : animeCharacters) {
                List<CharacterSeiyuuDetails> characterSeiyuuDetails = character.getCharacterSeiyuuDetailsList();
                characterSeiyuuDetails = filterCharacterSeiyuusByLanguage(characterSeiyuuDetails, SEIYUU_LANGUAGE);

                for(CharacterSeiyuuDetails characterJapaneseSeiyuuDetails : characterSeiyuuDetails) {
                    Seiyuu seiyuu = seiyuuService.getSeiyuuInfo(characterJapaneseSeiyuuDetails.getMalId());
                    summaryMap = addSeiyuuDetailsToSummaryMap(summaryMap, seiyuu, anime, character);
                }
            }
        }

        cacheService.addValueToCache(SEIYUU_DETAILS_MAP_KEY, summaryMap);
        return summaryMap;
    }

    List<CharacterSeiyuuDetails> filterCharacterSeiyuusByLanguage(List<CharacterSeiyuuDetails> allLanguagesSeiyuus, String filterLanguage) {
        return allLanguagesSeiyuus.stream().filter(characterSeiyuuDetails -> characterSeiyuuDetails.getLanguage().equals(filterLanguage))
                .collect(Collectors.toList());
    }

    Map<Integer, SeasonalSeiyuu> addSeiyuuDetailsToSummaryMap(Map<Integer, SeasonalSeiyuu> summaryMap, Seiyuu seiyuu, Anime anime, Character character) {
        SummaryRole summaryRole = createSummaryRoleFromAnimeAndCharacter(anime, character);
        int seiyuuID = seiyuu.getMalId();

        if(summaryMap.containsKey(seiyuuID)) {
            // add character to seiyuu's seasonal char list and return
            summaryMap.get(seiyuuID).getCurrentSeasonSummaryRoles().add(summaryRole);
        } else {
            SeasonalSeiyuu seasonalSeiyuu = createSeasonalSeiyuuFromGivenSummaryRole(seiyuu, summaryRole);
            summaryMap.put(seiyuuID, seasonalSeiyuu);
        }

        return summaryMap;
    }

    SummaryRole createSummaryRoleFromAnimeAndCharacter(Anime anime, Character character) {
        return SummaryRoleBuilder.aSummaryRole()
                .withCharacterName(character.getName())
                .withCharacterThumbnail(character.getImageUrl())
                .withSeriesName(anime.getTitle())
                .withSeriesID(anime.getMalId())
                .build();
    }

    SeasonalSeiyuu createSeasonalSeiyuuFromGivenSummaryRole(Seiyuu seiyuu, SummaryRole summaryRole) {
        List<SummaryRole> currentSeasonSummaryRoles = new ArrayList<>();
        currentSeasonSummaryRoles.add(summaryRole);

        return SeasonalSeiyuuBuilder.aSeasonalSeiyuu()
                .withName(seiyuu.getName())
                .withImage(seiyuu.getImageUrl())
                .withID(seiyuu.getMalId())
                .withCurrentSeasonSummaryRoles(currentSeasonSummaryRoles)
                .withOverallSummaryRoles(createOverallSummaryRolesListForSeiyuu(seiyuu))
                .build();
    }

    List<SummaryRole> createOverallSummaryRolesListForSeiyuu(Seiyuu seiyuu) {
        List<Role> seiyuuRoles = seiyuu.getVoiceActingRoles();
        return seiyuuRoles.stream().map(seiyuuRole -> createSummaryRoleFromAnimeAndCharacter(seiyuuRole.getAnime(), seiyuuRole.getCharacter()))
                .collect(Collectors.toList());
    }
}
