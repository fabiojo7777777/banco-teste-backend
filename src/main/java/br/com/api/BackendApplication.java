package br.com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@ComponentScan("br.com.api")
@SpringBootApplication
public class BackendApplication
{
	public BackendApplication()
	{
		super();
		System.err.println("Instanciando " + this.getClass().getSimpleName());
	}

//	@Bean
//	public BCryptPasswordEncoder bCryptPasswordEncoder()
//	{
//		System.err.println("Instanciando " + BCryptPasswordEncoder.class.getSimpleName());
//		return new BCryptPasswordEncoder();
//	}

	public static void main(String[] args)
	{
		SpringApplication.run(BackendApplication.class, args);
	}

}
