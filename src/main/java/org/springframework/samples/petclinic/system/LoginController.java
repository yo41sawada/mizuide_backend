package org.springframework.samples.petclinic.system;

import org.springframework.samples.petclinic.system.authentic.entity.TwitterUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	@GetMapping("/login")
	@ResponseBody
	public TwitterUser login(Model model) {
		TwitterUser twitterUser = (TwitterUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return twitterUser;
	}

}
