package com.interview;

import java.util.*;

class User {
    private Integer id;
    private String name;

    private Set<Integer> friends = new HashSet<>();

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Set<Integer> getFriends() {
        return friends;
    }

    public void addFriends(Integer id) {
        this.friends.add(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getId() + "," + this.getName() + "," + this.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && name.equals(user.name) && Objects.equals(friends, user.friends);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, friends);
    }
}
