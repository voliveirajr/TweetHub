package com.workday.tweethub.client.twitter;

import java.util.List;

import com.workday.tweethub.client.RequestProjectException;

public interface TwitterClient {

	public List<String> request(String projectName) throws RequestProjectException;

}
