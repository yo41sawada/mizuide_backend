package org.springframework.samples.petclinic.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.samples.petclinic.system.authentic.AuthOnTwitterService;
import org.springframework.samples.petclinic.system.authentic.entity.TwitterUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

	@Autowired
	public AuthController(AuthOnTwitterService authOnTwitterService) {
		this.authOnTwitterService = authOnTwitterService;
	}

	AuthOnTwitterService authOnTwitterService;

	@GetMapping("/doAuth")
	public String authOnTwitter(Model model) {
		String authUrl = "redirect:" + authOnTwitterService.generateAuthURL();
		return authUrl;
	}

	@Value("${front_url}")
	String frontUrl;

	@GetMapping("/doAuth/certification")
	public String goToTop(Model model) {
		String topPage = "redirect:" + frontUrl + "/";
		TwitterUser twitterUser = (TwitterUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆" + twitterUser.getUsername());
		return topPage + "?" + twitterUser.getUsername();
	}

}
