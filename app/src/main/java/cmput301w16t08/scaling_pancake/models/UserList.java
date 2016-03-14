package cmput301w16t08.scaling_pancake.models;

import java.util.ArrayList;

/**
 * <code>UserList</code> is meant to hold a list of <code>User</code> objects.
 * It is basically a wrapper around an <code>ArrayList</code>   .
 *
 * @author William
 * @see User
 */
public class UserList {
    private ArrayList<User> users;

    /**
     * Creates a new list of <code>User</code>s
     *
     * @see User
     */
    public UserList() {
        this.users = new ArrayList<User>();
    }

    /**
     * Returns the size of the list
     *
     * @return the size
     */
    public int size() {
        return this.users.size();
    }

    /**
     * Checks whether the supplied <code>User</code> is contained in the list
     *
     * @param user the user in question
     * @return true if user contained in list, else false
     */
    public boolean containsUser(User user) {
        for (int i = 0; i < this.users.size(); i++) {
            if (this.users.get(i).getId().equals(user.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the <code>User</code> referenced by id is contained in the list
     *
     * @param id the id of the user in question
     * @return true if user contained in list, else false
     */
    public boolean containsUser(String id) {
        for (int i = 0; i < this.users.size(); i++) {
            if (this.users.get(i).getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds the supplied <code>User</code> to the list
     *
     * @param user the user to add
     */
    public void addUser(User user) {
        this.users.add(user);
    }

    /**
     * Removes the supplied <code>User</code> from the list
     *
     * @param user the user to remove
     * @throws RuntimeException
     *          if user is not contained in the list
     */
    public void removeUser(User user) {
        for (int i = 0; i < this.users.size(); i++) {
            if (this.users.get(i).getId().equals(user.getId())) {
                this.users.remove(i);
                return;
            }
        }
        throw new RuntimeException();
    }

    /**
     * Removes the <code>User</code> at the supplied index
     *
     * @param index the index of the user to remove
     * @throws RuntimeException
     *          if the index is not in range <code>0</code> to <code>size()-1</code>
     */
    public void removeUser(int index) {
        if (index < this.users.size() && index >= 0) {
            this.users.remove(index);
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * Removes the supplied <code>User</code>
     *
     * @param id the id of the user to remove
     * @throws RuntimeException
     *          if the user referenced by id is not contained in the list
     */
    public void removeUser(String id) {
        for (int i = 0; i < this.users.size(); i++) {
            if (this.users.get(i).getId().equals(id)) {
                this.users.remove(i);
                return;
            }
        }
        throw new RuntimeException();
    }

    /**
     * Returns the <code>User</code> at the supplied index
     *
     * @param index the index of the user to return
     * @throws RuntimeException
     *          if the index is not in range <code>0</code> to <code>size()-1</code>
     * @return the user at the supplied index
     */
    public User getUser(int index) {
        if (index < this.users.size() && index >= 0) {
            return this.users.get(index);
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * Returns the <code>User</code> referenced by the supplied id
     *
     * @param id the id of the user to return
     * @throws RuntimeException
     *          if the user referenced by id is not contained in the list
     * @return the user referenced by id
     */
    public User getUser(String id) {
        for (int i = 0; i < this.users.size(); i++) {
            if (this.users.get(i).getId().equals(id)) {
                return this.users.get(i);
            }
        }
        throw new RuntimeException();
    }

    /**
     * Removes all <code>User</code>s in the list
     */
    public void clearUsers() {
        this.users.clear();
    }
}

