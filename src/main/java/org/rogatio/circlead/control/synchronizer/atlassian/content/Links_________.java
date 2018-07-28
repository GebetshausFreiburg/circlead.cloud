
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
    "editui",
    "webui",
    "context",
    "self",
    "tinyui",
    "collection",
    "base"
})
public class Links_________ {

    @JsonProperty("editui")
    private String editui;
    @JsonProperty("webui")
    private String webui;
    @JsonProperty("context")
    private String context;
    @JsonProperty("self")
    private String self;
    @JsonProperty("tinyui")
    private String tinyui;
    @JsonProperty("collection")
    private String collection;
    @JsonProperty("base")
    private String base;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("editui")
    public String getEditui() {
        return editui;
    }

    @JsonProperty("editui")
    public void setEditui(String editui) {
        this.editui = editui;
    }

    @JsonProperty("webui")
    public String getWebui() {
        return webui;
    }

    @JsonProperty("webui")
    public void setWebui(String webui) {
        this.webui = webui;
    }

    @JsonProperty("context")
    public String getContext() {
        return context;
    }

    @JsonProperty("context")
    public void setContext(String context) {
        this.context = context;
    }

    @JsonProperty("self")
    public String getSelf() {
        return self;
    }

    @JsonProperty("self")
    public void setSelf(String self) {
        this.self = self;
    }

    @JsonProperty("tinyui")
    public String getTinyui() {
        return tinyui;
    }

    @JsonProperty("tinyui")
    public void setTinyui(String tinyui) {
        this.tinyui = tinyui;
    }

    @JsonProperty("collection")
    public String getCollection() {
        return collection;
    }

    @JsonProperty("collection")
    public void setCollection(String collection) {
        this.collection = collection;
    }

    @JsonProperty("base")
    public String getBase() {
        return base;
    }

    @JsonProperty("base")
    public void setBase(String base) {
        this.base = base;
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
