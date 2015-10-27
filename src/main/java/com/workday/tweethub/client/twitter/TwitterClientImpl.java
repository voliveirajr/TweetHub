package com.workday.tweethub.client.twitter;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterObjectFactory;
import twitter4j.conf.ConfigurationBuilder;

import com.workday.tweethub.client.RequestProjectException;
import com.workday.tweethub.configuration.ProxyConfig;
import com.workday.tweethub.configuration.TwitterConfiguration;

@Getter@Setter
@Log4j
public class TwitterClientImpl implements TwitterClient {
	
	private TwitterConfiguration twitterConfiguration;
	private ProxyConfig proxyConfig;
	private ConfigurationBuilder cb;

	public TwitterClientImpl(TwitterConfiguration config, ProxyConfig proxyConfig) {
		this.twitterConfiguration = config;
		this.proxyConfig = proxyConfig;
	}

	public List<String> request(String projectName) throws RequestProjectException {
		
		List<String> statuses = new ArrayList<String>();
		
		try {
				ConfigurationBuilder cb = new ConfigurationBuilder();
				cb.setDebugEnabled(log.isDebugEnabled())
					.setOAuthConsumerKey(twitterConfiguration.getKey())
					.setOAuthConsumerSecret(twitterConfiguration.getSecret())
					.setOAuthAccessToken(twitterConfiguration.getToken())
					.setOAuthAccessTokenSecret(twitterConfiguration.getTokensecret())
					.setJSONStoreEnabled(true);
			
				if (proxyConfig != null && proxyConfig.getUrl() != null && proxyConfig.getPort() != null) {
				cb.setHttpProxyHost(proxyConfig.getUrl())
						.setHttpProxyPort(proxyConfig.getPort());
			}			
			
			TwitterFactory tf = new TwitterFactory(cb.build());
			Twitter twitter = tf.getInstance();
			Query query = new Query(projectName);
			QueryResult result = twitter.search(query);
			for (Status status : result.getTweets()) {	
				statuses.add(TwitterObjectFactory.getRawJSON(status));
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		log.debug("returned tweets "+ statuses.size());
		return statuses;
	}

}
