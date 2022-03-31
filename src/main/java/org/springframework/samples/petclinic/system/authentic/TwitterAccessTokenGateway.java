package org.springframework.samples.petclinic.system.authentic;

import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.system.authentic.entity.TwitterAccessTokenRequest;
import org.springframework.samples.petclinic.system.authentic.entity.TwitterAccessTokenResponse;
import org.springframework.samples.petclinic.system.authentic.properties.TwitterAPITokenProperties;
import org.springframework.samples.petclinic.system.authentic.properties.TwitterAPIUrlProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TwitterAccessTokenGateway {

	private RestTemplate restTemplate;

	private TwitterAPITokenProperties twitterAPITokenProperties;

	private TwitterAPIUrlProperties twitterAPIUrlProperties;

	public TwitterAccessTokenGateway(RestTemplate restTemplate, TwitterAPITokenProperties twitterAPITokenProperties,
			TwitterAPIUrlProperties twitterAPIUrlProperties) {
		this.restTemplate = restTemplate;
		this.twitterAPITokenProperties = twitterAPITokenProperties;
		this.twitterAPIUrlProperties = twitterAPIUrlProperties;
	}

	public TwitterAccessTokenResponse fetchAPI(String oauthToken, String oauthVerifier) {
		TwitterAccessTokenRequest request = new TwitterAccessTokenRequest(twitterAPITokenProperties,
				twitterAPIUrlProperties, oauthToken, oauthVerifier);
		TwitterAccessTokenResponse response = null;
		try {
			ResponseEntity<String> responseEntity = restTemplate.exchange(request.getRequetEntity(), String.class);
			response = new TwitterAccessTokenResponse(responseEntity.getBody());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

}