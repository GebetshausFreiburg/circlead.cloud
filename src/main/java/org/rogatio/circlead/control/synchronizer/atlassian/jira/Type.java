
package org.rogatio.circlead.control.synchronizer.atlassian.jira;

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
 * The Class Type.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "inward",
    "outward",
    "self"
})
public class Type {

    /** The id. */
    @JsonProperty("id")
    private String id;
    
    /** The name. */
    @JsonProperty("name")
    private String name;
    
    /** The inward. */
    @JsonProperty("inward")
    private String inward;
    
    /** The outward. */
    @JsonProperty("outward")
    private String outward;
    
    /** The self. */
    @JsonProperty("self")
    private String self;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the id.
     *
     * @return the id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the inward.
     *
     * @return the inward
     */
    @JsonProperty("inward")
    public String getInward() {
        return inward;
    }

    /**
     * Sets the inward.
     *
     * @param inward the new inward
     */
    @JsonProperty("inward")
    public void setInward(String inward) {
        this.inward = inward;
    }

    /**
     * Gets the outward.
     *
     * @return the outward
     */
    @JsonProperty("outward")
    public String getOutward() {
        return outward;
    }

    /**
     * Sets the outward.
     *
     * @param outward the new outward
     */
    @JsonProperty("outward")
    public void setOutward(String outward) {
        this.outward = outward;
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
