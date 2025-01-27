package com.example.todo_list6.user.VO;

import lombok.Data;

@Data
public class UserVO {

    // 사용자 고유 식별 번호
    private Long userSeq;

    // 사용자 이름
    private String userName;

    // 사용자 ID (로그인 시 사용되는 ID)
    private String userId;

    // 사용자 비밀번호 (로그인 시 사용)
    private String userPw;

    // 사용자 성별
    private String userGender;

    // 사용자 등록 일시
    private Data userRegAt;
}
