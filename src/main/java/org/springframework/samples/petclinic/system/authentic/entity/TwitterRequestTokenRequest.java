package org.springframework.samples.petclinic.system.authentic.entity;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.samples.petclinic.system.authentic.enums.OAuthAttrsEnum;
import org.springframework.samples.petclinic.system.authentic.properties.TwitterAPITokenProperties;
import org.springframework.samples.petclinic.system.authentic.properties.TwitterAPIUrlProperties;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class TwitterRequestTokenRequest extends RequestObject {

	private TwitterOAuthHeader twitterOAuthHeader;

	private RequestEntity<MultiValueMap<String, String>> requestEntity;

	public TwitterRequestTokenRequest(TwitterAPITokenProperties twitterAPITokenProperties,
			TwitterAPIUrlProperties twitterAPIUrlProperties, String oAuthCallback) {
		this.httpMethod = HttpRequestMethodEnum.POST;
		this.url = twitterAPIUrlProperties.getRequestToken();
		createOAuthHeader(twitterAPITokenProperties, oAuthCallback);

		createRequestEntity();
	}

	public void setParamXAuthAccessType(String xAuthAccessType) {
		param.put("xAuthAccessType", xAuthAccessType);
	}

	private void createOAuthHeader(TwitterAPITokenProperties twitterAPITokenProperties, String oAuthCallback) {
		twitterOAuthHeader = new TwitterOAuthHeader(twitterAPITokenProperties, this.httpMethod, this.url);
		twitterOAuthHeader.addOauthParametor(OAuthAttrsEnum.oauth_callback, oAuthCallback);
		twitterOAuthHeader.createSignature(param, "");
	}

	private void createRequestEntity() {
		MultiValueMap<String, String> reqParam = new LinkedMultiValueMap<>();

		param.entrySet().forEach(e -> reqParam.add(e.getKey(), e.getValue()));
		requestEntity = RequestEntity.post(url).contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.header("Authorization", twitterOAuthHeader.toString()).body(reqParam);
	}

	public RequestEntity<MultiValueMap<String, String>> getRequetEntity() {
		return requestEntity;
	}

}
