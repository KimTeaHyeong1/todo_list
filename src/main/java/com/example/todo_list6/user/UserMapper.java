package com.example.todo_list6.user;

import com.example.todo_list6.user.VO.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    // 아이디와 비밀번호로 사용자 조회
    UserVO selectUserByUserIdAndUserPw(UserVO userVO);

    // 새로운 사용자 추가
    void insertUser(UserVO userVO);

    // 사용자 정보 수정
    void updateUser(UserVO userVO);

    // 사용자 삭제
    void deleteUser(Long userSeq);
}
