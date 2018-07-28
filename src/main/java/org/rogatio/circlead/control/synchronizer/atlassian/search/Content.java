
package org.rogatio.circlead.control.synchronizer.atlassian.search;

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
    "id",
    "type",
    "status",
    "title",
    "childTypes",
    "macroRenderedOutput",
    "restrictions",
    "_expandable",
    "_links"
})
public class Content {

    @JsonProperty("id")
    private String id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("status")
    private String status;
    @JsonProperty("title")
    private String title;
    @JsonProperty("childTypes")
    private ChildTypes childTypes;
    @JsonProperty("macroRenderedOutput")
    private MacroRenderedOutput macroRenderedOutput;
    @JsonProperty("restrictions")
    private Restrictions restrictions;
    @JsonProperty("_expandable")
    private Expandable expandable;
    @JsonProperty("_links")
    private Links links;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("childTypes")
    public ChildTypes getChildTypes() {
        return childTypes;
    }

    @JsonProperty("childTypes")
    public void setChildTypes(ChildTypes childTypes) {
        this.childTypes = childTypes;
    }

    @JsonProperty("macroRenderedOutput")
    public MacroRenderedOutput getMacroRenderedOutput() {
        return macroRenderedOutput;
    }

    @JsonProperty("macroRenderedOutput")
    public void setMacroRenderedOutput(MacroRenderedOutput macroRenderedOutput) {
        this.macroRenderedOutput = macroRenderedOutput;
    }

    @JsonProperty("restrictions")
    public Restrictions getRestrictions() {
        return restrictions;
    }

    @JsonProperty("restrictions")
    public void setRestrictions(Restrictions restrictions) {
        this.restrictions = restrictions;
    }

    @JsonProperty("_expandable")
    public Expandable getExpandable() {
        return expandable;
    }

    @JsonProperty("_expandable")
    public void setExpandable(Expandable expandable) {
        this.expandable = expandable;
    }

    @JsonProperty("_links")
    public Links getLinks() {
        return links;
    }

    @JsonProperty("_links")
    public void setLinks(Links links) {
        this.links = links;
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
