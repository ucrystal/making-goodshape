package sm.mm.schedule_manager;


public class Schedule{
	Boolean one = false;
	Boolean two = false;
	Boolean three = false;
	Boolean four = false;
	Boolean five = false;
	Boolean six  = false;
	Boolean seven  = false;
	Boolean eight  = false;
	Boolean oneDayList[];
	
	public Schedule(Boolean one,Boolean two,Boolean three,Boolean four,Boolean five,Boolean six,Boolean seven,Boolean eight){
		this.one=one;
		this.two=two;
		this.three=three;
		this.four=four;
		this.five=five;
		this.six=six;
		this.seven=seven;
		this.eight=eight;
	}
	
	/*
	public Schedule(){
		this.mon=mon;
		this.tue=tue;
		this.wed=wed;
		this.thu=thu;
		this.fri=fri;
		this.name=generateRandomString(4);
		this.age=(int)(Math.random()*30+10);
		int gen = (int)(Math.random()*2);
		if(gen==0)
			this.gender="female";
		else if(gen==1)
			this.gender="male";
	}*/
	/*
	public String toString(){
		return "<"+mon+"> <"+tue+"> <"+wed+"> <"+thu+"> <"+fri+">";
	}*/
}