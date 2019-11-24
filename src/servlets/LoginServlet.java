package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypack.SQLBean;

public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 4118563311878631458L;
	public void doGet(HttpServletRequest request,HttpServletResponse response) 
	throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		session.setAttribute("login",false);
		request.getRequestDispatcher("/login.html").forward(request,response);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response)
	throws ServletException, IOException
	{
		request.setCharacterEncoding("utf-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println(username + " " + password);
		if(username != null 
		&& password != null
		&& username.contentEquals(password))
		{
			HttpSession session = request.getSession();
			Object o = session.getAttribute("sqlbean");//保证其他组件的访问。只加载一次数据。
			SQLBean bean = null;
			if (o == null) {
				bean = new SQLBean();
				session.setAttribute("sqlbean", bean);
			} else {
				bean = (SQLBean) o;
			}
			if(username.contentEquals("master"))
			{
				session.setAttribute("login", true);
				request.getRequestDispatcher("/crud.html").forward(request,response);
				return;
			}
			else
			{
				request.getSession().setAttribute("username", username);
				if(bean.hasId(username))
				{
					session.setAttribute("login", true);
					request.getRequestDispatcher("/navigator.jsp").forward(request,response);	
					return;
				}
			}
		}
		//若未登录成功，则重新登陆，直接doGet
		System.out.println("登陆失败");
		doGet(request,response);
	}

}
