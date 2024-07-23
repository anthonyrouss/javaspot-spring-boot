package gr.unipi.javaspot.security;

import gr.unipi.javaspot.enums.SkillLevel;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import static gr.unipi.javaspot.security.SecurityConfig.getUserDetails;
import static gr.unipi.javaspot.security.SecurityConfig.isAuthenticated;

public class SkillLevelAssignmentFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        boolean isCssOrJsOrImgRequest = request.getRequestURI().matches("(/css/.*|/js/.*|/img/.*)");

        // If user is not authenticated or request is for CSS, JS or image files, continue with the filter chain
        if (!isAuthenticated() || isCssOrJsOrImgRequest) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        boolean isSkillLevelAssignmentEndpoint = request.getRequestURI().equals("/me/skill-level");
        boolean isGETRequest = request.getMethod().equals("GET");
        SkillLevel userSkillLevel = getUserDetails().getSkillLevel();

        // Redirect to home page if requested URL is the skill level assignment page and user has already assigned a skill level
        if (isSkillLevelAssignmentEndpoint && isGETRequest && userSkillLevel != null) {
            response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
            response.setHeader("Location", request.getContextPath() + "/index");
            return;
        }

        // Redirect to skill level assignment page if requested URL is not the skill level assignment page and user has not assigned a skill level
        if (!isSkillLevelAssignmentEndpoint && isGETRequest && userSkillLevel == null) {
            String encodedRedirectURL = response.encodeRedirectURL(request.getContextPath() + "/me/skill-level");
            response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
            response.setHeader("Location", encodedRedirectURL);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

}
