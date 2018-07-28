
package org.rogatio.circlead.control.synchronizer.atlassian.content;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "previousVersion",
    "contributors",
    "nextVersion"
})
public class Expandable____ {

    @JsonProperty("previousVersion")
    private String previousVersion;
    @JsonProperty("contributors")
    private String contributors;
    @JsonProperty("nextVersion")
    private String nextVersion;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("previousVersion")
    public String getPreviousVersion() {
        return previousVersion;
    }

    @JsonProperty("previousVersion")
    public void setPreviousVersion(String previousVersion) {
        this.previousVersion = previousVersion;
    }

    @JsonProperty("contributors")
    public String getContributors() {
        return contributors;
    }

    @JsonProperty("contributors")
    public void setContributors(String contributors) {
        this.contributors = contributors;
    }

    @JsonProperty("nextVersion")
    public String getNextVersion() {
        return nextVersion;
    }

    @JsonProperty("nextVersion")
    public void setNextVersion(String nextVersion) {
        this.nextVersion = nextVersion;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
