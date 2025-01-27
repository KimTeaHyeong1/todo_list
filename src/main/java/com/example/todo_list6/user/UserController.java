package com.example.todo_list6.user;

import com.example.todo_list6.user.VO.UserVO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/userController")  // "/userController" URL에 대한 요청을 처리하는 컨트롤러
public class UserController {
    private final UserService userService;

    // 로그인 페이지로 이동
    @GetMapping("/login")
    public String loginPage() {
        return "login";  // "login" HTML 페이지로 이동
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(UserVO userVO, Model model, HttpSession session) {
        // userService의 로그인 메서드를 호출하여 로그인 처리
        UserVO result = userService.login(userVO);

        // 로그인 실패 시, 에러 메시지를 모델에 추가하고 로그인 페이지로 돌아감
        if (result == null) {
            model.addAttribute("errorMsg", "아이디&비밀번호를 확인하세요.");
            return "/login";  // 로그인 페이지로 이동
        }

        // 로그인 성공 시, 사용자 정보를 세션에 저장
        session.setAttribute("userSeq", result.getUserSeq());  // 사용자 고유 번호 세션에 저장
        session.setAttribute("userId", result.getUserId());  // 사용자 ID 세션에 저장
        return "redirect:/";  // 메인 페이지로 리다이렉트
    }

    // 로그아웃 처리
    @PostMapping("/logOut")
    public String logOut(HttpSession session) {
        session.removeAttribute("userSeq");  // 세션에서 사용자 고유 번호 제거
        return "redirect:/";  // 메인 페이지로 리다이렉트
    }

    // 회원가입 페이지로 이동
    @GetMapping("/signup")
    public String signUpPage() {
        return "/signup";  // 회원가입 HTML 페이지로 이동
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String signup(UserVO userVO, Model model) {
        try {
            // 회원가입 입력값 검증 (각 필드의 길이를 체크)
            if (userVO.getUserName().length() > 10) {
                model.addAttribute("errorMsg", "이름은 10자 이내로 입력해주세요.");
                return "/signup";  // 에러 메시지를 보여주고 회원가입 페이지로 이동
            }
            if (userVO.getUserId().length() > 20) {
                model.addAttribute("errorMsg", "아이디는 20자 이내로 입력해주세요.");
                return "/signup";  // 에러 메시지를 보여주고 회원가입 페이지로 이동
            }
            if (userVO.getUserPw().length() > 20) {
                model.addAttribute("errorMsg", "비밀번호는 20자 이내로 입력해주세요.");
                return "/signup";  // 에러 메시지를 보여주고 회원가입 페이지로 이동
            }
            if (userVO.getUserGender().length() > 10) {
                model.addAttribute("errorMsg", "성별은 10자 이내로 입력해주세요.");
                return "/signup";  // 에러 메시지를 보여주고 회원가입 페이지로 이동
            }

            // 회원가입 서비스 호출
            userService.register(userVO);
            return "redirect:/userController/login";  // 회원가입 성공 후 로그인 페이지로 리다이렉트
        } catch (Exception e) {
            // 예외 발생 시 에러 메시지 출력
            model.addAttribute("errorMsg", "회원가입 중 오류가 발생했습니다. 다시 시도해 주세요.");
            return "/signup";  // 회원가입 페이지로 다시 이동
        }
    }

    // 사용자 정보 수정 페이지로 이동
    @GetMapping("/update")
    public String updatePage() {
        return "/update";  // 정보 수정 HTML 페이지로 이동
    }

    // 사용자 정보 수정 처리
    @PostMapping("/update")
    public String updateUser(UserVO userVO) {
        // 사용자 정보 수정 서비스 호출
        userService.updateUser(userVO);
        return "redirect:/";  // 수정 후 메인 페이지로 리다이렉트
    }

    // 사용자 삭제 처리
    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long userSeq, HttpSession session) {
        // 사용자 삭제 서비스 호출
        userService.deleteUser(userSeq);

        // 세션에서 사용자 정보 제거
        session.removeAttribute("userSeq");

        return "redirect:/";  // 삭제 후 메인 페이지로 리다이렉트
    }
}
