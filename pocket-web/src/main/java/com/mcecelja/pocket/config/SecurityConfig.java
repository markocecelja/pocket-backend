package com.mcecelja.pocket.config;

import com.mcecelja.pocket.context.ContextCreationFilter;
import com.mcecelja.pocket.security.JwtAuthenticationFilter;
import com.mcecelja.pocket.utils.CorsDisableFilter;
import com.mcecelja.pocket.utils.ExceptionHandlerFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final String[] publicEndpoints = {
			"/h2-console/**",
			"/api/authentication/**"
	};

	@Resource(name = "userService")
	private UserDetailsService userDetailsService;

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Bean
	public ContextCreationFilter contextCreationFilter() {
		return new ContextCreationFilter();
	}

	@Bean
	public ExceptionHandlerFilter exceptionHandlerFilter() {
		return new ExceptionHandlerFilter();
	}

	@Bean
	public CorsDisableFilter corsDisableFilter() {
		return new CorsDisableFilter();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(encoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.authorizeRequests()
				.antMatchers(publicEndpoints).permitAll()
				.antMatchers(HttpMethod.OPTIONS,"/api/**").permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.headers().frameOptions().disable()
				.and()
				.cors().disable();

		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(contextCreationFilter(), JwtAuthenticationFilter.class);
		http.addFilterBefore(exceptionHandlerFilter(), ContextCreationFilter.class);
		http.addFilterBefore(corsDisableFilter(), ExceptionHandlerFilter.class);

	}
}
