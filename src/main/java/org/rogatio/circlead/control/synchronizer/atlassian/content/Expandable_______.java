
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
    "metadata",
    "operations",
    "children",
    "restrictions",
    "history",
    "ancestors",
    "body",
    "version",
    "descendants",
    "space"
})
public class Expandable_______ {

    @JsonProperty("childTypes")
    private String childTypes;
    @JsonProperty("container")
    private String container;
    @JsonProperty("metadata")
    private String metadata;
    @JsonProperty("operations")
    private String operations;
    @JsonProperty("children")
    private String children;
    @JsonProperty("restrictions")
    private String restrictions;
    @JsonProperty("history")
    private String history;
    @JsonProperty("ancestors")
    private String ancestors;
    @JsonProperty("body")
    private String body;
    @JsonProperty("version")
    private String version;
    @JsonProperty("descendants")
    private String descendants;
    @JsonProperty("space")
    private String space;
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

    @JsonProperty("metadata")
    public String getMetadata() {
        return metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(String metadata) {
        this.metadata = metadata;
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

    @JsonProperty("history")
    public String getHistory() {
        return history;
    }

    @JsonProperty("history")
    public void setHistory(String history) {
        this.history = history;
    }

    @JsonProperty("ancestors")
    public String getAncestors() {
        return ancestors;
    }

    @JsonProperty("ancestors")
    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }

    @JsonProperty("body")
    public String getBody() {
        return body;
    }

    @JsonProperty("body")
    public void setBody(String body) {
        this.body = body;
    }

    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(String version) {
        this.version = version;
    }

    @JsonProperty("descendants")
    public String getDescendants() {
        return descendants;
    }

    @JsonProperty("descendants")
    public void setDescendants(String descendants) {
        this.descendants = descendants;
    }

    @JsonProperty("space")
    public String getSpace() {
        return space;
    }

    @JsonProperty("space")
    public void setSpace(String space) {
        this.space = space;
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
