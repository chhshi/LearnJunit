package com.in28minutes.junit.mokito;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * Mock static Method
 * Invoke private Method
 * Mock constructor ("new")
 *
 * TO Mock static method, needs:
 * 1) Specific Runner: org.powermock.modules.junit4.PowerMockRunner
 * 2) PrepareForTest: @PrepareForTest(UtilityClass.class)
 * 3) Initialize the class (contains the static method): PowerMockito.mockStatic(UtilityClass.class);
 *
 *
 * TO Mock constructor ("new"), needs:
 * 1) Specific Runner: org.powermock.modules.junit4.PowerMockRunner
 * 2) PrepareForTest: @PrepareForTest(SystemUnderTest.class)
 * 3) Override the constructor of the class
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({UtilityClass.class, SystemUnderTest.class})
public class PowerMockitoTest {

  @Mock
  SystemUnderTest test;

  @Mock
  Dependency dependency;

  @InjectMocks
  SystemUnderTest systemUnderTest;

  @Mock
  ArrayList listMock;

  //Normal Mock won't work on static methods
  @Test
  public void test_normalMock() {
    List<Integer> stats = Arrays.asList(1, 2, 3);
    Mockito.when(dependency.retrieveAllStats()).thenReturn(stats);
    //Not able to mock static method use normal mock
    //Throw an exception because static method is not mocked.
        //Mockito.when(UtilityClass.staticMethod(6)).thenReturn(80);
        //systemUnderTest.methodCallingAStaticMethod();
  }


  //PowerMockito static methods
  @Test
  public void test_PowerMockito_OneStatic () {
    List<Integer> stats = Arrays.asList(1, 2, 3);
    Mockito.when(dependency.retrieveAllStats()).thenReturn(stats);

    //Initialize the class contains the static method
    PowerMockito.mockStatic(UtilityClass.class);
    Mockito.when(UtilityClass.staticMethod(6)).thenReturn(80);

    //verify return value
    assertEquals(80, systemUnderTest.methodCallingAStaticMethod());
    //verify static method is called:
    PowerMockito.verifyStatic();
    UtilityClass.staticMethod(6);  //必须紧跟在PowerMockito.verifyStatic()后面
  }


  //Add [PowerMockito.verifyStatic();] for each static method
  @Test
  public void test_PowerMockito_TwoStatic () {
    List<Integer> stats = Arrays.asList(1, 2, 3);
    Mockito.when(dependency.retrieveAllStats()).thenReturn(stats);

    //Initialize the class contains the static method
    PowerMockito.mockStatic(UtilityClass.class);
    Mockito.when(UtilityClass.staticMethod(6)).thenReturn(80);
    Mockito.when(UtilityClass.staticMethod2(12)).thenReturn(120);

    //verify return value
    assertEquals(200, systemUnderTest.methodCallingAStaticMethod2());

    //verify 2 static methods are called:
    //每个static method call都要有自己 PowerMockito.verifyStatic(); 语句并紧跟在后面
    PowerMockito.verifyStatic();
    UtilityClass.staticMethod(6);
    PowerMockito.verifyStatic();
    UtilityClass.staticMethod2(12);

  }


  //invoke private methods
  //Using: org.powermock.reflect.Whitebox;
  @Test
  public void test_PowerMockito_privateMethod () throws Exception {
    List<Integer> stats = Arrays.asList(1, 2, 3);
    Mockito.when(dependency.retrieveAllStats()).thenReturn(stats);

    //invoke private method here:
    long result = Whitebox.invokeMethod(systemUnderTest, "privateMethodUnderTest");
    assertEquals(6, result);
  }


  //Mock Constructor (new)
  @Test
  public void test_MockConstructor() throws Exception{
    List<Integer> stats = Arrays.asList(1, 2, 3);
    Mockito.when(dependency.retrieveAllStats()).thenReturn(stats);

    //override the constructor: .whenNew(class)
    PowerMockito.whenNew(ArrayList.class).withAnyArguments().thenReturn(listMock);
    Mockito.when(listMock.size()).thenReturn(8888);
    int response = systemUnderTest.methodUsingAnArrayListConstructor();
    assertEquals(8888, response);

  }



}
