package gr.unipi.javaspot.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import static gr.unipi.javaspot.security.SecurityConfig.isAuthenticated;

public class AuthPagesFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // Redirect to index page if user is authenticated and tries to access auth related endpoints
        String requestURI = request.getRequestURI();
        boolean isAuthRelatedEndpoint = requestURI.equals("/auth/signIn") || requestURI.equals("/auth/signUp");
        if (isAuthenticated() && isAuthRelatedEndpoint) {
            String encodedRedirectURL = response.encodeRedirectURL(request.getContextPath() + "/index");
            response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
            response.setHeader("Location", encodedRedirectURL);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

}
