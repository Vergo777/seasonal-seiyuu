package moe.vergo.seasonalseiyuuapi.summary.service;

import moe.vergo.seasonalseiyuuapi.summary.model.SeasonalSeiyuu;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface SummaryService {
    public List<SeasonalSeiyuu> getCurrentSeasonSummary();
    public Map<Integer, SeasonalSeiyuu> getSeiyuuDetailsMap();
}
