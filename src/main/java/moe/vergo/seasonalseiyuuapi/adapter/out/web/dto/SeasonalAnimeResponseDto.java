package moe.vergo.seasonalseiyuuapi.adapter.out.web.dto;

import java.util.List;

public class SeasonalAnimeResponseDto {
    private List<AnimeDto> data;
    private PaginationDto pagination;

    public List<AnimeDto> getData() {
        return data;
    }

    public PaginationDto getPagination() {
        return pagination;
    }
}
