package com.example.todo_list6.todo;

import com.example.todo_list6.todo.VO.TodoVO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {
    private final TodoService todoService;

    @PostMapping("/add")
    public String insertTodo(TodoVO todoVO, HttpSession session) {
        System.out.println(todoVO.toString());
        todoVO.setUserSeq((Long) session.getAttribute("userSeq"));
        System.out.println(todoVO);
          todoService.insertTodo(todoVO);
        return "redirect:/";
    }

    @PostMapping("/delete")
    String deleteTodo(Long todoSeq){
        todoService.deleteTodo(todoSeq);

        return "redirect:/";
    }
    @PostMapping("/update")
    String updateTodo(Long todoSeq){
        System.out.println(todoSeq);
        todoService.updateTodo(todoSeq);
        return "redirect:/";
    }
}
//추가를 누르면 투두부이오에 컨텐츠에 셋이된다 그다음은