package domain.utils;

public class GeneRange {

    private final double bottomRange;
    private final double topRange;


    public GeneRange(double bottomRange, double topRange) {
        this.bottomRange = bottomRange;
        this.topRange = topRange;
    }

    public double getTopRange() {
        return topRange;
    }

    public double getBottomRange() {
        return bottomRange;
    }
}
