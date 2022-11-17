package sample;

import javafx.scene.control.Button;

import java.time.LocalDate;

public class Reservation {

    private Campaign campaign;
    private LocalDate date;
    private String timeSlot;
    private Button deleteButton = new Button("X");

    public Reservation(Campaign campaign, LocalDate date, String timeSlot) {
        this.campaign = campaign;
        this.date = date;
        this.timeSlot = timeSlot;
    }
    public Reservation() {

    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }

    public static String formatTimeSlot(int timeSlot) {
        switch (timeSlot) {
            case 0:
                return "09:00";
            case 1:
                return "09:30";
            case 2:
                return "10:00";
            case 3:
                return"10:30";
            case 4:
                return "11:00";
            case 5:
                return "11:30";
            case 6:
                return "12:00";
            case 7:
                return "12:30";
            case 8:
                return "16:00";
            case 9:
                return "16:30";
            case 10:
                return "17:00";
            case 11:
                return "17:30";
            case 12:
                return "18:00";
            case 13:
                return "18:30";
            case 14:
                return "19:00";
            case 15:
                return "19:30";

        }
        return "null";

    }

    public static int formatTimeSlot(String timeString) {
        if (timeString.equals("09:00")) {
            return 0;
        }
        if (timeString.equals("09:30")) {
            return 1;
        }
        if (timeString.equals("10:00")) {
            return 2;
        }
        if (timeString.equals("10:30")) {
            return 3;
        }
        if (timeString.equals("11:00")) {
            return 4;
        }
        if (timeString.equals("11:30")) {
            return 5;
        }
        if (timeString.equals("12:00")) {
            return 6;
        }
        if (timeString.equals("12:30")) {
            return 7;
        }
        if (timeString.equals("16:00")) {
            return 8;
        }
        if (timeString.equals("16:30")) {
            return 9;
        }
        if (timeString.equals("17:00")) {
            return 10;
        }
        if (timeString.equals("17:30")) {
            return 11;
        }
        if (timeString.equals("18:00")) {
            return 12;
        }
        if (timeString.equals("18:30")) {
            return 13;
        }
        if (timeString.equals("19:00")) {
            return 14;
        }
        if (timeString.equals("19:30")) {
            return 15;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                ", campaign=" + campaign +
                ", date=" + date +
                ", timeSlot=" + timeSlot +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return timeSlot.equals(that.timeSlot) && campaign.equals(that.campaign) && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return campaign.hashCode() * timeSlot.charAt(2) * timeSlot.charAt(3) * date.hashCode();
    }
}
