package sm.mm.schedule_manager;

public class Schedule_push {
	int plusDay; 
	
	public Schedule_push(int today, String choice) {
		plusDay = plusDay(today, choice);		
	}

	public int plusDay(int today, String day) {
		// ��, ȭ, ��, ��, ��, ��, ��
		// 1, 2, 3, 4, 5, 6, 7
	
		int plus = 0;
		int choice = 0;
		
		if(day.equals("��"))
			choice = 2;
		else if(day.equals("ȭ"))
			choice = 3;
		else if(day.equals("��"))
			choice = 4;
		else if(day.equals("��"))
			choice = 5;
		else if(day.equals("��"))
			choice = 6;
		else if(day.equals("��"))
			choice = 7;
		else if(day.equals("��"))
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
