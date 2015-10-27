package com.workday.tweethub.client.github;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.workday.tweethub.client.RequestProjectException;
import com.workday.tweethub.configuration.GitHubConfiguration;
import com.workday.tweethub.configuration.ProxyConfig;
import com.workday.tweethub.data.github.GHProject;
import com.workday.tweethub.data.github.GHResult;

public class GitHubClientImplTest {

	@Test
	public void testListWithRestTemplate() throws Exception {
		
		GitHubConfiguration config = new GitHubConfiguration("Foo/");
		ProxyConfig proxy = new ProxyConfig("", null);
		
		ArrayList<GHProject> sampleList = new ArrayList<GHProject>();
		GHProject sample = new GHProject();
		sample.setName("name");
		sample.setFullName("fullName");
		sample.setId(1);
		sampleList.add(sample);
		sampleList.add(sample);
		GHResult result = new GHResult();
		result.setProjects(sampleList);
		
		RestTemplate restTemplate = new RestTemplate();
		MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
		mockServer.expect(requestTo("Foo/Foo")).andRespond(withSuccess(getJson(), MediaType.APPLICATION_JSON));
		
		GitHubClientImpl gitHubClient = new GitHubClientImpl(config, proxy);

		gitHubClient.setRestTemplate(restTemplate);
		List<GHProject> projs = gitHubClient.request("Foo");

		Assert.assertTrue(projs.size() == 1);
		
		for (GHProject project : projs) {			
			Assert.assertEquals("tetris", project.getName());
			Assert.assertEquals("tdd-elevator-training/tetris", project.getFullName());
			Assert.assertEquals(new Integer(3477759), project.getId());		
		}
	}
	
	@Test(expected = RequestProjectException.class)
	public void test400() throws Exception {
		
		GitHubConfiguration config = new GitHubConfiguration("Foo/");
		ProxyConfig proxy = new ProxyConfig("", null);
		
		RestTemplate restTemplate = new RestTemplate();
		MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
		mockServer.expect(requestTo("Foo/Foo")).andRespond(withStatus(HttpStatus.FORBIDDEN));

		GitHubClientImpl gitHubClient = new GitHubClientImpl(config, proxy);
		gitHubClient.setRestTemplate(restTemplate);
		gitHubClient.request("Foo");
	}
	
	@Test(expected = RequestProjectException.class)
	public void testWithError() throws Exception {
		GitHubConfiguration config = new GitHubConfiguration("Foo/");
		ProxyConfig proxy = new ProxyConfig("", null);
		
		RestTemplate restTemplate = new RestTemplate();
		MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
		mockServer.expect(requestTo("Foo/Foo")).andRespond(withServerError());

		GitHubClientImpl gitHubClient = new GitHubClientImpl(config, proxy);
		gitHubClient.setRestTemplate(restTemplate);
		gitHubClient.request("Foo");
	}
	
	public String getJson(){
		return "{\"total_count\": 5754, \"incomplete_results\": false,  \"items\": [  { \"id\": 3477759, \"name\": \"tetris\",  \"full_name\": \"tdd-elevator-training/tetris\", \"owner\": {  \"login\": \"tdd-elevator-training\",  \"id\": 1227498,  \"avatar_url\": \"https://avatars.githubusercontent.com/u/1227498?v=3\",  \"gravatar_id\": \"\",  \"url\": \"https://api.github.com/users/tdd-elevator-training\", \"html_url\": \"https://github.com/tdd-elevator-training\",  \"followers_url\": \"https://api.github.com/users/tdd-elevator-training/followers\", \"following_url\": \"https://api.github.com/users/tdd-elevator-training/following{/other_user}\",  \"gists_url\": \"https://api.github.com/users/tdd-elevator-training/gists{/gist_id}\",   \"starred_url\": \"https://api.github.com/users/tdd-elevator-training/starred{/owner}{/repo}\",  \"subscriptions_url\": \"https://api.github.com/users/tdd-elevator-training/subscriptions\",  \"organizations_url\": \"https://api.github.com/users/tdd-elevator-training/orgs\",  \"repos_url\": \"https://api.github.com/users/tdd-elevator-training/repos\", \"events_url\": \"https://api.github.com/users/tdd-elevator-training/events{/privacy}\", \"received_events_url\": \"https://api.github.com/users/tdd-elevator-training/received_events\", \"type\": \"User\", \"site_admin\": false   },  \"private\": false  }]}";		
	}
}
