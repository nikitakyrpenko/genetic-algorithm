package providers;

import domain.Individual;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

public class FitnessFunctionProvider {

    public static Function<Individual, Double> SIMPLE_FITNESS_FUNCTION = individual -> {
        // Track number of correct genes
        int correctGenes = 0;

        // Loop over individual's genes
        for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
            // Add one fitness point for each "1" found
            if (individual.getGene(geneIndex) == 1) {
                correctGenes += 1;
            }
        }

        // Calculate fitness
        double fitness = (double) correctGenes / individual.getChromosomeLength();

        // Store fitness
        individual.setFitness(fitness);

        return fitness;
    };

    public static Function<Individual, Double> DEBA1 = individual -> {
        double[] chromosomes = individual.getChromosome();

        double result = 0;
        for (double c : chromosomes) {
            result += Math.pow(Math.sin(((Math.PI * 5) * c)), 6);
        }

        return result * (1 / chromosomes.length);
    };

    public static Function<Individual, Double> DEBA2 = individual -> {
        double[] chromosomes = individual.getChromosome();
        double result = 0;
        for (double c : chromosomes) {
            result += Math.exp(-2*Math.log(2)*Math.pow((c-0.1)/0.8, 2)) * Math.pow(Math.sin(Math.PI*5*c), 6);
        }
        return result;
    };

    public static Function<Individual, Double> DEBA3 = individual -> {
        double[] chromosomes = individual.getChromosome();

        double result = 0;
        for (double c : chromosomes) {
            result += Math.pow(Math.sin((Math.PI * 5) * (Math.pow(c, 0.75) - 0.05)), 6);
        }

        return result * (1 / chromosomes.length);
    };

    public static Function<Individual, Double> DEBA4 = individual -> {
        double[] chromosomes = individual.getChromosome();
        double result = 0;
        for (double c : chromosomes) {
            result += Math.exp(-2*Math.log(2)*Math.pow((c-0.08)/0.854, 2)) * Math.pow(Math.sin(Math.PI*5*(Math.pow(c, 0.75) - 0.05)), 6);
        }
        return result;
    };
}
