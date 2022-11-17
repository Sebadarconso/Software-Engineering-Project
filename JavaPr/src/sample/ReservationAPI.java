package sample;

import java.io.IOException;

public interface ReservationAPI {
    void setReservation(User user) throws IOException;
    void setReservation(Clinic clinic) throws IOException;
    void addReservation(User user, UserReservation reservation) throws IOException;
    void addReservation(Clinic clinic, ClinicReservation reservation) throws IOException;
    void deleteReservation(User user, UserReservation reservation) throws IOException;
    void deleteReservation(Clinic clinic, ClinicReservation reservation) throws IOException;
    void refreshReservations() throws IOException;
}
