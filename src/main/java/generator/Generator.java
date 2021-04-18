package generator;

import computation.ComputationUtils;
import domain.Individual;
import domain.utils.fitness.FitnessFunctionDescription;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static computation.ComputationUtils.generateDoubleInRange;

public class Generator {

    public static List<Individual>  generateInitialPopulation(int n,
                                                             int populationSize,
                                                             FitnessFunctionDescription fitnessFunctionDescription){
        List<Individual> individuals = new ArrayList<>();

        for (int i = 0; i < populationSize; i++){
            individuals.add(generateOne(n, fitnessFunctionDescription));
        }

        return individuals;
    }


    private static Individual generateOne(int n, FitnessFunctionDescription fitnessFunctionDescription){
        double[] chromosomes = new double[n];

        for (int i = 0; i < chromosomes.length; i++){
            chromosomes[i] = generateDoubleInRange(fitnessFunctionDescription.getLow(), fitnessFunctionDescription.getHigh());
        }
        return new Individual(chromosomes, fitnessFunctionDescription.getFitnessFunction());
    }


}
