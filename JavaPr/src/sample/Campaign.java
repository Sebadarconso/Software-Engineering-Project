package sample;


import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class Campaign {

    private String name;
    private int availability;
    private String vaccine;
    private LocalDate startCampaign;
    private Set<CategorieCittadini> categories;
    private Set<Clinic> clinics;
    private Set<User> observers = new HashSet<>();


    public Campaign(){}
    public Campaign(String name) {
        this(name, 0, "null", LocalDate.parse("1000-01-01"), new HashSet<>(), new HashSet<>());
    }
    public Campaign(String name, int availability, String vaccine, LocalDate startCampaign, Set<CategorieCittadini> categories, Set<Clinic> clinics) {
        this.name = name;
        this.availability = availability;
        this.vaccine = vaccine;
        this.startCampaign = startCampaign;
        this.categories = categories;
        this.clinics = clinics;
    }

    public String getName() {
        return name;
    }
    public int getAvailability() {
        return availability;
    }
    public String getVaccine() {
        return vaccine;
    }
    public LocalDate getStartCampaign() {
        return startCampaign;
    }
    public Set<Clinic> getClinics() {
        return clinics;
    }
    public Set<CategorieCittadini> getCategories() {
        return categories;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAvailability(int availability) throws IOException {
        this.availability = availability;
        if (this.availability > 0) {
            notifyAllObservers();

        }
    }
    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }
    public void setStartCampaign(LocalDate date) {
        this.startCampaign = date;
    }
    public void setClinics(Set<Clinic> clinics) {
        this.clinics = clinics;
    }
    public void setCategories(Set<CategorieCittadini> categories) {
        this.categories = categories;
    }
    public Set<User> getObservers() {
        return observers;
    }
    public void setObservers(Set<User> observers) {
        this.observers = observers;
    }

    public boolean isActive() {
        return ((LocalDate.now().isAfter(startCampaign) || LocalDate.now().isEqual(startCampaign)) && !startCampaign.equals(LocalDate.parse("1000-01-01")));
    }

    public void attach(User observer){
        observers.add(observer);
    }
    public void notifyAllObservers() throws IOException {

        for (Iterator<User> o = observers.iterator(); o.hasNext();) {
            User observer = o.next();
            observer.update(name);
            observers.remove(observer);
            CampaignDaoImpl.getInstance().updateCampaign(this);

        }
    }

    public static Set<Campaign> parseCampaigns(String campaigns) {
        Set<Campaign> retSet = new HashSet<>();
        if (!(campaigns.equals("null"))) {
            String[] cmp = campaigns.split("/");
            for (String c: cmp) {
                retSet.add(CampaignDaoImpl.getInstance().getCampaign(c));
            }
        }
        return retSet;
    }
    public static String campaignsToString(Set<Campaign> campaigns) {
        if (campaigns.isEmpty())
            return "null";

        String retString = "";
        for (Campaign cmp:campaigns) {
            if(cmp !=null)
            retString += cmp.getName() + "/";
        }
        if(retString!="")
        return retString.substring(0, retString.length() - 1);
        else
            return "null";
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Campaign campaign = (Campaign) o;
        return name.equals(campaign.name);
    }
    @Override
    public int hashCode() {
        int hash = 7;
        for (int i = 0; i < name.length(); i++) {
            hash = hash*31 + name.charAt(i);
        }
        return hash;
    }
}
