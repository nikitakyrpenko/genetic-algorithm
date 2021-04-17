package flow;

import computation.Computations;
import domain.utils.GeneRange;
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

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithm {

    private final List<Double> eclidDistanceHistory;
    private final List<Double> averagePopulationHealth;
    private final int populationSize;
    private final int n;
    private final FitnessFunctionDescription fitnessFunctionDescription;
    private final Population population;
    private final GGSelectionType ggSelectionType;
    private final NewGenerationSelectionType newGenerationSelectionType;
    private final GeneralSettings generalSettings;

    public GeneticAlgorithm(int populationSize, int n, FitnessFunctionDescription fitnessFunctionDescription,
                            GGSelectionType ggSelectionType, NewGenerationSelectionType newGenerationSelectionType) {
        this.eclidDistanceHistory = new ArrayList<>();
        this.populationSize = populationSize;
        this.n = n;
        this.fitnessFunctionDescription = fitnessFunctionDescription;
        this.ggSelectionType = ggSelectionType;
        this.newGenerationSelectionType = newGenerationSelectionType;
        this.generalSettings = new GeneralSettings(ggSelectionType.numberOfContenders, 3, 0.75, fitnessFunctionDescription.getFitnessFunction(),
                new GeneRange(fitnessFunctionDescription.getLow(), fitnessFunctionDescription.getHigh()));
        this.averagePopulationHealth = new ArrayList<>();

        this.population = new Population(
                Generator.generateInitialPopulation(n, populationSize, fitnessFunctionDescription)
        );
    }

    public void start(){
        int iteration = 0;
        generalSettings.setEuclidAverageDistance(Computations.COMPUTE_EUCLID_DISTANCE_FOR_POPULATION(this.population));

        while (true){
            iteration++;

            selectParent(ggSelectionType, generalSettings);
            selectInNextGeneration(newGenerationSelectionType, null);
        }
    }

    public boolean isTerminationConditionMet(int iteration, int populationSize) {


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
