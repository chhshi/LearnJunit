package com.in28minutes.junit.mokito;

public class UtilityClass {
	static int staticMethod(long value) {
		// Some complex logic is done here...
		throw new RuntimeException(
				"I dont want to be executed. I will anyway be mocked out. #1");
	}

	static int staticMethod2(long value) {
		// Some complex logic is done here...
		throw new RuntimeException(
				"I dont want to be executed. I will anyway be mocked out. #2");
	}

}
