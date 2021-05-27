package com.clonning;

class Car implements Cloneable{
	
	String name;

	public Car(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Car [name=" + name + "]";
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
}
class Student implements Cloneable {

	int rollNo;
	String name;
	Car car;

	public Student(int rollNo, String name, Car car) {
		this.rollNo = rollNo;
		this.name = name;
		this.car = car;

	}
	
	
	
	
	@Override
	public String toString() {
		return "Student [rollNo=" + rollNo + ", name=" + name + ", car=" + car + "]";
	}




	@Override
	protected Object clone() throws CloneNotSupportedException {
		// Deep copy implementation 
		Student s = (Student) super.clone();
		s.car = (Car) car.clone();
		return s;
	}

}

public class cloneExample {

	public static void main(String[] args) {

		try {
		Student s1 = new Student(1, "RAM",new Car("Audi"));
		Student s2 = (Student) s1.clone();
		
		
		System.out.println("Before Modification Object 1 "+s1);
		System.out.println("Before Modification Object 2 "+s2);
		
		
		s2.rollNo = 2;
		s2.name = "Laksham"; 
		s2.car.name = "Mercedez"; 

		
		System.out.println("After Modification Object 1 "+s1);
		System.out.println("After Modification Object 2 "+s2);
		
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
	}
}
