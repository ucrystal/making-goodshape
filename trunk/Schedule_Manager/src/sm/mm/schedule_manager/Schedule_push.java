package sm.mm.schedule_manager;

public class Schedule_push {
	int plusDay; 
	
	public Schedule_push(int today, String choice) {
		plusDay = plusDay(today, choice);		
	}

	public int plusDay(int today, String day) {
		// 월, 화, 수, 목, 금, 토, 일
		// 1, 2, 3, 4, 5, 6, 7
	
		int plus = 0;
		int choice = 0;
		
		if(day.equals("월"))
			choice = 2;
		else if(day.equals("화"))
			choice = 3;
		else if(day.equals("수"))
			choice = 4;
		else if(day.equals("목"))
			choice = 5;
		else if(day.equals("금"))
			choice = 6;
		else if(day.equals("토"))
			choice = 7;
		else if(day.equals("일"))
			choice = 1;
		
		if (choice <= today) {
			plus = 7 - today + choice ;
		} else if (choice > today) {
			plus = choice - today;
		}
		return plus;
	}
	
	public int getDay(){
		return plusDay;
	}

}
