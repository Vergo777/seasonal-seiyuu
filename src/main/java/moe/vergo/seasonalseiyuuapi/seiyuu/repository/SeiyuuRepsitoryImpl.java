package moe.vergo.seasonalseiyuuapi.seiyuu.repository;

import moe.vergo.seasonalseiyuuapi.cache.CacheManager;
import moe.vergo.seasonalseiyuuapi.cache.CacheService;
import moe.vergo.seasonalseiyuuapi.seiyuu.model.Seiyuu;
import moe.vergo.seasonalseiyuuapi.utils.RequestThrottling;
import org.mapdb.DB;
import org.mapdb.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ConcurrentMap;

@Repository
public class SeiyuuRepsitoryImpl implements SeiyuuRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(SeiyuuRepsitoryImpl.class);

    private static final String SEIYUU_ENDPOINT = "/person";

    @Autowired
    private CacheManager<Seiyuu> seiyuuCacheManager;

    @Autowired
    private RestTemplate restTemplate;


    public Seiyuu getSeiyuuInfo(int seiyuuID) {
        CacheService<Seiyuu> cacheService = seiyuuCacheManager.initialiseCache("seiyuuCache");

        if(cacheService.isPresentInCache(seiyuuID)) {
            return cacheService.getValueFromCache(seiyuuID);
        }

        Seiyuu seiyuu = restTemplate.getForObject(SEIYUU_ENDPOINT + "/" + seiyuuID, Seiyuu.class);
        cacheService.addValueToCache(seiyuuID, seiyuu);
        return seiyuu;
    }
}
