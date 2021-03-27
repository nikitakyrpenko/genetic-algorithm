package domain.utils.fuds;

public class FUDSMinMaxHealth {

    private double maxHealth;
    private double minHealth;

    public FUDSMinMaxHealth(double maxHealth, double minHealth) {
        this.maxHealth = maxHealth;
        this.minHealth = minHealth;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getMinHealth() {
        return minHealth;
    }

    public void setMinHealth(double minHealth) {
        this.minHealth = minHealth;
    }
}
