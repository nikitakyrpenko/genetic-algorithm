package domain.utils.fuds;

import java.util.Objects;

public class FUDSHealthLevel {

    private double lowBound;
    private double highBound;

    public FUDSHealthLevel(double lowBound, double highBound) {
        this.lowBound = lowBound;
        this.highBound = highBound;
    }

    public double getHighBound() {
        return highBound;
    }

    public double getLowBound() {
        return lowBound;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        FUDSHealthLevel that = (FUDSHealthLevel) object;
        return Double.compare(that.lowBound, lowBound) == 0 &&
                Double.compare(that.highBound, highBound) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lowBound, highBound);
    }
}
