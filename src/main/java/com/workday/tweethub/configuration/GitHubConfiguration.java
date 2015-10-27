package com.workday.tweethub.configuration;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter@Setter
public class GitHubConfiguration {
	
	private String url;
	
	@Autowired
	public GitHubConfiguration(@Value("${github.url}") String url){
		this.url = url;
	}
	
}
