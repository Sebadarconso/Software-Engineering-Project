package sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Observer {
    protected Set<Campaign> subject = new HashSet<>();
    public abstract void update(String campaignName) throws IOException;
}
