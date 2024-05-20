package com.example.board.service;

import com.example.board.db.entity.User;
import com.example.board.db.repository.UserRepository;
import com.example.board.dto.UserRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /***
     * 유저 등록
     * @param userRequestDto
     * @return User || 중복 예외
     */
    public User save(UserRequestDto userRequestDto){
        validateDuplicateEmail(userRequestDto);
        return userRepository.save(userRequestDto.toEntity());
    }

    //이메일 중복 체크
    private void validateDuplicateEmail(UserRequestDto userRequestDto){
        findByEmail(userRequestDto.getEmail()).ifPresent(
                user -> {
                    throw new RuntimeException("이미 존재하는 이메일입니다.");
                }
        );
    }

    /***
     * 유저 조회 (이메일로)
     * @param email
     * @return User || null
     */
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    /*
     * 유저 삭제 (이메일로)
     */

    /***
     * 유저 삭제
     * @param email
     * @return User || null
     */
    public Optional<User> delete(String email){
        return userRepository.deleteByEmail(email);
    }

    /*
     * 유저 수정(이메일로)
     */

    /***
     * 유저 수정(이메일로)
     * @param email
     * @param userRequestDto
     * @return User || 예외
     */
    public User update(String email,UserRequestDto userRequestDto) {
        Optional<User> foundUser = findByEmail(email);

        return foundUser.map(user -> {
            user.setName(userRequestDto.getName());
            user.setEmail(userRequestDto.getEmail());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("해당 사용자가 없습니다."));
    }
}
