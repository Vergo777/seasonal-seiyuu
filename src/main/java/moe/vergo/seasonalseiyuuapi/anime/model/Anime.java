package moe.vergo.seasonalseiyuuapi.anime.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Anime implements Serializable {
    @JsonProperty("mal_id")
    private Integer malId;
    @JsonProperty("url")
    private String url;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("title")
    private String title;
    @JsonProperty("title_english")
    private String titleEnglish;

    // the anime object in the /person requests returns "name" rather than title, so we need to map that name -> title
    @JsonProperty("name")
    private String name;

    @JsonProperty("mal_id")
    public Integer getMalId() {
        return malId;
    }

    @JsonProperty("mal_id")
    public void setMalId(Integer malId) {
        this.malId = malId;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("image_url")
    public String getImageUrl() {
        return imageUrl;
    }

    @JsonProperty("image_url")
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("title_english")
    public String getTitleEnglish() {
        return titleEnglish;
    }

    @JsonProperty("title_english")
    public void setTitleEnglish(String titleEnglish) {
        this.titleEnglish = titleEnglish;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.title = name;
    }

    @JsonProperty("name")
    public String getName() {
        return this.title;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("malId", malId).append("url", url).append("imageUrl", imageUrl).append("title", title).append("titleEnglish", titleEnglish).toString();
    }
}