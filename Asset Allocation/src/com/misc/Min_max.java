package com.misc;

import java.util.ArrayList;
import java.util.Iterator;

public class Min_max {

	public Double find_min(ArrayList<Double> list) {
		Double val;
		Iterator<Double> itr = list.iterator();
		Double min = list.get(0);
		while(itr.hasNext()) {
			val =itr.next();
			if(min>val) {
				min = val;
			}
		}
		return min;
	}
	
	public Double find_max(ArrayList<Double> list) {
		Double val;
		Iterator<Double> itr = list.iterator();
		Double max = list.get(0);
		while(itr.hasNext()) {
			val =itr.next();
			if(max<val) {
				max = val;
			}
		}
		return max;
	}
}
