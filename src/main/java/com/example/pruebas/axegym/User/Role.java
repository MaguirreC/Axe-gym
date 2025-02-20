package com.example.pruebas.axegym.User;

import java.util.Arrays;
import java.util.List;

public enum Role {
    ADMINISTRATOR(Arrays.asList(Permission.ASSIGN_SHIFTS,Permission.CREATE_ONE_CLIENT,Permission.DELETE_ONE_CLIENT)),
    TRAINER(Arrays.asList(Permission.CREATE_ONE_MEMBERSHIP,Permission.DELETE_ONE_CLIENT,Permission.GET_ALL_CLIENTS));

    private List<Permission> permissions;

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
