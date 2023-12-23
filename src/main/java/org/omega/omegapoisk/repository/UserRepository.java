package org.omega.omegapoisk.repository;

import lombok.NoArgsConstructor;
import org.omega.omegapoisk.entity.Role;
import org.omega.omegapoisk.entity.User;
import org.omega.omegapoisk.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.*;

@NoArgsConstructor
@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    private final RowMapper<User> userRowMapper = (ResultSet rs, int rowNum) -> {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setLogin(rs.getString("login"));
        user.setPass(rs.getString("pass"));
        int role = rs.getInt("role");
        user.setRoles(Collections.singletonList(Role.fromInt(role)));
        return user;
    };


    public List<User> getUsersByLogin(String login) {
        return jdbcTemplate.query("select * from user_ where user_.login = ?",userRowMapper,login);
    }

    public User getUserByLogin(String login) {
        return getUsersByLogin(login).get(0);
    }

    public void addUser(User user) {
        int role = Role.toInt(user.getRoles().get(0));
        try {
            jdbcTemplate.update("INSERT INTO user_ (email,login, pass, role) values (?,?,?,?)",
                    user.getEmail(),
                    user.getLogin(),
                    user.getPass(),
                    role);
        }catch (DataAccessException ex) {
            ex.printStackTrace();
            throw new UserAlreadyExistsException();
        }


    }


}
