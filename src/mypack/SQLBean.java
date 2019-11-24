package mypack;

import java.util.ArrayList;
import java.util.List;

public class SQLBean  implements java.io.Serializable {
	private static final long serialVersionUID = -8890313644940036525L;
	public ArrayList<People> ret = new ArrayList<People>();
	DataManager dm = new DBdataManager();
	private boolean clean=true;
	private int totSize;
	public SQLBean() {
		super();
	}
	
	public boolean isClean() {
		return clean;
	}
	public void setClean(boolean clean) {
		this.clean = clean;
	}


	public int setQuery(String query,int begin, int pageSize)
	{
		ret = dm.load(query, begin, pageSize);
		return ret.size();
	}
	public boolean haveQuery()
	{
		return this.totSize != 0;
	}
	public void reset()
	{
		ret.clear();
		clean = true;
	}

	public boolean hasId(String id)
	{
		ArrayList<String> totid = (ArrayList<String>)getNameList();
		if(totid.contains(id))
			return true;
		return false;
	}
	public List<String> getNameList()
	{
		return dm.getIds();
	}

	public int getTotSize(String query) {
		if(query != null && query.length() != 0)
		{
			this.totSize = dm.totalSize(query);

		}else
			this.totSize = 0;
		return this.totSize;	
	}
	
	public int createPeople(People p)
	{
		if(p != null)
			return dm.createPeople(p);
		return -1;
	}
	public int updatePeople(People p)
	{
		if(p != null)
			return dm.updatePeople(p);
		return -1;
	}
	public int deletePeople(String id)
	{
		if(id != null)
			return dm.deletePeople(id);
		return -1;
	}
}
