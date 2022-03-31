package org.springframework.samples.petclinic.system.authentic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.system.authentic.entity.TwitterRequestTokenRequest;
import org.springframework.samples.petclinic.system.authentic.entity.TwitterRequestTokenResponse;
import org.springframework.samples.petclinic.system.authentic.properties.TwitterAPITokenProperties;
import org.springframework.samples.petclinic.system.authentic.properties.TwitterAPIUrlProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TwitterRequestTokenGateway {

	private RestTemplate restTemplate;

	private TwitterAPITokenProperties twitterAPITokenProperties;

	private TwitterAPIUrlProperties twitterAPIUrlProperties;

	@Autowired
	public TwitterRequestTokenGateway(RestTemplate restTemplate, TwitterAPITokenProperties twitterAPITokenProperties,
			TwitterAPIUrlProperties twitterAPIUrlProperties) {
		this.restTemplate = restTemplate;
		this.twitterAPITokenProperties = twitterAPITokenProperties;
		this.twitterAPIUrlProperties = twitterAPIUrlProperties;
	}

	public TwitterRequestTokenResponse fetchAPI(String oAuthCallback, String xAuthAccessType) {
		TwitterRequestTokenRequest request = new TwitterRequestTokenRequest(twitterAPITokenProperties,
				twitterAPIUrlProperties, oAuthCallback);
		ResponseEntity<String> responseEntity = restTemplate.exchange(request.getRequetEntity(), String.class);

		TwitterRequestTokenResponse response = new TwitterRequestTokenResponse(responseEntity.getBody());
		return response;
	}

}
