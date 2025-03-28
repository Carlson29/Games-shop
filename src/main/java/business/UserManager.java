package business;

import java.util.ArrayList;

public class UserManager {
    private void bootstrapUserList() {
        User u1 = new User("John");
        User u2 = new User("Sam");
        User u3 = new User("Joanne");
        User u4 = new User("Nathan");
        User u5 = new User("Eva");
        usersList.add(u1);
        usersList.add(u2);
        usersList.add(u3);
        usersList.add(u4);
        usersList.add(u5);
    }

    public UserManager() {
        bootstrapUserList();
    }

    private final ArrayList<User> usersList = new ArrayList<User>();

    /**
     * Search user by username that store in usersList.
     *
     * @param username username of the user we want to search.
     * @return a user by the specified username.
     */
    //Use this search function as connect function when user enter username
    public User searchByUsername(String username) {
        User user = null;
        synchronized (usersList) {
            for (User u : usersList) {
                if (u.getUserName().equalsIgnoreCase(username)) {
                    user = u;
                }
            }
        }
        return user;
    }

    /**
     * Add a new user to the list of usersList.
     * If a user already exists matching the username in usersList, new user won't be added.
     *
     * @param username username of the user
     * @return true if the user was successfully added, else return false.
     */
    public User addUser(String username) {
        User user = new User(username);
        synchronized(this) {
            usersList.add(user);
        }
        return user;
    }
}
