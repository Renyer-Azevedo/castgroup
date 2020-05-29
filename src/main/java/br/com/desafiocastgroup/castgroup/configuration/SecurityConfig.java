package br.com.desafiocastgroup.castgroup.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.desafiocastgroup.castgroup.converter.UserDetailConverter;
import br.com.desafiocastgroup.castgroup.filter.JwtTokenFilterConfigurer;
import br.com.desafiocastgroup.castgroup.service.JwtTokenService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
  	private JwtTokenService jwtTokenService;
	@Autowired
	private UserDetailConverter userDetailConverter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
	    http.csrf().disable();

	    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	    http.authorizeRequests()
	    	.antMatchers(HttpMethod.POST, "/funcionarios").authenticated()
	    	.antMatchers(HttpMethod.POST, "/ferias").authenticated()
	    	.antMatchers(HttpMethod.POST, "/equipes").authenticated()
	    	.antMatchers(HttpMethod.DELETE, "/ferias").authenticated()
	    	.antMatchers(HttpMethod.PUT).authenticated()
	        .antMatchers(HttpMethod.GET).permitAll();

	    // Apply JWT
	    http.apply(new JwtTokenFilterConfigurer(jwtTokenService,userDetailConverter));

	    http.httpBasic();
	
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
