package com.workday.tweethub.data.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GHProject {
	
	private Integer id;
	private String name;
	@JsonProperty("full_name")
	private String fullName;
	private GHOwner owner;
	@JsonProperty("private")
	private Boolean isPrivate;
	@JsonProperty("html_url")
	private String htmlUrl;
	private String description;
	private Boolean fork;
	private String url;
	@JsonProperty("created_at")
	private String createdAt;
	@JsonProperty("updated_at")
	private String updatedAt;
	@JsonProperty("pushed_at")
	private String pushedAt;
	private String homepage;
	private Integer size;
	@JsonProperty("stargazers_count")
	private Integer stargazersCount;
	@JsonProperty("watchers_count")
	private Integer watchersCount;
	private String language;
	@JsonProperty("forks_count")
	private Integer forksCount;
	@JsonProperty("open_issues_count")
	private Integer openIssuesCount;
	@JsonProperty("master_branch")
	private String masterBranch;
	@JsonProperty("default_branch")
	private String defaultBranch;
	private Long score;

}
