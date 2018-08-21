package com.in28minutes.junit.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestParameterized {

  private static StringHelper sh;

  @BeforeAll
  public static void beforeAll() {
    sh = new StringHelper();
  }


  @ParameterizedTest(name = "[{index}] {0}:{1}")
  @CsvSource({  //CSV: Comma Seperated Values
      "AB, true",
      "CDCD, true",
      "AAAA, true",
      "A, false",
      "ACDR, false"})
  public void testAreFirstAndLastTwoCharactersTheSame(String a, boolean b) {
    assertEquals(b, sh.areFirstAndLastTwoCharactersTheSame(a));
  }
}



/** Junit4 Implementation
 *
 * @RunWith(Parameterized.class)
 * public class StringHelperParameterizedTest {
 *
 * 	// AACD => CD ACD => CD CDEF=>CDEF CDAA => CDAA
 *
 * 	StringHelper helper = new StringHelper();
 *
 * 	private String input;
 * 	private String expectedOutput;
 *
 * 	public StringHelperParameterizedTest(String input, String expectedOutput) {
 * 		this.input = input;
 * 		this.expectedOutput = expectedOutput;
 * 	}
 *
 * 	@Parameters
 * 	public static Collection<String[]> testConditions() {
 * 		String expectedOutputs[][] = {
 * 				{ "AACD", "CD" },
 * 				{ "ACD", "CD" } };
 * 		return Arrays.asList(expectedOutputs);
 * 	}
 *
 * 	@Test
 * 	public void testTruncateAInFirst2Positions() {
 * 		assertEquals(expectedOutput,
 * 				helper.truncateAInFirst2Positions(input));
 * 	}
 * }
 */