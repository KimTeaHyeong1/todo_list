package com.example.todo_list6.todo;

import com.example.todo_list6.todo.VO.TodoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Mapper
public interface TodoMapper {

    // 할 일 추가 (DB에 새로운 할 일 저장)
    void insertTodo(TodoVO todoVO);

    // 할 일 목록 조회 (userSeq에 해당하는 할 일 목록을 반환)
    List<HashMap<String, Objects>> selectTodo(Long userSeq);

    // 할 일 삭제 (주어진 todoSeq에 해당하는 할 일 삭제)
    void deleteTodo(Long todoSeq);

    // 할 일 상태 업데이트 (todoSeq에 해당하는 할 일 상태 변경)
    void updateTodo(Long todoSeq);
}
