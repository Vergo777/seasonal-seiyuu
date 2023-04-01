package moe.vergo.seasonalseiyuuapi.adapter.out.web.dto;

public class SeiyuuVoicesDto {
    private String role;
    private SeiyuuAnimeDto anime;
    private AnimeCharacterDto character;

    public String getRole() {
        return role;
    }

    public SeiyuuAnimeDto getAnime() {
        return anime;
    }

    public AnimeCharacterDto getCharacter() {
        return character;
    }
}
