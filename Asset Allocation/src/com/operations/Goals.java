package com.operations;

public class Goals implements Comparable
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
	@Override
	public int compareTo(Object o) 
	{
		// TODO Auto-generated method stub
		int compare_year=((Goals) o).getYear();
        /* For Ascending order*/
        return this.year-compare_year;
	}

}
