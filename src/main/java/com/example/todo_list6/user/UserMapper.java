package com.example.todo_list6.user;

import com.example.todo_list6.user.VO.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserVO selectUserByUserIdAndUserPw(UserVO userVO);

    void insertUser(UserVO userVO);
}
