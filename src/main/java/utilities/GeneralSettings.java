package utilities;

import domain.Individual;
import domain.utils.GeneRange;

import java.util.function.Function;

public class GeneralSettings {

    private int tournamentContenders;
    private final int quantityOfSiblings;
    private double euclidAverageDistance;
    private final double mutationProbability;
    private final Function<Individual, Double> fitnessFunction;
    private final GeneRange geneRange;


    public GeneralSettings(int tournamentContenders, int quantityOfSiblings, double mutationProbability, Function<Individual, Double> fitnessFunction, GeneRange geneRange) {
        this.tournamentContenders = tournamentContenders;
        this.quantityOfSiblings = quantityOfSiblings;
        this.mutationProbability = mutationProbability;
        this.fitnessFunction = fitnessFunction;
        this.geneRange = geneRange;
    }

    public int getTournamentContenders() {
        return tournamentContenders;
    }

    public int getQuantityOfSiblings() {
        return quantityOfSiblings;
    }

    public double getEuclidAverageDistance() {
        return euclidAverageDistance;
    }

    public double getMutationProbability() {
        return mutationProbability;
    }

    public GeneRange getGeneRange() {
        return geneRange;
    }

    public Function<Individual, Double> getFitnessFunction() {
        return fitnessFunction;
    }

    public void setTournamentContenders(int tournamentContenders) {
        this.tournamentContenders = tournamentContenders;
    }

    public void setEuclidAverageDistance(double euclidAverageDistance) {
        this.euclidAverageDistance = euclidAverageDistance;
    }
}
