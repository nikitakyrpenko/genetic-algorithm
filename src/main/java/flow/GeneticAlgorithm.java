package flow;

import computation.FitnessFunctionProvider;
import domain.Individual;
import domain.Population;

public class GeneticAlgorithm {
    private int populationSize;

    /**
     * Mutation rate is the fractional probability than an individual gene will
     * mutate randomly in a given generation. The range is 0.0-1.0, but is
     * generally small (on the order of 0.1 or less).
     */
    private double mutationRate;

    /**
     * Crossover rate is the fractional probability that two individuals will
     * "mate" with each other, sharing genetic information, and creating
     * offspring with traits of each of the parents. Like mutation rate the
     * rance is 0.0-1.0 but small.
     */
    private double crossoverRate;


    public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
    }

    /**
     * Initialize population
     *
     * @param chromosomeLength
     *            The length of the individuals chromosome
     * @return population The initial population generated
     */
    public Population initPopulation(int chromosomeLength) {
        // Initialize population
        Population population = new Population(this.populationSize, chromosomeLength);
        return population;
    }


    /**
     * Evaluate the whole population
     *
     * Essentially, loop over the individuals in the population, calculate the
     * fitness for each, and then calculate the entire population's fitness. The
     * population's fitness may or may not be important, but what is important
     * here is making sure that each individual gets evaluated.
     *
     * @param population
     *            the population to evaluate
     */
    public void evalPopulation(Population population) {
        double populationFitness = 0;

        // Loop over population evaluating individuals and suming population
        // fitness
        for (Individual individual : population.getIndividuals()) {
            populationFitness += FitnessFunctionProvider.SIMPLE_FITNESS_FUNCTION.apply(individual);
        }

        population.setPopulationFitness(populationFitness);
    }

    /**
     * Check if population has met termination condition
     *
     * For this simple problem, we know what a perfect solution looks like, so
     * we can simply stop evolving once we've reached a fitness of one.
     *
     * @param population
     * @return boolean True if termination condition met, otherwise, false
     */
    public boolean isTerminationConditionMet(Population population) {
        for (Individual individual : population.getIndividuals()) {
            if (individual.getFitness() == 1) {
                return true;
            }
        }

        return false;
    }

    /**
     * Select parent for crossover
     *
     * @param population
     *            The population to select parent from
     * @return The individual selected as a parent
     */
    public Individual selectParent(Population population) {
        // Get individuals
        Individual[] individuals = population.getIndividuals();

        // Spin roulette wheel
        double populationFitness = population.getPopulationFitness();
        double rouletteWheelPosition = Math.random() * populationFitness;

        // Find parent
        double spinWheel = 0;
        for (Individual individual : individuals) {
            spinWheel += individual.getFitness();
            if (spinWheel >= rouletteWheelPosition) {
                return individual;
            }
        }
        return individuals[population.size() - 1];
    }

    /**
     * Apply mutation to population
     *
     * Mutation affects individuals rather than the population. We look at each
     * individual in the population, and if they're lucky enough (or unlucky, as
     * it were), apply some randomness to their chromosome. Like crossover, the
     * type of mutation applied depends on the specific problem we're solving.
     * In this case, we simply randomly flip 0s to 1s and vice versa.
     *
     * This method will consider the GeneticAlgorithm instance's mutationRate
     * and elitismCount
     *
     * @param population
     *            The population to apply mutation to
     * @return The mutated population
     */
    public Population mutatePopulation(Population population) {
        // Initialize new population
        Population newPopulation = new Population(this.populationSize);

        // Loop over current population by fitness
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            Individual individual = population.getFittest(populationIndex);

            // Loop over individual's genes
            for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
                    // Does this gene need mutation?
                    if (this.mutationRate > Math.random()) {
                        // Get new gene
                        int newGene = 1;
                        if (individual.getGene(geneIndex) == 1) {
                            newGene = 0;
                        }
                        // Mutate gene
                        individual.setGene(geneIndex, newGene);
                    }
                }

            // Add individual to population
            newPopulation.setIndividual(populationIndex, individual);
        }

        // Return mutated population
        return newPopulation;
    }

}
