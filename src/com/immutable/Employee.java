package com.immutable;

import java.util.Collections;
import java.util.List;

// Make class final so other class cannot extend it
public final class Employee {

	// Make properties properties private & final
	private final String name;
	private final int age;
	private final List<String> qualifications;

	public Employee(String name, int age, List<String> qualifications) {
		this.name = name;
		this.age = age;
		// below line modify element in list
		//this.qualifications = qualifications;
        // Below line will not allow modifying elements in list
		this.qualifications = Collections.unmodifiableList(qualifications);
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public List<String> getQualifications() {
		return qualifications;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", age=" + age + ", qualifications=" + qualifications + "]";
	}

}
