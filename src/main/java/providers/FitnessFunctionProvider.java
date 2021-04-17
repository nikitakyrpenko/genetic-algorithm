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

    public static Function<Individual, Double> DEBA = individual -> {
        double[] chromosome = individual.getChromosome();

        double accum = fx(chromosome);

        return accum * (1 / chromosome.length);
    };

    public static double fx(double[] x) {
        /*return Math.sin(5 * Math.PI * x);*/
        double chromosome = x[0];
        double sin = Math.sin(((Math.PI * 5) * chromosome));

        return sin;
    }
}
