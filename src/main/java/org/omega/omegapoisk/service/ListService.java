package org.omega.omegapoisk.service;

import org.omega.omegapoisk.entity.ListType;
import org.omega.omegapoisk.entity.UserList;
import org.omega.omegapoisk.repository.ListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListService {

    @Autowired
    ListRepository listRepository;


    public void addList(UserList userList) {
        listRepository.addList(userList);
    }

    public List<UserList> getAllListByType(ListType listType, int userId) {
        return listRepository.getAllListByType(listType, userId);
    }

    public UserList getListByOwner(int userId, int contentId) {
        return listRepository.getListByOwner(userId, contentId);
    }
}
