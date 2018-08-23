package com.in28minutes.junit.mokito;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

import com.in28minutes.business.TodoBusinessImpl;
import com.in28minutes.data.api.TodoService;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
//import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;


/**
 * @RunWith(MockitoJUnitRunner.class), you can have only one runner
 * @InjectMocks
 * @Mock
 */

@RunWith(MockitoJUnitRunner.class)
public class MockitoInject {


  @Mock
  TodoService todoServiceMock;
  //same as:  TodoService todoServiceMock = mock(TodoService.class);

  @Captor
  ArgumentCaptor<String> captor;
  //same as:  ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

  @Mock
  List<Integer> listMock;
  //same as: List listMock = Mockito.mock(List.class);

  @InjectMocks
  TodoBusinessImpl todoBusinessImpl;
  //automatically Inject todoServiceMock
  //same as:  TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);

  //  Same as: @RunWith(MockitoJUnitRunner.class) ， 且可以重复/共存？！！
  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void usingMockito() {

    List<String> allTodos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
    Mockito.when(todoServiceMock.retrieveTodos("Ranga")).thenReturn(allTodos);
    List<String> todos = todoBusinessImpl.retrieveTodosRelatedToSpring("Ranga");
    Assert.assertEquals(2, todos.size());
  }

  //Mocking List class
  @Test
  public void testMockList() {
    when(listMock.size()).thenReturn(2);
    Assert.assertEquals(2, listMock.size());
  }

  //Capturing arguments passed to a Mock
  @Test
  public void testDeleteTodosNotRelatedToSpring_captureArguments() {

    //Given
    List<String> todos = Arrays.asList("Learn Spring MVC", "Learn", "Learn to Dance");
    BDDMockito.given(todoServiceMock.retrieveTodos("dummyUser")).willReturn(todos);

    //When
    todoBusinessImpl.deleteTodosNotRelatedToSpring("dummyUser");

    //Then:
    //Capture the arguments: captor.capture()；captor.getAllValues();
    Mockito.verify(todoServiceMock, Mockito.times(2))
        .deleteTodo(captor.capture());  //org.mockito.Mockito.verify;
    Assert.assertThat(captor.getValue(), is("Learn to Dance"));  //.getValue() will get the last value if multiple
    Assert.assertThat(captor.getAllValues().size(), is(2));

  }

}
