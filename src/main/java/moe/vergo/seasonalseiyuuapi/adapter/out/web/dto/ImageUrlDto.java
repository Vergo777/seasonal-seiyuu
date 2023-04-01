package moe.vergo.seasonalseiyuuapi.adapter.out.web.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ImageUrlDto {
    String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }
}
