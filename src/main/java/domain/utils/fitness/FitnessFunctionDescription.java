package domain.utils.fitness;

import domain.Individual;

import java.util.function.Function;

public class FitnessFunctionDescription {

    private Function<Individual, Double> fitnessFunction;
    private double low;
    private double high;

    public FitnessFunctionDescription(Function<Individual, Double> fitnessFunction, double low, double high) {
        this.fitnessFunction = fitnessFunction;
        this.low = low;
        this.high = high;
    }

    public Function<Individual, Double> getFitnessFunction() {
        return fitnessFunction;
    }

    public void setFitnessFunction(Function<Individual, Double> fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }
}
