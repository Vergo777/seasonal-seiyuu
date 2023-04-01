package moe.vergo.seasonalseiyuuapi.adapter.out.web.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SeiyuuDataWrapperDto {
    private int malId;
    private String url;
    private ImageDto images;
    private String name;
    private List<SeiyuuVoicesDto> voices;

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

    public List<SeiyuuVoicesDto> getVoices() {
        return voices;
    }
}
