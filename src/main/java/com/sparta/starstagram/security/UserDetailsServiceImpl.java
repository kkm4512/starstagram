package com.sparta.starstagram.security;

import com.sparta.starstagram.entity.User;
import com.sparta.starstagram.util.UtilFind;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * login이 실행될떄 UsernamePasswordFilter가 실행되며 이 부분이 내부적으로 실행됨
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UtilFind utilFind;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = utilFind.userFindByUsername(username);
        return new UserDetailsImpl(user);
    }
}
