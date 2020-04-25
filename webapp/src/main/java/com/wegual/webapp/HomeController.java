package com.wegual.webapp;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wegual.webapp.client.BeneficiaryServiceClient;
import com.wegual.webapp.client.ClientBeans;
import com.wegual.webapp.client.UserServiceClient;
import com.wegual.webapp.ui.model.BeneficiaryTimelineUIElement;
import com.wegual.webapp.ui.model.UserTimelineUIElement;

import app.wegual.common.client.CommonBeans;
import app.wegual.common.model.Beneficiary;
import app.wegual.common.model.BeneficiaryProfileCounts;
import app.wegual.common.model.BeneficiaryProfileData;
import app.wegual.common.model.BeneficiaryTimelineItem;
import app.wegual.common.model.DBFile;
import app.wegual.common.model.User;
import app.wegual.common.model.UserProfileCounts;
import app.wegual.common.model.UserProfileData;
import app.wegual.common.model.UserTimelineItem;
import app.wegual.common.rest.model.UserFollowees;
import app.wegual.common.rest.model.UserFollowers;
import app.wegual.common.service.DBFileStorageService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	
	@Autowired
    private DBFileStorageService dbFileStorageService;
	
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
	
	@RequestMapping(value = "/home/piupload", method = { RequestMethod.POST })
	public ResponseEntity<String> upload(@RequestParam MultipartFile  avatar) {
		try {
			byte[] bytes = avatar.getBytes();
			if(bytes != null)
			{
				log.info("Received " + bytes.length + " bytes");
				DBFile dbFile = dbFileStorageService.storeFile("PROFILE_IMAGE", bytes);
				return new ResponseEntity<>("Saved file with id: " + dbFile.getId(), HttpStatus.OK);
			}
			else
				throw new IllegalStateException("Attempt to save file with empty content");
		} catch (Exception e) {
			log.error("error occured receiving file", e);
			return new ResponseEntity<>("Error occured", HttpStatus.BAD_REQUEST);
		}	
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
		String bearerToken = null;
		try {
			OAuth2RestTemplate ort = CommonBeans.getExternalServicesOAuthClients().restTemplate("user-service");
			if (ort != null) {
				token = ort.getAccessToken();
				log.info("Created token");
				log.info("Value: " + token.getValue());
				bearerToken = "Bearer " + token.getValue();
				User user = null;
				UserServiceClient usc = ClientBeans.getUserServiceClient();
				user = usc.getUser("Bearer " + token.getValue(), username);
				if(user != null && StringUtils.isEmpty(user.getPictureLink()))
					user.setPictureLink("/img/avatar-empty.png");
				
				UserFollowees following = usc.getUserFollowing(bearerToken, user.getId());
				UserFollowers followers = usc.getUserFollowers(bearerToken, user.getId());
				
				UserProfileData upd = new UserProfileData();
				upd.setUser(user);
				upd.setCounts(new UserProfileCounts(followers.getFollowersCount(), following.getFolloweesCount()));
				mv.addObject("userProfileData", upd);
				
				try {
					List<UserTimelineItem> timeline = usc.getUserTimeline(bearerToken, user.getId());
					List<UserTimelineUIElement> uiTimelineElements =  UserTimelineUIElement.build(timeline);
					Map<String, List<UserTimelineUIElement>> groupedElements
						= UserTimelineUIElement.groupByDate(uiTimelineElements);
					mv.addObject("timeline", groupedElements);
				} catch (Exception ex) {
					log.error("Error getting timeline", ex);
				}
			}

		} catch (Exception ex) {
			log.error("Error getting user profilde data", ex);
		}
		return mv;
	}
	
	@RequestMapping("/benadmin")
    public String benadmin(){
        return "user/home";
    }

	@RequestMapping("/benadmin/profile")
	public ModelAndView beneficiaryProfile() {
		ModelAndView mv = new ModelAndView("benadmin/profile");
		String username = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		log.info("Found username principal: " + username);

		OAuth2AccessToken token = null;
		String bearerToken = null;
		try {
			OAuth2RestTemplate ort = CommonBeans.getExternalServicesOAuthClients().restTemplate("beneficiary-service");
			if (ort != null) {
				token = ort.getAccessToken();
				log.info("Created token");
				log.info("Value: " + token.getValue());
				bearerToken = "Bearer " + token.getValue();
				Beneficiary ben = null;
				BeneficiaryServiceClient bsc = ClientBeans.getBeneficiaryServiceClient();
				ben = bsc.getBeneficiary("Bearer " + token.getValue(), username);
				if(ben != null && StringUtils.isEmpty(ben.getPictureLink()))
					ben.setPictureLink("/img/avatar-empty.png");
				BeneficiaryProfileData bpd = new BeneficiaryProfileData();
				bpd.setBeneficiary(ben);
				bpd.setCounts(new BeneficiaryProfileCounts());
				mv.addObject("beneficiaryProfileData", bpd);
				
				try {
					List<BeneficiaryTimelineItem> timeline = bsc.getBeneficiaryTimeline(bearerToken, ben.getId());
					List<BeneficiaryTimelineUIElement> uiTimelineElements =  BeneficiaryTimelineUIElement.build(timeline);
					Map<String, List<BeneficiaryTimelineUIElement>> groupedElements
						= BeneficiaryTimelineUIElement.groupByDate(uiTimelineElements);
					mv.addObject("timeline", groupedElements);
				} catch (Exception ex) {
					log.error("Error getting timeline", ex);
				}
			}

		} catch (Exception ex) {
			log.error("Error getting user profilde data", ex);
		}
		return mv;
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
