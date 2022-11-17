package sample;

import java.io.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ReservationAPIImpl implements ReservationAPI {

    public void setReservation(User user) throws IOException {
        BufferedReader readerRes = new BufferedReader(new FileReader("src/userReservations/" + user.getUsername() + ".csv"));
        String recordRes = readerRes.readLine();
        Set<UserReservation> reservations = new HashSet<>();
        while(recordRes != null) {
            Reservation newReservation = new UserReservation();
            String[] fieldRecRes = recordRes.split(",");
            newReservation.setCampaign(CampaignDaoImpl.getInstance().getCampaign(fieldRecRes[0]));
            newReservation.setDate(LocalDate.parse(fieldRecRes[1]));
            ((UserReservation) newReservation).setClinic(ClinicDaoImpl.getInstance().getClinic(fieldRecRes[2]));
            newReservation.setTimeSlot(fieldRecRes[3]);
            reservations.add((UserReservation) newReservation);

            recordRes = readerRes.readLine();
        }
        user.setReservations(reservations);
        readerRes.close();
    }

    public void setReservation(Clinic clinic) throws IOException {
        BufferedReader readerRes = new BufferedReader(new FileReader("src/clinicReservations/" + clinic.getName() + ".csv"));
        String recordRes = readerRes.readLine();
        Set<ClinicReservation> reservations = new HashSet<>();
        while(recordRes != null) {
            Reservation newReservation = new ClinicReservation();
            String[] fieldRecRes = recordRes.split(",");
            newReservation.setCampaign(CampaignDaoImpl.getInstance().getCampaign(fieldRecRes[0]));
            newReservation.setDate(LocalDate.parse(fieldRecRes[1]));
            ((ClinicReservation) newReservation).setUser(UserDaoImpl.getInstance().getUserByUsr(fieldRecRes[2]));
            newReservation.setTimeSlot(fieldRecRes[3]);
            reservations.add((ClinicReservation) newReservation);

            recordRes = readerRes.readLine();
        }
        clinic.setReservations(reservations);
        readerRes.close();
    }

    public void addReservation(User user, UserReservation reservation) throws IOException {
        Set<UserReservation> newSet = user.getReservations();
        newSet.add(reservation);
        user.setReservations(newSet);
        UserDaoImpl.getInstance().updateUser(user);
        writeReservations(user);
    }

    public void addReservation(Clinic clinic, ClinicReservation reservation) throws IOException {
        Set<ClinicReservation> newSet = clinic.getReservations();
        newSet.add(reservation);
        clinic.setReservations(newSet);
        ClinicDaoImpl.getInstance().updateClinic(clinic);
        writeReservations(clinic);
    }

    public void deleteReservation(User user, UserReservation reservation) throws IOException {
        user.getReservations().remove(reservation);
        UserDaoImpl.getInstance().updateUser(user);
        writeReservations(user);

    }

    public void deleteReservation(Clinic clinic, ClinicReservation reservation) throws IOException {
        clinic.getReservations().remove(reservation);
        ClinicDaoImpl.getInstance().updateClinic(clinic);
        writeReservations(clinic);

    }

    @Override
    public void refreshReservations() throws IOException {
        for (User usr:UserDaoImpl.getInstance().getAllUsers()) {
            for (Iterator<UserReservation> iterator = usr.getReservations().iterator(); iterator.hasNext();) {
                UserReservation rsv = iterator.next();
                if (rsv.getDate().isBefore(LocalDate.now())) {
                    deleteReservation(usr, rsv);
                }
            }
        }

        for (Clinic cln:ClinicDaoImpl.getInstance().getAllClinics()) {
            for (Iterator<ClinicReservation> iterator = cln.getReservations().iterator(); iterator.hasNext();) {
                ClinicReservation rsv = iterator.next();
                if (rsv.getDate().isBefore(LocalDate.now())) {
                    deleteReservation(cln, rsv);
                }
            }
        }
    }

    private void writeReservations(User user) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/userReservations/" + user.getUsername() + ".csv"));
        for (UserReservation rsv:user.getReservations()) {
            writer.write(rsv.getCampaign().getName() + "," + rsv.getDate() + "," + rsv.getClinic().getName() + "," + rsv.getTimeSlot() + "\n");
        }
        writer.close();
    }

    private void writeReservations(Clinic clinic) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/clinicReservations/" + clinic.getName() + ".csv"));
        for (ClinicReservation rsv:clinic.getReservations()) {
            writer.write(rsv.getCampaign().getName() + "," + rsv.getDate() + "," + rsv.getUser().getUsername() + "," + rsv.getTimeSlot() + "\n");
        }
        writer.close();

    }


}
