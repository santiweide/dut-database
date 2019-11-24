package paging;

public class Page {
	private int size;
	private int tot;
	public int start;
	public int end;
	private int nowPage;
	public Page(int tot, int size)
	{
		this.tot = tot;
		this.size = size;
	}
	public int getMax()//LastPage number
	{
		return (tot %size == 0)?tot/size:tot/size+1;
	}
	public int getStart(int nowPage) {
		this.nowPage = nowPage;
		this.start = size * nowPage;
		return this.start;
	}
	public int getEnd(int nowPage)
	{
		this.end = (getStart(nowPage) + size > tot)? tot:getStart(nowPage) + size; 
		return this.end;
	}
	public int getNextPage()
	{
		if(end == tot)
			return nowPage;
		return nowPage + 1;
	}
	public int getPrevPage()
	{
		if(start == 0)
			return 0;
		return nowPage - 1;
	}
	public int getNowPage()
	{
		return nowPage;
	}
}
