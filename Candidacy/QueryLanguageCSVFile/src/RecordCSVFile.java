
public class RecordCSVFile {
	
	private int id;
	private String name;
	private String hometown;
	
	public RecordCSVFile(int id, String name, String hometown) {
		super();
		this.id = id;
		this.name = name;
		this.hometown = hometown;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

}
