package com.achavez.springboot.controlempleados.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@SuppressWarnings("deprecation")
@Configuration

public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails usuario1 = User
				.withUsername("achavez")
				.password("$2a$10$21k9QLX0vdSytZ36T56yHeDwn2y6DiHdJ2k7fCzccV7CuskmNQmEq")
				.roles("USER")	
				.build();
		
		UserDetails usuario2 = User
				.withUsername("admin")
				.password("$2a$10$21k9QLX0vdSytZ36T56yHeDwn2y6DiHdJ2k7fCzccV7CuskmNQmEq")
				.roles("ADMIN")	
				.build();
		
		return new InMemoryUserDetailsManager(usuario1,usuario2);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		    .antMatchers("/",
		    		"/js/**",
                    "/css/**",
                    "/images/**").permitAll()
		    .antMatchers("/form/*","/eliminar/*").hasRole("ADMIN")
		    .anyRequest().authenticated()
		    .and()
		    .formLogin()
		        .loginPage("/login")
		        .permitAll()
		    .and()
		    .logout().permitAll();
	}
}
