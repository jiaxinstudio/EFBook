package com.book.ef.service;

import com.book.ef.config.BookUserDetails;
import com.book.ef.entity.User;
import com.book.ef.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BookUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public BookUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow();
        return new BookUserDetails(user);
    }
}
