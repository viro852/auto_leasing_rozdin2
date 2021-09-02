package com.autoleasing.entity;

import com.autoleasing.enums.RoleEnum;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private RoleEnum roleEnum;

    @ManyToMany(mappedBy = "setOfRoles")
    private Set<User> setOfUsers;

    public Role() {
    }

    public Role(RoleEnum roleEnum) {
        this.roleEnum = roleEnum;
    }

    public Set<User> getSetOfUsers() {
        return setOfUsers;
    }

    public void setSetOfUsers(HashSet<User> setOfUsers) {
        this.setOfUsers = setOfUsers;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RoleEnum getRoleEnum() {
        return roleEnum;
    }

    public void setRoleEnum(RoleEnum roleEnum) {
        this.roleEnum = roleEnum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id == role.id && roleEnum == role.roleEnum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleEnum);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleEnum=" + roleEnum +
                '}';
    }
}
