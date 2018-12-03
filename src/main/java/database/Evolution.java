package database;

import javafx.scene.image.Image;

public class Evolution {

    private String evolvedFrom;
    private String evolvesTo;
    private int evolvesId;
    private Image evolvedIcon;
    private String evolutionMethod;
    private String evolutionCriteria;

    public Evolution() {}

    public Evolution(String evolvedFrom, String evolvesTo, Image evolvedIcon, String evolutionMethod, String evolutionCriteria) {
        this.evolvedFrom = evolvedFrom;
        this.evolvesTo = evolvesTo;
        this.evolvedIcon = evolvedIcon;
        this.evolutionMethod = evolutionMethod;
        this.evolutionCriteria = evolutionCriteria;
    }

    public String getEvolvedFrom() {
        return evolvedFrom;
    }

    public void setEvolvedFrom(String evolvedFrom) {
        this.evolvedFrom = evolvedFrom;
    }

    public String getEvolvesTo() {
        return evolvesTo;
    }

    public void setEvolvesTo(String evolvesTo) {
        this.evolvesTo = evolvesTo;
    }

    public int getEvolvesId() {
        return evolvesId;
    }

    public void setEvolvesId(int evolvesId) {
        this.evolvesId = evolvesId;
    }

    public Image getEvolvedIcon() {
        return evolvedIcon;
    }

    public void setEvolvedIcon(Image evolvedIcon) {
        this.evolvedIcon = evolvedIcon;
    }

    public String getEvolutionMethod() {
        return evolutionMethod;
    }

    public void setEvolutionMethod(String evolutionMethod) {
        this.evolutionMethod = evolutionMethod;
    }

    public String getEvolutionCriteria() {
        return evolutionCriteria;
    }

    public void setEvolutionCriteria(String evolutionCriteria) {
        this.evolutionCriteria = evolutionCriteria;
    }
}
