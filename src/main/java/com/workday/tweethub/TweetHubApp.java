package com.workday.tweethub;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.workday.tweethub.client.github.GitHubClient;
import com.workday.tweethub.client.github.GitHubClientImpl;
import com.workday.tweethub.client.twitter.TwitterClient;
import com.workday.tweethub.client.twitter.TwitterClientImpl;
import com.workday.tweethub.configuration.GitHubConfiguration;
import com.workday.tweethub.configuration.ProxyConfig;
import com.workday.tweethub.configuration.TwitterConfiguration;
import com.workday.tweethub.data.github.GHProject;

@Log4j
@Getter@Setter
@Configuration
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class TweetHubApp implements CommandLineRunner {

	GitHubClient gitHubClient;
	TwitterClient twitterClient;
	
	@Autowired
	GitHubConfiguration gitHubConfiguration;
	
	@Autowired
	ProxyConfig proxyConfig;
	
	@Autowired
	TwitterConfiguration twiterConfig;
	
	List<String> statusList;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TweetHubApp.class);
		app.setShowBanner(false);
		app.run(args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		
		if (gitHubClient == null) {
			gitHubClient = new GitHubClientImpl(gitHubConfiguration, proxyConfig);
		}

		if (twitterClient == null) {
			twitterClient = new TwitterClientImpl(twiterConfig, proxyConfig);
		}
		
		for (String key : arg0) {
			List<GHProject> projectList = gitHubClient.request(key);
			
			int limit;
			if(twiterConfig.getLimit()!=null && twiterConfig.getLimit() <= projectList.size()){
				//API search restriction, default value is 10
				limit = twiterConfig.getLimit();
			}else{
				limit = projectList.size();
			}
			log.debug("fetching limited by "+limit);
			
			statusList = new ArrayList<String>();
			for (int i = 0;i<limit;i++) {
				statusList.addAll(twitterClient.request(projectList.get(i).getName()));	
			}
			
			for (String status : statusList) {
				//Missing specification on assesment what is the desired output, for while printing on console.
				System.out.println(status);
			}
		}
		log.info("finished");
	}
}
