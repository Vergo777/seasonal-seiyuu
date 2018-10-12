package moe.vergo.seasonalseiyuuapi.summary.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class SummaryRole implements Serializable {
    @JsonProperty("characterName")
    private String characterName;

    @JsonProperty("characterThumbnail")
    private String characterThumbnail;

    @JsonProperty("seriesName")
    private String seriesName;

    @JsonProperty("seriesID")
    private Integer seriesID;

    @JsonProperty("characterName")
    public String getCharacterName() {
        return characterName;
    }

    @JsonProperty("characterThumbnail")
    public String getCharacterThumbnail() {
        return characterThumbnail;
    }

    @JsonProperty("seriesName")
    public String getSeriesName() {
        return seriesName;
    }

    @JsonProperty("seriesID")
    public Integer getSeriesID() {
        return seriesID;
    }

    public static final class SummaryRoleBuilder {
        private String characterName;
        private String characterThumbnail;
        private String seriesName;
        private Integer seriesID;

        private SummaryRoleBuilder() {
        }

        public static SummaryRoleBuilder aSummaryRole() {
            return new SummaryRoleBuilder();
        }

        public SummaryRoleBuilder withCharacterName(String characterName) {
            this.characterName = characterName;
            return this;
        }

        public SummaryRoleBuilder withCharacterThumbnail(String characterThumbnail) {
            this.characterThumbnail = characterThumbnail;
            return this;
        }

        public SummaryRoleBuilder withSeriesName(String seriesName) {
            this.seriesName = seriesName;
            return this;
        }

        public SummaryRoleBuilder withSeriesID(Integer seriesID) {
            this.seriesID = seriesID;
            return this;
        }

        public SummaryRole build() {
            SummaryRole summaryRole = new SummaryRole();
            summaryRole.seriesID = this.seriesID;
            summaryRole.seriesName = this.seriesName;
            summaryRole.characterThumbnail = this.characterThumbnail;
            summaryRole.characterName = this.characterName;
            return summaryRole;
        }
    }
}
