//package br.com.api.seguranca2;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Component;
//
//import br.com.api.seguranca.regras.PermissoesAcesso;
//
//@Component
//public class JwtUserDetailsService implements UserDetailsService
//{
//	@Override
//	public UserDetails loadUserByUsername(String username)
//	{
//		// String username = username;
//		String password = "teste";
//		boolean enabled = true;
//		boolean accountNonExpired = true;
//		boolean credentialsNonExpired = true;
//		boolean accountNonLocked = true;
//		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//		authorities.add(PermissoesAcesso.getAuthorityByName("READ_Cliente"));
//		// authorities.add(PermissoesAcesso.getAuthorityByName("CREATE_Cliente"));
//		// authorities.add(PermissoesAcesso.getAuthorityByName("UPDATE_Cliente"));
//
//		UserDetails usuario = new User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
//
//		return usuario;
//	}
//
//}
