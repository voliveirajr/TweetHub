package com.workday.tweethub.client.github;

import java.util.List;

import com.workday.tweethub.client.RequestProjectException;
import com.workday.tweethub.data.github.GHProject;

public interface GitHubClient {
	/**
	 * Retrives a list of projects that match with the key informed
	 * @param projectName key to search city list
	 * @return
	 * @throws RequestProjectException
	 */
	public List<GHProject> request(String projectName) throws RequestProjectException;	
	
}
