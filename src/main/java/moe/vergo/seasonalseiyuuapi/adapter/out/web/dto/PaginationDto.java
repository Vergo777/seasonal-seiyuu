package moe.vergo.seasonalseiyuuapi.adapter.out.web.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaginationDto {
    private int lastVisiblePage;
    private boolean hasNextPage;
    private PaginationItemsDto items;

    public int getLastVisiblePage() {
        return lastVisiblePage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public PaginationItemsDto getItems() {
        return items;
    }
}
