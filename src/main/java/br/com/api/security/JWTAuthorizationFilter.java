//package br.com.api.security;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.context.ApplicationContext;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.SignatureException;
//import io.jsonwebtoken.UnsupportedJwtException;
//
//public class JWTAuthorizationFilter extends BasicAuthenticationFilter
//{
//	private JWTConstants jWTConstants;
//
//	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, ApplicationContext context)
//	{
//		super(authenticationManager);
//		System.err.println("Instanciando " + this.getClass().getSimpleName());
//		this.jWTConstants = context.getBean(JWTConstants.class);
//	}
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException
//	{
//		String header = request.getHeader(jWTConstants.tokenHeaderName);
//		if (header != null && header.startsWith(jWTConstants.tokenPrefix))
//		{
//			UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
//			SecurityContextHolder.getContext().setAuthentication(authentication);
//		}
//		chain.doFilter(request, response);
//	}
//
//	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException, UnsupportedEncodingException
//	{
//		String token = request.getHeader(jWTConstants.tokenHeaderName);
//		if (token != null)
//		{
//			System.err.println("JWTS: " + Jwts.parser().getClass().getCanonicalName());
//			// parse the token.
//			//io.jsonwebtoken.impl.DefaultJwtParser
//			Claims body = Jwts.parser()
//					// secret
//					.setSigningKey(jWTConstants.secret)
//					// parse retirando o prefixo 'Bearer '
//					.parseClaimsJws(token.replace(jWTConstants.tokenPrefix, ""))
//					// pegar conteúdo do token
//					.getBody();
//			String user = body.getSubject();
//			if (user != null)
//			{
//				boolean tokenExpirou = body.getExpiration() != null && body.getExpiration().compareTo(new Date()) < 0;
//				if (!tokenExpirou)
//				{
//					List<GrantedAuthority> permissoes = null;
//					String claims = body.get("permissoes", String.class);
//					System.err.println("$$$claims$$$" + claims + "$$$");
//					if (claims != null && !claims.trim().isEmpty())
//					{
//						permissoes = new ArrayList<GrantedAuthority>();
//						String[] listaPermissoes = claims.split("\\;");
//						for (String permissao : listaPermissoes)
//						{
//							System.err.println("***permissao***" + permissao + "***");
//							// permissoes.add(new SimpleGrantedAuthority("ROLE_"
//							// +
//							// permissao));
//							permissoes.add(new SimpleGrantedAuthority(permissao));
//						}
//					}
//					return new UsernamePasswordAuthenticationToken(user, null, permissoes);
//				}
//			}
//		}
//		return null;
//	}
//}