package com.biglobby.configuration;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.biglobby.filter.JwtTokenFilter;
import com.biglobby.repository.UserRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SercurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtTokenFilter jwtTokenFilter;

	@Autowired
	private UserRepository userRepo;

	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthorizationRequestRepository<OAuth2AuthorizationRequest> getRepository() {
		return new HttpSessionOAuth2AuthorizationRequestRepository();
	}

	@Bean
	public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> getToken() {
		return new DefaultAuthorizationCodeTokenResponseClient();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(username -> {
			try {
				com.biglobby.entity.User user = userRepo.getByUserName(username);
				String[] roles = user.getAuthorities().stream().map(au -> au.getRole().getRole())
						.collect(Collectors.toList()).toArray(new String[0]);
				return org.springframework.security.core.userdetails.User.withUsername(username)
						.password(user.getPassword()).roles(roles).build();
			} catch (Exception e) {
				e.printStackTrace();
				throw new UsernameNotFoundException(String.format("Username: %s, not found!", username));
			}
		});
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.cors().disable()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().exceptionHandling().authenticationEntryPoint((req, res, ex) -> {
					res.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
				})
				.and()
				.authorizeRequests()
				// .antMatchers("/app/**", "/biglobby/**","/biglobby").permitAll()
				// .antMatchers("/chat/**").permitAll()
				.antMatchers(HttpMethod.POST, "/upload/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/messages/**").permitAll()
				.antMatchers(HttpMethod.POST, "/authentication/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/users").permitAll()
				.antMatchers(HttpMethod.POST, "/api/users").permitAll()
				.antMatchers(HttpMethod.POST, "/api/registeractives").permitAll()
				.antMatchers(HttpMethod.GET, "/api/registeractives").permitAll()
				.antMatchers(HttpMethod.GET, "/api/sendmail/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/registeractives/active/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/registeractives/user/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/categories").permitAll()
				.antMatchers(HttpMethod.GET, "/api/recommend/**").permitAll()
				.antMatchers(HttpMethod.POST, "/api/upload/**").permitAll()
				.antMatchers(HttpMethod.POST, "/api/shops").permitAll()
				.antMatchers(HttpMethod.GET, "/api/shops").permitAll()
				.antMatchers(HttpMethod.DELETE, "/api/shops").permitAll()
				.antMatchers(HttpMethod.DELETE, "/api/registeractives").permitAll()
				.antMatchers(HttpMethod.POST, "/api/authorities").permitAll()
				.antMatchers(HttpMethod.POST, "/api/mybcoins").permitAll()
				.antMatchers(HttpMethod.PUT, "/api/users/**").permitAll()
				.antMatchers("/api/**").authenticated()
				.anyRequest().permitAll()
				.and()
				.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
		// http.formLogin().loginPage("/biglobby/login").loginProcessingUrl("/biglobby/login")
		// .defaultSuccessUrl("/biglobby/index",
		// false).failureUrl("/biglobby/login?ps=Login Failed!")
		// .usernameParameter("username").passwordParameter("password");
		// http.rememberMe().rememberMeParameter("remember_me");

		// OAuth2 - Đăng nhập từ mạng xã hội
		// http.oauth2Login()
		// .loginPage("/biglobby/login")
		// .defaultSuccessUrl("/biglobby/index", true)
		// .failureUrl("/biglobby/login?ps=Login Failed!")
		// // khai địa chỉ để đăng nhập bằng mạng xã hội
		// .authorizationEndpoint()
		// .baseUri("/oauth2/authorization");

	}

}
