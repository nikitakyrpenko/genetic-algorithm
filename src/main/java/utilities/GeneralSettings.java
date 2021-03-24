package utilities;

import domain.Individual;
import domain.utils.GeneRange;

import java.util.function.Function;

public class GeneralSettings {

    private final int tournamentContenders;
    private final int quantityOfSiblings;
    private final double euclidAverageDistance;
    private final double mutationProbability;
    private final Function<Individual, Double> fitnessFunction;
    private final GeneRange geneRange;


    public GeneralSettings(int tournamentContenders, int quantityOfSiblings, float euclidAverageDistance, double mutationProbability, Function<Individual, Double> fitnessFunction, GeneRange geneRange) {
        this.tournamentContenders = tournamentContenders;
        this.quantityOfSiblings = quantityOfSiblings;
        this.euclidAverageDistance = euclidAverageDistance;
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
}
