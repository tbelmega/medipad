package pardertec.de.medipad.fahrtenbuch.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Thiemo on 23.02.2016.
 */
public class Fahrtenzettel {
    private String kennzeichen;
    private String fahrer;
    private String sani;
    private Map<UUID, Fahrt> fahrten = new HashMap<>();

    public void setKennzeichen(String kennzeichen) {
        this.kennzeichen = kennzeichen;
    }

    public void setFahrer(String fahrer) {
        this.fahrer = fahrer;
    }

    public void setSani(String sani) {
        this.sani = sani;
    }

    public String getKennzeichen() {
        return kennzeichen;
    }

    public String getFahrer() {
        return fahrer;
    }

    public String getSani() {
        return sani;
    }

    public void addFahrt(Fahrt fahrt) {
        this.fahrten.put(fahrt.uuid, fahrt);
    }

    public Map<UUID, Fahrt> getFahrten() {
        return new HashMap<>(fahrten);
    }
}
