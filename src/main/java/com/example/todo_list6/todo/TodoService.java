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
    private final TodoMapper todoMapper;  // TodoMapper 의존성 주입

    // 할 일 추가 처리
    void insertTodo(TodoVO todoVO){
        todoMapper.insertTodo(todoVO);  // 할 일 추가를 위해 TodoMapper의 insertTodo 메소드 호출
    }

    // 할 일 목록 조회
    public List<HashMap<String, Objects>> selectTodo(Long userSeq){
        return todoMapper.selectTodo(userSeq);  // userSeq에 해당하는 할 일 목록을 DB에서 조회
    }

    // 할 일 삭제 처리
    void deleteTodo(Long todoSeq){
        todoMapper.deleteTodo(todoSeq);  // 주어진 todoSeq에 해당하는 할 일 삭제
    }

    // 할 일 상태 업데이트 처리
    public void updateTodo(Long todoSeq){
        todoMapper.updateTodo(todoSeq);  // 주어진 todoSeq에 해당하는 할 일 상태 업데이트
    }
}
