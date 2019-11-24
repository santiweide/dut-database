package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SecureFilter implements Filter{
	private FilterConfig config;
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest)req;
		HttpSession session = request.getSession();
		String s = (String)session.getAttribute("login");
		if(s == null)
		{
			request.getRequestDispatcher("/login.html").forward(req, res);
			return;
		}
		if(s.contentEquals("true"))
		{
			chain.doFilter(req,res);
		}else
		{
			request.getRequestDispatcher("/login.html").forward(req,res);
		}
	}
	public void init(FilterConfig config) throws ServletException
	{
		this.config = config;
	}

}
