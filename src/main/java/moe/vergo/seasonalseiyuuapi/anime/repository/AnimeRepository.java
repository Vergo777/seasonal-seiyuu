package moe.vergo.seasonalseiyuuapi.anime.repository;

import moe.vergo.seasonalseiyuuapi.anime.model.Anime;
import moe.vergo.seasonalseiyuuapi.anime.model.AnimeCharacters;

public interface AnimeRepository {
    public Anime getAnimeInfo(int animeID);

    public AnimeCharacters getAnimeCharactersInfo(int animeID);
}
