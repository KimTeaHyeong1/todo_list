package com.example.todo_list6;

import com.example.todo_list6.todo.TodoService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final TodoService todoService;
    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        Long userSeq = (Long) session.getAttribute("userSeq");  // 세션에서 사용자 정보 확인

        if(userSeq != null) {  // 로그인된 사용자가 있을 경우
            model.addAttribute("contentList", todoService.selectTodo(userSeq));  // 해당 사용자 Todo 목록을 모델에 추가
        }

        return "/home";  // "home" 뷰로 이동
    }
}


