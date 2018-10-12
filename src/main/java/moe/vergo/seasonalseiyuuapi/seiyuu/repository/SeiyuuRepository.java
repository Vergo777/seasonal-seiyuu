package moe.vergo.seasonalseiyuuapi.seiyuu.repository;

import moe.vergo.seasonalseiyuuapi.seiyuu.model.Seiyuu;

public interface SeiyuuRepository {
    public Seiyuu getSeiyuuInfo(int seiyuuID);
}
