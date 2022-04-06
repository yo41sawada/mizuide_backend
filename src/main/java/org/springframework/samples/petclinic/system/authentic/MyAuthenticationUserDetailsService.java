package org.springframework.samples.petclinic.system.authentic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.system.authentic.entity.TwitterAccessTokenResponse;
import org.springframework.samples.petclinic.system.authentic.entity.TwitterUser;
import org.springframework.samples.petclinic.system.authentic.entity.TwitterVerifyCredentialsResponse;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationUserDetailsService
		implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

	@Autowired
	TwitterAccessTokenGateway twitterAccessTokenGateway;

	@Autowired
	TwitterVerifyCredentialsGateway twitterVerifyCredentialsGateway;

	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
		String oauthToken = (String) token.getPrincipal();
		String oauthVerifier = (String) token.getCredentials();

		TwitterAccessTokenResponse twitterAccessTokenResponse = twitterAccessTokenGateway.fetchAPI(oauthToken,
				oauthVerifier);

		TwitterVerifyCredentialsResponse twitterVerifyCredentialsResponse = twitterVerifyCredentialsGateway
				.fetchAPI(twitterAccessTokenResponse.getOauthToken(), twitterAccessTokenResponse.getOauthTokenSecret());

		TwitterUser twitterUser = new TwitterUser(twitterVerifyCredentialsResponse);

		return twitterUser;
	}

}
