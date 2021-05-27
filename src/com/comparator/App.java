package com.comparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App {

	public static void main(String[] args) {

		List<Emp> l = new ArrayList<>();

		l.add(new Emp(10, "Tejas"));
		l.add(new Emp(40, "Sagar"));
		l.add(new Emp(12, "Bhavik"));
		l.add(new Emp(1, "Tejas"));
		l.add(new Emp(1, "Ankit"));

		Collections.sort(l);

		l.forEach(e -> System.out.println(e));

	}
}
