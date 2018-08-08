package ch.makery.address.model;

public class keywordInfo {
	/**
	 * MODEL FOR SAVING INFORMATION
	 */
	private final static keywordInfo model = new keywordInfo();
	private String keywords;
	private String size;
	private String color;
	private String catagory;
	private String proxy;
	private String modeID;
	private int totalTasks;

	public static keywordInfo getKeywordInfo() {
		return model;
	}

	public String getKeyword() {
		return keywords;
	}

	public int getTotalTasks() {
		return totalTasks;
	}

	public String getSize() {
		return size;
	}

	public String getColor() {
		return color;
	}

	public String getCatagory() {
		return catagory;
	}

	public String getProxy() {
		return proxy;
	}
	
	public void setTasks(int totalTasksID) {
		this.totalTasks = totalTasksID;
	}

	public void setKeyword(String keywordsID) {
		this.keywords = keywordsID;
	}

	public void setSize(String sizeID) {
		this.size = sizeID;
	}

	public void setProxy(String proxyID) {
		this.proxy = proxyID;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setCatagory(String catagory) {
		this.catagory = catagory;
	}

	@Override
	public String toString() {
		return "keywordInfo [keywords=" + keywords + ", size=" + size + ", color=" + color + ", catagory=" + catagory
				+ ", proxy=" + proxy + ", totalTasks=" + totalTasks + "]";
	}

	public void setMode(String modeID) {
		this.modeID = modeID;
	}
	
	public String getMode() {
		return modeID;
	}

}
