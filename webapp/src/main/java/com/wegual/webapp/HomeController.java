package com.wegual.webapp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wegual.webapp.client.ClientBeans;
import com.wegual.webapp.client.UserServiceClient;

import app.wegual.common.client.CommonBeans;
import app.wegual.common.model.User;
import app.wegual.common.model.UserProfileCounts;
import app.wegual.common.model.UserProfileData;
import app.wegual.common.rest.model.UserFollowees;
import app.wegual.common.rest.model.UserFollowers;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	@RequestMapping("/")
    public String index(){
        return "welcome";
    }

	@RequestMapping("/home")
    public String home(){
        return "user/home";
    }

	@RequestMapping(path = "/logout")
    public String logout(HttpServletRequest request){
		try {
			request.logout();
		} catch (ServletException e) {
			
			log.error("Error loggin out", e);
		}
        return "redirect:/";
    }
	
	@RequestMapping("/home/profile")
	public ModelAndView profile() {
		ModelAndView mv = new ModelAndView("user/profile");
		String username = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		log.info("Found username principal: " + username);

		OAuth2AccessToken token = null;
		try {
			OAuth2RestTemplate ort = CommonBeans.getExternalServicesOAuthClients().restTemplate("user-service");
			if (ort != null) {
				token = ort.getAccessToken();
				log.info("Created token");
				log.info("Value: " + token.getValue());
				User user = null;
				UserServiceClient usc = ClientBeans.getUserServiceClient();
				user = usc.getUser("Bearer " + token.getValue(), username);
				
				UserFollowees following = usc.getUserFollowing("Bearer " + token.getValue(), user.getId());
				UserFollowers followers = usc.getUserFollowers("Bearer " + token.getValue(), user.getId());
				
				UserProfileData upd = new UserProfileData();
				upd.setUser(user);
				upd.setCounts(new UserProfileCounts(followers.getFollowersCount(), following.getFolloweesCount()));
				mv.addObject("userProfileData", upd);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mv;
	}
	
	@RequestMapping("/benadmin")
    public String benadmin(){
        return "benadmin/benadmin";
    }

	@RequestMapping("/siteadmin")
    public String siteadmin(){
        return "siteadmin/siteadmin";
    }

	@RequestMapping("/sysadmin")
    public String sysadmin(){
        return "sysadmin/sysadmin";
    }
	
}
