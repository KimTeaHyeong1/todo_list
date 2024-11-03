package com.example.todo_list6.user;

import com.example.todo_list6.user.VO.UserVO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/userController")
public class UserController {
    private final UserService userService;
    @GetMapping("/login")//로그인주소로 이동
    public String loginPage() {// String 타입의 loginPage 매서드
        return "login";//폴더경로
    }

    @PostMapping("/login")//로그인 동작
    public String login(UserVO userVO, Model model, HttpSession session) {
        //UserVO userVO, Model model, HttpSession session 타입의 파마리터를 받는 String 타입의 login 매서드.
        UserVO result = userService.login(userVO);
        // UserVO 타입의 result 는 userService.login(userVO) 메서드의 결과 선언.
        if(result == null) {//userSeq, userId, userPw 값이 없음(null)일때
            model.addAttribute("errorMsg", "아이디&비밀번호를 확인하세요.");
            return "/login"; //로그인 html 로 이동
        }
        session.setAttribute("userSeq", result.getUserSeq());//세션에 키는 useSeq 값은 result.getUserSeq
        session.setAttribute("userId", result.getUserId());//세션에 키는 useId 값은 result.getUserId
        System.out.println(session.getAttribute("userSeq"));
        System.out.println(session.getAttribute("userId"));
        return "redirect:/"; //메인화면 주소로 다시 바로 이동
    }
    @PostMapping("/logOut")//로그아웃
    public String logOut(HttpSession session) {//HttpSession 타입의 session 파라미터를 받는 String 타입의 logout 매서드.
        session.removeAttribute("userSeq");//세션에 키는 useSeq 제거
        session.removeAttribute("userId");//세션에 키는 useId 제거
        return "redirect:/";//메인화면 주소로 다시 바로 이동
    }

    @GetMapping("/signup")// 회원가입 주소로 이동
    public String signUpPage() {//String 타입의 signUpPage 매서드.
        return "/signup";//회원가입 html 로 이동
    }

    @PostMapping("/signup")//회원가입 동작
    public String signup(UserVO userVO) {//UserVO 타입의 파라미터 userVO를 받는 String 타입의 signUp 매서드.
        System.out.println(userVO.toString());
        userService.register(userVO);//userService.register(userVO)에 값을 넘김
        return "redirect:/userController/login";//값을 넘긴 후 로그인 주소로 다시 바로 이동
    }
}
