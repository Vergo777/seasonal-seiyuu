package moe.vergo.seasonalseiyuuapi.adapter.out.web.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AnimeCharacterDto {
    private int malId;
    private String url;
    private ImageDto images;
    private String name;

    public int getMalId() {
        return malId;
    }

    public String getUrl() {
        return url;
    }

    public ImageDto getImages() {
        return images;
    }

    public String getName() {
        return name;
    }
}
