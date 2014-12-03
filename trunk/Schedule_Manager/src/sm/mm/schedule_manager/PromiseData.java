package sm.mm.schedule_manager;

public class PromiseData {
	
	private int id;
	private String name;
	private String p_day;
	private String p_time;

	public PromiseData(){}

	public PromiseData(String name, String day, String time) {
		super();
		this.name = name;
		this.p_day = day;
		this.p_time = time;
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
	
	public String getDay() {
		return p_day;
	}
	
	public void setDay(String day) {
		this.p_day = day;
	}

	public String getTime() {
		return p_time;
	}
	
	public void setTime(String time) {
		this.p_time = time;
	}

	

}
