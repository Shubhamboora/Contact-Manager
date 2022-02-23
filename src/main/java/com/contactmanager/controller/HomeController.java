package com.contactmanager.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.contactmanager.dao.UserRepository;
import com.contactmanager.entities.User;
import com.contactmanager.helper.Message;

@Controller
public class HomeController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/")
	public String home(Model model) {

		model.addAttribute("title", "Home - Contact Manager");
		return "home";
	}

	@GetMapping("/about")
	public String about(Model model) {

		model.addAttribute("title", "about - Contact Manager");
		return "about";
	}

	@GetMapping("/signup")
	public String signUp(Model model) {

		model.addAttribute("title", "Register - Contact Manager");
		model.addAttribute("user", new User());
		return "signup";
	}

	@PostMapping("do_register")
	public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult result,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model,HttpSession session) {
		try {
			if (!agreement) {
				
				throw new Exception("You have not agreed terms and conditions");
			}
			else if(result.hasErrors()) {
				model.addAttribute("user", user);
				return "signup";
			}
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			System.out.println("Agreement: " + agreement);
			System.out.println("User: " + user);
			user.setImageUrl("User_default.png");
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			this.userRepository.save(user);

			model.addAttribute("user", new User());
			session.setAttribute("message", new Message("Successfully Registered!!", "alert-success"));
			return "signup";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Something Went wrong!!"+e.getMessage(), "alert-danger"));
			return "signup";
		}
		
	}
	
	@GetMapping("/signin")
	public String customLogin(Model model) {
		model.addAttribute("title", "login - Contact Manager");
		return "login";
	}
}
