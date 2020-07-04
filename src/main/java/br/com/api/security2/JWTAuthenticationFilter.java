package br.com.api.security2;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

//Este filtro também é um "resource" atende ao endpoint /login,
// ver AbstractAuthenticationProcessingFilter.doFilter, que deixa passar 
// a url /login sem autenticação (linha if (!requiresAuthentication(request, response)))
@Service
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
	@Autowired
	private JWTUtil			jWTUtil;
	@Autowired
	private ObjectMapper	objectMapper;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager)
	{
		super();
		System.err.println("Instanciando " + this.getClass().getSimpleName());
		super.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException
	{
		try
		{
			System.out.println(">>>> " + getClass().getSimpleName() + ".attemptAuthentication()");
			JsonNode tree = objectMapper.readTree(req.getInputStream());
			String username = tree.get("username").asText();
			String password = tree.get("password").asText();
			return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException
	{
		System.out.println("comitado? " + response.isCommitted());
		SecurityContextHolder.getContext().setAuthentication(auth);
		jWTUtil.gerarRefreshToken(response);
	}

}