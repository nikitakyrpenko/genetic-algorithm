package computation;

import domain.Individual;
import domain.Population;

import java.util.List;
import java.util.TreeSet;

public class Computations {

 /*   public static double COMPUTE_EUCLID_DISTANCE_FOR_POPULATION(Population population){
        List<Individual> individuals = population.getIndividuals();

        double accumulator = 0.0;
        int distancesCount = 0;

        for (int i = 0; i < individuals.size(); i++){
            for (int j = i+1; j < individuals.size(); j++){
                accumulator += computeEuclidDistance(individuals.get(i), individuals.get(j));
                distancesCount++;
            }
        }

        return accumulator / distancesCount;

    }

    public static double COMPUTE_AVERAGE_POPULATION_HEALTH(Population population){

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
    }*/
}
