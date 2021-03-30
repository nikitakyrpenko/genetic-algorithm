package computation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class ComputationUtils {

    private static Random RANDOM = new Random();

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double generateDoubleInRange(double min, double max){
        return min + (max - min) * RANDOM.nextDouble();
    }
}
