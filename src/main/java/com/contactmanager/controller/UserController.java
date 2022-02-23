package com.contactmanager.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.contactmanager.dao.UserRepository;
import com.contactmanager.dao.ContactRepository;
import com.contactmanager.entities.Contact;
import com.contactmanager.entities.User;
import com.contactmanager.helper.Message;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ContactRepository contactRepository;
	private User user;

	// Common method load for every handler
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String name = principal.getName();
		this.user = this.userRepository.getUserByUserName(name);
		model.addAttribute(user);
	}

	@GetMapping("/index")
	public String dashboard(Model model) {

		return "normal/user_dashboard";
	}

	/* open add form handler */
	@GetMapping("/add-contact")
	public String openaddContactForm(Model model) {
		model.addAttribute("title", "user - Contact Manager");
		model.addAttribute("contact", new Contact());
		return "normal/add_contact_form";
	}

	// Processing contact form
	@PostMapping("/process-contact")
	public String processContact(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file,
			HttpSession session) {

		try {

			contact.setUser(user);
			user.getContacts().add(contact);
			// processing and uploading photo

			if (file.isEmpty()) {
				System.out.println("File is empty");
				contact.setImage("contact.png");

			} else {
				contact.setImage(file.getOriginalFilename());

				File saveFile = new ClassPathResource("static/img").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				

				
			}
			
			// Success Message
			session.setAttribute("message", new Message("Your contact is added...", "success"));
			
			this.userRepository.save(user);
			System.out.println("Successfully Added");
		} catch (Exception E) {
			E.printStackTrace();

			// Failed message
			session.setAttribute("message", new Message("Something went wrong "+E.getMessage(), "danger"));
		}

		return "normal/add_contact_form";
	}
	
	//Show contacts handler
	@GetMapping("/show-contacts/{page}")
	public String showContacts(@PathVariable("page") Integer page, Model m) {
		m.addAttribute("title", "user - Contact Manager");
		Pageable pageable = PageRequest.of(page, 5);
		Page<Contact> contacts = this.contactRepository.findContactsByUser(user.getId(), pageable);
		m.addAttribute("contacts",contacts);
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPages", contacts.getTotalPages());
		
		return "normal/show_contacts";
	}
	
	//Showing Perticular contact Details
	@GetMapping("/contact/{cId}")
	public String showContactDetail(@PathVariable("cId") Integer cId,Model model) {
		Optional<Contact> contactOptional = this.contactRepository.findById(cId);
		Contact contact = contactOptional.get();
		
		//Checking weather user is requesting to see his own contact's details or someone else.
		if(this.user.getId()==contact.getUser().getId()) {
			model.addAttribute("title", contact.getName());
			model.addAttribute("contact",contact);
		}else {
			model.addAttribute("title", "Error");
		}
		
		
		return "normal/describe_contacts";
	}
	
	//Delete contact handler
	@GetMapping("/delete/{cId}")
	public String deleteContact(@PathVariable("cId") Integer cId,Model model,HttpSession session) {
		try {
		Optional<Contact> findById = this.contactRepository.findById(cId);
		Contact contact = findById.get();
		if(this.user.getId()==contact.getUser().getId()) {
			model.addAttribute("title", "Delete");
			//
			this.contactRepository.delete(contact);
			session.setAttribute("message", new Message("Contact Deleted successfully...", "success"));
		}
		}catch(Exception e) {
			session.setAttribute("message", new Message("Something went wrong "+e.getMessage(), "danger"));
		}
		return "redirect:/user/show-contacts/0";
	}
	
	//open update form handler
	@PostMapping("/update-contact/{cId}")
	public String updateForm(@PathVariable("cId")Integer cId, Model m) {
		
		m.addAttribute("title", "Update Contact");
		
		 Contact findById = this.contactRepository.findById(cId).get();
		 m.addAttribute("contact", findById);
		return "normal/update_form";
	}
	
	//update contact handler
	@PostMapping("/process-update")
	public String updateHandler(@ModelAttribute Contact contact,@RequestParam("profileImage") MultipartFile file,
			Model model,HttpSession session) {
		try {
			System.out.println(contact.getName());
			System.out.println(contact.getcId());
			
			//old contact detail
			Contact existingdetails = this.contactRepository.findById(contact.getcId()).get();
			
			if(!file.isEmpty()) {
				//delete old photo
				if(file.getOriginalFilename() !="contact.png") {
				File deleteFile = new ClassPathResource("static/img").getFile();
				File file1 = new File(deleteFile, existingdetails.getImage());
				file1.delete();
				}
				//add new photo
				
				File saveFile = new ClassPathResource("static/img").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				
				contact.setImage(file.getOriginalFilename());
				session.setAttribute("message", new Message("Your contact is Updated", "success"));
			}else {
				contact.setImage(existingdetails.getImage());
			}
			contact.setUser(this.user);
			this.contactRepository.save(contact);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
			
		//"redirect:/user/show-contact/"+contact.getcId()
		return "redirect:/user/contact/"+contact.getcId();
	}
	
	//Profile handler
	@GetMapping("/profile")
	public String yourProfile(Model model) {
		model.addAttribute("Profile", "Profile - Contact Manager");
		return "normal/profile";
	}
}
