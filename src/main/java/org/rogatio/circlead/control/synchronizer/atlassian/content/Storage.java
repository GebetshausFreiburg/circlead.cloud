
package org.rogatio.circlead.control.synchronizer.atlassian.content;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "value",
    "representation",
    "embeddedContent",
    "_expandable"
})
public class Storage {

    @JsonProperty("value")
    private String value;
    @JsonProperty("representation")
    private String representation;
    @JsonProperty("embeddedContent")
    private List<Object> embeddedContent = null;
    @JsonProperty("_expandable")
    private Expandable________ expandable;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    @JsonProperty("representation")
    public String getRepresentation() {
        return representation;
    }

    @JsonProperty("representation")
    public void setRepresentation(String representation) {
        this.representation = representation;
    }

    @JsonProperty("embeddedContent")
    public List<Object> getEmbeddedContent() {
        return embeddedContent;
    }

    @JsonProperty("embeddedContent")
    public void setEmbeddedContent(List<Object> embeddedContent) {
        this.embeddedContent = embeddedContent;
    }

    @JsonProperty("_expandable")
    public Expandable________ getExpandable() {
        return expandable;
    }

    @JsonProperty("_expandable")
    public void setExpandable(Expandable________ expandable) {
        this.expandable = expandable;
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
