//package br.com.api.security;
//
//import java.io.IOException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.context.ApplicationContext;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import br.com.api.repository.ApplicationUserRepository;
//import br.com.api.repository.entity.ApplicationUser;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
////Este filtro também é um "resource" atende ao endpoint /login,
//// ver AbstractAuthenticationProcessingFilter.doFilter, que deixa passar 
//// a url /login sem autenticação (linha if (!requiresAuthentication(request, response)))
//// a diferença é que 
//public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter
//{
//	private JWTConstants				jWTConstants;
//	private AuthenticationManager		authenticationManager;
//	private ApplicationUserRepository	applicationUserRepository;
//
//	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, ApplicationContext context)
//	{
//		super();
//		System.err.println("Instanciando " + this.getClass().getSimpleName());
//		this.authenticationManager = authenticationManager;
//		this.jWTConstants = context.getBean(JWTConstants.class);
//		this.applicationUserRepository = context.getBean(ApplicationUserRepository.class);
//	}
//
//	@Override
//	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException
//	{
//		try
//		{
//			ApplicationUser creds = new ObjectMapper().readValue(req.getInputStream(), ApplicationUser.class);
//			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword()));
//		} catch (IOException e)
//		{
//			throw new RuntimeException(e);
//		}
//	}
//
//	@Override
//	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException
//	{
//		String username = ((User) auth.getPrincipal()).getUsername();
//		ApplicationUser user = applicationUserRepository.findByUsername(username);
//		Map<String, Object> claims = new HashMap<>();
//		claims.put("permissoes", user.getStringAutorities());
//		System.err.println("Gravando permissoes no token: " + claims);
//		String token = Jwts.builder()
//				// claims
//				.setClaims(claims)
//				// subject
//				.setSubject(username)
//				// data de criação
//				.setIssuedAt(new Date(System.currentTimeMillis()))
//				// validade
//				.setExpiration(new Date(System.currentTimeMillis() + jWTConstants.jwtTokenValidity))
//				// senha de encriptação
//				.signWith(SignatureAlgorithm.HS512, jWTConstants.secret)
//				// build
//				.compact();
//		res.addHeader(jWTConstants.tokenHeaderName, jWTConstants.tokenPrefix + token);
//	}
//
//}