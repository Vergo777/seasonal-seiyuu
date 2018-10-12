package moe.vergo.seasonalseiyuuapi.anime.model;

import java.io.Serializable;

public class FullAnimeDetails implements Serializable {
    private Anime anime;
    private AnimeCharacters animeCharacters;

    public FullAnimeDetails(Anime anime, AnimeCharacters animeCharacters) {
        this.anime = anime;
        this.animeCharacters = animeCharacters;
    }

    public Anime getAnime() {
        return anime;
    }

    public void setAnime(Anime anime) {
        this.anime = anime;
    }

    public AnimeCharacters getAnimeCharacters() {
        return animeCharacters;
    }

    public void setAnimeCharacters(AnimeCharacters animeCharacters) {
        this.animeCharacters = animeCharacters;
    }
}
