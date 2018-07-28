
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
    "currentuser",
    "properties",
    "frontend",
    "likes"
})
public class Expandable__________ {

    @JsonProperty("currentuser")
    private String currentuser;
    @JsonProperty("properties")
    private String properties;
    @JsonProperty("frontend")
    private String frontend;
    @JsonProperty("likes")
    private String likes;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("currentuser")
    public String getCurrentuser() {
        return currentuser;
    }

    @JsonProperty("currentuser")
    public void setCurrentuser(String currentuser) {
        this.currentuser = currentuser;
    }

    @JsonProperty("properties")
    public String getProperties() {
        return properties;
    }

    @JsonProperty("properties")
    public void setProperties(String properties) {
        this.properties = properties;
    }

    @JsonProperty("frontend")
    public String getFrontend() {
        return frontend;
    }

    @JsonProperty("frontend")
    public void setFrontend(String frontend) {
        this.frontend = frontend;
    }

    @JsonProperty("likes")
    public String getLikes() {
        return likes;
    }

    @JsonProperty("likes")
    public void setLikes(String likes) {
        this.likes = likes;
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
