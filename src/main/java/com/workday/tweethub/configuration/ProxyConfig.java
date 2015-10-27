package com.workday.tweethub.configuration;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProxyConfig {
	
	@Getter@Setter
	private String url;
	@Getter@Setter
	private Integer port;
	
	
	@Autowired
	public ProxyConfig(@Value("${proxy.url}") String url, @Value("${proxy.port}") Integer port){
		this.url = url;
		this.port = port;
	}

}
