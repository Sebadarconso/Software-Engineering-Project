package sample;

import java.time.LocalDate;
import java.util.Objects;

public class UserReservation extends Reservation {

    private Clinic clinic;

    public UserReservation(Campaign campaign, LocalDate date, String timeSlot, Clinic clinic) {
        super(campaign, date, timeSlot);
        this.clinic = clinic;
    }

    public UserReservation() {
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserReservation that = (UserReservation) o;
        return clinic.equals(that.clinic);
    }

    @Override
    public int hashCode() {
        return super.hashCode() * clinic.hashCode();
    }
}
