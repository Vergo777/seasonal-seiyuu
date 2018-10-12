package moe.vergo.seasonalseiyuuapi.anime.repository;

import moe.vergo.seasonalseiyuuapi.anime.model.Anime;
import moe.vergo.seasonalseiyuuapi.anime.model.AnimeCharacters;
import moe.vergo.seasonalseiyuuapi.cache.CacheManager;
import moe.vergo.seasonalseiyuuapi.cache.CacheService;
import moe.vergo.seasonalseiyuuapi.cache.CacheServiceImpl;
import moe.vergo.seasonalseiyuuapi.utils.RequestThrottling;
import org.mapdb.DB;
import org.mapdb.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ConcurrentMap;

@Repository
public class AnimeRepositoryImpl implements AnimeRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnimeRepositoryImpl.class);

    private static final String ANIME_ENDPOINT = "/anime";
    private static final String CHARACTERS_ENDPOINT = "/characters_staff";

    @Autowired
    private CacheManager<Anime> animeCacheManager;

    @Autowired
    private CacheManager<AnimeCharacters> animeCharactersCacheManager;

    @Autowired
    private RestTemplate restTemplate;


    public Anime getAnimeInfo(int animeID) {
        CacheService<Anime> cacheService = animeCacheManager.initialiseCache("animeCache");

        if(cacheService.isPresentInCache(animeID)) {
            return cacheService.getValueFromCache(animeID);
        }

        Anime anime = restTemplate.getForObject(ANIME_ENDPOINT + "/" + animeID, Anime.class);
        cacheService.addValueToCache(animeID, anime);
        return anime;
    }

    public AnimeCharacters getAnimeCharactersInfo(int animeID) {
        CacheService<AnimeCharacters> cacheService = animeCharactersCacheManager.initialiseCache("characterCache");

        if(cacheService.isPresentInCache(animeID)) {
            return cacheService.getValueFromCache(animeID);
        }

        AnimeCharacters animeCharacters =  restTemplate.getForObject(ANIME_ENDPOINT + "/" + animeID + "/" + CHARACTERS_ENDPOINT, AnimeCharacters.class);
        cacheService.addValueToCache(animeID, animeCharacters);
        return animeCharacters;
    }
}
