package com.wegual.webapp;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
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
import org.springframework.social.twitter.api.Tweet;
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
import org.springframework.web.servlet.view.RedirectView;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.wegual.webapp.client.BeneficiaryServiceClient;
import com.wegual.webapp.client.ClientBeans;
import com.wegual.webapp.client.PledgeServiceClient;
import com.wegual.webapp.client.GiveUpServiceClient;
import com.wegual.webapp.client.UserServiceClient;
import com.wegual.webapp.message.ProfileImageTimelineItemBuilder;
import com.wegual.webapp.message.TwitterMessageSender;
import com.wegual.webapp.message.UserActionsMessageSender;
import com.wegual.webapp.ui.model.BeneficiaryTimelineUIElement;
import com.wegual.webapp.ui.model.RegisterAccount;
import com.wegual.webapp.ui.model.UserFeedUIElement;
import com.wegual.webapp.ui.model.UserRegistrationValidator;
import com.wegual.webapp.ui.model.UserTimelineUIElement;
import com.wegual.webapp.ui.model.VerificationCodeValidator;
import com.wegual.webapp.ui.model.VerifyCode;
import com.wegual.webapp.util.KeycloakAuthenticationFacade;

import app.wegual.common.asynch.SenderRunnable;
import app.wegual.common.client.CommonBeans;
import app.wegual.common.model.Beneficiary;
import app.wegual.common.model.BeneficiaryProfileData;
import app.wegual.common.model.BeneficiaryTimelineItem;
import app.wegual.common.model.DBFile;
import app.wegual.common.model.FeedItem;
import app.wegual.common.model.GenericItem;
import app.wegual.common.model.GiveUp;

import app.wegual.common.model.TokenStatus;
import app.wegual.common.model.User;
import app.wegual.common.model.UserActionItem;
import app.wegual.common.model.UserActionType;
import app.wegual.common.model.UserDetails;
import app.wegual.common.model.PledgeFeedItem;
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
	private TwitterMessageSender tms;
	
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
	
	private static String getBearerTokenForPledgeService() {
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
	
	private static String getBearerTokenForBeneficiaryService() {
		String bearerToken = null;
		OAuth2AccessToken token = null;
		try {
			OAuth2RestTemplate ort = CommonBeans.getExternalServicesOAuthClients().restTemplate("beneficiary-service");
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
	
	private static String getBearerTokenForGiveUpService() {
		String bearerToken = null;
		OAuth2AccessToken token = null;
		try {
			OAuth2RestTemplate ort = CommonBeans.getExternalServicesOAuthClients().restTemplate("pledge-service");
			if (ort != null) {
				token = ort.getAccessToken();
				log.info("Created token for giveup service");
				log.info("Value: " + token.getValue());
				bearerToken = "Bearer " + token.getValue();
				return bearerToken;
			}
		}
		catch(Exception e) {
			log.info("Error getting access token for giveup service" + e);
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
			bearerToken = HomeController.getBearerTokenForPledgeService();
			PledgeServiceClient psc = ClientBeans.getPledgeServiceClient();			
			psc.savePledge(bearerToken, ra);
			}
		catch (Exception ex) {
			log.error("Error getting user profilde data", ex);
		}
        return new ModelAndView("user/home");
    }
	
	@RequestMapping(value ="/home/user/submit/details",method = RequestMethod.POST)
    public ResponseEntity<String> submitUserDetails(UserDetails ud) {
		String bearerToken = null;
		
		try {
			bearerToken = HomeController.getBearerToken();
			UserServiceClient usc = ClientBeans.getUserServiceClient();			
			usc.saveUserDetails(bearerToken, ud);
			return(new ResponseEntity<String>("Ok",HttpStatus.OK));
		}
		catch (Exception ex) {
			log.error("Error getting user profilde data", ex);
			return(new ResponseEntity<String>("Error",HttpStatus.BAD_REQUEST));
		}
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
			bearerTokenForPledge = HomeController.getBearerTokenForPledgeService();
			User user = null;
			UserServiceClient usc = ClientBeans.getUserServiceClient();
			PledgeServiceClient psc = ClientBeans.getPledgeServiceClient();
			user = usc.getUser(bearerToken, username);
			log.info("Got user object");
			String userServiceUrl = StringUtils.removeEnd(getUserServiceUrl(), "/");
			if(user != null && StringUtils.isEmpty(user.getPictureLink()))
				user.setPictureLink("/img/avatar-empty.png");
			else
			{
				log.info("User service URL is: " + userServiceUrl);
				user.setPictureLink(userServiceUrl + user.getPictureLink());
			}
			PledgeAnalyticsForUser counts = psc.getPledgeCounts(bearerTokenForPledge, user.getId());
			UserHomePageData uhd = new UserHomePageData();
			uhd.setUser(user);
			uhd.setCounts(counts);
			try {
				List<PledgeFeedItem> feed = usc.getUserFeed(bearerToken, user.getId());
				System.out.println("--------->>>>>>>> "+feed.size());
				List<UserFeedUIElement> uiFeedElements =  UserFeedUIElement.build(feed, userServiceUrl);
				mv.addObject("feeds", uiFeedElements);
			}
			catch(Exception e){
				log.error("Error getting feed", e);
			}
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
			bearerToken = HomeController.getBearerTokenForBeneficiaryService();
			BeneficiaryServiceClient bsc = ClientBeans.getBeneficiaryServiceClient();
			
			List<Beneficiary> beneficiary= bsc.getAllBeneficiary(bearerToken);
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
			bearerToken = HomeController.getBearerTokenForGiveUpService();
			GiveUpServiceClient gsc = ClientBeans.getGiveUpServiceClient();
			
			List<GiveUp> giveup = gsc.getAllGiveup(bearerToken);
			return new ResponseEntity<List<GiveUp>>(giveup,HttpStatus.OK);

		} catch (Exception ex) {
			log.error("Error getting user profilde data", ex);
			return new ResponseEntity<List<GiveUp>>(new ArrayList<GiveUp>(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(path ="/user/feed/{userid}", method = RequestMethod.GET )
	public ResponseEntity<List<PledgeFeedItem>> getFeed(@PathVariable String userid){
		String bearerToken = null;
		try {
			bearerToken = HomeController.getBearerToken();
			UserServiceClient usc = ClientBeans.getUserServiceClient();
			
			List<PledgeFeedItem> feed = usc.getUserFeed(bearerToken, userid);
			return new ResponseEntity<List<PledgeFeedItem>>(feed,HttpStatus.OK);

		} catch (Exception ex) {
			log.error("Error getting user profilde data", ex);
			return new ResponseEntity<List<PledgeFeedItem>>(new ArrayList<PledgeFeedItem>(),HttpStatus.BAD_REQUEST);
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
					System.out.println("Timeline size:- " + groupedElements.size());
					mv.addObject("timeline", groupedElements);
					
					List<UserTimelineItem> scrolltimeline = usc.getScrollableUserTimeline(bearerToken, user.getId(), System.currentTimeMillis());
					List<UserTimelineUIElement> uiScrollTimelineElements =  UserTimelineUIElement.build(scrolltimeline);
					Map<String, List<UserTimelineUIElement>> groupedScrollElements
						= UserTimelineUIElement.groupByDate(uiScrollTimelineElements);
					System.out.println("Scroll Timeline size:- " + scrolltimeline.size());
					mv.addObject("scrollTimeline", groupedScrollElements);
				} catch (Exception ex) {
					log.error("Error getting timeline", ex);
				}

		} catch (Exception ex) {
			log.error("Error getting user profilde data", ex);
		}
		return mv;
	}
	
	@RequestMapping("/home/scrolltimeline/{userid}/{timestamp}")
	public ResponseEntity<Map<String, List<UserTimelineUIElement>>> getNextTimeline(@PathVariable("userid") String userId, @PathVariable("timestamp") long timestamp) {
		String bearerToken = null;
		try {
			bearerToken = HomeController.getBearerToken();
			UserServiceClient usc = ClientBeans.getUserServiceClient();
			List<UserTimelineItem> scrolltimeline = usc.getScrollableUserTimeline(bearerToken, userId, timestamp);
			List<UserTimelineUIElement> uiScrollTimelineElements =  UserTimelineUIElement.build(scrolltimeline);
			Map<String, List<UserTimelineUIElement>> groupedScrollElements
				= UserTimelineUIElement.groupByDate(uiScrollTimelineElements);
			System.out.println("Scroll Timeline size:- " + scrolltimeline.size());
			return(new ResponseEntity<Map<String, List<UserTimelineUIElement>>>(groupedScrollElements, HttpStatus.OK));
		}
		catch(Exception e) {
			return(new ResponseEntity<Map<String, List<UserTimelineUIElement>>>((new HashMap<String, List<UserTimelineUIElement>>()),HttpStatus.BAD_REQUEST));
		}
	}
	
	@RequestMapping("/home/beneficiaries")
	public ModelAndView beneficiaries() {
		ModelAndView mv = new ModelAndView("user/beneficiaries");
		String username = kaf.getUserLoginName();
		String userId = kaf.getUserId();
		log.info("Found username principal: " + username);
		String userBearerToken = null;
		String beneficiaryBearerToken = null;
		try {
			userBearerToken = HomeController.getBearerToken();
			beneficiaryBearerToken = HomeController.getBearerTokenForBeneficiaryService();
			User user = null;
			UserServiceClient usc = ClientBeans.getUserServiceClient();
			BeneficiaryServiceClient bsc = ClientBeans.getBeneficiaryServiceClient();
			user = usc.getUser(userBearerToken, username);
			String userServiceUrl = StringUtils.removeEnd(getUserServiceUrl(), "/");
			log.info("User service URL is: " + userServiceUrl);
			if(user != null && StringUtils.isEmpty(user.getPictureLink()))
				user.setPictureLink("/img/avatar-empty.png");
			else
			{
				user.setPictureLink(userServiceUrl + user.getPictureLink());
			}
			List<GenericItem<String>> benFollowees = bsc.getBeneficiaryFollowees(beneficiaryBearerToken, userId);
			log.info("User service URL is: " + userServiceUrl);
			mv.addObject("user", user);
			mv.addObject("userServiceUrl", userServiceUrl);
			mv.addObject("benFollowees", benFollowees);
			
			
			List<Beneficiary> suggestedBen = bsc.suggestBeneficiaryToFollow(beneficiaryBearerToken, userId);
			mv.addObject("suggestedBen", suggestedBen);
			List<GenericItem<String>> followedBen = bsc.allBeneficiaryFollowedByUser(beneficiaryBearerToken, userId);
			mv.addObject("followedBen", followedBen);
			List<GenericItem<String>> benpledged = bsc.getAllBeneficiaryUserPledgedFor(beneficiaryBearerToken, userId);
			mv.addObject("benpledged", benpledged);
		}
		catch (Exception ex) {
			log.error("Error getting beneficiary followee", ex);
		}
		return mv;
	}
	
	@RequestMapping("/beneficiary/profile/{benid}")
	public ModelAndView beneficiaryProfilePage(@PathVariable ("benid") String benId) {
		ModelAndView mv = new ModelAndView("benadmin/benprofile");
		String username = kaf.getUserLoginName();
		log.info("Found username principal: " + username);
		String bearerToken = null;
		String beneficiaryBearerToken = null;
		try {
			bearerToken = HomeController.getBearerToken();
			beneficiaryBearerToken = HomeController.getBearerTokenForBeneficiaryService();
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
			Beneficiary ben = bsc.getBeneficiary(beneficiaryBearerToken, benId);
			BeneficiarySnapshot bs = bsc.getBeneficiarySnapshot(beneficiaryBearerToken, benId);
			BeneficiaryProfileData bpd = new BeneficiaryProfileData();
			bpd.setBeneficiary(ben);
			bpd.setBeneficiarySnapshot(bs);
			mv.addObject("user", user);
			mv.addObject("beneficiaryProfileData", bpd);
			try {
				List<BeneficiaryTimelineItem> timeline = bsc.getTimeline(beneficiaryBearerToken, benId);
				System.out.println("Ben Timeline found with size :- " + timeline.size());
				List<BeneficiaryTimelineUIElement> uiTimelineElements =  BeneficiaryTimelineUIElement.build(timeline);
				Map<String, List<BeneficiaryTimelineUIElement>> groupedElements
					= BeneficiaryTimelineUIElement.groupByDate(uiTimelineElements);
				mv.addObject("timeline", groupedElements);
			} catch (Exception ex) {
				log.error("Error getting ben timeline", ex);
			}
		}
		catch (Exception ex) {
			log.error("Error getting beneficiary profile", ex);
		}
		return mv;
	}
	
	@RequestMapping("/home/beneficiary/follow/{benId}")
	public ResponseEntity<String> followBeneficiary(@PathVariable String benId) {
		String userId = kaf.getUserId();
		log.info(userId + " initiates follow request for ben with id " + benId);
		
		try {
			UserActionItem uat = new UserActionItem();
			uat.setActorId(userId);
			uat.setTargetId(benId);
			uat.setActionType(UserActionType.FOLLOW_BENEFICIARY);
			te.execute(new SenderRunnable<UserActionsMessageSender, UserActionItem>(uams, uat));
			return(new ResponseEntity<String>("Ok",HttpStatus.OK));
		} catch (Exception e) {
			log.info("Unable to follow ben with id" + benId + "for userId" + userId, e);
			return(new ResponseEntity<String>("Error",HttpStatus.BAD_REQUEST));
		}
	}
	
	@RequestMapping("/home/beneficiary/unfollow/{benId}")
	public ResponseEntity<String> unfollowBeneficiary(@PathVariable String benId) {
		String userId = kaf.getUserId();
		log.info(userId + " initiates unfollow request for ben with id " + benId);
		
		try {
			UserActionItem uat = new UserActionItem();
			uat.setActorId(userId);
			uat.setTargetId(benId);
			uat.setActionType(UserActionType.UNFOLLOW_BENEFICIARY);
			te.execute(new SenderRunnable<UserActionsMessageSender, UserActionItem>(uams, uat));
			return(new ResponseEntity<String>("Ok",HttpStatus.OK));
		} catch (Exception e) {
			log.info("Unable to unfollow ben with id " + benId + " for userId " + userId, e);
			return(new ResponseEntity<String>("Error",HttpStatus.BAD_REQUEST));
		}
	}

	@RequestMapping("/home/pledges")
	public ModelAndView pledge() {
		ModelAndView mv = new ModelAndView("user/pledge");
		String userId = kaf.getUserId();
		
		log.info("Found username principal: " + userId);
		String bearerToken = null;
		try {
			bearerToken = HomeController.getBearerTokenForPledgeService();
			PledgeServiceClient psc = ClientBeans.getPledgeServiceClient();
			
			try {
				List<Map<String, Object>> pledges = psc.getAllPledgesForUser(bearerToken, userId);
				mv.addObject("pledges", pledges);
			} catch (Exception ex) {
				log.error("Error getting pledges taken by user", ex);
			}
		} catch (Exception ex) {
			log.error("Error getting user pledge data", ex);
		}
		return mv;
	}
	
	@RequestMapping(value = "/home/giveup/like/{giveupId}", method = {RequestMethod.POST})
	public ResponseEntity<String> likeGiveUp(@PathVariable String giveupId) {
		String userId = kaf.getUserId();
		log.info(userId + " initiates like for giveup with id " + giveupId);
		
		try {
			UserActionItem uat = new UserActionItem();
			uat.setActorId(userId);
			uat.setTargetId(giveupId);
			uat.setActionType(UserActionType.LIKE);
			te.execute(new SenderRunnable<UserActionsMessageSender, UserActionItem>(uams, uat));
			return(new ResponseEntity<String>("Ok",HttpStatus.OK));
		} catch (Exception e) {
			log.info("Unable to like giveup with id" + giveupId + "for userId" + userId, e);
			return(new ResponseEntity<String>("Error",HttpStatus.BAD_REQUEST));
		}
	}
	
	@RequestMapping(value = "/home/giveup/unlike/{giveupId}", method = {RequestMethod.POST})
	public ResponseEntity<String> unlikeGiveUp(@PathVariable String giveupId) {
		String userId = kaf.getUserId();
		log.info(userId + " initiates like for giveup with id " + giveupId);
		
		try {
			UserActionItem uat = new UserActionItem();
			uat.setActorId(userId);
			uat.setTargetId(giveupId);
			uat.setActionType(UserActionType.UNLIKE);
			te.execute(new SenderRunnable<UserActionsMessageSender, UserActionItem>(uams, uat));
			return(new ResponseEntity<String>("Ok",HttpStatus.OK));
		} catch (Exception e) {
			log.info("Unable to unlike giveup with id" + giveupId + "for userId" + userId, e);
			return(new ResponseEntity<String>("Error",HttpStatus.BAD_REQUEST));
		}
	}
	
	@RequestMapping(value = "/home/giveup/follow/{giveupId}", method = {RequestMethod.POST})
	public ResponseEntity<String> followGiveUp(@PathVariable String giveupId) {
		String userId = kaf.getUserId();
		log.info(userId + " initiates follow for giveup with id " + giveupId);
		
		try {
			UserActionItem uat = new UserActionItem();
			uat.setActorId(userId);
			uat.setTargetId(giveupId);
			uat.setActionType(UserActionType.FOLLOW_GIVEUP);
			te.execute(new SenderRunnable<UserActionsMessageSender, UserActionItem>(uams, uat));
			return(new ResponseEntity<String>("Ok",HttpStatus.OK));
		} catch (Exception e) {
			log.info("Unable to follow giveup with id " + giveupId + " for userId " + userId, e);
			return(new ResponseEntity<String>("Error",HttpStatus.BAD_REQUEST));
		}
	}
	
	@RequestMapping(value = "/home/giveup/unfollow/{giveupId}", method = {RequestMethod.POST})
	public ResponseEntity<String> unfollowGiveUp(@PathVariable String giveupId) {
		String userId = kaf.getUserId();
		log.info(userId + " initiates unfollow for giveup with id " + giveupId);
		
		try {
			UserActionItem uat = new UserActionItem();
			uat.setActorId(userId);
			uat.setTargetId(giveupId);
			uat.setActionType(UserActionType.UNFOLLOW_GIVEUP);
			te.execute(new SenderRunnable<UserActionsMessageSender, UserActionItem>(uams, uat));
			return(new ResponseEntity<String>("Ok",HttpStatus.OK));
		} catch (Exception e) {
			log.info("Unable to unfollow giveup with id " + giveupId + " for userId " + userId, e);
			return(new ResponseEntity<String>("Error",HttpStatus.BAD_REQUEST));
		}
	}
	
	@RequestMapping("/home/giveups")
	public ModelAndView giveup() {
		ModelAndView mv = new ModelAndView("user/giveup");
		String userId = kaf.getUserId();
		
		log.info("Found username principal: " + userId);

		String giveUpBearerToken = null;
		try {
			giveUpBearerToken = HomeController.getBearerTokenForGiveUpService();
			GiveUpServiceClient gusc = ClientBeans.getGiveUpServiceClient();
			try {
				List<GenericItem<String>> pledgedgiveups = gusc.getAllGiveupUserPledgedFor(giveUpBearerToken, userId);
				mv.addObject("pledgedgiveups", pledgedgiveups);
				
				List<GenericItem<String>> likedGiveups = gusc.allGiveUpLikedByUser(giveUpBearerToken, userId);
				mv.addObject("likedGiveups", likedGiveups);
				
				List<GenericItem<String>> followedGiveups = gusc.allGiveUpFollowedByUser(giveUpBearerToken, userId);
				log.info("Number of giveups followed by user :" + followedGiveups.size());
				mv.addObject("followedGiveups", followedGiveups);
			} catch (Exception ex) {
				log.error("Error getting unique giveups user pledged for", ex);
			}
			try {
				List<GiveUp> suggestedGiveups = gusc.suggestGiveUpToLike(giveUpBearerToken, userId);
				log.info("Suggested Giveups to like :" + suggestedGiveups.size());
				mv.addObject("suggestedGiveups", suggestedGiveups);
				
				List<GiveUp> suggestedGiveupsforFollow = gusc.suggestGiveUpToFollow(giveUpBearerToken, userId);
				log.info("Suggested Giveups to follow :" + suggestedGiveups.size());
				mv.addObject("suggestedGiveupsforFollow", suggestedGiveupsforFollow);
			}catch (Exception e) {
				log.error("Error getting suggested giveups for user", e);
			} 
		} catch (Exception ex) {
			log.error("Error getting user pledge data", ex);
		}
		return mv;
	}
	
	@GetMapping(value = "/home/checktwitterauth")
	public ResponseEntity<Long> checkTwitterAuth() {
		System.out.println("Checking Twitter Auth status");
		String userId = kaf.getUserId();
		System.out.println(userId);
		UserServiceClient usc = ClientBeans.getUserServiceClient();
		return(new ResponseEntity<Long>(usc.checkTwitterAuth(getBearerToken(), userId),HttpStatus.OK));
	}
	
	@GetMapping(value = "/auth/twitter")
	public RedirectView twitterAuthorizationRedirect() {
		UserServiceClient usc = ClientBeans.getUserServiceClient();
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl(usc.twitterAuthUrl(getBearerToken()));
		return redirectView;
	}
	
	@GetMapping(value = "/auth/twitter/url")
	public ResponseEntity<String> getTwitterAuthUrl() {
		UserServiceClient usc = ClientBeans.getUserServiceClient();
		return (new ResponseEntity<String>(usc.twitterAuthUrl(getBearerToken()),HttpStatus.OK));
	}
	
	@GetMapping(value = "/twitter/save/{oauth_verifier}/{oauth_token}")
	public RedirectView dummy(@PathVariable("oauth_verifier") String oauthverifier, @PathVariable("oauth_token") String oauthToken) {
		UserServiceClient usc = ClientBeans.getUserServiceClient();
		String userId = kaf.getUserId();
		System.out.println(userId);
		usc.saveTwitterOauthToken(getBearerToken(), userId, oauthverifier, oauthToken);
		tms.sendMessage(userId);
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("http://localhost:9000/home");
		return redirectView;
	}
	
	@GetMapping(value="/twitter/oauth")
	public RedirectView twitterCallback(@RequestParam("oauth_verifier") String oauthverifier, @RequestParam("oauth_token") String oauthToken) {
//		UserServiceClient usc = ClientBeans.getUserServiceClient();
//		String userId = kaf.getUserId();
//		usc.saveTwitterOauthToken(getBearerToken(), userId, oauthverifier, oauthToken);
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("http://localhost:9000/twitter/save/"+oauthverifier+"/"+oauthToken);
		return redirectView;
	}
	
	@GetMapping(value="/twitter/tweet")
	public void postTweeet() {
		String tweetText = "Tweeting from Spting Boot!!!";
		System.out.println(tweetText);
		String userId = kaf.getUserId();
		UserServiceClient usc = ClientBeans.getUserServiceClient();
		usc.postTweet(getBearerToken(), tweetText, userId);
	}
	
	@GetMapping(value="/user/twitter/timeline")
	public ResponseEntity<String> getAllTweets() {
		String userId = kaf.getUserId();
		UserServiceClient usc = ClientBeans.getUserServiceClient();
		//String tweets = usc.getAllTweet(getBearerToken(), userId);
		//System.out.println(tweets);
		return(usc.getAllTweet(getBearerToken(), userId));
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
