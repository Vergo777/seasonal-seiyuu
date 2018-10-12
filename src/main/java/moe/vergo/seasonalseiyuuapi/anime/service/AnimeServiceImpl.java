package moe.vergo.seasonalseiyuuapi.anime.service;

import moe.vergo.seasonalseiyuuapi.anime.model.Anime;
import moe.vergo.seasonalseiyuuapi.anime.model.AnimeCharacters;
import moe.vergo.seasonalseiyuuapi.anime.model.FullAnimeDetails;
import moe.vergo.seasonalseiyuuapi.anime.repository.AnimeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static moe.vergo.seasonalseiyuuapi.anime.Constants.CURRENTLY_AIRING_SHOWS;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnimeServiceImpl implements AnimeService {
    Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AnimeRepository animeRepository;

    public Anime getAnimeInfo(int animeID) {
        return animeRepository.getAnimeInfo(animeID);
    }

    public AnimeCharacters getAnimeCharactersInfo(int animeID) {
        return animeRepository.getAnimeCharactersInfo(animeID);
    }

    public List<FullAnimeDetails> getDetailsForCurrentlyAiringAnime() {
        int index = 1;
        List<FullAnimeDetails> currentlyAiringAnimeDetails = new ArrayList<>();

        for(Integer animeID : CURRENTLY_AIRING_SHOWS) {
            LOGGER.info("Fetching details for anime ID {}, {} of {} total shows", animeID, index, CURRENTLY_AIRING_SHOWS.size());
            Anime anime = getAnimeInfo(animeID);
            AnimeCharacters animeCharacters = getAnimeCharactersInfo(animeID);
            FullAnimeDetails fullAnimeDetails = new FullAnimeDetails(anime, animeCharacters);
            currentlyAiringAnimeDetails.add(fullAnimeDetails);

            index++;
        }

        return currentlyAiringAnimeDetails;
    }
}
