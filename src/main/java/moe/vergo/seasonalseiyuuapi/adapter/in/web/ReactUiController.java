package moe.vergo.seasonalseiyuuapi.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonView;
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

    /**
     * <p>
     *  This GET endpoint provides the primary set of data required to populate the app's homepage. It returns a list of each seasonal seiyuu's data, ranging from basic
     *  details such as name and image to more detailed information on their current season and overall voice acting roles. Note that the list of seiyuu's at this point
     *  has already been restricted to those who have at least one role in the current season, rather than the full set of all-time seiyuus (which would be a massive list
     *  and not in line with the scope of this app).
     *</p>
     *
     * <p>
     *  In retrospect would have used hyphens for the naming of this endpoint in line with best practices (i.e, /current-season-summary): https://stackoverflow.com/a/18450653
     * </p>
     *
     * <p>
     *  However the endpoint has been left as camel case here because the legacy UI expects this naming and I didn't want to touch that for now!
     * </p>
     *
     * @return list of each seasonal seiyuu's data, such as their name, image and voice acting roles
     */
    @GetMapping("/currentSeasonSummary")
    @JsonView(Views.Simple.class)
    public List<CurrentSeasonSummaryItemDto> currentSeasonSummary(){
        return generateCurrentSeasonSummaryUseCase.generateCurrentSeasonSummary(CURRENT_YEAR, CURRENT_SEASON, true);
    }

    /**
     * <p>
     *     This GET endpoint provides the data required for the "detailed" view of a single selected seiyuu. This is the same data returned in the summary endpoint,
     *     just filtered down to the specific seiyuu in question. The main data of interest here is the voice acting roles, split into current season and overall
     *     roles. The overall roles provides are not strictly a value addition since this information already exists in this form on most of the anime tracking websites,
     *     however it is included here primarily for convenience as people often like a refresher on the other roles their seiyuu of interest has done.
     * </p>
     *
     * @param id MyAnimeList ID of the seiyuu for whom we want to fetch detailed information
     * @return the requested seasonal seiyuu's data, such as their name, image and voice acting roles
     */
    @GetMapping("/seiyuuDetails/{id}")
    public CurrentSeasonSummaryItemDto seiyuuDetails(@PathVariable int id) {
        return generateSeiyuuDetailsUseCase.generateSeiyuuDetails(id, CURRENT_YEAR, CURRENT_SEASON);
    }
}
