package com.example.board.dto;

import com.example.board.db.entity.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardRequestDto {
    private String title;
    private String content;

    @Builder
    public BoardRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Board toEntity(){
        return Board.builder()
                .title(title)
                .content(content)
                .build();
    }
}
