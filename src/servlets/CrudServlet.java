package servlets;

import mypack.People;
import mypack.SQLBean;
import paging.Page;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;

@WebServlet("*.do")
public class CrudServlet extends HttpServlet {
    private int pageSize = 10;
    protected synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        String method = request.getServletPath();
        method = method.substring(1,method.length() - 3);
        Method m = null;
        try{
            m = this.getClass().getDeclaredMethod(method,HttpServletRequest.class, HttpServletResponse.class);
            m.invoke(this,request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected synchronized void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        doGet(request,response);
    }
    protected synchronized void create(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException
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
        System.out.println("I am Create");
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String tel = request.getParameter("tel");
        String qq= request.getParameter("qq");
        String email = request.getParameter("mail");
        People people = null;
        if(id != null && id.length() != 0 && name != null && name.length() != 0 && tel != null && tel.length() != 0)
            people = new People(id,name,tel,qq,email);
        int result = bean.createPeople(people);

        ServletContext context = request.getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/create_result.jsp");
        dispatcher.forward(request, response);
    }
    protected synchronized void retrive(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException
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
            String paging = "";
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
        RequestDispatcher dispatcher = context.getRequestDispatcher("/retrive_result.jsp");
        dispatcher.forward(request, response);
    }
    protected synchronized void update(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException
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
        RequestDispatcher dispatcher = context.getRequestDispatcher("/update_result.jsp");
        dispatcher.forward(request, response);
    }

    protected synchronized void delete(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException
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
        String id = request.getParameter("id");
        int result = bean.deletePeople(id);

        ServletContext context = request.getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/delete_result.jsp");
        dispatcher.forward(request, response);
    }


}