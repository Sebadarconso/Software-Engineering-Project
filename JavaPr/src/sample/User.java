package sample;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class User extends Observer {

    private String name;
    private String surname;
    private String CF;
    private String healthCard;
    private String placeBirth;
    private LocalDate dateBirth;
    private Set<CategorieCittadini> category;
    private String username;
    private String password;
    private Boolean registered;
    private Set<UserReservation> reservations = new HashSet<>();


    public User() {
    }

    public User(String name, String surname, String CF, String healthCard, String placeBirth, LocalDate dateBirth, Set<CategorieCittadini> category, String username, String password, Boolean registered) {
        this.name = name;
        this.surname = surname;
        this.CF = CF;
        this.healthCard = healthCard;
        this.placeBirth = placeBirth;
        this.dateBirth = dateBirth;
        this.category = category;
        this.username = username;
        this.password = password;
        this.registered = registered;
    }

    public Boolean getRegistered() {
        return registered;
    }
    public void setRegistered(Boolean registered) {
        this.registered = registered;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getCF() {
        return CF;
    }
    public void setCF(String CF) {
        this.CF = CF;
    }
    public String getHealthCard() {
        return healthCard;
    }
    public void setHealthCard(String healthCard) {
        this.healthCard = healthCard;
    }
    public String getPlaceBirth() {
        return placeBirth;
    }
    public void setPlaceBirth(String placeBirth) {
        this.placeBirth = placeBirth;
    }
    public LocalDate getDateBirth() {
        return dateBirth;
    }
    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }
    public Set<CategorieCittadini> getCategory() {
        return category;
    }
    public void setCategory(Set<CategorieCittadini> category) {
        this.category = category;
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
    public Set<UserReservation> getReservations() {
        return reservations;
    }
    public void setReservations(Set<UserReservation> reservations) {
        this.reservations = reservations;
    }
    public Set<Campaign> getSubject() {
        return subject;
    }
    public void setSubject(Set<Campaign> subject) {
        this.subject = subject;
    }

    public static Set<User> parseUsers(String users) {
        Set<User> retSet = new HashSet<>();

        if (!(users.equals("null"))) {
            String[] usrs = users.split("/");
            for (String usr: usrs) {
                retSet.add(UserDaoImpl.getInstance().getUserByUsr(usr));
            }
        }
        return retSet;
    }

    public static String usersToString(Set<User> users) {
        String retString = "";
        for (User usr:users) {
            retString = retString + usr.getUsername() + "/";
        }
        if (users.isEmpty())
            return "null";
        return retString.substring(0, retString.length() - 1);
    }

    @Override
    public void update(String campaignName) throws IOException {
        subject.add(CampaignDaoImpl.getInstance().getCampaign(campaignName));
        UserDaoImpl.getInstance().updateUser(this);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", CF='" + CF + '\'' +
                ", healthCard='" + healthCard + '\'' +
                ", placeBirth='" + placeBirth + '\'' +
                ", dateBirth=" + dateBirth +
                ", category=" + category +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", registered=" + registered +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return user.getCF().equals(getCF()) && user.getName().equals(getName()) && user.getSurname().equals(getSurname()) && user.getDateBirth().equals(getDateBirth()) && user.getPlaceBirth().equals(getPlaceBirth()) && user.getHealthCard().equals(getHealthCard());
    }
    @Override
    public int hashCode() {
        if (healthCard.isEmpty())
            return 0;
        return Integer.parseInt(healthCard);
    }
}
