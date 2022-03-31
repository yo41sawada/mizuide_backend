package org.springframework.samples.petclinic.system.authentic;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class MyPreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter {

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest req) {
		String oauthVerifier = req.getParameter("oauth_token");
		return oauthVerifier;
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest req) {

		String oauthToken = req.getParameter("oauth_verifier");
		return oauthToken;
	}

}
