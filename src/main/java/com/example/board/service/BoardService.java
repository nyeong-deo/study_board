package com.example.board.service;

import com.example.board.db.entity.Board;
import com.example.board.db.repository.BoardRepository;
import com.example.board.dto.BoardRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;
    @Autowired
    public BoardService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    //게시글 작성
    public Board save(Long userId,BoardRequestDto boardRequestDto){
        //유저 id 있는지 확인
        Board board = Board.builder()
                .userId(userId)
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .build();
        return boardRepository.save(board);
    }

    //게시글 전체 조회
    public List<Board> findAll(){
        return boardRepository.findAll();
    }

    //특정 유저 게시글 전체 조회
    public List<Board> findByUserId(Long userId){
        //유저 있는지 화인 -> Optional
        return boardRepository.findByUserId(userId);
    }

    //게시글 상세 조회
    public Optional<Board> findById(Long postId){
        return boardRepository.findById(postId);
    }

    //게시글 삭제
    public void delById(Long userId, Long postId) {
        Optional<Board> board = findById(postId);
        board.ifPresentOrElse(it -> {
            if (it.getUserId().equals(userId)) {
                boardRepository.deleteById(postId);
            } else {
                throw new RuntimeException("해당 사용자의 게시글이 없습니다.");
            }
        }, () -> {
            throw new RuntimeException("해당 게시글이 없습니다.");
        });
    }

    //게시글 수정
    public Board update(Long userId,Long postId,BoardRequestDto boardRequestDto){
        Optional<Board> board = findById(postId);
        return board.map(it -> {
            if (it.getUserId().equals(userId)) {
                it.setTitle(boardRequestDto.getTitle());
                it.setContent(boardRequestDto.getContent());
                return boardRepository.save(it);
            } else {
                throw new RuntimeException("해당 사용자의 게시글이 없습니다.");
            }
        }).orElseThrow(() -> new RuntimeException("해당 게시글이 없습니다."));
    }
}
