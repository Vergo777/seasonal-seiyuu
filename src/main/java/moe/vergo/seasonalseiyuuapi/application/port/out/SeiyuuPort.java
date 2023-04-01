package moe.vergo.seasonalseiyuuapi.application.port.out;

import moe.vergo.seasonalseiyuuapi.domain.Seiyuu;
import org.springframework.cache.annotation.Cacheable;

public interface SeiyuuPort {
    Seiyuu getSeiyuuDetails(int seiyuuId);
}
