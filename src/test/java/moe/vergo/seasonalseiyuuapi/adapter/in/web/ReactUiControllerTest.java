package moe.vergo.seasonalseiyuuapi.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import moe.vergo.seasonalseiyuuapi.adapter.in.web.dto.CurrentSeasonSummaryItemDto;
import moe.vergo.seasonalseiyuuapi.application.exception.GetSeiyuuDetailsException;
import moe.vergo.seasonalseiyuuapi.application.port.in.GenerateCurrentSeasonSummaryUseCase;
import moe.vergo.seasonalseiyuuapi.application.port.in.GenerateSeiyuuDetailsUseCase;
import moe.vergo.seasonalseiyuuapi.domain.Season;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ReactUiController.class)
public class ReactUiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GenerateCurrentSeasonSummaryUseCase generateCurrentSeasonSummaryUseCase;

    @MockBean
    private GenerateSeiyuuDetailsUseCase generateSeiyuuDetailsUseCase;

    @Test
    public void testGetCurrentSeasonSummaryCallsCorrespondingUseCase() throws Exception {
        // Given
        int year = 2023;
        Season season = Season.spring;
        String endpoint = "/currentSeasonSummary";

        List<CurrentSeasonSummaryItemDto> currentSeasonSummaryItems = List.of(
                new CurrentSeasonSummaryItemDto("Touyama Nao", "URL1", 1, Collections.emptyList(), Collections.emptyList())
        );

        Mockito.when(generateCurrentSeasonSummaryUseCase.generateCurrentSeasonSummary(year, season, true)).thenReturn(currentSeasonSummaryItems);

        // When
        ResultActions actualResponse = mockMvc.perform(get(endpoint));

        // Then
        actualResponse
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"name\":\"Touyama Nao\",\"image\":\"URL1\",\"id\":1,\"currentSeasonRolesArray\":[]}]"));
    }

    @Test
    public void testGetSeiyuuDetailsCallsCorrespondingUseCaseWithSeiyuuId() throws Exception {
        // Given
        int year = 2023;
        Season season = Season.spring;
        int seiyuuId = 1;
        String endpoint = "/seiyuuDetails/" + seiyuuId;

        CurrentSeasonSummaryItemDto currentSeasonSummaryItemDto = new CurrentSeasonSummaryItemDto("Touyama Nao", "URL1", 1, Collections.emptyList(), Collections.emptyList());

        Mockito.when(generateSeiyuuDetailsUseCase.generateSeiyuuDetails(seiyuuId, year, season)).thenReturn(currentSeasonSummaryItemDto);

        // When
        ResultActions actualResponse = mockMvc.perform(get(endpoint));

        // Then
        actualResponse
                .andExpect(status().isOk())
                .andExpect(content().string("{\"name\":\"Touyama Nao\",\"image\":\"URL1\",\"id\":1,\"currentSeasonRolesArray\":[],\"overallRolesArray\":[]}"));
    }

    @Test
    public void testGetCurrentSeasonSummaryReturns500InternalServerErrorWhenCorrespondingUseCaseThrowsGeneralException() throws Exception {
        // Given
        int year = 2023;
        Season season = Season.spring;
        String endpoint = "/currentSeasonSummary";

        Mockito.when(generateCurrentSeasonSummaryUseCase.generateCurrentSeasonSummary(year, season, true)).thenThrow(new NullPointerException());

        // When
        ResultActions actualResponse = mockMvc.perform(get(endpoint));

        // Then
        actualResponse
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("Something went wrong on our side - get in touch so we can look into it"));
    }

    @Test
    public void testGetCurrentSeasonSummaryReturns503ServiceUnavailableErrorWhenCorrespondingUseCaseThrowsGetSeiyuuDetailsException() throws Exception {
        // Given
        int year = 2023;
        Season season = Season.spring;
        String endpoint = "/currentSeasonSummary";

        Mockito.when(generateCurrentSeasonSummaryUseCase.generateCurrentSeasonSummary(year, season, true)).thenThrow(new GetSeiyuuDetailsException());

        // When
        ResultActions actualResponse = mockMvc.perform(get(endpoint));

        // Then
        actualResponse
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("Failed to fetch seiyuu details from upstream Rest API, try again later"));
    }
}
