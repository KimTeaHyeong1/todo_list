package com.example.todo_list6.board;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper  // MyBatis에서 Mapper 인터페이스로 인식되도록 해주는 어노테이션
public interface BoardMapper {

    // 게시글 작성 처리 (제목, 내용, 사용자 ID, 이미지 경로 포함)
    void insertBoard(String boardHd, String boardBd, String userId, String imagePath);

    // 게시글 번호로 게시글 상세 정보 조회
    HashMap<String, Object> boardNumber(Integer boardSeq);

    // 모든 게시글 목록 조회
    List<HashMap<String, Object>> boardShow();

    // 게시글 수정 처리 (제목, 내용, 이미지 경로 포함)
    void updateBoard(Integer boardSeq, String boardHd, String boardBd, String imagePath);

    // 게시글 삭제 처리 (게시글 번호로 삭제)
    void deleteBoard(Integer boardSeq);

    // 게시글 조회수 증가 (게시글 번호로 조회수 증가)
    void incrementViews(Integer boardSeq);
}
