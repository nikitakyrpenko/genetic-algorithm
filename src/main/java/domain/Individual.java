package domain;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

public class Individual {

    private final double[] chromosome;
    private double fitness = -1;
    private Function<Individual, Double> fitnessFunction;

    public Individual(double[] chromosome, Function<Individual, Double> fitnessFunction){
        this.chromosome = chromosome;
        this.fitness = fitnessFunction.apply(this);
        this.fitnessFunction = fitnessFunction;
    }

    public Function<Individual, Double> getFitnessFunction() {
        return fitnessFunction;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double[] getChromosome() {
        return chromosome;
    }

    public int getChromosomeLength(){
        return this.chromosome.length;
    }

    public double getGene(int offset) {
        return this.chromosome[offset];
    }

    public void setGene(int offset, double gene) {
        this.chromosome[offset] = gene;
    }

    @Override
    public String toString() {
        String output = "";
        for (double i : this.chromosome) {
            output += i;
        }
        return output;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Individual that = (Individual) object;
        return Double.compare(that.fitness, fitness) == 0 &&
                Arrays.equals(chromosome, that.chromosome);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(fitness);
        result = 31 * result + Arrays.hashCode(chromosome);
        return result;
    }
}
