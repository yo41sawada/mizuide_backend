package org.springframework.samples.petclinic.system.authentic.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TwitterAPIUrlProperties {

	@Value("${request_token}")
	private String requestToken;

	@Value("${authenticate}")
	private String authenticate;

	@Value("${access_token}")
	private String accessToken;

	@Value("${verify_credentials}")
	private String verifyCredentials;

	public String getRequestToken() {
		return requestToken;
	}

	public String getAuthenticate() {
		return authenticate;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getVerifyCredentials() {
		return verifyCredentials;
	}

}
