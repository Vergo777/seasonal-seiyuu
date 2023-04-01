package moe.vergo.seasonalseiyuuapi.application.port.out;

import moe.vergo.seasonalseiyuuapi.domain.Character;

import java.util.List;

public interface AnimeCharactersPort {
    List<Character> getAnimeCharacters(int animeId);
}
