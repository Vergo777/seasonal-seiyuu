package moe.vergo.seasonalseiyuuapi.adapter.out.web.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AnimeCharacterDataWrapperDto {
    private AnimeCharacterDto character;
    private String role;
    private List<AnimeSeiyuuDto> voiceActors;

    public AnimeCharacterDto getCharacter() {
        return character;
    }

    public String getRole() {
        return role;
    }

    public List<AnimeSeiyuuDto> getVoiceActors() {
        return voiceActors;
    }
}
