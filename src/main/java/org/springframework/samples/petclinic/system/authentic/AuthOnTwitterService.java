package org.springframework.samples.petclinic.system.authentic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.samples.petclinic.system.authentic.entity.TwitterRequestTokenResponse;
import org.springframework.samples.petclinic.system.authentic.properties.TwitterAPIUrlProperties;
import org.springframework.stereotype.Service;

@Service
public class AuthOnTwitterService {

	@Autowired
	public AuthOnTwitterService(TwitterRequestTokenGateway twitterRequestTokenGateway,
			TwitterAPIUrlProperties twitterAPIUrlProperties) {
		this.twitterRequestTokenGateway = twitterRequestTokenGateway;
		this.twitterAPIUrlProperties = twitterAPIUrlProperties;
	}

	TwitterRequestTokenGateway twitterRequestTokenGateway;

	TwitterAPIUrlProperties twitterAPIUrlProperties;

	@Value("${callback_url}")
	public String callbackURL;

	public String generateAuthURL() {
		TwitterRequestTokenResponse requestToken = twitterRequestTokenGateway.fetchAPI(callbackURL, "");
		String authUrl = twitterAPIUrlProperties.getAuthenticate();
		String urlWithParam = authUrl + "?oauth_token=" + requestToken.getOauthToken();

		return urlWithParam;
	}

}
