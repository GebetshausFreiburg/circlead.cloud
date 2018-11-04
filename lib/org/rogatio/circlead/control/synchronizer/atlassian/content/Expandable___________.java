
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
 * The Class Expandable___________.
 */
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

    /** The child types. */
    @JsonProperty("childTypes")
    private String childTypes;
    
    /** The container. */
    @JsonProperty("container")
    private String container;
    
    /** The operations. */
    @JsonProperty("operations")
    private String operations;
    
    /** The children. */
    @JsonProperty("children")
    private String children;
    
    /** The restrictions. */
    @JsonProperty("restrictions")
    private String restrictions;
    
    /** The descendants. */
    @JsonProperty("descendants")
    private String descendants;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the child types.
     *
     * @return the child types
     */
    @JsonProperty("childTypes")
    public String getChildTypes() {
        return childTypes;
    }

    /**
     * Sets the child types.
     *
     * @param childTypes the new child types
     */
    @JsonProperty("childTypes")
    public void setChildTypes(String childTypes) {
        this.childTypes = childTypes;
    }

    /**
     * Gets the container.
     *
     * @return the container
     */
    @JsonProperty("container")
    public String getContainer() {
        return container;
    }

    /**
     * Sets the container.
     *
     * @param container the new container
     */
    @JsonProperty("container")
    public void setContainer(String container) {
        this.container = container;
    }

    /**
     * Gets the operations.
     *
     * @return the operations
     */
    @JsonProperty("operations")
    public String getOperations() {
        return operations;
    }

    /**
     * Sets the operations.
     *
     * @param operations the new operations
     */
    @JsonProperty("operations")
    public void setOperations(String operations) {
        this.operations = operations;
    }

    /**
     * Gets the children.
     *
     * @return the children
     */
    @JsonProperty("children")
    public String getChildren() {
        return children;
    }

    /**
     * Sets the children.
     *
     * @param children the new children
     */
    @JsonProperty("children")
    public void setChildren(String children) {
        this.children = children;
    }

    /**
     * Gets the restrictions.
     *
     * @return the restrictions
     */
    @JsonProperty("restrictions")
    public String getRestrictions() {
        return restrictions;
    }

    /**
     * Sets the restrictions.
     *
     * @param restrictions the new restrictions
     */
    @JsonProperty("restrictions")
    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    /**
     * Gets the descendants.
     *
     * @return the descendants
     */
    @JsonProperty("descendants")
    public String getDescendants() {
        return descendants;
    }

    /**
     * Sets the descendants.
     *
     * @param descendants the new descendants
     */
    @JsonProperty("descendants")
    public void setDescendants(String descendants) {
        this.descendants = descendants;
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
