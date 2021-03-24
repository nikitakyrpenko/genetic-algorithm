package computation;

import domain.Individual;
import domain.Population;

public class Computations {

    public static double COMPUTE_EUCLID_DISTANCE_FOR_POPULATION(Population population){
        Individual[] individuals = population.getIndividuals();

        double accumulator = 0.0;
        int distancesCount = 0;

        for (int i = 0; i < individuals.length; i++){
            for (int j = i+1; j < individuals.length; j++){
                accumulator += computeEuclidDistance(individuals[i], individuals[j]);
                distancesCount++;
            }
        }

        return accumulator / distancesCount;
    }

    private static double computeEuclidDistance(Individual from, Individual to){
        return compute(from.getChromosome(), to.getChromosome());
    }

    private static double compute(double[] from, double[] to){
        double accumulator = 0.0;

        for (int i = 0; i < from.length && i < to.length; i++){
            accumulator += Math.pow(from[i] - to[i], 2.0);
        }
        return Math.sqrt(accumulator);
    }
}
