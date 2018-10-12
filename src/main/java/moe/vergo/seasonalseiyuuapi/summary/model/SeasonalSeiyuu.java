package moe.vergo.seasonalseiyuuapi.summary.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import moe.vergo.seasonalseiyuuapi.summary.views.Views;

import java.io.Serializable;
import java.util.List;

public class SeasonalSeiyuu implements Serializable {
    @JsonProperty("name")
    @JsonView(Views.Simple.class)
    private String name;

    @JsonProperty("image")
    @JsonView(Views.Simple.class)
    private String image;

    @JsonProperty("id")
    @JsonView(Views.Simple.class)
    private Integer ID;

    @JsonProperty("currentSeasonRolesArray")
    @JsonView(Views.Simple.class)
    private List<SummaryRole> currentSeasonSummaryRoles;

    @JsonProperty("overallRolesArray")
    @JsonView(Views.Detailed.class)
    private List<SummaryRole> overallSummaryRoles;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("image")
    public String getImage() {
        return image;
    }

    @JsonProperty("id")
    public Integer getID() {
        return ID;
    }

    @JsonProperty("currentSeasonRolesArray")
    public List<SummaryRole> getCurrentSeasonSummaryRoles() {
        return currentSeasonSummaryRoles;
    }

    @JsonProperty("overallRolesArray")
    public List<SummaryRole> getOverallSummaryRoles() {
        return overallSummaryRoles;
    }

    public static final class SeasonalSeiyuuBuilder {
        private String name;
        private String image;
        private Integer ID;
        private List<SummaryRole> currentSeasonSummaryRoles;
        private List<SummaryRole> overallSummaryRoles;

        private SeasonalSeiyuuBuilder() {
        }

        public static SeasonalSeiyuuBuilder aSeasonalSeiyuu() {
            return new SeasonalSeiyuuBuilder();
        }

        public SeasonalSeiyuuBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public SeasonalSeiyuuBuilder withImage(String image) {
            this.image = image;
            return this;
        }

        public SeasonalSeiyuuBuilder withID(Integer ID) {
            this.ID = ID;
            return this;
        }

        public SeasonalSeiyuuBuilder withCurrentSeasonSummaryRoles(List<SummaryRole> currentSeasonSummaryRoles) {
            this.currentSeasonSummaryRoles = currentSeasonSummaryRoles;
            return this;
        }

        public SeasonalSeiyuuBuilder withOverallSummaryRoles(List<SummaryRole> overallSummaryRoles) {
            this.overallSummaryRoles = overallSummaryRoles;
            return this;
        }

        public SeasonalSeiyuu build() {
            SeasonalSeiyuu seasonalSeiyuu = new SeasonalSeiyuu();
            seasonalSeiyuu.ID = this.ID;
            seasonalSeiyuu.image = this.image;
            seasonalSeiyuu.name = this.name;
            seasonalSeiyuu.overallSummaryRoles = this.overallSummaryRoles;
            seasonalSeiyuu.currentSeasonSummaryRoles = this.currentSeasonSummaryRoles;
            return seasonalSeiyuu;
        }
    }
}
