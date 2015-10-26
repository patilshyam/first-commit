package com.ideas.webportal.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.ideas.webportal.exception.ApplicationException;



@Controller
@SessionAttributes("user")
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private UserProfile userProfile;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordService passwordService;
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/portal/login", method = RequestMethod.GET)
    public String login(ModelMap model,HttpServletRequest request, HttpServletResponse response) {
		logger.info("login method inside...");
		request.getSession().invalidate();
        return "login";
    }
	/**
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/portal/loginfailed", method = RequestMethod.GET)
    public String loginfailed(ModelMap model,HttpServletRequest request, HttpServletResponse response) {
		logger.info("loginfailed method inside...");
		String username = "";
		Exception exception = (Exception) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		if(exception instanceof BadCredentialsException){
			username = (String)((BadCredentialsException) exception).getAuthentication().getPrincipal();
//			int loginFailCount = userService.getLoginFailCounter(username);
			String userRole = "";
			com.ideas.webportal.model.domain.User user = userService.getUserDetailsForUserName(username);
			List<UserRole> userRoles = user.getUserRoleList();
			for(UserRole r : userRoles){
				RoleMaster role = r.getRoleMaster();
				if(role.getRoleCode().equals(CommonConstants.VADMIN_ROLE_CODE)){
					userRole = role.getRoleCode();
				}
			}
			if(user.getLoginFailCounter() < 3 && !userRole.equals(CommonConstants.VADMIN_ROLE_CODE)){
				userService.updateLoginFailCounter(username);
			}
			if(user.getLoginFailCounter()==2){
				user.setLoginFailCounter(3);
				try {
					String contextName= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
					passwordService.sendPasswordResetLinkForLockedUser(user, contextName);
				} catch (ApplicationException e) {
					e.printStackTrace();
				}
				model.addAttribute("error", "lock_error");
			}else{
				model.addAttribute("error", "true");
			}
		}else if(exception instanceof LockedException){
			model.addAttribute("error", "lock_error");
		}else {
			model.addAttribute("error", "true");
		}
        return "login";
    }
	
	
	
	/**
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping(value = "/portal/logout", method = RequestMethod.GET)
    public String logout(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
    	logger.info("loginfailed method inside...");
    	request.getSession().invalidate();
        return "logout";
    }
    /**
     * 
     * @param map
     * @param request
     * @param response
     * @param authentication
     * @return
     */

    @RequestMapping(value={"/portal", "/portal/welcome"}, method = RequestMethod.GET)
    public ModelAndView defaultPage(ModelMap map, HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    	logger.info("defaultPage method inside...");
    	try{
    		logger.info("/ method inside...");
    		HttpSession session = request.getSession(false);
    		BankMaster bankMaster=null;
    		userProfile = (UserProfile) session.getAttribute("userProfile");
    		com.ideas.webportal.model.domain.User user=null;
    		if(userProfile == null){
    			userProfile = new UserProfile();
    			session = request.getSession(true);
    			String userName = ((User)authentication.getPrincipal()).getUsername();
    			user = userService.getUserDetailsForUserName(userName);
    			userProfile.setFirstName(user.getFirstName());
    			userProfile.setLastName(user.getLastName());
    			userProfile.setUserId(user.getUserId());
    			userProfile.setUserName(user.getUserName());
    			userProfile.setUserRole(user.getUserRoleList().get(0).getRoleMaster().getRoleName());
    			userProfile.setRoleId(user.getUserRoleList().get(0).getRoleMaster().getRoleId());
    			userProfile.setBankMaster(user.getBankMaster());
    			userProfile.setNoOfDaysPassReset(noOfDaysPasswordReset(user));
    			userService.resetLoginFailCounter(userName);
    			}else {
    				String userName = ((User)authentication.getPrincipal()).getUsername();
    				user = userService.getUserDetailsForUserName(userName);
    				userProfile.setNoOfDaysPassReset(noOfDaysPasswordReset(user));
    			}
    		session.setAttribute("userProfile", userProfile);
    		user = userService.getUserDetailsForUserName(userProfile.getUserName());
    		int passworExpired = noOfDaysPasswordReset(user);
    		if(passworExpired<0){
    			return new ModelAndView("change_password", "password", new Password());
    		}
    		if(userProfile.getRoleId()==4)
    		{
    			return new ModelAndView("home_page");
    		}
    		else 
    		{
    			bankMaster = userProfile.getBankMaster();
    			if(!bankMaster.getStatus().equalsIgnoreCase(CommonConstants.ACTIVE))
        		{
    				session.invalidate();
    				return new ModelAndView("unauthorized");
        		}
    		}
    		return new ModelAndView("home_page");
    	}
    	catch(Exception ex){
    		ex.printStackTrace();
    		return new ModelAndView("exception"); 
    	}
    }

    /**
     * 
     * @param model
     * @param password
     * @return
     */
    @RequestMapping(value = "/portal/forgotpassword", method = RequestMethod.GET)
    public ModelAndView forgotPassword(ModelMap model, @ModelAttribute Password password, HttpServletRequest request, HttpServletResponse response) {
    	logger.info("forgotPassword method inside...");
    	request.getSession().invalidate();
    	return new ModelAndView("forgot_password", "password", new Password());  
    }
    
    /**
     * 
     * @param password
     * @param model
     * @param model1
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/portal/processforgotpassword", method = RequestMethod.POST)
    public ModelAndView processForgotPassword(@ModelAttribute Password password, ModelMap model, Map<String, Object> model1, HttpServletRequest request, HttpServletResponse response) {
    	try {
    		logger.info("processForgotPassword method inside...");
    		int loginFailCounter = userService.getLoginFailCounter(password.getUserName());
    		if(loginFailCounter < (Integer)CommonConstants.LOCKED_LOGIN_ATTEMPTS){
    		String contextName= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
			com.ideas.webportal.model.domain.User user = passwordService.forgotePassword(contextName, password.getEmailId(), password.getUserName(), null);
				if(user.getEmailId().equals(password.getEmailId()) && user.getUserName().equals(password.getUserName())){
					request.setAttribute("messages", "resetpasswordsuccess");
					logger.info("processForgotPassword method end...");
					return new ModelAndView("logout");
				}else{
					request.setAttribute("messages", "invalidEmailOrPass");
					logger.info("processForgotPassword method end...");
					return new ModelAndView("forgot_password", "password", new Password());
				}
			}else{
				request.setAttribute("messages", "accountLoced");
				return new ModelAndView("forgot_password", "password", new Password());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("exception");
		}
    }
    /**
     * 
     * @param e
     * @param u
     * @param t
     * @param password
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/portal/resetpassword", method = RequestMethod.GET)
    public ModelAndView resetPassword(@RequestParam String e, @RequestParam String u, @RequestParam String t, @ModelAttribute Password password, HttpServletRequest request, HttpServletResponse response) {
    	try {
    		logger.info("resetPassword method start...");
			com.ideas.webportal.model.domain.User user = passwordService.validateNewUser(e, u, t);
			if(user != null && t.equals(user.getOneTimePassword())){
				logger.info("resetPassword method end...");
				return new ModelAndView("reset_password", "user", user);  
			}
			request.setAttribute("messages", "timeout");
			logger.info("resetPassword method end...");
			return new ModelAndView("forgot_password", "password", new Password());
		} catch (Exception ea) {
			ea.printStackTrace();
			return new ModelAndView("exception");
		}
    }
    
    

    /**
     * 
     * @param user
     * @param request
     * @param response
     * @param status
     * @return
     */
    @RequestMapping(value = "/portal/processresetpassword", method = RequestMethod.POST)
    public ModelAndView processResetPassword(@ModelAttribute("user") com.ideas.webportal.model.domain.User user, HttpServletRequest request, HttpServletResponse response, SessionStatus status) {
    	try {
    		logger.info("processResetPassword method start...");
			if(user.getPassword().equals(user.getConfirmPassword())){
				user.setLastModifiedTime(new Date());
				boolean flag = passwordService.resetPassword(user);
				if(flag){
		    		request.setAttribute("messages", "resetpassword");
		    	}
			}
			else{
				request.setAttribute("messages", "resetpassworderror");
				logger.info("processResetPassword method end...");
				return new ModelAndView("exception");
			}
			status.setComplete();
			logger.info("processResetPassword method end...");
			return new ModelAndView("logout");
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("exception");
		}
    }
    
    public int noOfDaysPasswordReset(com.ideas.webportal.model.domain.User u){
    	logger.info("noOfDaysPasswordReset method inside...");
    	Date startDate=u.getPasswordResetDate();
    	
    	Calendar end = Calendar.getInstance();
    	Date endDate=end.getTime();
    	
    	long startTime = startDate.getTime();
    	long endTime = endDate.getTime();
    	
    	long diffTime = endTime - startTime;
    	long diffDays = diffTime / (1000 * 60 * 60 * 24);
    	
    	long noOfDaysLeft=30-diffDays;
    	logger.info("Number of days left is");
    	logger.info(Long.toString(noOfDaysLeft));
    	return (int)noOfDaysLeft;
    }
    
    @RequestMapping(value = "/portal/changepassword", method = RequestMethod.GET)
    public ModelAndView changepassword(HttpServletRequest request, HttpServletResponse response) {
    	logger.info("changepassword method inside...");
//    	userProfile = (UserProfile) request.getSession().getAttribute("userProfile");
    	return new ModelAndView("change_password", "password", new Password());  	
    }
    
    /**
     * 
     * @param user
     * @param request
     * @param response
     * @param status
     * @return
     */
    @RequestMapping(value = "/portal/processchangepassword", method = RequestMethod.POST)
    public ModelAndView processChangePassword(@ModelAttribute Password password, HttpServletRequest request, HttpServletResponse response, SessionStatus status) {
    	try {
        	userProfile = (UserProfile) request.getSession().getAttribute("userProfile");
        	com.ideas.webportal.model.domain.User user = userService.getUserDetailsForUserName(userProfile.getUserName());
    		logger.info("processChangePassword method start...");
    		String oldPAssword=passwordService.decryptPassword(user.getPassword());
			if(password.getPassword().equals(password.getConfirmPassword()) && (password.getOldPassword().equals(oldPAssword))){
				user.setLastModifiedTime(new Date());
				user.setPasswordResetDate(new Date());
				user.setPassword(password.getPassword());
				boolean flag = passwordService.changePassword(user);
				if(flag){
		    		request.setAttribute("messages", "changepassword");
		    	}
				return new ModelAndView("change_password_success");
			}
			else{
				request.setAttribute("messages", "passwordnotmatch");
				logger.info("processChangePassword method end...");
				return new ModelAndView("change_password", "password", new Password());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("exception");
		}
    }
}