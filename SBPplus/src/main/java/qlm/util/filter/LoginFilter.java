package qlm.util.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter", urlPatterns = "/pages/*")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;

        HttpSession session = request.getSession();
        if (session.getAttribute("eid") != null) {
            chain.doFilter(req, response);
        } else {
            req.setAttribute("msg", "您还未登录，请重新登陆");
            req.setAttribute("url", "/login.jsp");
            req.getRequestDispatcher("/pages/forward.jsp").forward(req, response);

        }
    }

    public void init(FilterConfig config) throws ServletException {
    }
}
