
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
    "id",
    "type",
    "status",
    "title",
    "space",
    "history",
    "version",
    "ancestors",
    "macroRenderedOutput",
    "body",
    "metadata",
    "extensions",
    "_expandable",
    "_links"
})
public class Page {

    @JsonProperty("id")
    private String id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("status")
    private String status;
    @JsonProperty("title")
    private String title;
    @JsonProperty("space")
    private Space space;
    @JsonProperty("history")
    private History history;
    @JsonProperty("version")
    private Version version;
    @JsonProperty("ancestors")
    private List<Ancestor> ancestors = null;
    @JsonProperty("macroRenderedOutput")
    private MacroRenderedOutput_ macroRenderedOutput;
    @JsonProperty("body")
    private Body body;
    @JsonProperty("metadata")
    private Metadata metadata;
    @JsonProperty("extensions")
    private Extensions_ extensions;
    @JsonProperty("_expandable")
    private Expandable___________ expandable;
    @JsonProperty("_links")
    private Links_________ links;
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

    @JsonProperty("space")
    public Space getSpace() {
        return space;
    }

    @JsonProperty("space")
    public void setSpace(Space space) {
        this.space = space;
    }

    @JsonProperty("history")
    public History getHistory() {
        return history;
    }

    @JsonProperty("history")
    public void setHistory(History history) {
        this.history = history;
    }

    @JsonProperty("version")
    public Version getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(Version version) {
        this.version = version;
    }

    @JsonProperty("ancestors")
    public List<Ancestor> getAncestors() {
        return ancestors;
    }

    @JsonProperty("ancestors")
    public void setAncestors(List<Ancestor> ancestors) {
        this.ancestors = ancestors;
    }

    @JsonProperty("macroRenderedOutput")
    public MacroRenderedOutput_ getMacroRenderedOutput() {
        return macroRenderedOutput;
    }

    @JsonProperty("macroRenderedOutput")
    public void setMacroRenderedOutput(MacroRenderedOutput_ macroRenderedOutput) {
        this.macroRenderedOutput = macroRenderedOutput;
    }

    @JsonProperty("body")
    public Body getBody() {
        return body;
    }

    @JsonProperty("body")
    public void setBody(Body body) {
        this.body = body;
    }

    @JsonProperty("metadata")
    public Metadata getMetadata() {
        return metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @JsonProperty("extensions")
    public Extensions_ getExtensions() {
        return extensions;
    }

    @JsonProperty("extensions")
    public void setExtensions(Extensions_ extensions) {
        this.extensions = extensions;
    }

    @JsonProperty("_expandable")
    public Expandable___________ getExpandable() {
        return expandable;
    }

    @JsonProperty("_expandable")
    public void setExpandable(Expandable___________ expandable) {
        this.expandable = expandable;
    }

    @JsonProperty("_links")
    public Links_________ getLinks() {
        return links;
    }

    @JsonProperty("_links")
    public void setLinks(Links_________ links) {
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
