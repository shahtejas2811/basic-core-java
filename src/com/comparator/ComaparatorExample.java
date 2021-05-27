package com.comparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Student {
	
	private int rollNo;
	private String name;

	public Student(int rollNo, String name) {
		super();
		this.rollNo = rollNo;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Student [rollNo=" + rollNo + ", name=" + name + "]";
	}

	public int getRollNo() {
		return rollNo;
	}

	public void setRollNo(int rollNo) {
		this.rollNo = rollNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}


class studentName implements Comparator<Student> {

	@Override
	public int compare(Student o1, Student o2) {
		return o1.getName().compareTo(o2.getName());
	}

	
}

public class ComaparatorExample {

	public static void main(String[] args) {

		List<Student> l = new ArrayList<>();

		l.add(new Student(10, "Tejas"));
		l.add(new Student(40, "Sagar"));
		l.add(new Student(12, "Bhavik"));
		l.add(new Student(1, "Tejas"));
		l.add(new Student(1, "Ankit"));
		// Sort by name
		Collections.sort(l,new studentName());
		//l.forEach(s -> System.out.println(s));
		
		// sort by student Roll no.
		/*
		 * What is Lambda Expressions: lambda expression represents an anonymous
		 * function. It comprises of a set of parameters, a lambda operator (->)
		 * and a function body . You can call it function without name,
		 */
		Collections.sort(l,(Student s1, Student s2) -> { return s1.getRollNo() - s2.getRollNo() ;});
		l.forEach(s -> System.out.println(s));
		

	}

}