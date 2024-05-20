package com.example.board.web;

import com.example.board.db.entity.Board;
import com.example.board.dto.BoardRequestDto;
import com.example.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    @Autowired
    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }

    //게시글 등록
    @PostMapping("/{userId}")
    public ResponseEntity<Board> saveBoard(
            @PathVariable Long userId,
            @RequestBody BoardRequestDto boardRequestDto
    ){
        Board board = boardService.save(userId,boardRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(board);
    }

    //모든 게시글 조회
    @GetMapping
    public ResponseEntity<List<Board>> findAllBoard(){
        List<Board> boards = boardService.findAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(boards);
    }

    //특정 사용자 게시글 조회
    @GetMapping("/user-id/{userId}")
    public ResponseEntity<List<Board>> findByUserIdBoard(
            @PathVariable Long userId
    ){
        List<Board> boards = boardService.findByUserId(userId);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(boards);
    }

    //특정 게시글 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<Optional<Board>> findByPostIdBoard(
            @PathVariable Long postId
    ){
        Optional<Board> board = boardService.findById(postId);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(board);
    }

    //특정 사용자가 자신의 특정 게시물 삭제
    @DeleteMapping
    public ResponseEntity<String> deleteBoard(
            @RequestParam Long userId,
            @RequestParam Long postId
    ){
        boardService.delById(userId,postId);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("삭제 되었습니다.");
    }

    //특정 사용자가 자신의 특정 게시물 수정
    @PutMapping
    public ResponseEntity<Board> updateBoard(
            @RequestParam Long userId,
            @RequestParam Long postId,
            @RequestBody BoardRequestDto boardRequestDto
    ){
        Board board = boardService.update(userId,postId,boardRequestDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(board);
    }
}
