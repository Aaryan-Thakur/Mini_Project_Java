package com.example.demo;

import java.io.IOException;
// import java.lang.ProcessBuilder.Redirect;
import java.time.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


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

	@Autowired
	private UserRepository repo4;

	@Autowired
	private ResponseRepo repo5;

	@Autowired
	private CommentRepo repo6;
	
	
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
	public String mainpage(@AuthenticationPrincipal CustomUserDetails user,Model model) {
		Iterable<Post> listPosts = repo1.findAll();
		model.addAttribute("listPosts", listPosts);
		User userr = repo.findByID(user.getuserid());
		model.addAttribute("user", userr);
		return "main";
	}
	
	@GetMapping("/create_post")
	public String showUploadForm(@AuthenticationPrincipal CustomUserDetails user,Model model) {
		User userr = repo.findByID(user.getuserid());
		model.addAttribute("post1", new Post());
		model.addAttribute("user", userr);

		
		return "create_post";
	}
	
	@PostMapping("/upload_post")
	public String upload_post(@AuthenticationPrincipal CustomUserDetails user,Post post,@RequestParam("image") MultipartFile multipartFile,Model model) throws IOException {
		post.setUsername(user.getFullName());
		post.setCreate_Date(LocalDateTime.now());
		post.setEmail(user.getUsername());
		post.setOP_id(user.getuserid());
		repo1.save(post);
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        post.setPhotos(fileName);

		Post savedUser = repo1.save(post);

		String uploadDir = "user-photos/" + savedUser.getId();
 
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

		User userr = repo.findByID(user.getuserid());
		model.addAttribute("user", userr);

		


		return "post_success";
	}
	

	
	@GetMapping("/post/{post.id}")
	public String singlePathVariable(@AuthenticationPrincipal CustomUserDetails user,@PathVariable("post.id") long id,Model model) {
		Post post = repo1.findByID(id);
		Iterable<Response> responses = repo5.findByID(post);
		Iterable<Comment> comments = repo6.findByID(post);
		model.addAttribute("post", post);
		model.addAttribute("responses", responses);
		model.addAttribute("comments", comments);
		User userr = repo.findByID(user.getuserid());
		model.addAttribute("user", userr);
		return "postdisplay";
	}
	
	@PostMapping("/upload_response/{post.id}")
	public String upload_response(@AuthenticationPrincipal CustomUserDetails user,@PathVariable("post.id") long id,@RequestParam String sourceText,Model model) {
		Post post = repo1.findByID(id);
		Response response = new Response();
		response.post_id = post;
		response.Response = sourceText;
		response.Responder = user.getFullName();
		response.Create_Date = LocalDateTime.now();
		post.Status = (long) 1;
		repo1.save(post);
		repo5.save(response);
		User userr = repo.findByID(user.getuserid());
		model.addAttribute("user", userr);
		return "response_success";


		}

	@GetMapping("/add_response/{post.id}")
	public String singlePathVariable1(@AuthenticationPrincipal CustomUserDetails user,@PathVariable("post.id") long id,Model model) {
		Post post = repo1.findByID(id);
		model.addAttribute("post2", post);
		User userr = repo.findByID(user.getuserid());
		model.addAttribute("user", userr);
		
		return "add_response";
	}
	
	@GetMapping("/maresolved/{post.id}")
	public String maresolved(@AuthenticationPrincipal CustomUserDetails user,@PathVariable("post.id") long id) {
		Post post = repo1.findByID(id);
		post.Status = (long) 3;
		post.Resolver = user.getFullName();
		post.R_id = user.getuserid();
		repo1.save(post);
		return "redirect:/main";
		}

	@GetMapping("/maresolvedOP/{post.id}")
	public String maresolvedOP(@AuthenticationPrincipal CustomUserDetails user,@PathVariable("post.id") long id) {
		Post post = repo1.findByID(id);
		post.Status = (long) 2;
		repo1.save(post);
		return "redirect:/main";
		}


	// @PostMapping("/upload_response/{post.id}")
	// public String upload_response(@AuthenticationPrincipal CustomUserDetails user,@PathVariable("post.id") long id,@RequestParam String sourceText) {
	// 	Post post = repo1.findByID(id);
	// 	post.Respone = sourceText;
	// 	post.Responder = user.getFullName();
	// 	repo1.save(post);
	// 	return "response_success";
	// 	}

	@PostMapping("/upload_comment/{post.id}")
	public String upload_comment(@AuthenticationPrincipal CustomUserDetails user,@PathVariable("post.id") long id,@RequestParam String sourceText) {
		Post post = repo1.findByID(id);
		Comment comment = new Comment();
		comment.post_id = post;
		comment.comment = sourceText;
		comment.commentor = user.getFullName();
		comment.Create_Date = LocalDateTime.now();
		repo6.save(comment);
		return "redirect:/post/{post.id}";
		}
	
	@GetMapping("/edit_des/{post.id}")
	public String singlePathVariable2(@AuthenticationPrincipal CustomUserDetails user,@PathVariable("post.id") long id,Model model) {
		Post post = repo1.findByID(id);
		model.addAttribute("post3", post);
		User userr = repo.findByID(user.getuserid());
		model.addAttribute("user", userr);
		return "edit_des";
	}
	
	@PostMapping("/upload_des/{post.id}")
	public String upload_des(@AuthenticationPrincipal CustomUserDetails user,@PathVariable("post.id") long id,@RequestParam String sourceText,Model model) {
		Post post = repo1.findByID(id);
		post.Description = sourceText;
		repo1.save(post);
		User userr = repo.findByID(user.getuserid());
		model.addAttribute("user", userr);
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
		User userr = repo.findByID(user.getuserid());
		model.addAttribute("user", userr);
		return "role";
	}
	
	@PostMapping("/declare_role")
	public String uploadrole(@AuthenticationPrincipal CustomUserDetails user,Request R,Model model) {
		
		R.req_user_id=user.getuserid();
		R.setUsername(user.getFullName());
		repo3.save(R);
		if(R.role_id==2)
			R.setRolename("Authority");
		if(R.role_id==3)
			R.setRolename("Moderator");
		
		repo3.save(R);
		User userr = repo.findByID(user.getuserid());
		model.addAttribute("user", userr);
		return "request_success";
	}
	
	@GetMapping("/requests")
	public String req(@AuthenticationPrincipal CustomUserDetails user,Model model) {
		Iterable<Request> listRequests = repo3.findAll();
		model.addAttribute("listRequests", listRequests);
		User userr = repo.findByID(user.getuserid());
		model.addAttribute("user", userr);
		
		return "requests";
	}
	
	@PostMapping("/approve/{id}")
	public String approve(@AuthenticationPrincipal CustomUserDetails user,@PathVariable("id") long id,Model model) {
		
		Request req = repo3.findByID(id);
		
		UR UR1=repo2.findByID(req.getReq_user_id());
		
		
		UR1.setRole_id(req.getRole_id());
		
		repo2.save(UR1);
		
		repo3.deleteById(id);
		User userr = repo.findByID(user.getuserid());
		model.addAttribute("user", userr);
		
		return "approved";
	}
	
	@PostMapping("/deny/{id}")
	public String deny(@AuthenticationPrincipal CustomUserDetails user,@PathVariable("id") long id,Model model) {
		
		repo3.deleteById(id);
		User userr = repo.findByID(user.getuserid());
		model.addAttribute("user", userr);
		return "denied";
	}
	
	@GetMapping("/delete/{post.id}")
	public String singlePathVariable3(@AuthenticationPrincipal CustomUserDetails user,@PathVariable("post.id") long id,Model model) {
		repo1.deleteById(id);
		User userr = repo.findByID(user.getuserid());
		model.addAttribute("user", userr);
		
		return "deleted";
	}

	@GetMapping("/user/{users.id}")
	public String user1(@PathVariable("users.id") long id,Model model) {
		User user = repo4.findByID(id);
		Iterable<Post> listPosts = repo1.findByOPID(id);
		model.addAttribute("listPosts", listPosts);
		model.addAttribute("user", user);
		
		
		return "userpage";
	}

	
}
	
	

