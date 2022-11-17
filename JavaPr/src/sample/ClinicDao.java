package sample;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

public interface ClinicDao {
    public Set<Clinic> getAllClinics();
    public Clinic getClinic(String name);
    public void updateClinic(Clinic clinic) throws IOException;
    public void deleteClinic(Clinic clinic) throws IOException;
}
