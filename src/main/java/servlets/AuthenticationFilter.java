package servlets;

import util.SessionCheck;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String rec = ((HttpServletRequest)servletRequest).getRequestURI();
        if(!(rec.equals("/Kurs_war/") || rec.equals("/Kurs_war/login") || rec.equals("/Kurs_war/register"))){
            boolean success = SessionCheck.success((HttpServletRequest)servletRequest);
            if(!success){
                ((HttpServletResponse)servletResponse).sendRedirect(((HttpServletRequest)servletRequest).getContextPath() + "/login");
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
