package com.misc;

public class Randomizer {
	
	public static double getRandomValue(double min, double max){
	    double x = (Math.random()*((max-min)+1))+min;
	    return x;
	}

}
