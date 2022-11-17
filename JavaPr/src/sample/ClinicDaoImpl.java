package sample;

import java.io.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ClinicDaoImpl implements ClinicDao {

    private static ClinicDao instance;
    static {
        try {
            instance = new ClinicDaoImpl();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    Set<Clinic> clinics = new HashSet<>();

    private ClinicDaoImpl() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/clinics.csv"));
        String recordClinic = reader.readLine();
        while(recordClinic != null) {
            Clinic newClinic = new Clinic();
            String[] fieldRec = recordClinic.split(",");
            newClinic.setName(fieldRec[0]);
            newClinic.setAddress(fieldRec[1]);
            newClinic.setCity(fieldRec[2]);
            clinics.add(newClinic);
            recordClinic = reader.readLine();
        }

        reader.close();

    }
    public static ClinicDao getInstance() {
        return instance;
    }
    @Override
    public Set<Clinic> getAllClinics() {
        return clinics;
    }
    @Override
    public Clinic getClinic(String name) {
        for (Clinic cln:clinics){
            if (cln.getName().equals(name)) {
                return cln;
            }
        }
        return null;
    }
    @Override
    public void updateClinic(Clinic clinic) {
        for (Clinic cln:clinics) {
            if (clinic.getName().equals(cln.getName())) {
                cln.setName(clinic.getName());
                cln.setAddress(clinic.getAddress());
                cln.setCity(clinic.getCity());
                cln.setReservations(clinic.getReservations());
            }
        }

    }
    @Override
    public void deleteClinic(Clinic clinic) {
        for (Clinic cln:clinics) {
            if (cln.equals(clinic)) {
                clinics.remove(cln);
            }
        }
    }
}
