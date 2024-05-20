package com.example.board.web;

import com.example.board.db.entity.User;
import com.example.board.dto.UserRequestDto;
import com.example.board.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    //유저 등록
    @PostMapping("/new-user")
    public ResponseEntity<User> addUser(
            @RequestBody UserRequestDto userRequestDto
    ){
      User savedUser = userService.save(userRequestDto);
      return ResponseEntity.status(HttpStatus.CREATED)
              .body(savedUser);
    }

    //이메일로 유저 찾기
    @GetMapping("/user/{email}")
    public ResponseEntity<Optional<User>> findUser(
            @PathVariable String email
    ){
        Optional<User> foundUser = userService.findByEmail(email);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(foundUser);
    }

    //이메일로 유저 삭제
    @DeleteMapping("/user/{email}")
    public ResponseEntity<Optional<User>> deleteUser(
            @PathVariable String email
    ){
        Optional<User> deletedNum = userService.delete(email);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(deletedNum);
    }

    //이메일로 유저 수정
    @PutMapping("/user/{email}")
    public ResponseEntity<User> updateUser(
            @PathVariable String email,
            @RequestBody UserRequestDto userRequestDto
    ){
        User updateUser = userService.update(email,userRequestDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(updateUser);
    }
}
