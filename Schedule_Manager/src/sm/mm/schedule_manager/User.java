package sm.mm.schedule_manager;

import java.util.ArrayList;
import java.util.Random;

public class User{
	String objectId;
	String name="";
	ArrayList<Schedule> scheduleArrayList=new ArrayList<Schedule>();
	String phone="";
	//private static final String CHAR_LIST="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

	//월 화 수 목 금 마다 한번씩 값 받아와서 리스트에 저장
	void getSchedule(Boolean one,Boolean two,Boolean three,Boolean four,Boolean five,Boolean six,Boolean seven,Boolean eight){
		Schedule s=new Schedule(one, two, three, four, five, six, seven, eight);
		//u.objectId=generateRandomString(10);
		//u.name=generateRandomString(4);
		scheduleArrayList.add(s);
	}
	public User(String objectId, String name, String phone){
		this.objectId=objectId;
		this.name=name;
		this.phone=phone;
		//this.scheduleArrayList.add(getSchedule());
	}
	/*
	public String generateRandomString(int len){
		 Random rng=new Random(1);
		StringBuffer randStr = new StringBuffer();
		for(int i=0; i<len; i++){
			char ch = CHAR_LIST.charAt((int)(CHAR_LIST.length()*rng.nextDouble()));
			randStr.append(ch);
		}
		return randStr.toString();
	}*/
    /*
	void print(){
		StringBuffer sb=new StringBuffer("objectId: <"+objectId+"> name: <"+name+"> gender: <"+gender+"> schedule{");
		for(Schedule m:scheduleArrayList){
			sb.append("<"+m.name+"|"+m.age+"|"+m.gender+">");
		}
		sb.append("}");
	}*/

	/*
	void deleteMaleSchedule(User c) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Tell me the object name");
		
		String objname = scanner.nextLine();
		
		if(c.name.equals(objname)) {
			
			//for(Iterator<Schedule> it = c.scheduleArrayList.iterator(); it.hasNext(); ) {
				//Schedule m = it.next();
				//if(m.gender=="male")
					//c.memberArrayList.remove(m);
			//}
			
			for(int i=0; i<c.memberArrayList.size(); i++) {
				Schedule m = c.memberArrayList.get(i);
				if(m.gender=="male"){
					c.memberArrayList.remove(i);
					i--; //if Schedule is deleted, the size of the User is resized. so --i !
				}
			}

		} else {
			
			System.out.println("Not Correct");
			for(Iterator<Schedule> it = c.memberArrayList.iterator(); it.hasNext(); ) {
				Schedule m = it.next();
				System.out.println("Schedule name:"+m.name+"  Schedule age:"+m.age+" gender:"+m.gender+"\n");
			}
		}
		
		for(Iterator<Schedule> it = c.memberArrayList.iterator(); it.hasNext(); ) {
				Schedule m = it.next();
				System.out.println("Schedule name:"+m.name+"  Schedule age:"+m.age+" gender:"+m.gender+"\n");
			}
	}*/
}
