package mypack;

import java.util.ArrayList;
import java.util.List;

public interface DataManager {
	public ArrayList<People> load();
	public ArrayList<People> load(String query, int begin, int pageSize);
	public int totalSize(String query);
	public int deletePeople(String p);
	public int createPeople(People p);
	public int updatePeople(People p);
	public List<String> getIds();
}
