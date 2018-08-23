package com.in28minutes.junit.mokito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


/**
 *
 * Some Theory : Why does Mockito not allow stubbing final and private methods?
 * Useful Snippets and References
 *
 * https://github.com/mockito/mockito/wiki/Mockito-And-Private-Methods
 * https://github.com/mockito/mockito/wiki/FAQ
 */

@RunWith(MockitoJUnitRunner.class)
public class MockitoSpy {


  //Use Mocked Object, any actual method call of that object will be dummy, and has no impact on the object
  @Test
  public void test() {

    //mock method can only return a raw type
    @SuppressWarnings( "unchecked" )
    ArrayList<String> arrayListMock = Mockito.mock(ArrayList.class);

    //without stub, mock return default value
    assertEquals(0, arrayListMock.size());
    arrayListMock.add("Dummy"); // dummy add won't have inpact on mocked object
    assertEquals(0, arrayListMock.size());

    //stub value to mocked object
    Mockito.stub(arrayListMock.size()).toReturn(2);
    arrayListMock.add("Dummy"); // dummy add won't have inpact on mocked object
    assertEquals(2, arrayListMock.size());


  }

  //SPY (Partial Mock): if you need object with some mocked function and some real function
  @Test
  public void testSpy() {

    //SPY: This is a real arrayList
    @SuppressWarnings( "unchecked" )
    ArrayList<String> arrayListSpy = Mockito.spy(ArrayList.class);

    //real list, .add() will actually add element
    assertEquals(0, arrayListSpy.size());
    arrayListSpy.add("Dummy");
    assertEquals(1, arrayListSpy.size());
    Mockito.verify(arrayListSpy).add("Dummy");  //verify .add("Dummy") is called
    verify(arrayListSpy, never()).remove("Dummy");//verify .remove("Dummy") is never called

    //if .size() is stubbed/overridden, then .size() has nothing to do with the real list object
    Mockito.stub(arrayListSpy.size()).toReturn(5);
    arrayListSpy.add("Dummy2");
    assertEquals(5, arrayListSpy.size());

    Mockito.verify(arrayListSpy, times(2)).add(anyString());  //verify .add("Dummy") is called
    System.out.println(arrayListSpy);  //print [Dummy, Dummy2]
  }

}
