package sample;

import java.io.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class UserDaoImpl implements UserDao {

    private static UserDao instance;

    static {
        try {
            instance = new UserDaoImpl();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final Set<User> users = new HashSet<>();

    private UserDaoImpl() throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader("src/users.csv"));
        String recordUser = reader.readLine();
        while(recordUser != null) {
            User newUser = new User();
            String[] fieldRec = recordUser.split(",");
            newUser.setName(fieldRec[0]);
            newUser.setSurname(fieldRec[1]);
            newUser.setCF(fieldRec[2]);
            newUser.setHealthCard(fieldRec[3]);
            newUser.setPlaceBirth(fieldRec[4]);
            newUser.setDateBirth(LocalDate.parse(fieldRec[5]));
            newUser.setCategory(CategorieCittadini.parseCategories(fieldRec[6]));
            newUser.setRegistered(Boolean.parseBoolean(fieldRec[7]));
            newUser.setUsername(fieldRec[8]);
            newUser.setPassword(fieldRec[9]);
            newUser.setSubject(Campaign.parseCampaigns(fieldRec[10]));

            users.add(newUser);
            recordUser = reader.readLine();
        }

        reader.close();
    }

    public static UserDao getInstance() {
        return instance;
    }

    @Override
    public Set<User> getAllUsers() {
        return users;
    }

    @Override
    public User getUser(String CF) {
        for (User usr:users) {
            if (usr.getCF().equals(CF)) {
                return usr;
            }
        }
        return null;
    }

    @Override
    public User getUserByUsr(String username) {
        for (User usr:users) {
            if (usr.getUsername().equals(username)) {
                return usr;
            }
        }
        return null;
    }

    @Override
    public void updateUser(User user) throws IOException {
        for (User usr:users) {
            if (usr.getCF().equals(user.getCF())) {
                usr.setName(user.getName());
                usr.setSurname(user.getSurname());
                usr.setCF(user.getCF());
                usr.setHealthCard(user.getHealthCard());
                usr.setPlaceBirth(user.getPlaceBirth());
                usr.setDateBirth(user.getDateBirth());
                usr.setCategory(user.getCategory());
                usr.setRegistered(user.getRegistered());
                usr.setUsername(user.getUsername());
                usr.setPassword(user.getPassword());
                usr.setSubject(user.getSubject());
                usr.setReservations(user.getReservations());
            }
        }
        writeLog();
    }

    @Override
    public void deleteUser(User user) throws IOException {
        users.removeIf(usr -> usr.getCF().equals(user.getCF()));
        writeLog();
    }

    private void writeLog() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/users.csv"));
        for (User usr:users) {
            writer.write(usr.getName() + "," + usr.getSurname() + "," + usr.getCF() + "," + usr.getHealthCard() + "," + usr.getPlaceBirth()
                    + "," + usr.getDateBirth() + "," + CategorieCittadini.categoriesToString(usr.getCategory()) + "," + usr.getRegistered() + "," + usr.getUsername() + "," + usr.getPassword() + "," + Campaign.campaignsToString(usr.getSubject()) + "\n");
        }
        writer.close();
    }
}
