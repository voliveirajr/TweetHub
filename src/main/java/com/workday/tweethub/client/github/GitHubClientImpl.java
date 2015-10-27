package com.workday.tweethub.client.github;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.workday.tweethub.client.RequestProjectException;
import com.workday.tweethub.configuration.GitHubConfiguration;
import com.workday.tweethub.configuration.ProxyConfig;
import com.workday.tweethub.data.github.GHProject;
import com.workday.tweethub.data.github.GHResult;

@Log4j
public class GitHubClientImpl implements GitHubClient{
	
	@Getter@Setter
	private GitHubConfiguration gitHubConfiguration;
	@Getter@Setter
	private ProxyConfig proxyConfig;
	
	@Getter@Setter
	private  RestTemplate restTemplate = new RestTemplate();
	
	public GitHubClientImpl(GitHubConfiguration config, ProxyConfig proxyConfig) {
		this.gitHubConfiguration = config;
		this.proxyConfig = proxyConfig;
	}
	
	public List<GHProject> request(String projectName) throws RequestProjectException {
		
		if (proxyConfig != null && proxyConfig.getUrl() != null && proxyConfig.getPort() != null) {
			SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
			Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress(proxyConfig.getUrl(), proxyConfig.getPort()));
			requestFactory.setProxy(proxy);
			restTemplate.setRequestFactory(requestFactory);
		}
		
		try {
						
			ResponseEntity<GHResult> response = restTemplate.getForEntity(new StringBuilder(gitHubConfiguration.getUrl()).append(projectName).toString(), GHResult.class);
			
			GHResult result = response.getBody();
			
			log.debug("github request response: "+response.getStatusCode().toString());
			log.debug("Returned Projects: "+result.getProjects().size());
			return result.getProjects();
			
		} catch (Exception e) {
			throw new RequestProjectException("API request not succeded", e);
		}

	}
}
