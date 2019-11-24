package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypack.People;
import mypack.SQLBean;

public class DeleteDispatcherServlet extends HttpServlet{
	String target= "/delete_result.jsp";
	private static final long serialVersionUID = -2951407773524641722L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		request.setCharacterEncoding("utf-8");
		// 得到session对象
		HttpSession session = request.getSession();
		Object o = session.getAttribute("sqlbean");//保证其他组件的访问。只加载一次数据。
		SQLBean bean = null;
		if (o == null) {
			bean = new SQLBean();
			session.setAttribute("sqlbean", bean);
		} else {
			bean = (SQLBean) o;
		}
		

		System.out.println("I am Delete");
		String id = request.getParameter("id");
		int result = bean.deletePeople(id);

		ServletContext context = request.getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(target);
		dispatcher.forward(request, response);
	}
}
