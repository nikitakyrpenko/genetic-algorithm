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
import java.util.Collections;
import java.util.List;

public class GeneticAlgorithm {

    private final List<Double> averagePopulationHealth = Collections.synchronizedList(new ArrayList<Double>());
    private final int populationSize;
    private final int n;
    private final FitnessFunctionDescription fitnessFunctionDescription;
    private final Population population;
    private final GGSelectionType ggSelectionType;
    private final NewGenerationSelectionType newGenerationSelectionType;
    private final GeneralSettings generalSettings;
    private final FUDSMinMaxHealth fudsMinMaxHealth;

    public GeneticAlgorithm(int populationSize, int n, FitnessFunctionDescription fitnessFunctionDescription,
                            GGSelectionType ggSelectionType, NewGenerationSelectionType newGenerationSelectionType) {
        this.populationSize = populationSize;
        this.n = n;
        this.fitnessFunctionDescription = fitnessFunctionDescription;
        this.ggSelectionType = ggSelectionType;
        this.newGenerationSelectionType = newGenerationSelectionType;
        this.generalSettings = new GeneralSettings(ggSelectionType.numberOfContenders, 3, 0.75, fitnessFunctionDescription.getFitnessFunction(),
                new GeneRange(fitnessFunctionDescription.getLow(), fitnessFunctionDescription.getHigh()));

        this.population = new Population(
                Generator.generateInitialPopulation(n, populationSize, fitnessFunctionDescription)
        );

        this.fudsMinMaxHealth = new FUDSMinMaxHealth();
    }

    public void start(){
        int iteration = 0;

        while (isTerminationConditionMet(iteration, this.populationSize)){
            fudsSetMinMaxHealthValue();
            checkIsEuclidDistanceRecomputeNeeded(iteration, this.populationSize);

            averagePopulationHealth.add(computeAveragePopulationAverageHealth());

            selectParent(ggSelectionType, generalSettings);
            selectInNextGeneration(newGenerationSelectionType, this.fudsMinMaxHealth);
        }

        System.out.println(iteration);
        System.out.println(this.population.getIndividuals());
    }

    private void fudsSetMinMaxHealthValue(){
        try {
        List<Individual> individuals = this.population.getIndividuals();
        Individual ff = individuals.get(0);
        Individual fl = individuals.get(individuals.size() - 1);

            this.fudsMinMaxHealth.setMinHealth(ff.getFitness());
            this.fudsMinMaxHealth.setMaxHealth(fl.getFitness());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private boolean isTerminationConditionMet(int iteration, int populationSize) {

        return iteration == (40000 * populationSize) || !isPopulationHealthChangedForDelta();
    }

    private void checkIsEuclidDistanceRecomputeNeeded(int iteration, int populationSize){
        if (iteration == 0 || iteration == 60 * populationSize){
            generalSettings.setEuclidAverageDistance(COMPUTE_EUCLID_DISTANCE_FOR_POPULATION());
        }
    }

    private boolean isPopulationHealthChangedForDelta(){
        int currentSize = averagePopulationHealth.size();
        double delta = 0.0001;

        if (currentSize >= 10) {
            List<Double> lastTen = new ArrayList<>(averagePopulationHealth.subList(currentSize - 10, currentSize));

            Double first = lastTen.get(0);

            double sumOfLastTen = lastTen
                    .stream()
                    .reduce(0.0, Double::sum);

            double averageForLastTen = sumOfLastTen / 10;

            if (Math.abs(averageForLastTen - first) <= delta)
                return true;
        }else {
            return false;
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

    private double computeAveragePopulationAverageHealth(){
        double accumulator = 0.0;

        for (int i = 0; i < this.population.getIndividuals().size() ; i++){
            accumulator += this.population.getIndividuals().get(i).getFitness();
        }

        return accumulator/this.population.size();
    }

    public  double COMPUTE_EUCLID_DISTANCE_FOR_POPULATION(){
        List<Individual> individuals = this.population.getIndividuals();

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

    private  double computeEuclidDistance(Individual from, Individual to){
        return compute(from.getChromosome(), to.getChromosome());
    }

    private  double compute(double[] from, double[] to){
        double accumulator = 0.0;

        for (int i = 0; i < from.length && i < to.length; i++){
            accumulator += Math.pow(from[i] - to[i], 2.0);
        }
        return Math.sqrt(accumulator);
    }
}
