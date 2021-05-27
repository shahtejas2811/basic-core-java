package com.comparator;

public class Emp implements Comparable<Emp> {

	private int empID;
	private String empName;

	public Emp(int empID, String empName) {
		super();
		this.empID = empID;
		this.empName = empName;
	}

	@Override
	public int compareTo(Emp o) {
		if (this.empID > o.empID) {
			return 1;
		} else if (this.empID < o.empID) {
			return -1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return "Emp [empID=" + empID + ", empName=" + empName + "]";
	}

}
