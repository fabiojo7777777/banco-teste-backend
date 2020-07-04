package br.com.api.security2;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class JWTUtil
{
	@Autowired
	private JWTConstants jWTConstants;

	public Authentication getAuthenticationFromToken(HttpServletRequest request)
			throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException, UnsupportedEncodingException
	{
		String token = getTokenFromRequest(request);
		if (token != null)
		{
			System.err.println("JWTS: " + Jwts.parser().getClass().getCanonicalName());
			// parse the token.
			// io.jsonwebtoken.impl.DefaultJwtParser
			Claims body = Jwts.parser()
					// secret
					.setSigningKey(jWTConstants.secret)
					// parse retirando o prefixo 'Bearer '
					.parseClaimsJws(token.replace(jWTConstants.tokenPrefix, ""))
					// pegar conteúdo do token
					.getBody();
			String user = body.getSubject();
			if (user != null)
			{
				boolean tokenExpirou = body.getExpiration() != null && body.getExpiration().compareTo(new Date()) < 0;
				if (!tokenExpirou)
				{
					List<GrantedAuthority> permissoes = null;
					String claims = body.get("permissoes", String.class);
					System.err.println("$$$claims$$$" + claims + "$$$");
					if (claims != null && !claims.trim().isEmpty())
					{
						permissoes = new ArrayList<GrantedAuthority>();
						String[] listaPermissoes = claims.split("\\;");
						for (String permissao : listaPermissoes)
						{
							System.err.println("***permissao***" + permissao + "***");
							// permissoes.add(new SimpleGrantedAuthority("ROLE_"
							// +
							// permissao));
							permissoes.add(new SimpleGrantedAuthority(permissao));
						}
					}
					return new UsernamePasswordAuthenticationToken(user, null, permissoes);
				}
			}
		}
		return null;
	}

	private String getTokenFromRequest(HttpServletRequest request)
	{
		String header = request.getHeader(jWTConstants.tokenHeaderName);
		if (header != null && header.startsWith(jWTConstants.tokenPrefix))
		{
			// Remover prefixo Bearer antes do valor do token
			return header.substring(jWTConstants.tokenPrefix.length());
		}
		return null;
	}

	public void gerarRefreshToken(HttpServletResponse response)
	{
		System.err.println("Gerando refresh token");
		// Cria novo token e envia na resposta
		String novoToken = criarNovoToken();
		System.err.println("com " + novoToken);
		if (novoToken != null)
		{
			// Acrescentar header Authorization e o prefixo Bearer antes do
			// valor do token
			response.addHeader(jWTConstants.tokenHeaderName, jWTConstants.tokenPrefix + novoToken);
		}
	}

	private String criarNovoToken()
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null)
		{
			Object principal = auth.getPrincipal();
			String username = null;
			Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
			if (principal != null)
			{
				if (principal instanceof UserDetails)
				{
					username = ((UserDetails) principal).getUsername();
				} else if (principal instanceof String)
				{
					username = (String) principal;
				}
			}
			if (username != null)
			{
				Map<String, Object> claims = new HashMap<>();
				claims.put("permissoes", getStringAutorities(authorities));
				System.err.println("Gravando permissoes no token: " + claims);
				return Jwts.builder()
						// claims
						.setClaims(claims)
						// subject
						.setSubject(username)
						// data de criação
						.setIssuedAt(new Date(System.currentTimeMillis()))
						// validade
						.setExpiration(new Date(System.currentTimeMillis() + jWTConstants.jwtTokenValidity))
						// senha de encriptação
						.signWith(SignatureAlgorithm.HS512, jWTConstants.secret)
						// build
						.compact();
			}
		}
		return null;
	}

	private String getStringAutorities(Collection<? extends GrantedAuthority> authorities)
	{
		if (authorities != null && !authorities.isEmpty())
		{
			StringBuffer sb = new StringBuffer();
			for (GrantedAuthority authority : authorities)
			{
				sb.append(authority.getAuthority());
				sb.append(';');
			}
			if (sb.length() > 0)
			{
				sb.deleteCharAt(sb.length() - 1);
			}
			return sb.toString();
		}
		return null;
	}

}
