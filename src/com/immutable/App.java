package com.immutable;

import java.util.ArrayList;
import java.util.List;

public class App {

	public static void main(String[] args) {
		 List<String> qualifications = new ArrayList<String>();
		 qualifications.add("BSC");
		 qualifications.add("MCA");
		 
		Employee e = new Employee("Tejas", 35, qualifications);
		//e.getQualifications().add("CKAD");
		System.out.println("Employee "+e);
		
	}
}
