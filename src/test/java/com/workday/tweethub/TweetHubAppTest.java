package com.workday.tweethub;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.workday.tweethub.client.github.GitHubClient;
import com.workday.tweethub.client.twitter.TwitterClient;
import com.workday.tweethub.configuration.GitHubConfiguration;
import com.workday.tweethub.configuration.ProxyConfig;
import com.workday.tweethub.configuration.TwitterConfiguration;
import com.workday.tweethub.data.github.GHProject;

@RunWith(MockitoJUnitRunner.class)
public class TweetHubAppTest {
	
	@Mock
	GitHubClient gitHubClient;
	@Mock
	GitHubConfiguration gitHubConfiguration;
	@Mock
	ProxyConfig proxyConfig;
	@Mock
	TwitterConfiguration twitterConfiguration;
	@Mock
	TwitterClient twitterClient;
		
	@Test
	public void testRun() throws Exception{
		
		Mockito.when(twitterConfiguration.getLimit()).thenReturn(2);
		Mockito.when(gitHubClient.request(Mockito.anyString())).thenReturn(getMockProjects());
		Mockito.when(twitterClient.request(Mockito.anyString())).thenReturn(getMockTweets());
		
		TweetHubApp app = new TweetHubApp();
		app.setGitHubClient(gitHubClient);
		app.setGitHubConfiguration(gitHubConfiguration);
		app.setProxyConfig(proxyConfig);
		app.setTwiterConfig(twitterConfiguration);
		app.setTwitterClient(twitterClient);		
		
		app.run("tetris");
		
		Assert.assertEquals(2, app.getStatusList().size());
		
		for (String msg : app.getStatusList()) {
			Assert.assertEquals("tetris", msg);
		}
	}

	private List<String> getMockTweets() {
		return Arrays.asList("tetris", "tetris");
	}

	private List<GHProject> getMockProjects() {
		GHProject proj = new GHProject();
		proj.setName("tetris");
		return Arrays.asList(proj); 
	}

}
