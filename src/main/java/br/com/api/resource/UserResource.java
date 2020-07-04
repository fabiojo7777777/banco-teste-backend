//package br.com.api.resource;
//
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import br.com.api.repository.ApplicationUserRepository;
//import br.com.api.repository.AuthorityRepository;
//import br.com.api.repository.entity.ApplicationUser;
//
//@RestController
//@RequestMapping("/users")
//public class UserResource
//{
//	private ApplicationUserRepository	applicationUserRepository;
//	private AuthorityRepository			authorityRepository;
//	private PasswordEncoder				passwordEncoder;
//
//	public UserResource(AuthorityRepository authorityRepository, ApplicationUserRepository applicationUserRepository, PasswordEncoder passwordEncoder)
//	{
//		super();
//		System.err.println("Instanciando " + this.getClass().getSimpleName());
//		this.applicationUserRepository = applicationUserRepository;
//		this.authorityRepository = authorityRepository;
//		this.passwordEncoder = passwordEncoder;
//	}
//
//	@PostMapping("/sign-up")
//	public void signUp(@RequestBody ApplicationUser user)
//	{
//		System.err.println(user);
//		System.err.println(">>>" + user.getAuthorities());
//		System.err.println("&&&" + authorityRepository);
//		System.err.println("+++" + applicationUserRepository);
//		user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//		// if (user.getAuthorities() != null)
//		// {
//		// authorityRepository.saveAll(user.getAuthorities());
//		// }
//		applicationUserRepository.save(user);
//		// applicationUserRepository.flush();
//		// authorityRepository.flush();
//	}
//}