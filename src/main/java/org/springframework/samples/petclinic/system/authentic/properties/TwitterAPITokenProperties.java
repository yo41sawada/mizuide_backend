package org.springframework.samples.petclinic.system.authentic.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TwitterAPITokenProperties {

	@Value("${APIKey}")
	private String APIKey;

	@Value("${APIKeySecret}")
	private String APIKeySecret;

	public String getAPIKey() {
		return this.APIKey;
	}

	public String getAPIKeySecret() {
		return this.APIKeySecret;
	}

}
