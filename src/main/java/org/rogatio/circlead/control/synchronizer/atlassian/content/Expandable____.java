
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
 * The Class Expandable____.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "previousVersion",
    "contributors",
    "nextVersion"
})
public class Expandable____ {

    /** The previous version. */
    @JsonProperty("previousVersion")
    private String previousVersion;
    
    /** The contributors. */
    @JsonProperty("contributors")
    private String contributors;
    
    /** The next version. */
    @JsonProperty("nextVersion")
    private String nextVersion;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the previous version.
     *
     * @return the previous version
     */
    @JsonProperty("previousVersion")
    public String getPreviousVersion() {
        return previousVersion;
    }

    /**
     * Sets the previous version.
     *
     * @param previousVersion the new previous version
     */
    @JsonProperty("previousVersion")
    public void setPreviousVersion(String previousVersion) {
        this.previousVersion = previousVersion;
    }

    /**
     * Gets the contributors.
     *
     * @return the contributors
     */
    @JsonProperty("contributors")
    public String getContributors() {
        return contributors;
    }

    /**
     * Sets the contributors.
     *
     * @param contributors the new contributors
     */
    @JsonProperty("contributors")
    public void setContributors(String contributors) {
        this.contributors = contributors;
    }

    /**
     * Gets the next version.
     *
     * @return the next version
     */
    @JsonProperty("nextVersion")
    public String getNextVersion() {
        return nextVersion;
    }

    /**
     * Sets the next version.
     *
     * @param nextVersion the new next version
     */
    @JsonProperty("nextVersion")
    public void setNextVersion(String nextVersion) {
        this.nextVersion = nextVersion;
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
