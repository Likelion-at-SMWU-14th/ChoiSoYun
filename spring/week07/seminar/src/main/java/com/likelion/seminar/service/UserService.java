package com.likelion.seminar.service;

import com.likelion.seminar.dto.UserSaveRequest;
import com.likelion.seminar.dto.UserUpdateRequest;
import com.likelion.seminar.entity.User;
import com.likelion.seminar.global.exception.DuplicateEmailException;
import com.likelion.seminar.global.exception.UserNotFoundException;
import com.likelion.seminar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void saveUser(UserSaveRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException();
        }

        userRepository.save(
                User.builder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .password(request.getPassword())
                        .age(request.getAge())
                        .build()
        );
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public void updateUser(Long id, UserUpdateRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        if (!user.getEmail().equals(request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException();
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setAge(request.getAge());
    }

}