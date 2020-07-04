package br.com.api.security2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

// Classe que é ativado pelo evento de aplicação é iniciada ou atualizada
@Component
public class ContextRefreshListener implements ApplicationListener<ContextRefreshedEvent>
{
	@Autowired
	private UserDetailsManager	userDetailsManager;
	@Autowired
	private PasswordEncoder		passwordEncoder;

	public ContextRefreshListener()
	{
		super();
		System.err.println("Instanciando " + this.getClass().getSimpleName());
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event)
	{
		if (userDetailsManager instanceof JdbcUserDetailsManager)
		{
			String username = "usuario_root";
			String password = "usuario_root";
			String[] authorities = new String[]
			{ "CREATE_Cliente", "READ_Cliente", "UPDATE_Cliente", "DELETE_Cliente" };

			UserDetails user = User
					//
					.withUsername(username)
					//
					.password(password)
					//
					.passwordEncoder(passwordEncoder::encode)
					//
					.authorities(authorities)
					//
					.build();

			JdbcUserDetailsManager userService = (JdbcUserDetailsManager) userDetailsManager;
			if (userService.userExists(user.getUsername()))
			{
				userService.updateUser(user);
			} else
			{
				userService.createUser(user);
			}
		} else
		{
			throw new RuntimeException("Não há banco de dados configurado.");
		}
	}

}
