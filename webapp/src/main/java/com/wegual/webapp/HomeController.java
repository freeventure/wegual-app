package com.wegual.webapp;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.wegual.webapp.client.BeneficiaryServiceClient;
import com.wegual.webapp.client.ClientBeans;
import com.wegual.webapp.client.PledgeServiceClient;
import com.wegual.webapp.client.UserServiceClient;
import com.wegual.webapp.message.ProfileImageTimelineItemBuilder;
import com.wegual.webapp.message.UserActionsMessageSender;
import com.wegual.webapp.ui.model.RegisterAccount;
import com.wegual.webapp.ui.model.UserRegistrationValidator;
import com.wegual.webapp.ui.model.UserTimelineUIElement;
import com.wegual.webapp.ui.model.VerificationCodeValidator;
import com.wegual.webapp.ui.model.VerifyCode;
import com.wegual.webapp.util.KeycloakAuthenticationFacade;

import app.wegual.common.asynch.SenderRunnable;
import app.wegual.common.client.CommonBeans;
import app.wegual.common.model.Beneficiary;
import app.wegual.common.model.BeneficiaryProfileData;
import app.wegual.common.model.DBFile;

import app.wegual.common.model.GenericItem;
import app.wegual.common.model.GiveUp;

import app.wegual.common.model.TokenStatus;
import app.wegual.common.model.User;
import app.wegual.common.model.UserHomePageData;
import app.wegual.common.model.UserProfileCounts;
import app.wegual.common.model.UserProfileData;
import app.wegual.common.model.UserTimelineItem;
import app.wegual.common.model.PledgeAnalyticsForUser;
import app.wegual.common.model.RegisterPledge;
import app.wegual.common.rest.model.BeneficiarySnapshot;
import app.wegual.common.rest.model.UserFollowees;
import app.wegual.common.rest.model.UserFollowers;
import app.wegual.common.service.DBFileStorageService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	
	@Autowired
	private UserRegistrationValidator userValidator;
	
	@Autowired
	private VerificationCodeValidator vcValidator;
	
	@Autowired
	@Qualifier("executorMessageSender")
	TaskExecutor te;
	
	@Autowired
	KeycloakAuthenticationFacade kaf;
	
	@Autowired
	private UserActionsMessageSender uams;	
	
	@Autowired
	private EurekaClient discoveryClient;

	@Autowired
    private DBFileStorageService dbFileStorageService;
	
	private static String getBearerToken() {
		String bearerToken = null;
		OAuth2AccessToken token = null;
		try {
			OAuth2RestTemplate ort = CommonBeans.getExternalServicesOAuthClients().restTemplate("user-service");
			if (ort != null) {
				token = ort.getAccessToken();
				log.info("Created token");
				log.info("Value: " + token.getValue());
				bearerToken = "Bearer " + token.getValue();
				return bearerToken;
			}
		}
		catch(Exception e) {
			log.info("Error getting access token" + e);
			return bearerToken;
		}
		return bearerToken;	
	}
	
	private static String getBearerTokenForPledge() {
		String bearerToken = null;
		OAuth2AccessToken token = null;
		try {
			OAuth2RestTemplate ort = CommonBeans.getExternalServicesOAuthClients().restTemplate("pledge-service");
			if (ort != null) {
				token = ort.getAccessToken();
				log.info("Created token");
				log.info("Value: " + token.getValue());
				bearerToken = "Bearer " + token.getValue();
				return bearerToken;
			}
		}
		catch(Exception e) {
			log.info("Error getting access token" + e);
			return bearerToken;
		}
		return bearerToken;	
	}
	
	@RequestMapping("/")
    public String index(){
        return "welcome";
    }

	@GetMapping("/public/login")
    public String loginForm(){
        return "login";
    }

	@PostMapping("/public/authenticate")
    public String loginVerify(@RequestParam("username") String username, @RequestParam("password") String password){
        return "public/verifySuccess";
    }
	
	@GetMapping("/public/signup")
    public ModelAndView signupForm(){
        return new ModelAndView("public/register", "registerAccount", new RegisterAccount());
    }

	@PostMapping("/public/signup")
    public ModelAndView signupFormSubmit(@ModelAttribute("registerAccount") @Validated RegisterAccount ra, BindingResult result) {

		this.userValidator.validate(ra, result);
		if (result.hasErrors()) {
	         return new ModelAndView("public/register");
	    }
		
		// generate a temporary (no user id) record with OTP
		UserServiceClient usc = ClientBeans.getUserServiceClient();
		String encodedSecret = Base64.getEncoder().encodeToString(ra.getPassword().getBytes());
		String tokenId = usc.getUserEmailVerifyToken(encodedSecret, ra.userFrom());
		VerifyCode code = new VerifyCode();
		code.setTokenId(tokenId);
		
		// send to user with token and tokenId (tokenId is _id of ES document)
        return new ModelAndView("public/verifycode", "verifyCode", code) ;
    }
	
	@RequestMapping(value ="/home/pledge/submit",method = RequestMethod.POST)
    public ModelAndView submitPledge(RegisterPledge ra) {
		String bearerToken = null;
		
		try {
			bearerToken = HomeController.getBearerTokenForPledge();
			PledgeServiceClient usc = ClientBeans.getPledgeServiceClient();			
			usc.savePledge(bearerToken, ra);
			}
		catch (Exception ex) {
			log.error("Error getting user profilde data", ex);
		}
        return new ModelAndView("user/home");
    }

	@PostMapping("/public/verify/token")
    public String verifyTokenSubmit(@ModelAttribute VerifyCode vc, BindingResult result) {
		// verify token is six digits and all numeric
		this.vcValidator.validate(vc, result);
		
		if (result.hasErrors()) {
	         return "public/verifycode";
	    }
		UserServiceClient usc = ClientBeans.getUserServiceClient();
		TokenStatus ts =  usc.verifyUserToken(vc.getToken(), vc.getTokenId());
		
		switch (ts) {
			case TOKEN_EXPIRED : result.rejectValue("token", "user.verify.token.expired");
				break;
			case ATTEMPTS_EXCEEDED : result.rejectValue("token", "user.verify.token.attempts");
				break;
			case NOT_FOUND : result.rejectValue("token", "user.verify.token.invalid");
				break;
			case ERROR : result.rejectValue("token", "user.verify.token.error");
				break;
			case BAD_REQUEST : result.rejectValue("token", "user.verify.token.invalid.data");
			break;

			case VERIFIED:
				return "public/verifySuccess";
		}
		return "public/verifycode";
    }
	
	@RequestMapping("/home")
    public ModelAndView home() {
		ModelAndView mv = new ModelAndView("user/home");
		String username = kaf.getUserLoginName();
		
		log.info("Found username principal: " + username);
		String bearerToken = null;
		String bearerTokenForPledge = null;
		try {
			bearerToken = HomeController.getBearerToken();
			bearerTokenForPledge = HomeController.getBearerTokenForPledge();
			User user = null;
			UserServiceClient usc = ClientBeans.getUserServiceClient();
			PledgeServiceClient psc = ClientBeans.getPledgeServiceClient();
			user = usc.getUser(bearerToken, username);
			log.info("Got user object");
			if(user != null && StringUtils.isEmpty(user.getPictureLink()))
				user.setPictureLink("/img/avatar-empty.png");
			else
			{
				String userServiceUrl = StringUtils.removeEnd(getUserServiceUrl(), "/");
				log.info("User service URL is: " + userServiceUrl);
				user.setPictureLink(userServiceUrl + user.getPictureLink());
			}
			PledgeAnalyticsForUser counts = psc.getPledgeCounts(bearerTokenForPledge, user.getId());
			UserHomePageData uhd = new UserHomePageData();
			uhd.setUser(user);
			uhd.setCounts(counts);
			mv.addObject("homePageData", uhd);

		} catch (Exception ex) {
			log.error("Error getting user profilde data", ex);
		}
		return mv;
    }
	@RequestMapping(path ="/beneficiary/all", method = RequestMethod.GET )
	public ResponseEntity<List<Beneficiary>> getAllBeneficiary(){
		String bearerToken = null;
		try {
			bearerToken = HomeController.getBearerToken();
			UserServiceClient usc = ClientBeans.getUserServiceClient();
			
			List<Beneficiary> beneficiary= usc.getAllBeneficiary(bearerToken);
			return new ResponseEntity<List<Beneficiary>>(beneficiary, HttpStatus.OK);

		} catch (Exception ex) {
			log.error("Error getting user profilde data", ex);
			return new ResponseEntity<List<Beneficiary>>(new ArrayList<Beneficiary>(), HttpStatus.BAD_REQUEST);
		}
	}
	@RequestMapping(path ="/giveup/all", method = RequestMethod.GET )
	public ResponseEntity<List<GiveUp>> getAllGiveUp(){
		String bearerToken = null;
		try {
			bearerToken = HomeController.getBearerToken();
			UserServiceClient usc = ClientBeans.getUserServiceClient();
			
			List<GiveUp> giveup = usc.getAllGiveup(bearerToken);
			return new ResponseEntity<List<GiveUp>>(giveup,HttpStatus.OK);

		} catch (Exception ex) {
			log.error("Error getting user profilde data", ex);
			return new ResponseEntity<List<GiveUp>>(new ArrayList<GiveUp>(),HttpStatus.BAD_REQUEST);
		}
	}
	@RequestMapping(path = "/logout" )
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
				UserTimelineItem uti = new ProfileImageTimelineItemBuilder()
						.userId(kaf.getUserId())
						.time(System.currentTimeMillis())
						.udpateImage(dbFile.getId())
						.userName(kaf.getUserFullName())
						.build();
				te.execute(new SenderRunnable<UserActionsMessageSender, UserTimelineItem>(uams, uti));
				return new ResponseEntity<>("Saved file with id: " + dbFile.getId(), HttpStatus.OK);
			}
			else
				throw new IllegalStateException("Attempt to save file with empty content");
		} catch (Exception e) {
			log.error("error occured receiving file", e);
			return new ResponseEntity<>("Error occured", HttpStatus.BAD_REQUEST);
		}	
	}
	
	private String getUserServiceUrl() {
	    InstanceInfo instance = discoveryClient.getNextServerFromEureka("user-service", false);
	    return instance.getHomePageUrl();
	}	
	
	@RequestMapping("/home/profile")
	public ModelAndView profile() {
		ModelAndView mv = new ModelAndView("user/profile");
		String username = kaf.getUserLoginName();
		
		log.info("Found username principal: " + username);

		String bearerToken = null;
		try {
			bearerToken = HomeController.getBearerToken();
			User user = null;
			UserServiceClient usc = ClientBeans.getUserServiceClient();
			user = usc.getUser(bearerToken, username);
			if(user != null && StringUtils.isEmpty(user.getPictureLink()))
				user.setPictureLink("/img/avatar-empty.png");
			else
			{
				String userServiceUrl = StringUtils.removeEnd(getUserServiceUrl(), "/");
				log.info("User service URL is: " + userServiceUrl);
				user.setPictureLink(userServiceUrl + user.getPictureLink());
			}
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

		} catch (Exception ex) {
			log.error("Error getting user profilde data", ex);
		}
		return mv;
	}
	
	@RequestMapping("/home/beneficiaries")
	public ModelAndView beneficiaries() {
		ModelAndView mv = new ModelAndView("user/beneficiaries");
		String username = kaf.getUserLoginName();
		log.info("Found username principal: " + username);
		String bearerToken = null;
		try {
			bearerToken = HomeController.getBearerToken();
			User user = null;
			UserServiceClient usc = ClientBeans.getUserServiceClient();
			user = usc.getUser(bearerToken, username);
			String userServiceUrl = StringUtils.removeEnd(getUserServiceUrl(), "/");
			log.info("User service URL is: " + userServiceUrl);
			if(user != null && StringUtils.isEmpty(user.getPictureLink()))
				user.setPictureLink("/img/avatar-empty.png");
			else
			{
				user.setPictureLink(userServiceUrl + user.getPictureLink());
			}
			List<GenericItem> benFollowees = usc.getBeneficiaryFollowees(bearerToken, user.getId());
			log.info("User service URL is: " + userServiceUrl);
			mv.addObject("user", user);
			mv.addObject("userServiceUrl", userServiceUrl);
			mv.addObject("benFollowees", benFollowees);
		}
		catch (Exception ex) {
			log.error("Error getting beneficiary followee", ex);
		}
		return mv;
	}
	
	@RequestMapping("/beneficiary/profile/{benid}")
	public ModelAndView beneficiaryProfilePage(@PathVariable ("benid") Long benId) {
		ModelAndView mv = new ModelAndView("benadmin/benprofile");
		String username = kaf.getUserLoginName();
		log.info("Found username principal: " + username);
		String bearerToken = null;
		try {
			bearerToken = HomeController.getBearerToken();
			User user = null;
			UserServiceClient usc = ClientBeans.getUserServiceClient();
			user = usc.getUser(bearerToken, username);
			String userServiceUrl = StringUtils.removeEnd(getUserServiceUrl(), "/");
			log.info("User service URL is: " + userServiceUrl);
			if(user != null && StringUtils.isEmpty(user.getPictureLink()))
				user.setPictureLink("/img/avatar-empty.png");
			else
			{
				user.setPictureLink(userServiceUrl + user.getPictureLink());
			}
			BeneficiaryServiceClient bsc = ClientBeans.getBeneficiaryServiceClient();
			Beneficiary ben = bsc.getBeneficiary(bearerToken, benId);
			if(ben!= null)
				System.out.println(ben.getName());
			BeneficiarySnapshot bs = bsc.getBeneficiarySnapshot(bearerToken, benId);
			BeneficiaryProfileData bpd = new BeneficiaryProfileData();
			bpd.setBeneficiary(ben);
			bpd.setBeneficiarySnapshot(bs);
			mv.addObject("user", user);
			mv.addObject("beneficiaryProfileData", bpd);
		}
		catch (Exception ex) {
			log.error("Error getting beneficiary profile", ex);
		}
		return mv;
	}

	@RequestMapping("/home/pledges")
	public ModelAndView pledge() {
		ModelAndView mv = new ModelAndView("user/pledge");
		String userId = kaf.getUserId();
		
		log.info("Found username principal: " + userId);
		String bearerToken = null;
		try {
			bearerToken = HomeController.getBearerTokenForPledge();
			PledgeServiceClient psc = ClientBeans.getPledgeServiceClient();
			
			try {
				List<Map<String, Object>> pledges = psc.getAllPledgesForUser(bearerToken, userId);
				System.out.println(pledges.size());
				mv.addObject("pledges", pledges);
			} catch (Exception ex) {
				log.error("Error getting pledges taken by user", ex);
			}
		} catch (Exception ex) {
			log.error("Error getting user pledge data", ex);
		}
		return mv;
	}
	
	@RequestMapping("/home/giveups")
	public ModelAndView giveup() {
		ModelAndView mv = new ModelAndView("user/giveup");
		String userId = kaf.getUserId();
		
		log.info("Found username principal: " + userId);

		String bearerToken = null;
		try {
			bearerToken = HomeController.getBearerToken();
			UserServiceClient usc = ClientBeans.getUserServiceClient();
			
			try {
				List<Object> giveups = usc.getAllGiveupUserPledgedFor(bearerToken, userId);
				System.out.println(giveups.size());
				mv.addObject("giveups", giveups);
			} catch (Exception ex) {
				log.error("Error getting unique giveups user pledged for", ex);
			}
		} catch (Exception ex) {
			log.error("Error getting user pledge data", ex);
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
