package sm.mm.nicebody;

//�Ϸ��� ��¥ ������ �����ϴ� Ŭ����
public class Schedule_calendar_day {
	private String day;
	private boolean isToday = false;
	private boolean inMonth = false;
	private boolean infoGym = false;

	// ��¥ ��ȯ
	public String getDay() {
		return day;
	}

	// ��¥����
	public void setDay(String day) {
		this.day = day;
	}

	// ���� ��¥�ΰ�?
	public boolean isToday() {
		return isToday;
	}

	// ���� ��¥�ΰ�?
	public void setIsToday(boolean isToday) {
		this.isToday = isToday;
	}

	// �̹����� ��¥���� ������ ��ȯ
	public boolean isInMonth() {
		return inMonth;
	}

	// �̹����� ��¥���� ������ ����
	public void setInMonth(boolean inMonth) {
		this.inMonth = inMonth;
	}

	// ������� �����ϴ� ������ ������ ��ȯ 
	public boolean isInfoGym() {
		return infoGym;
	}
	
	// ������� �����ϴ��� ����
	public void setInfoGym(boolean infoGym) {
		this.infoGym = infoGym;
	}

}