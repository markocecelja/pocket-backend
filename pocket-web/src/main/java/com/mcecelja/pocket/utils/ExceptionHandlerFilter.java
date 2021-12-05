package com.mcecelja.pocket.utils;

import com.mcecelja.pocket.common.exceptions.PocketError;
import com.mcecelja.pocket.common.exceptions.PocketException;
import io.jsonwebtoken.JwtException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (PocketException e) {
			response.getWriter().write( ResponseMessage.packageAndJsoniseError(e.getError()));
		} catch (JwtException e) {
			response.getWriter().write( ResponseMessage.packageAndJsoniseError(PocketError.SESSION_EXPIRED));
		}
	}

}