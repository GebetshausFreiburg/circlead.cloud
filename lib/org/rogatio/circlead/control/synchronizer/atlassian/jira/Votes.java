
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
 * The Class Votes.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "self",
    "votes",
    "hasVoted"
})
public class Votes {

    /** The self. */
    @JsonProperty("self")
    private String self;
    
    /** The votes. */
    @JsonProperty("votes")
    private Integer votes;
    
    /** The has voted. */
    @JsonProperty("hasVoted")
    private Boolean hasVoted;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     * Gets the votes.
     *
     * @return the votes
     */
    @JsonProperty("votes")
    public Integer getVotes() {
        return votes;
    }

    /**
     * Sets the votes.
     *
     * @param votes the new votes
     */
    @JsonProperty("votes")
    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    /**
     * Gets the checks for voted.
     *
     * @return the checks for voted
     */
    @JsonProperty("hasVoted")
    public Boolean getHasVoted() {
        return hasVoted;
    }

    /**
     * Sets the checks for voted.
     *
     * @param hasVoted the new checks for voted
     */
    @JsonProperty("hasVoted")
    public void setHasVoted(Boolean hasVoted) {
        this.hasVoted = hasVoted;
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
