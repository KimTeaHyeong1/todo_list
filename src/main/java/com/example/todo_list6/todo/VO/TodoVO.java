package com.example.todo_list6.todo.VO;

import lombok.Data;

@Data
public class TodoVO {

    // 사용자 식별 번호 (유저와 할 일을 구분하는 데 사용)
    private Long userSeq;

    // 할 일의 내용 (사용자가 작성한 할 일의 텍스트 내용)
    private String content;
}
