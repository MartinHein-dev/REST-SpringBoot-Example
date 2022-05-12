package de.weconvert.restspringbootexample.header;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;


/**
 * Zur Verwendung des Filters, muss folgende Annotation in die SpringbootApplication @ServletComponentScan
 *
 */
@WebFilter("/users/*") //Alle Pfad die /users/ enthalten, erhalten diesen zusätzlichen Header
//@WebFilter //Alle Endpoints erhalten diesen Filter
public class AddResponseHeaderFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setHeader("Example-Filter-Header", "Value-Filter-Value");
		chain.doFilter(request, response);
	}

}
