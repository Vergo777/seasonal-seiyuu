package moe.vergo.seasonalseiyuuapi.anime.service;

import moe.vergo.seasonalseiyuuapi.anime.model.Anime;
import moe.vergo.seasonalseiyuuapi.anime.model.AnimeCharacters;
import moe.vergo.seasonalseiyuuapi.anime.model.FullAnimeDetails;

import java.util.List;

public interface AnimeService {
    public Anime getAnimeInfo(int animeID);

    public AnimeCharacters getAnimeCharactersInfo(int animeID);

    public List<FullAnimeDetails> getDetailsForCurrentlyAiringAnime();
}
