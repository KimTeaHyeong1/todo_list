package com.example.todo_list6.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper boardMapper;

    // 게시글 작성 처리 & 이미지 경로 처리
    void insertBoard(String boardHd, String boardBd, String userId, String imagePath){
        // boardMapper를 통해 DB에 게시글을 삽입하는 작업을 수행
        boardMapper.insertBoard(boardHd, boardBd, userId, imagePath);
    }

    // 게시글 번호로 게시글 상세 정보 조회
    HashMap<String, Object> boardNumber(Integer boardSeq) {
        // boardSeq(게시글 번호)를 이용해 DB에서 해당 게시글의 상세 정보를 조회
        return boardMapper.boardNumber(boardSeq);
    }

    // 게시글 목록 조회
    List<HashMap<String, Object>> boardShow(){
        // DB에서 모든 게시글 목록을 가져옴
        return boardMapper.boardShow();
    }

    // 게시글 수정 처리 (이미지 경로 포함)
    void updateBoard(Integer boardSeq, String boardHd, String boardBd, String imagePath) {
        // boardSeq(게시글 번호)와 새로운 제목, 내용, 이미지 경로를 이용하여 게시글을 수정
        boardMapper.updateBoard(boardSeq, boardHd, boardBd, imagePath);
    }

    // 게시글 삭제 처리
    void deleteBoard(Integer boardSeq) {
        // boardSeq(게시글 번호)를 이용해 해당 게시글을 삭제
        boardMapper.deleteBoard(boardSeq);
    }

    // 게시글 조회수 증가
    public void incrementViews(Integer boardSeq) {
        // boardSeq(게시글 번호)를 이용해 해당 게시글의 조회수를 증가시킴
        boardMapper.incrementViews(boardSeq);
    }
}
