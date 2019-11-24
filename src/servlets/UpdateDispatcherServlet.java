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

public class UpdateDispatcherServlet extends HttpServlet{
	String target = "/update_result.jsp";
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
		
		System.out.println("I am Update");
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String tel = request.getParameter("tel");
		String qq= request.getParameter("qq");
		String email = request.getParameter("mail");
		People people = null;
		if(id != null && id.length() != 0 && name != null && name.length() != 0 && tel != null && tel.length() != 0)
			people = new People(id,name,tel,qq,email);
		bean.updatePeople(people);


		ServletContext context = request.getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(target);
		dispatcher.forward(request, response);
	}
}

