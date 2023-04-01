package moe.vergo.seasonalseiyuuapi;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SeasonalSeiyuuApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeasonalSeiyuuApiApplication.class, args);
	}

	@Bean
	public DB createCacheDB() {
		DB db = DBMaker.fileDB("./tmp/cache.db").closeOnJvmShutdown().checksumHeaderBypass().make();
		return db;
	}
}
