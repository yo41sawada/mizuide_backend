package org.springframework.samples.petclinic.system.authentic.entity;

public class TwitterRequestTokenResponse {

	public TwitterRequestTokenResponse(String resBody) {
		String[] resArray = resBody.split("&");
		for (String res : resArray) {
			String key = res.substring(0, res.indexOf("="));
			String value = res.substring(res.indexOf("=") + 1, res.length()).replace("\"", "");
			switch (key) {
			case "oauth_token":
				this.oauthToken = value;
				break;
			case "oauth_token_secret":
				this.oauthTokenSecret = value;
				break;
			case "oauth_callback_confirmed":
				this.oauthCallbackConfirmed = value;
				break;
			default:
				throw new RuntimeException("想定外の形式です 本文:" + resBody);
			}
		}

	}

	private String oauthToken;

	private String oauthTokenSecret;

	private String oauthCallbackConfirmed;

	public String getOauthToken() {
		return oauthToken;
	}

	public String getOauthTokenSecret() {
		return oauthTokenSecret;
	}

	public String getOauthCallbackConfirmed() {
		return oauthCallbackConfirmed;
	}

}
