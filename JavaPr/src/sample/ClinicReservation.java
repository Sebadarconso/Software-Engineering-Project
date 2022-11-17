package sample;

import java.time.LocalDate;
import java.util.Objects;

public class ClinicReservation extends Reservation {

    private User user;

    public ClinicReservation(Campaign campaign, LocalDate date, String timeSlot, User user) {
        super(campaign, date, timeSlot);
        this.user = user;
    }
    public ClinicReservation() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ClinicReservation that = (ClinicReservation) o;
        return user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return super.hashCode() * user.hashCode();
    }
}
