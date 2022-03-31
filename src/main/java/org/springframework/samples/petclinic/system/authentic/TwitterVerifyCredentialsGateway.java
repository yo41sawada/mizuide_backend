package org.springframework.samples.petclinic.system.authentic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.system.authentic.entity.TwitterVerifyCredentialsRequest;
import org.springframework.samples.petclinic.system.authentic.entity.TwitterVerifyCredentialsResponse;
import org.springframework.samples.petclinic.system.authentic.properties.TwitterAPITokenProperties;
import org.springframework.samples.petclinic.system.authentic.properties.TwitterAPIUrlProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TwitterVerifyCredentialsGateway {

	private RestTemplate restTemplate;

	private TwitterAPITokenProperties twitterAPITokenProperties;

	private TwitterAPIUrlProperties twitterAPIUrlProperties;

	@Autowired
	public TwitterVerifyCredentialsGateway(RestTemplate restTemplate,
			TwitterAPITokenProperties twitterAPITokenProperties, TwitterAPIUrlProperties twitterAPIUrlProperties) {
		this.restTemplate = restTemplate;
		this.twitterAPITokenProperties = twitterAPITokenProperties;
		this.twitterAPIUrlProperties = twitterAPIUrlProperties;
	}

	public TwitterVerifyCredentialsResponse fetchAPI(String oauthToken, String oauthSecretToken) {
		TwitterVerifyCredentialsRequest request = new TwitterVerifyCredentialsRequest(twitterAPITokenProperties,
				twitterAPIUrlProperties, oauthToken, oauthSecretToken);

		TwitterVerifyCredentialsResponse responese = restTemplate
				.exchange(request.getRequetEntity(), TwitterVerifyCredentialsResponse.class).getBody();
		return responese;
	}

}
