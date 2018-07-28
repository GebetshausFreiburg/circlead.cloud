
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
    "childTypes",
    "container",
    "operations",
    "children",
    "restrictions",
    "descendants"
})
public class Expandable___________ {

    @JsonProperty("childTypes")
    private String childTypes;
    @JsonProperty("container")
    private String container;
    @JsonProperty("operations")
    private String operations;
    @JsonProperty("children")
    private String children;
    @JsonProperty("restrictions")
    private String restrictions;
    @JsonProperty("descendants")
    private String descendants;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("childTypes")
    public String getChildTypes() {
        return childTypes;
    }

    @JsonProperty("childTypes")
    public void setChildTypes(String childTypes) {
        this.childTypes = childTypes;
    }

    @JsonProperty("container")
    public String getContainer() {
        return container;
    }

    @JsonProperty("container")
    public void setContainer(String container) {
        this.container = container;
    }

    @JsonProperty("operations")
    public String getOperations() {
        return operations;
    }

    @JsonProperty("operations")
    public void setOperations(String operations) {
        this.operations = operations;
    }

    @JsonProperty("children")
    public String getChildren() {
        return children;
    }

    @JsonProperty("children")
    public void setChildren(String children) {
        this.children = children;
    }

    @JsonProperty("restrictions")
    public String getRestrictions() {
        return restrictions;
    }

    @JsonProperty("restrictions")
    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    @JsonProperty("descendants")
    public String getDescendants() {
        return descendants;
    }

    @JsonProperty("descendants")
    public void setDescendants(String descendants) {
        this.descendants = descendants;
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
