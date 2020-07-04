//package br.com.api.security;
//
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import br.com.api.repository.ApplicationUserRepository;
//import br.com.api.repository.entity.ApplicationUser;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService
//{
//	private ApplicationUserRepository applicationUserRepository;
//
//	public UserDetailsServiceImpl(ApplicationUserRepository applicationUserRepository)
//	{
//		super();
//		System.err.println("Instanciando " + this.getClass().getSimpleName());
//		this.applicationUserRepository = applicationUserRepository;
//	}
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
//	{
//		ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
//		if (applicationUser == null)
//		{
//			throw new UsernameNotFoundException(username);
//		}
//		return new User(applicationUser.getUsername(), applicationUser.getPassword(), applicationUser.getAuthorities());
//	}
//}