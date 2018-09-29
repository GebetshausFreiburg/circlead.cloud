
package org.rogatio.circlead.control.synchronizer.atlassian.search;

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
 * The Class Links_.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "base",
    "context"
})
public class Links_ {

    /** The base. */
    @JsonProperty("base")
    private String base;
    
    /** The context. */
    @JsonProperty("context")
    private String context;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
