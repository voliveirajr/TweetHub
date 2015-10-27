package com.workday.tweethub.data.github;

import java.util.List;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GHResult {	
	@JsonProperty("total_count")
	Integer totalCount;
	@JsonProperty("incomplete_results")
	Boolean incompleteResults;
	@JsonProperty("items")
	List<GHProject> projects;
}
