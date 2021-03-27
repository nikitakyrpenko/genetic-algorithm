package domain.utils.fuds;

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
}
