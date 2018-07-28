
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

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "results", "start", "limit", "size", "totalSize", "cqlQuery", "searchDuration", "_links" })
public class Results {

	@JsonProperty("results")
	private List<Result> results = null;
	@JsonProperty("start")
	private Integer start;
	@JsonProperty("limit")
	private Integer limit;
	@JsonProperty("size")
	private Integer size;
	@JsonProperty("totalSize")
	private Integer totalSize;
	@JsonProperty("cqlQuery")
	private String cqlQuery;
	@JsonProperty("searchDuration")
	private Integer searchDuration;
	@JsonProperty("_links")
	private Links_ links;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("results")
	public List<Result> getResults() {
		return results;
	}

	@JsonProperty("results")
	public void setResults(List<Result> results) {
		this.results = results;
	}

	@JsonProperty("start")
	public Integer getStart() {
		return start;
	}

	@JsonProperty("start")
	public void setStart(Integer start) {
		this.start = start;
	}

	@JsonProperty("limit")
	public Integer getLimit() {
		return limit;
	}

	@JsonProperty("limit")
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	@JsonProperty("size")
	public Integer getSize() {
		return size;
	}

	@JsonProperty("size")
	public void setSize(Integer size) {
		this.size = size;
	}

	@JsonProperty("totalSize")
	public Integer getTotalSize() {
		return totalSize;
	}

	@JsonProperty("totalSize")
	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}

	@JsonProperty("cqlQuery")
	public String getCqlQuery() {
		return cqlQuery;
	}

	@JsonProperty("cqlQuery")
	public void setCqlQuery(String cqlQuery) {
		this.cqlQuery = cqlQuery;
	}

	@JsonProperty("searchDuration")
	public Integer getSearchDuration() {
		return searchDuration;
	}

	@JsonProperty("searchDuration")
	public void setSearchDuration(Integer searchDuration) {
		this.searchDuration = searchDuration;
	}

	@JsonProperty("_links")
	public Links_ getLinks() {
		return links;
	}

	@JsonProperty("_links")
	public void setLinks(Links_ links) {
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
