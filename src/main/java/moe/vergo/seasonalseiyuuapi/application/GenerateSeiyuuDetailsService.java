package moe.vergo.seasonalseiyuuapi.application;

import com.google.common.collect.MoreCollectors;
import moe.vergo.seasonalseiyuuapi.adapter.in.web.dto.CurrentSeasonSummaryItemDto;
import moe.vergo.seasonalseiyuuapi.application.port.in.GenerateCurrentSeasonSummaryUseCase;
import moe.vergo.seasonalseiyuuapi.application.port.in.GenerateSeiyuuDetailsUseCase;
import moe.vergo.seasonalseiyuuapi.domain.Season;

public class GenerateSeiyuuDetailsService implements GenerateSeiyuuDetailsUseCase {
    private final GenerateCurrentSeasonSummaryUseCase generateCurrentSeasonSummaryUseCase;

    public GenerateSeiyuuDetailsService(GenerateCurrentSeasonSummaryUseCase generateCurrentSeasonSummaryUseCase) {
        this.generateCurrentSeasonSummaryUseCase = generateCurrentSeasonSummaryUseCase;
    }

    @Override
    public CurrentSeasonSummaryItemDto generateSeiyuuDetails(int seiyuuId, int year, Season season) {
        return generateCurrentSeasonSummaryUseCase.generateCurrentSeasonSummary(year, season).stream()
                .filter(item -> item.getId() == seiyuuId)
                .collect(MoreCollectors.onlyElement());
    }
}
