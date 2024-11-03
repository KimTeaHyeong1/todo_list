package com.example.todo_list6.todo;

import com.example.todo_list6.todo.VO.TodoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoMapper todoMapper;

    void insertTodo(TodoVO todoVO){
        todoMapper.insertTodo(todoVO);
    }

    public List<HashMap<String, Objects>> selectTodo(Long userSeq){
        return todoMapper.selectTodo(userSeq);
    }

    void deleteTodo(Long todoSeq){
        todoMapper.deleteTodo(todoSeq);
    }

    void updateTodo(Long todoSeq){
        todoMapper.updateTodo(todoSeq);
    }
}
