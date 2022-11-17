package sample;

import javafx.util.converter.LocalDateStringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.util.*;

public class Clinic {

    private String name;
    private String address;
    private String city;
    private Set<ClinicReservation> reservations;


    public Clinic() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<ClinicReservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<ClinicReservation> reservations) {
        this.reservations = reservations;
    }

    public static Set<Clinic> parseClinics(String clinics) {
        Set<Clinic> retSet = new HashSet<>();
        if (!(clinics.equals("null"))) {
            String cln[] = clinics.split("/");
            for (String c: cln) {
                retSet.add((ClinicDaoImpl.getInstance().getClinic(c)));
            }
        }
        return retSet;
    }
    public static String clinicsToString(Set<Clinic> clinics) {
        String retString = "";
        for (Clinic clg:clinics) {
            retString = retString + clg.getName() + "/";
        }
        if (clinics.isEmpty())
            return "null";
        return retString.substring(0, retString.length() - 1);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return name.equals(((Clinic) o).getName()) && address.equals(((Clinic) o).getAddress()) && city.equals(((Clinic) o).getCity());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        for (int i = 0; i < name.length(); i++) {
            hash = hash*31 + name.charAt(i);
        }
        return hash;
    }


    public Set<LocalDate> getBusyDays(Campaign campaign) {
        int counter = 0;
        Map<LocalDate, Integer> dateOccurs = new HashMap<>();
        Set<LocalDate> retSet = new HashSet<>();

        for (ClinicReservation rsv:reservations) {
            if (rsv.getCampaign().equals(campaign)) {
                dateOccurs.putIfAbsent(rsv.getDate(), 1);
                if (dateOccurs.containsKey(rsv.getDate())) {
                    dateOccurs.put(rsv.getDate(), dateOccurs.get(rsv.getDate()) + 1);
                }
            }
        }

        for (LocalDate date:dateOccurs.keySet()) {
            if (dateOccurs.get(date) == 16) {
                retSet.add(date);
            }
        }

        return retSet;

    }

}
