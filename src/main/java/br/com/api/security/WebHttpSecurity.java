//package br.com.api.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//@EnableWebSecurity
//public class WebHttpSecurity extends WebSecurityConfigurerAdapter
//{
//	@Autowired
//	private JWTConstants			jWTConstants;
//	private UserDetailsServiceImpl	userDetailsService;
//	private BCryptPasswordEncoder	bCryptPasswordEncoder;
//
//	public WebHttpSecurity(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder)
//	{
//		super();
//		System.err.println("Instanciando " + this.getClass().getSimpleName());
//		this.userDetailsService = userDetailsService;
//		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception
//	{
//		http
//				// cors
//				.cors().and()
//				// csrf
//				.csrf().disable()
//				// endpoint sem autenticação
//				.authorizeRequests().antMatchers(HttpMethod.POST, jWTConstants.signUpUrl).permitAll()
//				// favicon.ico
//				.antMatchers("/favicon.ico").permitAll()
//				// qualquer endpoint, autenticado
//				.anyRequest().authenticated().and()
//				// filtro1
//				.addFilter(new JWTAuthenticationFilter(authenticationManager(), getApplicationContext()))
//				// filtro2
//				.addFilter(new JWTAuthorizationFilter(authenticationManager(), getApplicationContext()))
//				// this disables session creation on Spring Security
//				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//	}
//
//	@Override
//	public void configure(AuthenticationManagerBuilder auth) throws Exception
//	{
//		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
//	}
//
//	@Bean
//	CorsConfigurationSource corsConfigurationSource()
//	{
//		System.err.println("Instanciando " + CorsConfigurationSource.class.getSimpleName());
//		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
//		return source;
//	}
//}