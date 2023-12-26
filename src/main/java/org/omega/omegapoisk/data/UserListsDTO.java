package org.omega.omegapoisk.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.omega.omegapoisk.entity.UserList;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListsDTO {
    List<UserList> watched;
    List<UserList> watching;
    List<UserList> willWatch;
}
