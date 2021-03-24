package domain;

import java.util.function.Function;

public class Individual {

    private final double[] chromosome;
    private double fitness = -1;

    public Individual(double[] chromosome, Function<Individual, Double> fitnessFunction){
        this.chromosome = chromosome;
        this.fitness = fitnessFunction.apply(this);
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
}
