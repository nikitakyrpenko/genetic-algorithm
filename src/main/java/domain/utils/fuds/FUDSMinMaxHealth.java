package domain.utils.fuds;

import java.util.Objects;

public class FUDSMinMaxHealth {

    private double maxHealth;
    private double minHealth;

    public FUDSMinMaxHealth(double minHealth, double maxHealth) {
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        FUDSMinMaxHealth that = (FUDSMinMaxHealth) object;
        return Double.compare(that.maxHealth, maxHealth) == 0 &&
                Double.compare(that.minHealth, minHealth) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxHealth, minHealth);
    }
}
