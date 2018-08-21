package com.in28minutes.junit.helper;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class StringHelperTest {


  private static StringHelper sh1;
  private StringHelper sh;

  @BeforeAll  //must be static, same as @BeforeClass in Junit4
  public static void beforeAll() {
    sh1 = new StringHelper();
    System.out.println("Before All . . . ");
  }

  @AfterAll
  public static void afterAll() {
    System.out.println("After All . . .");
  }


  @BeforeEach  //must not be static, same as @Before in Junit4
  public void before() {
    sh = new StringHelper();
    System.out.println("Before Each");
  }

  @AfterEach
  public void after() {
    System.out.println("After Each");
  }


  @Test
  public void testTruncateAInFirst2Positions_aInFirst2Positions() {
    assertEquals("CD", sh1.truncateAInFirst2Positions("AACD"));
  }

  @Test
  public void testTruncateAInFirst2Positions_aNotInFirst2Positions() {
    assertEquals("CDAA", sh1.truncateAInFirst2Positions("CDAA"));
  }

  @Test
  public void testAreFirstAndLastTwoCharactersTheSame_Same() {
    assertTrue(sh.areFirstAndLastTwoCharactersTheSame("ABAB"));
  }

  @Test
  public void testAreFirstAndLastTwoCharactersTheSame_NotSame() {
    assertFalse(sh.areFirstAndLastTwoCharactersTheSame("ABCD"));
  }

  @ParameterizedTest
  @ValueSource(strings = {"ABAB", "CDCD", "AAAA"})
  public void testAreFirstAndLastTwoCharactersTheSame(String candidate) {
    assertTrue(sh.areFirstAndLastTwoCharactersTheSame(candidate));
  }
}