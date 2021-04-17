import domain.Individual;
import domain.utils.fitness.FitnessFunctionDescription;
import providers.FitnessFunctionProvider;

import java.util.Arrays;

public class Runner {

    public static void main(String[] args) {

        double[] chromosomes = {0.1, 0.2};

        Individual individual = new Individual(chromosomes, FitnessFunctionProvider.DEBA);

        System.out.println(individual.getFitness());

    }
}