package moe.vergo.seasonalseiyuuapi.seiyuu.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Seiyuu implements Serializable {

    @JsonProperty("mal_id")
    private Integer malId;
    @JsonProperty("url")
    private String url;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("website_url")
    private Object websiteUrl;
    @JsonProperty("name")
    private String name;
    @JsonProperty("voice_acting_roles")
    private List<Role> voiceActingRoles;

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

    @JsonProperty("website_url")
    public Object getWebsiteUrl() {
        return websiteUrl;
    }

    @JsonProperty("website_url")
    public void setWebsiteUrl(Object websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("voice_acting_roles")
    public List<Role> getVoiceActingRoles() {
        return voiceActingRoles;
    }

    @JsonProperty("voice_acting_roles")
    public void setVoiceActingRoles(List<Role> voiceActingRoles) {
        this.voiceActingRoles = voiceActingRoles;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("malId", malId).append("url", url).append("imageUrl", imageUrl).append("websiteUrl", websiteUrl).append("name", name).append("voiceActingRoles", voiceActingRoles).toString();
    }

}