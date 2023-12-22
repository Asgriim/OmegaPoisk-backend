package org.omega.omegapoisk.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.omega.omegapoisk.entity.Role;
import org.omega.omegapoisk.entity.User;
import org.omega.omegapoisk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public void test() {
        User user = new User(1,"1","2","1",Collections.singletonList(Role.ADMIN));
        userRepository.addUser(user);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> usersByLogin = userRepository.getUsersByLogin(username);
        if (usersByLogin.size() < 1) {
            throw new UsernameNotFoundException("no such user");
        }
        return usersByLogin.get(0);
    }

    public void addUser(User user) {
        userRepository.addUser(user);
    }
}
