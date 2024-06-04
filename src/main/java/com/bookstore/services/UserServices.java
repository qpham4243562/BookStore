package com.bookstore.services;

import com.bookstore.entity.User;
import com.bookstore.repository.IRoleRepository;
import com.bookstore.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserServices {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(User user) {
        userRepository.save(user);
        Long userId = userRepository.getUserIdByUsername(user.getUsername());
        Long roleId = roleRepository.getRoleIdByName("USER");

        if (roleId != null && userId != null) {
            userRepository.addRoleToUser(userId, roleId);
        }
    }

    @Transactional
    public boolean processForgotPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }

        String token = UUID.randomUUID().toString();
        user.setResetPasswordToken(token);
        userRepository.save(user);

        sendResetPasswordEmail(email, token);

        return true;
    }

    private void sendResetPasswordEmail(String email, String token) {
        String resetUrl = "http://localhost:8080/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Đặt lại mật khẩu");
        message.setText("Nhấn vào liên kết sau để đặt lại mật khẩu của bạn: " + resetUrl);

        mailSender.send(message);
    }

    public boolean isValidToken(String token) {
        User user = userRepository.findByResetPasswordToken(token);
        return user != null;
    }

    public boolean updatePassword(String token, String password) {
        User user = userRepository.findByResetPasswordToken(token);
        if (user == null) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(password));
        user.setResetPasswordToken(null);
        userRepository.save(user);
        return true;
    }
}
