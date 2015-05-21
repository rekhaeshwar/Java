package com.bookstore.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bookstore.business.BusinessService;
import com.bookstore.data.User;
import com.bookstore.data.UserInfo;
import com.bookstore.web.util.RESTUtil;
import com.meetup.memcached.MemcachedClient;

@PropertySource(value = { "classpath:application.properties" })

@Controller
public class UserLoginContoller {
	
	@Autowired
	BusinessService transactionService;
private static final Logger LOG = LoggerFactory.getLogger(UserLoginContoller.class);
	
	@Autowired
	private RESTUtil restUtil;
	

	@Autowired
	private Environment env;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginVerify( Model model) {
		System.out.print("login() called");
		model.addAttribute("loginForm", new User());

		return "login";
	}
	@RequestMapping(value = { "/authenticate" }, method = RequestMethod.POST)
	public String authenticate(
			@ModelAttribute(value = "loginForm") @Valid User loginForm,
			BindingResult result, HttpServletRequest request, Model model) {
		System.out.print("loginVerify() called");
		//System.out.print("user "+user.getName() +" pass "+user.getPwd());
		UsernamePasswordToken token = new UsernamePasswordToken(
				loginForm.getUsername(), loginForm.getPassword());
		token.setRememberMe(true);
		Subject currentUser = SecurityUtils.getSubject();
		try {
			currentUser.login(token);
			LOG.debug("User " + loginForm.getUsername()
					+ " has been authenticated");

		} catch (AuthenticationException ae) {
			LOG.error("User " + loginForm.getUsername() + " failed to login");
			loginForm.setErrorMsg("Incorrect username or password");
			return "login";
		}

		if (currentUser.isAuthenticated()) {
			Session session = currentUser.getSession();
			session.setAttribute("username", loginForm.getUsername());
			/*session.setAttribute("userId", userDto.getUserId());
			session.setAttribute("firstName", userDto.getFirstName());
			session.setAttribute("lastName", userDto.getLastName());*/
			MemcachedClient memcache=  transactionService.startMemcached();
			memcache.add(loginForm.getUsername(),loginForm);
			
			
			return "afterlogin";
		}
		else
		{
			loginForm.setErrorMsg("Incorrect username or password");
			return "login";
			
		}

}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		System.out.print("index() called");
		return "index";
	}
	
	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public String cart() {
		System.out.print("booksearch() called");
		return "cart";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup() {
		System.out.print("signup() called");
		return "signup";
	}
	
	@RequestMapping(value = "/signupsubmit", method = RequestMethod.POST)
	public String signupsubmit(@ModelAttribute(value = "userInfo") @Valid UserInfo userInfo,
			BindingResult result, HttpServletRequest request, Model model) {
		System.out.print("signupsubmit() called");

		//userInfo.getUser().setSalt(User.getSalt());
		User user= new User();
		user.setUsername(userInfo.getUsername());
		user.setPassword(userInfo.getPassword());
		user.setSalt(getCombinedSalt());
		//user.setPassword(encodePassword(userInfo.getPassword(), user.getSalt()));
		userInfo.setUser(user);
		try{
			transactionService.signup(userInfo);

		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		//transactionService.userSignUp(userInfo.getUser());
		return "afterlogin";
	}
	
	@RequestMapping(value = "/giftcard", method = RequestMethod.GET)
	public String  giftcard() {
		System.out.print("booksearch() called");
		return "giftcard";
	}
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
		System.out.print("home() called");
		return "home";
	}
	public static String getSalt() {
	    return new SecureRandomNumberGenerator().nextBytes().toBase64();
	  }
	
	/*public String encodePassword(String rawPassphrase, String salt) {
		return new Sha512Hash(rawPassphrase, getCombinedSalt(salt), getIterations()).toBase64();
	  }*/
	public String getCombinedSalt() {
		LOG.debug("env.getProperty(applicationSalt)=="+env.getProperty("shiro.applicationSalt"));
	    return env.getProperty("shiro.applicationSalt");
	  }
	  
	public Integer getIterations() {
		LOG.debug("env.getProperty(hashIterations)=="+env.getProperty("shiro.hashIterations"));
	    return Integer.parseInt(env.getProperty("shiro.hashIterations"));
	  }
}
