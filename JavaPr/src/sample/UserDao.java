package sample;

import java.io.IOException;
import java.util.Set;

public interface UserDao {
    public Set<User> getAllUsers();
    public User getUser(String CF);
    public User getUserByUsr(String username);
    public void updateUser(User user) throws IOException;
    public void deleteUser(User user) throws IOException;
}
