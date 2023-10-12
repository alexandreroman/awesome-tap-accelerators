package com.vmware.tanzu.tap.accelerators.springboot.contributors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
class Contributor {
    @Id
    @Column(length = 64, nullable = false)
    private String login;
    @Column(length = 256)
    private String avatar;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contributor that)) return false;
        return Objects.equals(getLogin(), that.getLogin()) && Objects.equals(getAvatar(), that.getAvatar());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin(), getAvatar());
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
