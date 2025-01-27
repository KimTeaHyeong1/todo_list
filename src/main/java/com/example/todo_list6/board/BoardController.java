package com.example.todo_list6.board;

import com.example.todo_list6.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@Controller
@RequestMapping("/board") // "/board" URL로 들어오는 요청을 처리하는 클래스
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;  // 게시글 관련 서비스
    private final UserService userService;    // 사용자 관련 서비스

    // 게시글 목록 페이지
    @GetMapping("/boardList")
    public String boardPage(Model model) {
        // 서비스에서 게시글 목록을 받아와서 모델에 담음
        model.addAttribute("boardShow", boardService.boardShow());
        // 게시글 목록을 보여주는 페이지로 이동
        return "/board/boardList";
    }

    // 게시글 작성 처리
    @PostMapping("/board")
    public String insertBoard(String boardHd, String boardBd, MultipartFile imageFile, HttpSession session, Model model) throws IOException {
        String userId = (String) session.getAttribute("userId"); // 로그인된 사용자 ID를 세션에서 가져옴

        // 제목이 비어 있으면 에러 메시지와 함께 다시 작성 페이지로 돌아감
        if (boardHd == null || boardHd.isEmpty()) {
            model.addAttribute("errorMsg", "제목을 입력하세요.");
            model.addAttribute("boardBd", boardBd); // 내용은 그대로 유지
            return "/board/board";
        }

        // 내용이 비어 있으면 에러 메시지와 함께 다시 작성 페이지로 돌아감
        if (boardBd == null || boardBd.isEmpty()) {
            model.addAttribute("errorMsg", "내용을 입력하세요.");
            model.addAttribute("boardHd", boardHd); // 제목은 그대로 유지
            return "/board/board";
        }

        // 이미지가 있다면 처리
        String imagePath = null;
        if (imageFile != null && !imageFile.isEmpty()) {  // 이미지 파일이 있을 경우
            // 파일을 저장할 경로 설정
            String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/images"; // 프로젝트 경로로 설정
            File uploadDirectory = new File(uploadDir);

            // 경로가 없으면 디렉터리 생성
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }

            // 파일 이름을 UUID로 변경해서 저장 (파일명이 중복되지 않도록)
            String originalFileName = imageFile.getOriginalFilename();
            String fileName = UUID.randomUUID().toString() + "_" + originalFileName;
            File dest = new File(uploadDirectory, fileName);
            imageFile.transferTo(dest); // 파일을 지정한 경로에 저장

            // 저장된 이미지 파일 경로를 DB에 저장할 경로로 설정
            imagePath = "/images/" + fileName;
        }

        // 게시글 정보를 DB에 저장
        boardService.insertBoard(boardHd, boardBd, userId, imagePath);

        // 게시글 목록 페이지로 리다이렉트
        return "redirect:/board/boardList";
    }

    // 게시글 상세 페이지
    @GetMapping("/boardDetail/{boardSeq}")
    public String boardDetail(@PathVariable Integer boardSeq, Model model, HttpSession session) {
        HashMap<String, Object> boardDetail = null;

        try {
            // 게시글 번호로 게시글 정보 조회
            boardDetail = boardService.boardNumber(boardSeq);

            // 게시글이 없으면 에러 메시지를 띄우고 게시글 목록으로 돌아감
            if (boardDetail == null) {
                model.addAttribute("errorMsg", "삭제된 게시글입니다.");
                return "/board/boardList";
            }

        } catch (Exception e) {
            model.addAttribute("errorMsg", "게시글을 찾을 수 없습니다.");
            return "/board/boardList";  // 게시글 목록으로 돌아감
        }

        // 게시글에 이미지가 있으면 이미지 경로를 모델에 추가
        String imagePath = (String) boardDetail.get("IMAGE_PATH");
        if (imagePath != null && !imagePath.isEmpty()) {
            model.addAttribute("imagePath", imagePath);
        }

        // 게시글 상세 정보를 모델에 추가해서 페이지로 전달
        model.addAttribute("boardDetail", boardDetail);

        // 조회수 증가
        boardService.incrementViews(boardSeq);

        // 로그인된 사용자의 ID 가져오기
        String loggedInUserId = (String) session.getAttribute("userId");

        // 게시글 작성자가 맞는지 확인 후 수정 가능 여부 표시
        if (loggedInUserId != null && loggedInUserId.equals(boardDetail.get("USER_ID").toString())) {
            model.addAttribute("boardEdit", true);  // 작성자면 수정 버튼 표시
        } else {
            model.addAttribute("boardEdit", false); // 작성자가 아니면 수정 버튼 숨김
        }

        // 게시글 상세 페이지로 이동
        return "/board/boardDetail";
    }

    // 게시글 수정 페이지
    @GetMapping("/boardEdit/{boardSeq}")
    public String boardEdit(@PathVariable Integer boardSeq, Model model, HttpSession session) {
        String loggedInUserId = (String) session.getAttribute("userId");

        // 게시글 정보 조회
        HashMap<String, Object> boardDetail = boardService.boardNumber(boardSeq);

        // 로그인한 사용자가 작성자일 경우 수정 페이지로 이동
        if (boardDetail != null && loggedInUserId != null
                && loggedInUserId.equals(boardDetail.get("USER_ID").toString())) {
            model.addAttribute("boardEdit", boardDetail);  // 수정할 게시글 정보를 모델에 담아서 전달
            return "/board/boardEdit";  // 수정 페이지로 이동
        } else {
            return "redirect:/board/boardList";  // 권한이 없으면 게시글 목록으로 리다이렉트
        }
    }

    // 게시글 수정 처리
    @PostMapping("/updateBoard")
    public String updateBoard(Integer boardSeq, String boardHd, String boardBd, MultipartFile imageFile, HttpSession session) throws IOException {
        String loggedInUserId = (String) session.getAttribute("userId");

        // 게시글 정보 조회
        HashMap<String, Object> boardDetail = boardService.boardNumber(boardSeq);

        // 작성자가 맞으면 수정 처리
        if (boardDetail != null && loggedInUserId.equals(boardDetail.get("USER_ID"))) {
            String imagePath = null;

            // 이미지가 새로 업로드되면 처리
            if (imageFile != null && !imageFile.isEmpty()) {
                String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/images"; // 이미지 업로드 경로
                File uploadDirectory = new File(uploadDir);

                // 디렉터리가 없으면 생성
                if (!uploadDirectory.exists()) {
                    uploadDirectory.mkdirs();
                }

                // 파일 이름을 UUID로 변경하여 저장
                String originalFileName = imageFile.getOriginalFilename();
                String fileName = UUID.randomUUID().toString() + "_" + originalFileName;
                File dest = new File(uploadDirectory, fileName);
                imageFile.transferTo(dest); // 파일을 지정된 경로에 저장

                imagePath = "/images/" + fileName; // DB에 저장할 경로
            } else {
                // 이미지를 변경하지 않으면 기존 이미지를 그대로 사용
                imagePath = (String) boardDetail.get("IMAGE_PATH");
            }

            // 수정된 게시글 정보 업데이트
            boardService.updateBoard(boardSeq, boardHd, boardBd, imagePath);
            return "redirect:/board/boardList"; // 수정 후 게시글 목록으로 리다이렉트
        } else {
            return "redirect:/board/boardList"; // 권한이 없으면 게시글 목록으로 리다이렉트
        }
    }

    // 게시글 삭제 처리
    @PostMapping("/deleteBoard/{boardSeq}")
    public String deleteBoard(@PathVariable Integer boardSeq, HttpSession session) {
        String loggedInUserId = (String) session.getAttribute("userId");

        // 게시글 정보 조회
        HashMap<String, Object> boardDetail = boardService.boardNumber(boardSeq);

        // 작성자가 맞으면 게시글 삭제 처리
        if (boardDetail != null && loggedInUserId.equals(boardDetail.get("USER_ID"))) {
            // 이미지 파일 삭제 처리
            String imagePath = (String) boardDetail.get("IMAGE_PATH");
            if (imagePath != null && !imagePath.isEmpty()) {
                File file = new File("src/main/resources/static" + imagePath);
                if (file.exists()) {
                    file.delete(); // 이미지 파일 삭제
                }
            }

            // 게시글 삭제 처리
            boardService.deleteBoard(boardSeq);
            return "redirect:/board/boardList";  // 삭제 후 게시글 목록으로 리다이렉트
        } else {
            return "redirect:/board/boardList";  // 권한이 없으면 게시글 목록으로 리다이렉트
        }
    }
}
