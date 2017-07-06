package botalibrium.entity.embedded.containers;

/**
 * Extends plant container with count of seeds sown.
 */
public class SeedsCommunityContainer extends CommunityContainer {

    private int sownSeedsCount;

    private String treatment;

    public int getSownSeedsCount() {
        return sownSeedsCount;
    }

    public void setSownSeedsCount(int sownSeedsCount) {
        this.sownSeedsCount = sownSeedsCount;
    }

    public double getGerminationRate() {
        return 0;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
}
