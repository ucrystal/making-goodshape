package com.mm.nicebody;


//하루의 날짜 정보를 저장하는 클래스
public class Schedule_calendar_day
{
	private String day;
	private boolean inMonth;
	
	//날짜 반환
	public String getDay()
	{
		return day;
	}

	//날짜저장
	public void setDay(String day)
	{
		this.day = day;
	}

	//이번달의 날짜인지 정보를 반환
	public boolean isInMonth()
	{
		return inMonth;
	}

	//이번달의 날짜인지 정보를 저장
	public void setInMonth(boolean inMonth)
	{
		this.inMonth = inMonth;
	}

}