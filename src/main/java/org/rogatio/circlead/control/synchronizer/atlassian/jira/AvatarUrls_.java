
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
 * The Class AvatarUrls_.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "48x48",
    "24x24",
    "16x16",
    "32x32"
})
public class AvatarUrls_ {

    /** The 48 x 48. */
    @JsonProperty("48x48")
    private String _48x48;
    
    /** The 24 x 24. */
    @JsonProperty("24x24")
    private String _24x24;
    
    /** The 16 x 16. */
    @JsonProperty("16x16")
    private String _16x16;
    
    /** The 32 x 32. */
    @JsonProperty("32x32")
    private String _32x32;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the 48 x 48.
     *
     * @return the 48 x 48
     */
    @JsonProperty("48x48")
    public String get48x48() {
        return _48x48;
    }

    /**
     * Sets the 48 x 48.
     *
     * @param _48x48 the new 48 x 48
     */
    @JsonProperty("48x48")
    public void set48x48(String _48x48) {
        this._48x48 = _48x48;
    }

    /**
     * Gets the 24 x 24.
     *
     * @return the 24 x 24
     */
    @JsonProperty("24x24")
    public String get24x24() {
        return _24x24;
    }

    /**
     * Sets the 24 x 24.
     *
     * @param _24x24 the new 24 x 24
     */
    @JsonProperty("24x24")
    public void set24x24(String _24x24) {
        this._24x24 = _24x24;
    }

    /**
     * Gets the 16 x 16.
     *
     * @return the 16 x 16
     */
    @JsonProperty("16x16")
    public String get16x16() {
        return _16x16;
    }

    /**
     * Sets the 16 x 16.
     *
     * @param _16x16 the new 16 x 16
     */
    @JsonProperty("16x16")
    public void set16x16(String _16x16) {
        this._16x16 = _16x16;
    }

    /**
     * Gets the 32 x 32.
     *
     * @return the 32 x 32
     */
    @JsonProperty("32x32")
    public String get32x32() {
        return _32x32;
    }

    /**
     * Sets the 32 x 32.
     *
     * @param _32x32 the new 32 x 32
     */
    @JsonProperty("32x32")
    public void set32x32(String _32x32) {
        this._32x32 = _32x32;
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
