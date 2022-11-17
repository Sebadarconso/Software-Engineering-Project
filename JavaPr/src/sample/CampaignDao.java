package sample;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface CampaignDao {
    public Set<Campaign> getAllCampaigns();
    public Campaign getCampaign(String name);
    public void updateCampaign(Campaign campaign) throws IOException;
    public void addCampaign(Campaign campaign) throws IOException;
    public void deleteCampaign(String name) throws IOException;
}
