package br.com.api.security2;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Service;

@Service
public class JWTAuthorizationFilter extends BasicAuthenticationFilter
{
	@Autowired
	private JWTUtil jWTUtil;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager)
	{
		super(authenticationManager);
		System.err.println("Instanciando " + this.getClass().getSimpleName());
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		System.out.println(">>>> " + getClass().getSimpleName() + ".doFilterInternal()");

		Authentication auth = jWTUtil.getAuthenticationFromToken(request);
		SecurityContextHolder.getContext().setAuthentication(auth);
		jWTUtil.gerarRefreshToken(response);

		chain.doFilter(request, response);
	}

}