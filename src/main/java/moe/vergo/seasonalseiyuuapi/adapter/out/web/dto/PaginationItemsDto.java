package moe.vergo.seasonalseiyuuapi.adapter.out.web.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaginationItemsDto {
    private int count;
    private int total;
    private int perPage;

    public int getCount() {
        return count;
    }

    public int getTotal() {
        return total;
    }

    public int getPerPage() {
        return perPage;
    }
}
