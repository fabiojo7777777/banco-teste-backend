package br.com.api.resource;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/users")
public class UserResource2
{
	@Autowired
	private UserDetailsManager	userDetailsManager;
	@Autowired
	private PasswordEncoder		passwordEncoder;
	@Autowired
	private ObjectMapper		objectMapper;

	public UserResource2()
	{
		super();
		System.err.println("Instanciando " + this.getClass().getSimpleName());
	}

	@PostMapping("/sign-up")
	public void signUp(@Autowired HttpServletRequest req) throws Exception
	{
		if (userDetailsManager instanceof JdbcUserDetailsManager)
		{
			JsonNode tree = objectMapper.readTree(req.getInputStream());
			String username = tree.get("username").asText();
			String password = tree.get("password").asText();
			JsonNode authNode = tree.withArray("authorities");
			String[] authorities = objectMapper.convertValue(authNode, String[].class);

			UserDetails user = User.withUsername(username)
					//
					.passwordEncoder(passwordEncoder::encode)
					//
					.password(password)
					//
					.authorities(authorities)
					//
					.build();

			System.err.println(user);

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