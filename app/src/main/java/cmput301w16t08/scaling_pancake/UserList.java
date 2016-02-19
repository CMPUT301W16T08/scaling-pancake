package cmput301w16t08.scaling_pancake;

import java.util.ArrayList;

/**
 * Created by William on 2016-02-19.
 */
public class UserList {
    private ArrayList<User> users;

    public UserList() {
        this.users = new ArrayList<User>();
    }

    public int size() {
        return this.users.size();
    }

    public boolean containsUser(User user) {
        return this.users.contains(user);
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        if (this.users.contains(user)) {
            this.users.remove(user);
        } else {
            throw new RuntimeException();
        }
    }

    public void removeUser(int index) {
        if (index < this.users.size()) {
            this.users.remove(index);
        } else {
            throw new RuntimeException();
        }
    }

    public User getUser(int index) {
        if (index < this.users.size()) {
            return this.users.get(index);
        } else {
            throw new RuntimeException();
        }
    }

    public void clearUsers() {
        this.users.clear();
    }
}

