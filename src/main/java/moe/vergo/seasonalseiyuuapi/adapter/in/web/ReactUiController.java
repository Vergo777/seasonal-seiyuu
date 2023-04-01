package moe.vergo.seasonalseiyuuapi.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.collect.MoreCollectors;
import moe.vergo.seasonalseiyuuapi.adapter.in.web.dto.CurrentSeasonSummaryItemDto;
import moe.vergo.seasonalseiyuuapi.adapter.in.web.dto.Views;
import moe.vergo.seasonalseiyuuapi.application.port.in.GenerateCurrentSeasonSummaryUseCase;
import moe.vergo.seasonalseiyuuapi.application.port.in.GenerateSeiyuuDetailsUseCase;
import moe.vergo.seasonalseiyuuapi.domain.Season;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReactUiController {
    private static final Season CURRENT_SEASON = Season.spring;
    private static final int CURRENT_YEAR = 2023;

    private final GenerateCurrentSeasonSummaryUseCase generateCurrentSeasonSummaryUseCase;
    private final GenerateSeiyuuDetailsUseCase generateSeiyuuDetailsUseCase;

    public ReactUiController(GenerateCurrentSeasonSummaryUseCase generateCurrentSeasonSummaryUseCase, GenerateSeiyuuDetailsUseCase generateSeiyuuDetailsUseCase) {
        this.generateCurrentSeasonSummaryUseCase = generateCurrentSeasonSummaryUseCase;
        this.generateSeiyuuDetailsUseCase = generateSeiyuuDetailsUseCase;

    }

    @GetMapping("/currentSeasonSummary")
    @JsonView(Views.Simple.class)
    public List<CurrentSeasonSummaryItemDto> currentSeasonSummary(){
        return generateCurrentSeasonSummaryUseCase.generateCurrentSeasonSummary(CURRENT_YEAR, CURRENT_SEASON);
    }

    @GetMapping("/seiyuuDetails/{id}")
    public CurrentSeasonSummaryItemDto seiyuuDetails(@PathVariable int id) {
        return generateSeiyuuDetailsUseCase.generateSeiyuuDetails(id, CURRENT_YEAR, CURRENT_SEASON);
    }
}
