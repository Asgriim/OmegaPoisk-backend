package org.omega.omegapoisk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserList implements OmegaEntity{
    private int userId;
    private int listId;
    private int contentId;
    private String contentTitle;

    @Override
    public String TableName() {
        return "user_list";
    }
}
