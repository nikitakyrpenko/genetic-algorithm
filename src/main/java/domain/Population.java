package domain;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class Population {

    public enum  Members{
        POPULATION,
        PARENTS_PULL
    }

    private Individual[] population;

    private Individual[] parentsPull;

    /*
    * Used for generate initial Population
    */
    public Population(Individual[] initialPopulation){
        this.population = initialPopulation;

    }

    public Individual[] getIndividuals() {
        return this.population;
    }

    public int size() {
        return this.population.length;
    }

    public Individual setIndividual(int offset, Individual individual) {
        return population[offset] = individual;
    }

    public Individual getIndividualFrom(Members from, int offset) {
        if (from.equals(Members.PARENTS_PULL))
            return parentsPull[offset];
        return population[offset];
    }


    public Individual[] getParentsPull() {
        return parentsPull;
    }

    public void setParentsPull(Individual[] parentsPull) {
        if (Objects.nonNull(parentsPull)) {
            this.parentsPull = parentsPull;
            Arrays.sort(parentsPull, getFitnessComparator());
        }else {
            this.parentsPull = null;
        }
    }

    public void setPopulation(Individual[] population) {
        this.population = population;
        Arrays.sort(population, getFitnessComparator());
    }

    private Comparator<Individual> getFitnessComparator(){
        return (o1, o2) -> {
            if (o1.getFitness() > o2.getFitness()) {
                return -1;
            } else if (o1.getFitness() < o2.getFitness()) {
                return 1;
            }
            return 0;
        };
    }

    @Override
    public String toString() {
        return "Population{" +
                "population=" + Arrays.toString(population) +
                '}';
    }
}
