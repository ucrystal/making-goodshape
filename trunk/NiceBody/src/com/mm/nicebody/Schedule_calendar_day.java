package com.mm.nicebody;


//�Ϸ��� ��¥ ������ �����ϴ� Ŭ����
public class Schedule_calendar_day
{
	private String day;
	private boolean inMonth;
	
	//��¥ ��ȯ
	public String getDay()
	{
		return day;
	}

	//��¥����
	public void setDay(String day)
	{
		this.day = day;
	}

	//�̹����� ��¥���� ������ ��ȯ
	public boolean isInMonth()
	{
		return inMonth;
	}

	//�̹����� ��¥���� ������ ����
	public void setInMonth(boolean inMonth)
	{
		this.inMonth = inMonth;
	}

}