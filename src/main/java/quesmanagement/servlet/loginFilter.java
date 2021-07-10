package quesmanagement.servlet;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "loginFilter",value = {"/servlet/*","*.jsp"})
public class loginFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession httpSession = httpServletRequest.getSession();
        String[] excludes = {
                "/loginServlet",
                "/registServlet",
                "/login.jsp",
                "/register.jsp",
                "/common.jsp",
                "/"
        };
        String uri = httpServletRequest.getRequestURI();
        //System.out.println(uri);
        for (String exclude : excludes) {
            if (uri.equals(exclude)) {
                chain.doFilter(request, response);
                return;
            }
        }
        httpSession = httpServletRequest.getSession();
        if (httpSession.getAttribute("user") != null) {
            chain.doFilter(request, response);
        } else {
            httpServletRequest.setAttribute("loginErr","请先登录");
            httpServletRequest.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
