package com.example.todo_list6.user;

import com.example.todo_list6.user.VO.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service  // 서비스 클래스임을 나타냄
@RequiredArgsConstructor  // 자동으로 생성자 주입 생성
public class UserService {
    private final UserMapper userMapper;

    // 로그인 처리 메서드
    UserVO login(UserVO userVO) {
        // userMapper를 사용해 아이디와 비밀번호로 사용자 조회
        UserVO result = userMapper.selectUserByUserIdAndUserPw(userVO);

        // 조회된 결과가 null이면 로그인 실패로 null 반환
        if (Objects.isNull(result)) {
            return null;  // 로그인 실패 (아이디/비밀번호 불일치)
        }

        // 로그인 성공하면 사용자 정보 반환
        return result;  // 로그인 성공 (사용자 정보)
    }

    // 사용자 등록 처리 메서드
    void register(UserVO userVO) {
        // userMapper를 사용하여 새로운 사용자 등록
        userMapper.insertUser(userVO);
    }

    // 사용자 정보 수정 처리 메서드
    void updateUser(UserVO userVO) {
        // userMapper를 사용하여 사용자 정보 업데이트
        userMapper.updateUser(userVO);
    }

    // 사용자 삭제 처리 메서드
    void deleteUser(Long userSeq) {
        // userMapper를 사용하여 사용자 삭제
        userMapper.deleteUser(userSeq);
    }
}
