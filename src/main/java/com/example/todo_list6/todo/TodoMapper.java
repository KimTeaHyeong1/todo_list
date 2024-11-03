package com.example.todo_list6.todo;

import com.example.todo_list6.todo.VO.TodoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Mapper
public interface TodoMapper {
    void insertTodo(TodoVO todoVO);

    List<HashMap<String, Objects>> selectTodo(Long userSeq);

    void deleteTodo(Long todoSeq);

    void updateTodo(Long todoSeq);
}
