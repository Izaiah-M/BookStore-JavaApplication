package com.takehome.bookstore.models.User;

import static com.takehome.bookstore.models.User.Permission.ADMIN_CREATE;
import static com.takehome.bookstore.models.User.Permission.ADMIN_DELETE;
import static com.takehome.bookstore.models.User.Permission.ADMIN_READ;
import static com.takehome.bookstore.models.User.Permission.ADMIN_UPDATE;
import static com.takehome.bookstore.models.User.Permission.MANAGER_CREATE;
import static com.takehome.bookstore.models.User.Permission.MANAGER_DELETE;
import static com.takehome.bookstore.models.User.Permission.MANAGER_READ;
import static com.takehome.bookstore.models.User.Permission.MANAGER_UPDATE;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
    USER(Collections.emptySet()),

    ADMIN(Set.of(
            ADMIN_READ,
            ADMIN_CREATE,
            ADMIN_DELETE,
            ADMIN_UPDATE,

            MANAGER_CREATE,
            MANAGER_DELETE,
            MANAGER_READ,
            MANAGER_UPDATE)),

    MANAGER(Set.of(
            MANAGER_CREATE,
            MANAGER_DELETE,
            MANAGER_READ,
            MANAGER_UPDATE));

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
}
