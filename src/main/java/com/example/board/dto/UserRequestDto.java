package com.example.board.dto;

import com.example.board.db.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {
    private String  name;
    private String email;

    @Builder
    public UserRequestDto(String name, String email){
        this.name = name;
        this.email = email;
    }

    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .build();
    }
}
