package moe.vergo.seasonalseiyuuapi.seiyuu.service;

import moe.vergo.seasonalseiyuuapi.seiyuu.model.Seiyuu;

import java.util.List;

public interface SeiyuuService {
    public Seiyuu getSeiyuuInfo(int seiyuuID);

    public List<Seiyuu> getSeiyuusForCurrentlyAiringAnime();
}
