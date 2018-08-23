package com.in28minutes.junit.mokito;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

import com.in28minutes.business.TodoBusinessImpl;
import com.in28minutes.data.api.TodoService;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;


//@RunWith(MockitoJUnitRunner.class)
public class MockitoJunitRule {

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Rule
  public MockitoRule mockitoRule2 = MockitoJUnit.rule();
  //you can have multiple rules, rather only 1 runner

  @InjectMocks
  TodoBusinessImpl todoBusinessImpl;

  @Mock
  TodoService todoServiceMock;

  @Captor
  ArgumentCaptor<String> captor;

  @Mock
  List<Integer> listMock;

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
    List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
    BDDMockito.given(todoServiceMock.retrieveTodos("dummyUser")).willReturn(todos);

    //When
    todoBusinessImpl.deleteTodosNotRelatedToSpring("dummyUser");

    //Then:
    //Capture the arguments: captor.capture()；captor.getAllValues();
    Mockito.verify(todoServiceMock, Mockito.times(1))
        .deleteTodo(captor.capture());  //org.mockito.Mockito.verify;
    Assert.assertThat(captor.getValue(), is("Learn to Dance"));
    Assert.assertThat(captor.getAllValues().size(), is(1));

  }

}
