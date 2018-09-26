
package org.rogatio.circlead.control.synchronizer.atlassian.jira;

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
    "hasEpicLinkFieldDependency",
    "showField",
    "nonEditableReason"
})
public class Customfield10009 {

    @JsonProperty("hasEpicLinkFieldDependency")
    private Boolean hasEpicLinkFieldDependency;
    @JsonProperty("showField")
    private Boolean showField;
    @JsonProperty("nonEditableReason")
    private NonEditableReason nonEditableReason;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("hasEpicLinkFieldDependency")
    public Boolean getHasEpicLinkFieldDependency() {
        return hasEpicLinkFieldDependency;
    }

    @JsonProperty("hasEpicLinkFieldDependency")
    public void setHasEpicLinkFieldDependency(Boolean hasEpicLinkFieldDependency) {
        this.hasEpicLinkFieldDependency = hasEpicLinkFieldDependency;
    }

    @JsonProperty("showField")
    public Boolean getShowField() {
        return showField;
    }

    @JsonProperty("showField")
    public void setShowField(Boolean showField) {
        this.showField = showField;
    }

    @JsonProperty("nonEditableReason")
    public NonEditableReason getNonEditableReason() {
        return nonEditableReason;
    }

    @JsonProperty("nonEditableReason")
    public void setNonEditableReason(NonEditableReason nonEditableReason) {
        this.nonEditableReason = nonEditableReason;
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
