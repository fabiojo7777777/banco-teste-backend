//package br.com.api.security;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import br.com.api.repository.ApplicationUserRepository;
//import br.com.api.repository.entity.ApplicationUser;
//import br.com.api.repository.entity.Authority;
//
//// Classe que é ativado pelo evento de aplicação é iniciada ou atualizada
//@Component
//public class ContextRefreshListener implements ApplicationListener<ContextRefreshedEvent>
//{
//	@Autowired
//	private ApplicationUserRepository	applicationUserRepository;
//	@Autowired
//	private BCryptPasswordEncoder		passwordEncoder;
//
//	@Override
//	public void onApplicationEvent(ContextRefreshedEvent event)
//	{
//		Set<Authority> authorities = new HashSet<Authority>();
//		authorities.add(new Authority("CREATE_Cliente"));
//		authorities.add(new Authority("READ_Cliente"));
//		authorities.add(new Authority("UPDATE_Cliente"));
//		authorities.add(new Authority("DELETE_Cliente"));
//
//		ApplicationUser user = new ApplicationUser("xxxaaa", passwordEncoder.encode("xxxaaa"));
//		user.setAuthorities(authorities);
//
//		applicationUserRepository.save(user);
//	}
//
//}
