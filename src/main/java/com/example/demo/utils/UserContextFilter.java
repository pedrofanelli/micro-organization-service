package com.example.demo.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * No es como el del gateway, este depende de jakarta.servlet
 * 
 * Filtro que se encarga de tomar los datos del header de la request y formar el objeto local, es
 * decir, UserContext. Es un filtro PRE request, es decir, ANTES de que llegue al microservicio
 */
@Component
public class UserContextFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(UserContextFilter.class);
	
	@Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {


        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        UserContextHolder.getContext().setCorrelationId(  httpServletRequest.getHeader(UserContext.CORRELATION_ID) );
        UserContextHolder.getContext().setUserId(httpServletRequest.getHeader(UserContext.USER_ID));
        UserContextHolder.getContext().setAuthToken(httpServletRequest.getHeader(UserContext.AUTH_TOKEN));
        UserContextHolder.getContext().setOrganizationId(httpServletRequest.getHeader(UserContext.ORGANIZATION_ID));

        logger.debug("UserContextFilter en ORGANIZATION SERVICE Correlation id: {}", UserContextHolder.getContext().getCorrelationId());

        filterChain.doFilter(httpServletRequest, servletResponse);
        
        // si agregamos lógica acá abajo, se implementa en la response, es decir, en la salida del microservicio
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
