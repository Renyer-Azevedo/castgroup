package br.com.desafiocastgroup.castgroup.filter;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.desafiocastgroup.castgroup.converter.UserDetailConverter;
import br.com.desafiocastgroup.castgroup.service.JwtTokenService;

public class JwtTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{

	private JwtTokenService jwtTokenService;
	private UserDetailConverter userDetailConverter;

	public JwtTokenFilterConfigurer(JwtTokenService jwtService, UserDetailConverter userDetailConverter) {
		this.jwtTokenService = jwtService;
		this.userDetailConverter = userDetailConverter;
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenService,userDetailConverter);
		http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
}
