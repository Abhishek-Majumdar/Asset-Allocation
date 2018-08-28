package com.operations;

public class Goals 
{
	private int year;
	private int id;
	private double amount;
	
	public Goals(int year, int id, double amount) 
	{
		super();
		this.year = year;
		this.id = id;
		this.amount = amount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}

}
