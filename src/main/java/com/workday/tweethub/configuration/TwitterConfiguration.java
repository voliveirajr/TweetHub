package com.workday.tweethub.configuration;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter@Setter
public class TwitterConfiguration {
	
	private String key;
	private String secret;
	private String token;
	private String tokensecret;
	private Integer limit;
	
	@Autowired
	public TwitterConfiguration(@Value("${twitter.limit}") Integer limit,
								@Value("${twitter.key}") String key,
								@Value("${twitter.secret}") String secret,
								@Value("${twitter.token}") String token,
								@Value("${twitter.tokensecret}") String tokensecret){
		this.key = key;
		this.secret = secret;
		this.token = token;
		this.tokensecret = tokensecret;
		this.limit = limit;
	}

}
