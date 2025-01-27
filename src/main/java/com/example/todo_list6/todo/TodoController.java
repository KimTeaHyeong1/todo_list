package com.example.todo_list6.todo;

import com.example.todo_list6.todo.VO.TodoVO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {
    private final TodoService todoService;  // TodoService 의존성 주입

    // 할 일 추가 처리
    @PostMapping("/add")
    public String insertTodo(TodoVO todoVO, HttpSession session) {
        // 세션에서 userSeq를 가져와서 TodoVO에 설정 (사용자 구분을 위한 처리)
        todoVO.setUserSeq((Long) session.getAttribute("userSeq"));
        System.out.println(todoVO);  // TodoVO의 정보를 로그로 출력 (디버깅용)

        // TodoService의 insertTodo 메소드를 호출하여 할 일 추가
        todoService.insertTodo(todoVO);

        // 추가 후 홈 화면으로 리다이렉트
        return "redirect:/";  // "/"로 리다이렉트하여 홈 페이지로 돌아감
    }

    // 할 일 삭제 처리
    @PostMapping("/delete")
    public String deleteTodo(Long todoSeq) {
        // 주어진 todoSeq를 통해 할 일을 삭제
        todoService.deleteTodo(todoSeq);

        // 삭제 후 홈 화면으로 리다이렉트
        return "redirect:/";  // 삭제 후 홈 화면으로 돌아감
    }

    // 할 일 상태 업데이트 처리 (예: 완료, 미완료 등)
    @PostMapping("/update")
    public String updateTodo(Long todoSeq) {
        // 주어진 todoSeq를 통해 할 일 상태를 업데이트
        todoService.updateTodo(todoSeq);

        // 업데이트 후 홈 화면으로 리다이렉트
        return "redirect:/";  // 업데이트 후 홈 화면으로 돌아감
    }
}
