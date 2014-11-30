package sm.mm.nicebody;

//하루의 날짜 정보를 저장하는 클래스
public class Schedule_calendar_day {
	private String day;
	private boolean isToday = false;
	private boolean inMonth = false;
	private boolean infoGym = false;

	// 날짜 반환
	public String getDay() {
		return day;
	}

	// 날짜저장
	public void setDay(String day) {
		this.day = day;
	}

	// 오늘 날짜인가?
	public boolean isToday() {
		return isToday;
	}

	// 오늘 날짜인가?
	public void setIsToday(boolean isToday) {
		this.isToday = isToday;
	}

	// 이번달의 날짜인지 정보를 반환
	public boolean isInMonth() {
		return inMonth;
	}

	// 이번달의 날짜인지 정보를 저장
	public void setInMonth(boolean inMonth) {
		this.inMonth = inMonth;
	}

	// 운동정보가 존재하는 날인지 정보를 반환 
	public boolean isInfoGym() {
		return infoGym;
	}
	
	// 운동정보가 존재하는지 저장
	public void setInfoGym(boolean infoGym) {
		this.infoGym = infoGym;
	}

}