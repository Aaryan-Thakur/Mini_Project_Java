package com.example.demo;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AppController {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private PostRepsitory repo1;
	
	@Autowired
	private UseriRoleRep repo2;
	
	@Autowired
	private RequestRepo repo3;
	
	
	@GetMapping("/home")
	public String viewHomePage() {
		return "index";
	}

	@GetMapping("/register")
	public String showSignUpForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("UR", new UR());
		
		return "signup_form";
	}
	
	@PostMapping("/process_register")
	public String processRegisteration(User user,UR UR) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		repo.save(user);
		
		UR.setUser_id(user.getId());
		UR.setRole_id((long) 1);
		repo2.save(UR);
		return "register_success";
	}
	
	@GetMapping("/main")
	public String mainpage(Model model) {
		Iterable<Post> listPosts = repo1.findAll();
		model.addAttribute("listPosts", listPosts);
		return "main";
	}
	
	@GetMapping("/create_post")
	public String showUploadForm(Model model) {
		model.addAttribute("post1", new Post());
		return "create_post";
	}
	
	@PostMapping("/upload_post")
	public String upload_post(@AuthenticationPrincipal CustomUserDetails user,Post post) {
		post.setUsername(user.getFullName());
		post.setCreate_Date(LocalDate.now());
		post.setEmail(user.getUsername());
		post.setOP_id(user.getuserid());
		repo1.save(post);
		return "post_success";
	}
	

	
	@GetMapping("/post/{post.id}")
	public String singlePathVariable(@PathVariable("post.id") long id,Model model) {
		Post post = repo1.findByID(id);
		model.addAttribute("post", post);
		
		return "postdisplay";
	}
	
	@GetMapping("/add_response/{post.id}")
	public String singlePathVariable1(@PathVariable("post.id") long id,Model model) {
		Post post = repo1.findByID(id);
		model.addAttribute("post2", post);
		return "add_response";
	}
	
	@PostMapping("/upload_response/{post.id}")
	public String upload_response(@AuthenticationPrincipal CustomUserDetails user,@PathVariable("post.id") long id,@RequestParam String sourceText) {
		Post post = repo1.findByID(id);
		post.Respone = sourceText;
		post.Responder = user.getFullName();
		repo1.save(post);
		return "response_success";
		}
	
	@GetMapping("/edit_des/{post.id}")
	public String singlePathVariable2(@PathVariable("post.id") long id,Model model) {
		Post post = repo1.findByID(id);
		model.addAttribute("post3", post);
		return "edit_des";
	}
	
	@PostMapping("/upload_des/{post.id}")
	public String upload_des(@PathVariable("post.id") long id,@RequestParam String sourceText) {
		Post post = repo1.findByID(id);
		post.Description = sourceText;
		repo1.save(post);
		return "post_success";
		}
	
	
//	@GetMapping("/role")
//	public String showroleoptions(@AuthenticationPrincipal CustomUserDetails user,Model model) {
//		Optional<UR> UR= repo2.findById(user.getuserid());
//		model.addAttribute("UR",UR);
//		return "role";
//	}
//	
//	@PostMapping("/declare_role")
//	public String uploadrole(@AuthenticationPrincipal CustomUserDetails user,UR UR1) {
//		
//		UR1.user_id=user.getuserid();
//		repo2.save(UR1);
//		return "post_success";
//	}
	
	@GetMapping("/role")
	public String showroleoptions(@AuthenticationPrincipal CustomUserDetails user,Model model) {
		model.addAttribute("Request",new Request());
		return "role";
	}
	
	@PostMapping("/declare_role")
	public String uploadrole(@AuthenticationPrincipal CustomUserDetails user,Request R) {
		
		R.req_user_id=user.getuserid();
		R.setUsername(user.getFullName());
		repo3.save(R);
		if(R.role_id==2)
			R.setRolename("Authority");
		if(R.role_id==3)
			R.setRolename("Moderator");
		
		repo3.save(R);
		return "request_success";
	}
	
	@GetMapping("/requests")
	public String req(Model model) {
		Iterable<Request> listRequests = repo3.findAll();
		model.addAttribute("listRequests", listRequests);
		
		return "requests";
	}
	
	@PostMapping("/approve/{id}")
	public String approve(@PathVariable("id") long id) {
		
		Request req = repo3.findByID(id);
		
		UR UR1=repo2.findByID(req.getReq_user_id());
		
		
		UR1.setRole_id(req.getRole_id());
		
		repo2.save(UR1);
		
		repo3.deleteById(id);
		
		return "requests";
	}
	
	@PostMapping("/deny/{id}")
	public String deny(@PathVariable("id") long id) {
		
		repo3.deleteById(id);
		return "requests";
	}
	
	@GetMapping("/delete/{post.id}")
	public String singlePathVariable3(@PathVariable("post.id") long id,Model model) {
		repo1.deleteById(id);
		
		return "deleted";
	}
}
	
	

