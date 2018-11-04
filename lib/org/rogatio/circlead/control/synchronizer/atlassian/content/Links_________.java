
package org.rogatio.circlead.control.synchronizer.atlassian.content;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

// TODO: Auto-generated Javadoc
/**
 * The Class Links_________.
 */
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

    /** The editui. */
    @JsonProperty("editui")
    private String editui;
    
    /** The webui. */
    @JsonProperty("webui")
    private String webui;
    
    /** The context. */
    @JsonProperty("context")
    private String context;
    
    /** The self. */
    @JsonProperty("self")
    private String self;
    
    /** The tinyui. */
    @JsonProperty("tinyui")
    private String tinyui;
    
    /** The collection. */
    @JsonProperty("collection")
    private String collection;
    
    /** The base. */
    @JsonProperty("base")
    private String base;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the editui.
     *
     * @return the editui
     */
    @JsonProperty("editui")
    public String getEditui() {
        return editui;
    }

    /**
     * Sets the editui.
     *
     * @param editui the new editui
     */
    @JsonProperty("editui")
    public void setEditui(String editui) {
        this.editui = editui;
    }

    /**
     * Gets the webui.
     *
     * @return the webui
     */
    @JsonProperty("webui")
    public String getWebui() {
        return webui;
    }

    /**
     * Sets the webui.
     *
     * @param webui the new webui
     */
    @JsonProperty("webui")
    public void setWebui(String webui) {
        this.webui = webui;
    }

    /**
     * Gets the context.
     *
     * @return the context
     */
    @JsonProperty("context")
    public String getContext() {
        return context;
    }

    /**
     * Sets the context.
     *
     * @param context the new context
     */
    @JsonProperty("context")
    public void setContext(String context) {
        this.context = context;
    }

    /**
     * Gets the self.
     *
     * @return the self
     */
    @JsonProperty("self")
    public String getSelf() {
        return self;
    }

    /**
     * Sets the self.
     *
     * @param self the new self
     */
    @JsonProperty("self")
    public void setSelf(String self) {
        this.self = self;
    }

    /**
     * Gets the tinyui.
     *
     * @return the tinyui
     */
    @JsonProperty("tinyui")
    public String getTinyui() {
        return tinyui;
    }

    /**
     * Sets the tinyui.
     *
     * @param tinyui the new tinyui
     */
    @JsonProperty("tinyui")
    public void setTinyui(String tinyui) {
        this.tinyui = tinyui;
    }

    /**
     * Gets the collection.
     *
     * @return the collection
     */
    @JsonProperty("collection")
    public String getCollection() {
        return collection;
    }

    /**
     * Sets the collection.
     *
     * @param collection the new collection
     */
    @JsonProperty("collection")
    public void setCollection(String collection) {
        this.collection = collection;
    }

    /**
     * Gets the base.
     *
     * @return the base
     */
    @JsonProperty("base")
    public String getBase() {
        return base;
    }

    /**
     * Sets the base.
     *
     * @param base the new base
     */
    @JsonProperty("base")
    public void setBase(String base) {
        this.base = base;
    }

    /**
     * Gets the additional properties.
     *
     * @return the additional properties
     */
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    /**
     * Sets the additional property.
     *
     * @param name the name
     * @param value the value
     */
    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
