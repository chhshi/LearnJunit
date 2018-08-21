package com.in28minutes.junit.helper;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ArraysCompareTest {

  @Test
  public void testArraySort_RandomArray() {
    int[] nums = {4, 3, 2, 1};
    int[] numsSorted = {1, 2, 3, 4};
    Arrays.sort(nums);
//        assertEquals(nums, numsSorted);  //compare is same object OR not
    assertArrayEquals(nums, numsSorted);
  }


  @Test
  public void testArraySort_NullArray() {
    int[] nums = null;
    //same as @Test(expected=NullPointerException.class) in Junit4
    assertThrows(NullPointerException.class, () -> Arrays.sort(nums));
  }

  @Test
  public void testSort_Performace() {
    //same as @Test(timeout=80) in Junit4
    assertTimeout(Duration.ofMillis(80), () -> {
      int[] arr = {12, 23, 4};
      for (int i = 1; i < 1000000; i++) {
        arr[0] = i;
        Arrays.sort(arr);
      }
    });
  }

}
