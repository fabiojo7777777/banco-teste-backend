package br.com.api.security2;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebHttpSecurity extends WebSecurityConfigurerAdapter
{
	@Autowired
	private JWTConstants	jWTConstants;
	@Autowired
	private DataSource		dataSource;

	@Bean
	public UserDetailsManager userDetailsManager() throws Exception
	{
		System.err.println("Instanciando " + JdbcUserDetailsManager.class.getSimpleName());
		JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
		manager.setDataSource(dataSource);
		manager.setAuthenticationManager(authenticationManagerBean());
		return manager;
	}

	@Bean
	public PasswordEncoder passwordEncoder()
	{
		System.err.println("Instanciando " + BCryptPasswordEncoder.class.getSimpleName());
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		System.err.println("Obtendo " + AuthenticationManager.class.getSimpleName());
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		System.err.println("Configure 1");
		// https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#filter-stack
		http
				// cors
				.cors().and()
				// csrf
				.csrf().disable()
				//
				.authorizeRequests()
				//
				.antMatchers(HttpMethod.POST, jWTConstants.signUpUrl).permitAll()
				//
				.anyRequest().authenticated().and()
				// filtro1
				.addFilter(getApplicationContext().getBean(JWTAuthenticationFilter.class))
				// filtro2
				.addFilter(getApplicationContext().getBean(JWTAuthorizationFilter.class))
				//
				// this disables session creation on Spring Security
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		System.err.println("Configure 2");
		auth
				//
				.userDetailsService(userDetailsManager())
				// EXECUTAR COM ESTA LINHA SÓ 1 VEZ PARA CRIAR BANCO DE DADOS
				// .and().jdbcAuthentication().dataSource(dataSource).withDefaultSchema()
				//
				.passwordEncoder(passwordEncoder());
	}

}