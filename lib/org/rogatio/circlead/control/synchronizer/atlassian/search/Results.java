
package org.rogatio.circlead.control.synchronizer.atlassian.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

// TODO: Auto-generated Javadoc
/**
 * The Class Results.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "results", "start", "limit", "size", "totalSize", "cqlQuery", "searchDuration", "_links" })
public class Results {

	/** The results. */
	@JsonProperty("results")
	private List<Result> results = null;
	
	/** The start. */
	@JsonProperty("start")
	private Integer start;
	
	/** The limit. */
	@JsonProperty("limit")
	private Integer limit;
	
	/** The size. */
	@JsonProperty("size")
	private Integer size;
	
	/** The total size. */
	@JsonProperty("totalSize")
	private Integer totalSize;
	
	/** The cql query. */
	@JsonProperty("cqlQuery")
	private String cqlQuery;
	
	/** The search duration. */
	@JsonProperty("searchDuration")
	private Integer searchDuration;
	
	/** The links. */
	@JsonProperty("_links")
	private Links_ links;
	
	/** The additional properties. */
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * Gets the results.
	 *
	 * @return the results
	 */
	@JsonProperty("results")
	public List<Result> getResults() {
		return results;
	}

	/**
	 * Sets the results.
	 *
	 * @param results the new results
	 */
	@JsonProperty("results")
	public void setResults(List<Result> results) {
		this.results = results;
	}

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	@JsonProperty("start")
	public Integer getStart() {
		return start;
	}

	/**
	 * Sets the start.
	 *
	 * @param start the new start
	 */
	@JsonProperty("start")
	public void setStart(Integer start) {
		this.start = start;
	}

	/**
	 * Gets the limit.
	 *
	 * @return the limit
	 */
	@JsonProperty("limit")
	public Integer getLimit() {
		return limit;
	}

	/**
	 * Sets the limit.
	 *
	 * @param limit the new limit
	 */
	@JsonProperty("limit")
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	@JsonProperty("size")
	public Integer getSize() {
		return size;
	}

	/**
	 * Sets the size.
	 *
	 * @param size the new size
	 */
	@JsonProperty("size")
	public void setSize(Integer size) {
		this.size = size;
	}

	/**
	 * Gets the total size.
	 *
	 * @return the total size
	 */
	@JsonProperty("totalSize")
	public Integer getTotalSize() {
		return totalSize;
	}

	/**
	 * Sets the total size.
	 *
	 * @param totalSize the new total size
	 */
	@JsonProperty("totalSize")
	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}

	/**
	 * Gets the cql query.
	 *
	 * @return the cql query
	 */
	@JsonProperty("cqlQuery")
	public String getCqlQuery() {
		return cqlQuery;
	}

	/**
	 * Sets the cql query.
	 *
	 * @param cqlQuery the new cql query
	 */
	@JsonProperty("cqlQuery")
	public void setCqlQuery(String cqlQuery) {
		this.cqlQuery = cqlQuery;
	}

	/**
	 * Gets the search duration.
	 *
	 * @return the search duration
	 */
	@JsonProperty("searchDuration")
	public Integer getSearchDuration() {
		return searchDuration;
	}

	/**
	 * Sets the search duration.
	 *
	 * @param searchDuration the new search duration
	 */
	@JsonProperty("searchDuration")
	public void setSearchDuration(Integer searchDuration) {
		this.searchDuration = searchDuration;
	}

	/**
	 * Gets the links.
	 *
	 * @return the links
	 */
	@JsonProperty("_links")
	public Links_ getLinks() {
		return links;
	}

	/**
	 * Sets the links.
	 *
	 * @param links the new links
	 */
	@JsonProperty("_links")
	public void setLinks(Links_ links) {
		this.links = links;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@JsonIgnore
	public String toString() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
		}
		return null;
	}
}
