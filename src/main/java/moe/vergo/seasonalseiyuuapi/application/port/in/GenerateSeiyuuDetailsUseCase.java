package moe.vergo.seasonalseiyuuapi.application.port.in;

import moe.vergo.seasonalseiyuuapi.adapter.in.web.dto.CurrentSeasonSummaryItemDto;
import moe.vergo.seasonalseiyuuapi.domain.Season;

public interface GenerateSeiyuuDetailsUseCase {
    CurrentSeasonSummaryItemDto generateSeiyuuDetails(int seiyuuId, int year, Season season);
}
