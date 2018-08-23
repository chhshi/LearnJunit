package com.in28minutes.junit.mokito;

import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Every.everyItem;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.in28minutes.business.TodoBusinessImpl;
import com.in28minutes.data.api.TodoService;
import com.in28minutes.data.stub.TodoServiceStub;
import java.util.Arrays;
import java.util.List;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class MockitoTest {

  //stubbing: create a stub (dummy implementation class) to mock external service call
  // Hard Coded,  Difficult to maintain
  @Test
  public void testRetrieveTodosRelatedToSpring_usingAStub() {

    //  com.in28minutes.data.stub.TodoServiceStub
    TodoService todoServiceStub = new TodoServiceStub();
    TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceStub);
    List<String> filteredTodo = todoBusinessImpl.retrieveTodosRelatedToSpring("dummyUser");
    Assert.assertEquals(2, filteredTodo.size());
  }

  //mocking:  is creating objects that simulates the behavior of real objects.
  // unlike stubs, mocks can be -dynamically- created from code - at RUN TIME.
  // Mocks offer more functionality than stubbing.
  @Test
  public void testRetrieveTodosRelatedToSping_usingAMock() {

    //Mockito.mock will return default value back if not stubbed!!
    TodoService todoServiceMock = mock(TodoService.class);

    //Mockito.when set a return value when method call happens
    List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
    when(todoServiceMock.retrieveTodos("dummyUser")).thenReturn(todos);

    TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);
    List<String> filteredTodo = todoBusinessImpl.retrieveTodosRelatedToSpring("dummyUser");
    Assert.assertEquals(2, filteredTodo.size());
  }


  //Mocking List class
  @Test
  public void testMockList() {
    List listMock = Mockito.mock(List.class);
    when(listMock.size()).thenReturn(2);
    Assert.assertEquals(2, listMock.size());
  }

  //Mock return multiple return values for 1st, 2nd, 3rd ... method calls
  @Test
  public void testMockList_returnMultipleValues() {
    List listMock = Mockito.mock(List.class);
    Mockito.when(listMock.size()).thenReturn(1).thenReturn(2).thenReturn(3);  //.thenReturn().thenReturn()
    Assert.assertEquals(1, listMock.size());
    assertEquals(2, listMock.size());
    assertEquals(3, listMock.size());
  }

  @Test
  public void testMockList_get() {
    List listMock = Mockito.mock(List.class);
    Mockito.when(listMock.get(0)).thenReturn("This is one");
    Assert.assertEquals("This is one", listMock.get(0));
    assertEquals(null, listMock.get(1));
    Assert.assertNull(listMock.get(1));
  }

  //Argument Matchers
  @Test
  public void testMockList_ArgumentMatchers() {
    List listMock = Mockito.mock(List.class);
    //import static org.mockito.Matchers.anyInt;
    Mockito.when(listMock.get(Matchers.anyInt())).thenReturn("This is one");
    Assert.assertEquals("This is one", listMock.get(0));
    assertEquals("This is one", listMock.get(1));
  }

  //Mockito throw Exception
  @Test
  public void testMockList_throwAnExpcetion() {
    List listMock = Mockito.mock(List.class);
    //import static org.mockito.Matchers.anyInt;
    Mockito.when(listMock.get(Matchers.anyInt())).thenThrow(new RuntimeException("Something went wrong"));
    Assertions.assertThrows(RuntimeException.class, () -> listMock.get(0));
    assertThrows(RuntimeException.class, () -> listMock.get(1));
  }



  //org.mockito.Matchers does NOT allow mixed of matchers & hard-coded-values
  @Test
  public void testMockList_mixed() {
    List listMock = Mockito.mock(List.class);
    //Error When using matchers, all arguments have to be provided by matchers.
      //when(listMock.subList(anyInt(), 4)).thenThrow(new RuntimeException("Something went wrong"));

    //import static org.mockito.Matchers.eq;
    Mockito.when(listMock.subList(Matchers.anyInt(), Matchers.eq(4))).thenThrow(new RuntimeException("Something went wrong"));
    Assertions.assertThrows(RuntimeException.class, () -> listMock.subList(0, 4));
    assertThrows(RuntimeException.class, () -> listMock.subList(1, 4));
  }


  // BDD Style - Given - When - Then
  // org.mockito.BDDMockito: A more readable mock syntax
  @Test
  public void testMockList_usingBDD() {

    //Given
    TodoService todoServiceMock = Mockito.mock(TodoService.class);
    List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
        //given...willReturn...
    BDDMockito.given(todoServiceMock.retrieveTodos("dummyUser")).willReturn(todos); //import static org.mockito.BDDMockito.given;
    TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);

    //When
    List<String> filteredTodo = todoBusinessImpl.retrieveTodosRelatedToSpring("dummyUser");

    //Then
        //assertThat...is...
    Assert.assertThat(filteredTodo.size(), Is.is(2));  //import static org.hamcrest.core.Is.is;

  }



  //How to verify a (service method) call is made?  i.e.todoService.deleteTodo(todo);
  @Test
  public void testDeleteTodosNotRelatedToSpring_verifyCalls() {

    //Given
    TodoService todoServiceMock = Mockito.mock(TodoService.class);
    List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
    BDDMockito.given(todoServiceMock.retrieveTodos("dummyUser")).willReturn(todos);
    TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);

    //When
    todoBusinessImpl.deleteTodosNotRelatedToSpring("dummyUser");

    //Then: verify todoService.deleteTodo("Learn to Dance") is called
    Mockito.verify(todoServiceMock).deleteTodo("Learn to Dance");  //org.mockito.Mockito.verify;
    verify(todoServiceMock, Mockito.times(1)).deleteTodo("Learn to Dance");
    verify(todoServiceMock, Mockito.atLeast(1)).deleteTodo("Learn to Dance");
    verify(todoServiceMock, Mockito.never()).deleteTodo("Learn Spring MVC");
    verify(todoServiceMock, never()).deleteTodo("Learn Spring");
    //BDD syntax
    BDDMockito.then(todoServiceMock).should(never()).deleteTodo("Learn Spring");

  }


  //Capturing arguments passed to a Mock
  @Test
  public void testDeleteTodosNotRelatedToSpring_captureArguments() {

    //Declare Argument Captor
    ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

    //Given
    TodoService todoServiceMock = Mockito.mock(TodoService.class);
    List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
    BDDMockito.given(todoServiceMock.retrieveTodos("dummyUser")).willReturn(todos);
    TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);

    //When
    todoBusinessImpl.deleteTodosNotRelatedToSpring("dummyUser");

    //Then:
    //Capture the arguments: captor.capture()；captor.getAllValues();
    Mockito.verify(todoServiceMock, Mockito.times(1)).deleteTodo(captor.capture());  //org.mockito.Mockito.verify;
    Assert.assertThat(captor.getValue(), is("Learn to Dance"));
    Assert.assertThat(captor.getAllValues().size(), is(1));

  }


  //Hamcrest Matchers: More readable syntax
  /*
   * import static org.hamcrest.Matchers.hasItems;
   * import static org.hamcrest.Matchers.hasSize;
   * import static org.hamcrest.core.Every.everyItem;
   * import static org.hamcrest.Matchers.isEmptyOrNullString;
   * import static org.hamcrest.Matchers.isEmptyString;
   * import static org.hamcrest.Matchers.arrayContaining;
   * import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
   * import static org.hamcrest.Matchers.arrayWithSize;
   */
  @Test
  public void test() {
    //List
    List<Integer> scores = Arrays.asList(99,100,101,105);
    assertEquals(4, scores.size());
    assertThat(scores, org.hamcrest.Matchers.hasSize(4));
    assertThat(scores, hasItems(99, 100));
    assertThat(scores, everyItem(greaterThan(90)));
    assertThat(scores, everyItem(lessThan(150)));

    //string
    assertThat("", isEmptyString());
    assertThat(null, isEmptyOrNullString());

    //Array
    Integer[] marks = {1, 2, 3};
    assertThat(marks, arrayWithSize(3));
    assertThat(marks, arrayContaining(1, 2, 3));
    assertThat(marks, arrayContainingInAnyOrder(3, 2, 1));
  }

}
