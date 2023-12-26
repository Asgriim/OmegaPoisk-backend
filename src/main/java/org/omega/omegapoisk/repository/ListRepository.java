package org.omega.omegapoisk.repository;

import org.omega.omegapoisk.ORM.OmegaORM;
import org.omega.omegapoisk.entity.ListType;
import org.omega.omegapoisk.entity.UserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ListRepository {

    @Autowired
    private OmegaORM omegaORM;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addList(UserList userList) {
        jdbcTemplate.update("delete from user_list where userid = ? and contentid = ?",
                userList.getUserId(),
                userList.getContentId());
        jdbcTemplate.update("insert into user_list(userid, contentid, contenttitle, listid) VALUES (?, ?, ?, ?)",
                userList.getUserId(),
                userList.getContentId(),
                userList.getContentTitle(),
                userList.getListId());
    }

    public List<UserList> getAllListByType(ListType listType, int userId) {
        String req = "select * from user_list where userid = ? and listid = ?";

        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(req,
                userId,
                listType.getVal());
        List<UserList> list = (List<UserList>) omegaORM.getListFromRowSet(UserList.class, sqlRowSet);

        return list;
    }

    public UserList getListByOwner(int userId, int contentId) {
        String req ="select * from user_list where userid = ? and contentid = ?";


        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(req,
                userId,
                contentId);
        List<UserList> list = (List<UserList>) omegaORM.getListFromRowSet(UserList.class, sqlRowSet);

        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

}
