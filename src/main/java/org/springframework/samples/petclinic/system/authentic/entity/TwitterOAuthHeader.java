package org.springframework.samples.petclinic.system.authentic.entity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.samples.petclinic.system.authentic.enums.OAuthAttrsEnum;
import org.springframework.samples.petclinic.system.authentic.properties.TwitterAPITokenProperties;

public class TwitterOAuthHeader {

	private TwitterAPITokenProperties properties;

	private Map<String, String> oAuthParam;

	private String baseURL;

	private HttpRequestMethodEnum requestMethod;

	public TwitterOAuthHeader(TwitterAPITokenProperties twitteAPITokenRequest, HttpRequestMethodEnum requestMethod,
			String baseURL) {
		this.properties = twitteAPITokenRequest;
		this.baseURL = baseURL;
		this.requestMethod = requestMethod;
		oAuthParam = new HashMap<>();
		oAuthParam.put(OAuthAttrsEnum.oauth_consumer_key.name(), twitteAPITokenRequest.getAPIKey());
		oAuthParam.put(OAuthAttrsEnum.oauth_signature_method.name(), "HMAC-SHA1");
		oAuthParam.put(OAuthAttrsEnum.oauth_nonce.name(), createNonce());
		oAuthParam.put(OAuthAttrsEnum.oauth_version.name(), "1.0");
		oAuthParam.put(OAuthAttrsEnum.oauth_timestamp.name(),
				String.valueOf(System.currentTimeMillis() / 1000l).replace("l", ""));
	}

	public void addOauthParametor(OAuthAttrsEnum oAuthAttr, String paramValue) {
		if (oAuthParam.containsKey(oAuthAttr.name())) {
			throw new RuntimeException("キーが重複しています");
		}
		oAuthParam.put(oAuthAttr.name(), paramValue);
	}

	public void createSignature(Map<String, String> param, String oAuthTokenSecret) {
		Map<String, String> baseMap = createParamMap(param);

		String value = createSignatureValue(requestMethod, baseURL, baseMap);
		String key = createSignatureKey(properties.getAPIKeySecret(), oAuthTokenSecret);

		String signature = calculateSignature(key, value);
		oAuthParam.put(OAuthAttrsEnum.oauth_signature.name(), signature);
	}

	private Map<String, String> createParamMap(Map<String, String> param) {
		Map<String, String> mergeMap = new HashMap<>();

		mergeMap.putAll(oAuthParam);
		// 重複時に例外を投げたいのでstreamは使用しない
		for (Entry<String, String> e : param.entrySet()) {
			if (mergeMap.containsKey(e.getKey())) {
				throw new RuntimeException("パラメータとoauthでキーが重複しています" + e.getKey());
			}
			else {
				mergeMap.put(e.getKey(), e.getValue());
			}
		}
		return mergeMap;
	}

	private String collectingParameters(Map<String, String> param) {

		// oautの標準仕様で、urlエンコード後のキーでソートするため、詰め直し
		SortedMap<String, String> urlEncodeMap = new TreeMap<>();
		param.entrySet().stream().forEach(e -> urlEncodeMap.put(urlEncode(e.getKey()), urlEncode(e.getValue())));

		StringBuilder paramStr = new StringBuilder();
		for (Entry<String, String> e : urlEncodeMap.entrySet()) {
			paramStr.append("&" + e.getKey() + "=" + e.getValue());
		}
		paramStr.delete(0, 1);

		return paramStr.toString();
	}

	private String createSignatureValue(HttpRequestMethodEnum HTTPMethod, String baseURL, Map<String, String> param) {
		String encodedParamStr = collectingParameters(param);
		String value = HTTPMethod.name() + "&" + urlEncode(baseURL) + "&" + urlEncode(encodedParamStr);
		return value;
	}

	private String createSignatureKey(String consumerSecret, String oAuthTokenSecret) {
		String key = consumerSecret + "&" + oAuthTokenSecret;
		return key;
	}

	private String calculateSignature(String key, String value) {
		String signature = null;
		try {
			SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
			Mac mac = Mac.getInstance(signingKey.getAlgorithm());
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(value.getBytes());
			signature = Base64.getEncoder().encodeToString(rawHmac);
		}
		catch (InvalidKeyException e1) {
			e1.printStackTrace();
		}
		catch (NoSuchAlgorithmException e2) {
			e2.printStackTrace();
		}
		return signature;
	}

	private String createNonce() {

		return String.valueOf(Math.random());
		// return "ea9ec8429b68d6b77cd5600adbbb0456";
	}

	private String urlEncode(String string) {
		try {
			// Javaのエンコーダの仕様でスペースを+に変換してしまうため、標準に合わせて%20にする
			String tmp = URLEncoder.encode(string, "UTF-8");
			return tmp.replace("+", "%20");
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String toString() {
		StringBuilder oAuthHeader = new StringBuilder("OAuth");
		SortedMap<String, String> sortedOAuthParam = new TreeMap<>();
		sortedOAuthParam.putAll(oAuthParam);
		sortedOAuthParam.entrySet().stream().forEach(e -> {
			oAuthHeader.append(" " + urlEncode(e.getKey().toString()));
			oAuthHeader.append("=\"");
			oAuthHeader.append(urlEncode(e.getValue().toString()));
			oAuthHeader.append("\",");
		});
		oAuthHeader.delete(oAuthHeader.length() - 1, oAuthHeader.length());
		return oAuthHeader.toString();

	}

}
