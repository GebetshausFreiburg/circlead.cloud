
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
    "isEditable",
    "nonEditableReason"
})
public class Customfield10001 {

    @JsonProperty("isEditable")
    private Boolean isEditable;
    @JsonProperty("nonEditableReason")
    private NonEditableReason_ nonEditableReason;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("isEditable")
    public Boolean getIsEditable() {
        return isEditable;
    }

    @JsonProperty("isEditable")
    public void setIsEditable(Boolean isEditable) {
        this.isEditable = isEditable;
    }

    @JsonProperty("nonEditableReason")
    public NonEditableReason_ getNonEditableReason() {
        return nonEditableReason;
    }

    @JsonProperty("nonEditableReason")
    public void setNonEditableReason(NonEditableReason_ nonEditableReason) {
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
