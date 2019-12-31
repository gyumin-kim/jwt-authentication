package com.gyumin.jwtauthentication.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gyumin.jwtauthentication.user.ApplicationUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.gyumin.jwtauthentication.security.SecurityConstants.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(final AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(final HttpServletRequest request,
												final HttpServletResponse response) throws AuthenticationException {
		try {
			ApplicationUser creds = new ObjectMapper()
				.readValue(request.getInputStream(), ApplicationUser.class);
			return authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
					creds.getUsername(),
					creds.getPassword(),
					new ArrayList<>()
				)
			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(final HttpServletRequest request,
											final HttpServletResponse response,
											final FilterChain chain,
											final Authentication authResult) throws IOException, ServletException {

		String token = Jwts.builder()
			.setSubject(((User)authResult.getPrincipal()).getUsername())
			.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
			.signWith(SignatureAlgorithm.HS512, SECRET)
			.compact();
		response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
	}
}
