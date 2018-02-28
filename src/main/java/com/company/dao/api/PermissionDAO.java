package com.company.dao.api;

import com.company.model.Permission;

import java.util.List;

public interface PermissionDAO extends IDAO<Permission> {

    List<Permission> readByGroup(int groupId);

    void update(List<Permission> permissions);

    Permission readByAccount(int accountId);
}
