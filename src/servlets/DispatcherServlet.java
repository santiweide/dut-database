package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypack.SQLBean;
import paging.Page;



public class DispatcherServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private String target = "/query_result.jsp";
	private int pageSize = 10;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		
		String query = "";
		query = request.getParameter("query");
		if(query!=null)
			query = query.trim();
		System.out.println("query = " + query);
		if (query != null && !query.equals("")) { 
			request.getSession().setAttribute("storeQ", query);
		}
		if(query == null)
			query = (String)request.getSession().getAttribute("storeQ");
		if(query == null)
			query = "";
		query.trim();
		int totSize = bean.getTotSize(query);

		request.setAttribute("TOT",totSize);
		
		String data = "";
		
		if (bean.haveQuery()) {
			System.out.println("~~");
			String psStr = null;
			if((psStr = request.getParameter("pageSize")) != null)
			{
				pageSize = Integer.parseInt(psStr);	
			}
			Page thisPage = new Page(totSize,pageSize);
			String getPage = request.getParameter("page");
			int start = -1;
			if(getPage != null)
			{
				start = thisPage.getStart(Integer.parseInt(getPage));
			}else
			{
				start = 0;
			}
			bean.setQuery(query, start, pageSize);
			for(int i = 0;i < bean.ret.size();i ++)
			{
				data = data + bean.ret.get(i).toString();
			}
			request.setAttribute("PREV", thisPage.getPrevPage());
			request.setAttribute("NEXT", thisPage.getNextPage());
			request.setAttribute("THIS",thisPage.getNowPage());
			String paging = new String();
			for(int i = 0 ;i < thisPage.getMax();i ++)
			{
				if(thisPage.getNowPage() != i)
					paging = paging + "<li><a href=\"?page=" + i + "\">" + i + "</a></li>\r\n";		
				else
					paging = paging + "<li class=\"active\"><a href=\"?page=" + i + "\">" + i + "</a></li>\r\n";		
			}
			request.setAttribute("PDATA", paging);
			request.setAttribute("CURNUM", bean.ret.size());
		} else {
			if(bean.isClean())
				bean.setClean(false);
			request.setAttribute("LASTPAGE", 0);
			request.setAttribute("PDATA", "");	
			request.setAttribute("CURNUM", 0);
			request.setAttribute("THIS",0);
			
		}
		bean.reset();
		request.setAttribute("QUERY", query);
		request.setAttribute("DATA", data);
		
		ServletContext context = request.getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(target);
		dispatcher.forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse respond) throws ServletException, IOException {
		
	}
}
