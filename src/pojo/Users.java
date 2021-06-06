package pojo;

import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Users {
    private int id;
    private byte permission;
    private String username;
    private String password;
    private String name;
    private Date dob;
    private byte gender;
    private String stdCode;
    private Set<Registers> registersSet = new HashSet<Registers>(0);
    private Classes classes;

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public Set<Registers> getRegistersSet() {
        return registersSet;
    }

    public void setRegistersSet(Set<Registers> registersSet) {
        this.registersSet = registersSet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte getPermission() {
        return permission;
    }

    public void setPermission(byte permission) {
        this.permission = permission;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    public String getStdCode() {
        return stdCode;
    }

    public void setStdCode(String stdCode) {
        this.stdCode = stdCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return id == users.id && permission == users.permission && gender == users.gender && Objects.equals(username, users.username) && Objects.equals(password, users.password) && Objects.equals(name, users.name) && Objects.equals(dob, users.dob)  && Objects.equals(stdCode, users.stdCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, permission, username, password, name, dob, gender, stdCode);
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", permission=" + permission +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", gender=" + gender +
                ", stdCode='" + stdCode + '\'' +
                '}';
    }
}
