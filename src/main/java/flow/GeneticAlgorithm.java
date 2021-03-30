package flow;

import computation.Computations;
import domain.utils.fitness.FitnessFunctionDescription;
import domain.Individual;
import domain.Population;
import domain.utils.fuds.FUDSMinMaxHealth;
import generator.Generator;
import providers.GGSelectionProvider;
import providers.NextGenerationSelectionProvider;
import providers.enums.GGSelectionType;
import providers.enums.NewGenerationSelectionType;
import utilities.GeneralSettings;

public class GeneticAlgorithm {

    private final int populationSize;
    private final int n;
    private final FitnessFunctionDescription fitnessFunctionDescription;
    private final Population population;

    public GeneticAlgorithm(int populationSize, int n, FitnessFunctionDescription fitnessFunctionDescription) {
        this.populationSize = populationSize;
        this.n = n;
        this.fitnessFunctionDescription = fitnessFunctionDescription;

        this.population = new Population(
                Generator.generateInitialPopulation(n, populationSize, fitnessFunctionDescription)
        );
    }

    public void start(){
        int iteration = 0;

        while (isTerminationConditionMet()){
            iteration++;

            this.

        }
    }

    public boolean isTerminationConditionMet() {
        for (Individual individual : population.getIndividuals()) {
            if (individual.getFitness() == 1) {
                return true;
            }
        }

        return false;
    }


    private void selectParent(GGSelectionType type, GeneralSettings generalSettings) {
        switch (type){
            case TOUR2:
                generalSettings.setTournamentContenders(2);
                GGSelectionProvider.TOUR_SELECTION.accept(generalSettings, this.population);
                break;
            case TOUR12:
                generalSettings.setTournamentContenders(12);
                GGSelectionProvider.TOUR_SELECTION.accept(generalSettings, this.population);
                break;
        }
    }



    private void selectInNextGeneration(NewGenerationSelectionType type, FUDSMinMaxHealth minMaxHealth) {
       switch (type){
           case WORST:
               NextGenerationSelectionProvider.WORST_SELECTION.accept(minMaxHealth, this.population);
               break;
           case FUDS:
               NextGenerationSelectionProvider.FUDS_SELECTION.accept(minMaxHealth, this.population);
               break;
           case MODFUDS:
               NextGenerationSelectionProvider.MOD_FUDS_SELECTION.accept(minMaxHealth, this.population);
               break;
       }
    }

    private double computeEuclidDistance(){
        return Computations.COMPUTE_EUCLID_DISTANCE_FOR_POPULATION(this.population);
    }

    private double computeAveragePopulationAverageHealth(){
        return Computations.COMPUTE_AVERAGE_POPULATION_HEALTH(this.population);
    }

}
