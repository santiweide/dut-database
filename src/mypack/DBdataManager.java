package mypack;

import java.io.Serializable;
import java.sql.*;
import java.util.*;

import javax.naming.*;
import javax.sql.*;



public class DBdataManager implements DataManager,Serializable{
	private String dbName = " face_info ";
	private static final long serialVersionUID = 6379710035719112850L;
	private DataSource ds = null;
	
	public DBdataManager()
	{
		try {
			Context context = new InitialContext();
			if(context != null)
				ds = (DataSource)context.lookup("java:comp/env/jdbc/dut");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Connection getConnection()throws Exception
	{
		return ds.getConnection();
	}
	public void closeConnection(Connection con)
	{
		try {
			if(con != null)
				con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void closePreparedStatement(PreparedStatement prepstmt)
	{
		try {
			if(prepstmt != null)
				prepstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void closeResultSet(ResultSet rs)
	{
		try {
			if(rs != null)
				rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<String> getIds()
	{
		List<String> ret = new ArrayList<String>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = getConnection();
			String sql = "select id from" + dbName;
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next())
			{
				ret.add(rs.getString(1));
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			closeResultSet(rs);
			closePreparedStatement(ps);
			closeConnection(con);
		}
		return ret;
	}
	public int totalSize(String query)
	{
		int ret = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = getConnection();
			String sql="select count(1) from" + dbName
					+ "where id like '%" + query + "%' or name like '%" + query + "%' " 
					+ "or tel like '%" + query + "%' or qq like '%" + query + "%' or mail like '%" + query + "%'";
			System.out.println(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
			{
				ret = rs.getInt(1);
				System.out.println(ret);
			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			closeConnection(con);
			closePreparedStatement(ps);
			closeResultSet(rs);
		}
		return ret;
	}
	public int createPeople(People p)
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int ret = -1;
		try
		{
			con = getConnection();
			String testSql = "select count(*) from " +dbName +" where id = " + p.getId();
			ps = con.prepareStatement(testSql);
			rs = ps.executeQuery();
			if(rs.next())
			{
				if(rs.getInt(1) == 0)
				{
					PreparedStatement pss = null;
					String sql = "insert into " + dbName + " (id,name,tel,qq,mail) values (" +p.getId() +",'" +p.getName() +"','" +p.getTel() +"','" +p.getQq() +"','" +p.getEmail() +"')";
					System.out.println(sql);
					pss = con.prepareStatement(sql);
					ret = pss.executeUpdate();
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			closePreparedStatement(ps);
			closeConnection(con);
		}
		return ret;
	}
	public int updatePeople(People p)
	{
		Connection con = null;
		PreparedStatement ps = null;
		int ret = 0;
		try
		{
			con = getConnection();
			String sql = "UPDATE " + dbName+" SET id=" + p.getId() +",name='" +p.getName() +"',tel='" +p.getTel() +"',qq='" +p.getQq() +"',mail='" +p.getEmail() 
			+"' where id=" + p.getId();
			System.out.println(sql);
			
			ps = con.prepareStatement(sql);
			ret = ps.executeUpdate();
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			closePreparedStatement(ps);
			closeConnection(con);
		}
		return ret;
	}
	public int deletePeople(String id)
	{
		Connection con = null;
		PreparedStatement ps = null;
		int ret = 0;
		try
		{
			con = getConnection();
			String sql = "delete from " +dbName +" where id=" + id;
			System.out.println(sql);
			ps = con.prepareStatement(sql);
			ret = ps.executeUpdate();//受影响的行数
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			closePreparedStatement(ps);
			closeConnection(con);
		}
		return ret;
	}
	public ArrayList<People> load(String query, int begin, int pageSize)
	{
		ArrayList<People> ret = new ArrayList<People>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = getConnection();
			String selectElement = "select id,name,tel,qq,mail from" + dbName 
			+ "where id like '%" + query + "%' or name like '%" + query + "%' " 
			+ "or tel like '%" + query + "%' or qq like '%" + query + "' or mail like '%" + query + "%'"
			+ "limit " + begin + "," + pageSize;
			System.out.println(selectElement);
			ps = con.prepareStatement(selectElement);
			rs = ps.executeQuery();
			while(rs.next())
			{
				String id = rs.getString(1);
				String name = rs.getString(2);
				String tel = rs.getString(3);
				String qq=rs.getString(4);
				String email = rs.getString(5);
				People people = new People(id,name,tel,qq,email);
				ret.add(people);
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			closeConnection(con);
			closePreparedStatement(ps);
			closeResultSet(rs);
		}
		return ret;
	}
	public ArrayList<People> load()
	{
		ArrayList<People> ret = new ArrayList<People>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = getConnection();
			String selectElement = "select * from" + dbName;
			ps = con.prepareStatement(selectElement);
			rs = ps.executeQuery();
			while(rs.next())
			{
				String id = rs.getString(1);
				String name = rs.getString(2);
				String tel = rs.getString(3);
				String qq=rs.getString(4);
				String email=rs.getString(5);
				People people = new People(id,name,tel,qq,email);
				ret.add(people);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			closeResultSet(rs);
			closePreparedStatement(ps);
			closeConnection(con);
		}
		return ret;
	}
	

}
