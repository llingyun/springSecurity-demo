package bnu.edu.cn.config;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import bnu.edu.cn.authentication.MyAuthenticationFailureHandler;
import bnu.edu.cn.authentication.MyAuthenticationSuccessHandler;
import bnu.edu.cn.core.properties.SecurityProperties;
import bnu.edu.cn.core.validate.ValidateCodeFilter;
import bnu.edu.cn.service.CustomUserService;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Inject
	private SecurityProperties securityProperies;
	
	@Inject
	private MyAuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Inject
	private MyAuthenticationFailureHandler authenticationFailureHandler;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	UserDetailsService customUserService() {
		return new CustomUserService();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserService());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
		validateCodeFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
		validateCodeFilter.setSecurityProperties(securityProperies);
		validateCodeFilter.afterPropertiesSet();
		
		http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
		.formLogin().loginPage("/authentication/require")
		.loginProcessingUrl("/authentication/form")
		.successHandler(authenticationSuccessHandler)
		.failureHandler(authenticationFailureHandler)
		.and()
		.authorizeRequests()
		.antMatchers("/authentication/require","/code/image", securityProperies.getBrowser().getLoginPage()).permitAll()
		.anyRequest().authenticated()
		.and()
		.csrf().disable();
	}

	/*@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/login")
		.failureUrl("/login?error")
		.permitAll()
		.and()
		.logout()
		.permitAll();
	}*/

}
