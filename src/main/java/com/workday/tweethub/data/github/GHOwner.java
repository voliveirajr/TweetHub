package com.workday.tweethub.data.github;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GHOwner {
	
	private String login;
	private Integer id;
	@JsonProperty("avatar_url")
	private String avatarUrl;
	@JsonProperty("gravatar_id")
	private String gravatarId;
	private String url;
	@JsonProperty("received_events_url")
	private String receivedEventsUrl;
	private String type;

}
