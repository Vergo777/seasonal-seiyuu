package moe.vergo.seasonalseiyuuapi.adapter.out.web.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AnimeDto {
    private int malId;
    private String url;
    private List<AnimeTitlesDto> titles;

    public int getMalId() {
        return malId;
    }

    public String getUrl() {
        return url;
    }

    public List<AnimeTitlesDto> getTitles() {
        return titles;
    }
}
