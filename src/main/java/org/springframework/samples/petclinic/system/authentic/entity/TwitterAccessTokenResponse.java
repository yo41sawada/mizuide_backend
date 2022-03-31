package org.springframework.samples.petclinic.system.authentic.entity;

public class TwitterAccessTokenResponse {

	public TwitterAccessTokenResponse(String resBody) {
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
			case "user_id":
				this.userId = value;
				break;
			case "screen_name":
				this.screenName = value;
				break;
			default:
				throw new RuntimeException("想定外の形式です キー:" + value);
			}
		}

	}

	private String oauthToken;

	private String oauthTokenSecret;

	private String userId;

	private String screenName;

	public String getOauthToken() {
		return oauthToken;
	}

	public String getOauthTokenSecret() {
		return oauthTokenSecret;
	}

	public String getUserId() {
		return userId;
	}

	public String getScreenName() {
		return screenName;
	}

}