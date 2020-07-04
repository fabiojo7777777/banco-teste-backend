package br.com.api.security2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTConstants
{
	@Value("${jwt.tokenPrefix}")
	public String	tokenPrefix;
	@Value("${jwt.tokenHeaderName}")
	public String	tokenHeaderName;
	@Value("${jwt.signUpUrl}")
	public String	signUpUrl;
	@Value("${jwt.validity}")
	public long		jwtTokenValidity;
	@Value("${jwt.secret}")
	public String	secret;

	public JWTConstants()
	{
		super();
		System.err.println("Instanciando " + this.getClass().getSimpleName());
	}
}
