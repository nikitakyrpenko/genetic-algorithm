package domain;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

public class Population {

    private Individual population[];

    private double populationFitness = -1;

    public Population(int populationSize) {
        this.population = new Individual[populationSize];
    }

    public Population(int populationSize, int chromosomeLength) {
        this.population = new Individual[populationSize];
        for (int individualCount = 0; individualCount < populationSize; individualCount++) {
            Individual individual = new Individual(chromosomeLength);
            this.population[individualCount] = individual;
        }
    }


    /*
    * Used for Tournament selection
    */
    public Population(Individual[] individuals){
        this.population = individuals;
    }

    public Individual[] getIndividuals() {
        return this.population;
    }

    public Individual getFittest(int offset) {
        Arrays.sort(this.population, (i1, i2) -> {
            if (i1.getFitness() > i2.getFitness()) {
                return -1;
            } else if (i1.getFitness() < i2.getFitness()) {
                return 1;
            }
            return 0;
        });
        return this.population[offset];
    }

    public void setPopulationFitness(double fitness) {
        this.populationFitness = fitness;
    }

    public double getPopulationFitness() {
        return this.populationFitness;
    }

    public int size() {
        return this.population.length;
    }

    public Individual setIndividual(int offset, Individual individual) {
        return population[offset] = individual;
    }

    public Individual getIndividual(int offset) {
        return population[offset];
    }

    public void appendIndividuals(Individual[] other){
        this.population = Stream.concat(Arrays.stream(this.getIndividuals()), Arrays.stream(other))
                .toArray(Individual[]::new);
    }

    public void shuffle() {
        Random rnd = new Random();
        for (int i = population.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            Individual a = population[index];
            population[index] = population[i];
            population[i] = a;
        }
    }

    @Override
    public String toString() {
        return "Population{" +
                "population=" + Arrays.toString(population) +
                '}';
    }
}
