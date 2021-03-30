package domain;

import domain.utils.fuds.FUDSHealthLevel;

import java.util.*;
import java.util.stream.Collectors;

public class Population {

    public enum  Members{
        POPULATION,
        PARENTS_PULL
    }

    private List<Individual> population;

    private List<Individual> parentsPull;

    /*
    * Used for generate initial Population
    */
    public Population(List<Individual> initialPopulation){
        this.population = initialPopulation;

    }

    public List<Individual> getIndividuals() {
        return this.population;
    }

    public int size() {
        return this.population.size();
    }


    public List<Individual> getParentsPull() {
        return parentsPull;
    }

    public void setParentsPull(List<Individual> parentsPull) {
        if (Objects.nonNull(parentsPull)) {
            this.parentsPull = parentsPull;
            this.parentsPull.sort(getFitnessComparator());
        }else {
            this.parentsPull = null;
        }
    }

    public void setPopulation(List<Individual> population) {
        this.population = population;
        this.parentsPull.sort(getFitnessComparator());
    }

    private Comparator<Individual> getFitnessComparator(){
        return Comparator.comparing(Individual::getFitness);
    }

    public Map.Entry<FUDSHealthLevel, List<Individual>> findAllIndividualsByHealthLevel(FUDSHealthLevel healthLevel){
        List<Individual> individualsByHealthLevel = this.population
                .stream()
                .filter(individual -> {
                    double health = individual.getFitness();
                    return health < healthLevel.getHighBound() && health >= healthLevel.getHighBound();
                })
                .collect(Collectors.toList());

        return Map.entry(healthLevel, individualsByHealthLevel);
    }

    @Override
    public String toString() {
        return "Population{" +
                "population=" + population.toString() +
                '}';
    }
}
