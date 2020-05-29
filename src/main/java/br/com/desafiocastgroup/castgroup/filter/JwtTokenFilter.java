package br.com.desafiocastgroup.castgroup.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.desafiocastgroup.castgroup.converter.UserDetailConverter;
import br.com.desafiocastgroup.castgroup.service.JwtTokenService;
import br.com.desafiocastgroup.castgroup.util.Util;

public class JwtTokenFilter extends OncePerRequestFilter{
	
	private JwtTokenService jwtTokenService;
	private UserDetailConverter userDetailConverter;
	
	private static String header = "Authorization";

	public JwtTokenFilter(JwtTokenService jwtService, UserDetailConverter userDetailConverter) {
		this.jwtTokenService = jwtService;
		this.userDetailConverter = userDetailConverter;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
		
			final String authorizationHeader = request.getHeader(header);
			String token = null;
			String login = null;
			
			if (!Util.isStringVazia(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
				token = authorizationHeader.substring(7);
				login = this.jwtTokenService.extractLogin(token);
			}
			
			if (!Util.isStringVazia(login) && SecurityContextHolder.getContext().getAuthentication() == null) {
				
				UserDetails userDetail = this.userDetailConverter.loadUserByUsername(login);
				
				if (this.jwtTokenService.validateToken(token, userDetail)) {
					
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(login, "", new ArrayList<>());
					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
				}
				
			}
			
		}catch (Exception e) {
			SecurityContextHolder.clearContext();
			response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
			return;
		}
		
		filterChain.doFilter(request, response);
		
	}

}
