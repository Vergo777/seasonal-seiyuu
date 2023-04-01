package moe.vergo.seasonalseiyuuapi.adapter.out.web.helper;

import moe.vergo.seasonalseiyuuapi.adapter.out.web.dto.PaginationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebOutputAdapterHelper {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    // https://github.com/jikan-me/jikan/issues/475
    public static int getTotalNumberOfPages(PaginationDto paginationDto) {
        return paginationDto.getLastVisiblePage();
    }
}
