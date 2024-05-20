//package com.example.board.service;
//
//import com.example.board.db.entity.User;
//import com.example.board.db.repository.UserRepository;
//import com.example.board.dto.UserRequestDto;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class UserServiceTest {
//
//    @Autowired
//    UserService userService;
//    @Autowired
//    UserRepository userRepository;
//
//    @Test
//    void 등록() throws IllegalAccessException {
//        //given
//        UserRequestDto userRequestDto = UserRequestDto.builder()
//                .name("name")
//                .email("email")
//                .build();
//        //when
//        Long id = userService.add(userRequestDto).getId();
//        //then
//        User foundUser = userRepository.findById(id).get();
//        assertThat(userRequestDto.getName()).isEqualTo(foundUser.getName());
//
//    }
//    @Test
//    void 중복_이메일_체크() throws IllegalAccessException{
//        //given
//        UserRequestDto user1 = UserRequestDto.builder()
//                .name("user1")
//                .email("email1")
//                .build();
//        UserRequestDto user2 = UserRequestDto.builder()
//                .name("user2")
//                .email("email1")
//                .build();
//        //when
//        userService.add(user1);
//        //then
//        IllegalAccessException e = assertThrows(IllegalAccessException.class, () -> userService.add(user2));//예외가 발생해야 한다.
//        assertThat(e.getMessage()).isEqualTo("이미 존재하는 이메일입니다");
//    }
//
//    @Test
//    void 이메일로_조회() throws IllegalAccessException {
//        //given
//        UserRequestDto user1 = UserRequestDto.builder()
//                .name("user1")
//                .email("email1")
//                .build();
//        UserRequestDto user2 = UserRequestDto.builder()
//                .name("user2")
//                .email("email2")
//                .build();
//        userService.add(user1);
//        userService.add(user2);
//
//        //when
//        User result = userService.find("email1");
//
//        //then
//        assertThat(result).isEqualTo(user1);
//
//    }
//
//    @Test
//    void 이메일로_삭제() throws IllegalAccessException {
//        //given
//        UserRequestDto user1 = UserRequestDto.builder()
//                .name("user1")
//                .email("email1")
//                .build();
//        UserRequestDto user2 = UserRequestDto.builder()
//                .name("user2")
//                .email("email2")
//                .build();
//        userService.add(user1);
//        userService.add(user2);
//
//        //when
//        userService.delete("email1");
//
//        //then
//        List<User> result = userRepository.findAll();
//        assertThat(result.size()).isEqualTo(1);
//    }
//
//    @Test
//    void 수정() throws IllegalAccessException {
//        //given
//        UserRequestDto user1 = UserRequestDto.builder()
//                .name("user1")
//                .email("email1")
//                .build();
//        User user = userService.add(user1);
//
//        //when
//        User result = userService.update("email1", UserRequestDto.builder().name("userrr1").build());
//
//        //then
//        assertThat(result.getId()).isEqualTo(user.getId());
//    }
//}