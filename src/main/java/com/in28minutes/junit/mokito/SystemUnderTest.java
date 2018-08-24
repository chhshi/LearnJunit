package com.in28minutes.junit.mokito;

import java.util.ArrayList;
import java.util.List;

interface Dependency {
	List<Integer> retrieveAllStats();
}

public class SystemUnderTest {
	private Dependency dependency;

	public int methodUsingAnArrayListConstructor() {
		ArrayList list = new ArrayList();
		return list.size();
	}

	public int methodCallingAStaticMethod() {
		//privateMethodUnderTest calls static method SomeClass.staticMethod
		List<Integer> stats = dependency.retrieveAllStats();  //depend on an interface
		long sum = 0;
		for (int stat : stats)
			sum += stat;
		return UtilityClass.staticMethod(sum);  //calls a static method
	}

	//call 2 static methods
	public int methodCallingAStaticMethod2() {
		//privateMethodUnderTest calls static method SomeClass.staticMethod
		List<Integer> stats = dependency.retrieveAllStats();  //depend on an interface
		long sum = 0;
		for (int stat : stats)
			sum += stat;
		return UtilityClass.staticMethod(sum)
				+ UtilityClass.staticMethod2(sum * 2);  //calls 2 static method
	}


	private long privateMethodUnderTest() {
		List<Integer> stats = dependency.retrieveAllStats();
		long sum = 0;
		for (int stat : stats)
			sum += stat;
		return sum;
	}
}
