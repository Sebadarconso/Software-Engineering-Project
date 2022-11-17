package sample;

import java.io.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CampaignDaoImpl implements CampaignDao {

    private static CampaignDao instance;

    static {
        try {
            instance = new CampaignDaoImpl();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Set<Campaign> campaigns = new HashSet<>();

    private CampaignDaoImpl() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/campaigns.csv"));
        String recordCampaign = reader.readLine();

        while(recordCampaign != null) {
            Campaign newCampaign = new Campaign();
            String fieldRec[] = recordCampaign.split(",");
            newCampaign.setName(fieldRec[0]);
            newCampaign.setAvailability(Integer.parseInt(fieldRec[1]));
            newCampaign.setVaccine(fieldRec[2]);
            if (LocalDate.now().isAfter(LocalDate.parse(fieldRec[3]).plusMonths(3))) {
                newCampaign.setStartCampaign(LocalDate.parse("1000-01-01"));
            } else {
                newCampaign.setStartCampaign(LocalDate.parse(fieldRec[3]));
            }
            newCampaign.setCategories(CategorieCittadini.parseCategories(fieldRec[4]));
            newCampaign.setClinics(Clinic.parseClinics(fieldRec[5]));
            campaigns.add(newCampaign);
            recordCampaign = reader.readLine();
        }

        reader.close();
    }

    public static CampaignDao getInstance() {
        return instance;
    }

    @Override
    public Set<Campaign> getAllCampaigns() {
        return campaigns;
    }

    @Override
    public Campaign getCampaign(String name) {
        for (Campaign cmp:campaigns) {
            if (cmp.getName().equals(name)) {
                return cmp;
            }
        }
        return null;
    }

    @Override
    public void updateCampaign(Campaign campaign) throws IOException {
        for (Campaign cmp:campaigns) {
            if (cmp.getName().equals(campaign.getName())) {
                cmp.setStartCampaign(campaign.getStartCampaign());
                cmp.setObservers(campaign.getObservers());
            }
        }
        writeLog();
    }

    @Override
    public void addCampaign(Campaign campaign) throws IOException {
        campaigns.add(campaign);
        writeLog();
    }

    @Override
    public void deleteCampaign(String name) throws IOException {  //TODO
        for (Campaign cmp:campaigns) {
            if (cmp.getName().equals(name)) {
                cmp.setStartCampaign(LocalDate.parse("1000-01-01"));
            }
        }
        writeLog();
    }

    private void writeLog() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/campaigns.csv"));
        for (Campaign cmp:campaigns) {
            writer.write(cmp.getName() + "," + cmp.getAvailability() + "," + cmp.getVaccine() + "," + cmp.getStartCampaign() + "," + CategorieCittadini.categoriesToString(cmp.getCategories()) + "," + Clinic.clinicsToString(cmp.getClinics()) + "," + User.usersToString(cmp.getObservers()) + "\n");
        }
        writer.close();
    }


}