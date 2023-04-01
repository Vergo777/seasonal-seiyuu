package moe.vergo.seasonalseiyuuapi.application.port.in;

import moe.vergo.seasonalseiyuuapi.adapter.in.web.dto.CurrentSeasonSummaryItemDto;
import moe.vergo.seasonalseiyuuapi.domain.Season;

import java.util.List;

public interface GenerateCurrentSeasonSummaryUseCase {
    List<CurrentSeasonSummaryItemDto> generateCurrentSeasonSummary(int year, Season season);
}
