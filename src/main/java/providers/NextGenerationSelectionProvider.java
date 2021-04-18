package providers;

import domain.Individual;
import domain.Population;
import domain.utils.fuds.FUDSHealthLevel;
import domain.utils.fuds.FUDSMinMaxHealth;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static computation.ComputationUtils.*;

public class NextGenerationSelectionProvider {

    public static BiConsumer<FUDSMinMaxHealth, Population> WORST_SELECTION = (fudsMinMaxHealth, population)  -> {

        // TODO Could be the problem of delete with max health instead of least

        List<Individual> parentsPull = population.getParentsPull();

        List<Individual> cleared = new ArrayList<>(parentsPull.subList(2, parentsPull.size()));

        population.setPopulation(cleared);
        population.setParentsPull(null);
    };

    public static BiConsumer<FUDSMinMaxHealth, Population> FUDS_SELECTION = (fudsMinMaxHealth, population) -> {
        List<Individual> parentsPull = population.getParentsPull();
        List<Individual> lookingHealthLevel = createHealthLevelsAndFindTheMostPopulatedLevel(fudsMinMaxHealth, population);

        Random random = new Random();

        Individual dead = lookingHealthLevel.get(random.nextInt(lookingHealthLevel.size()));

        parentsPull.remove(dead);

        population.setPopulation(parentsPull);
        population.setParentsPull(null);
    };

    public static BiConsumer<FUDSMinMaxHealth, Population> MOD_FUDS_SELECTION = (fudsMinMaxHealth, population) -> {
        List<Individual> parentsPull = population.getParentsPull();
        List<Individual> lookingHealthLevel = createHealthLevelsAndFindTheMostPopulatedLevel(fudsMinMaxHealth, population);

        Individual dead = lookingHealthLevel.stream()
                .min(Comparator.comparing(Individual::getFitness))
                .orElseThrow(NoSuchElementException::new);


        parentsPull.remove(dead);

        population.setPopulation(parentsPull);
        population.setParentsPull(null);
    };

    private static List<Individual> createHealthLevelsAndFindTheMostPopulatedLevel(FUDSMinMaxHealth fudsMinMaxHealth, Population population){
        List<Individual> parentsPull = population.getParentsPull();

        int quantityOfHealthsLevels = (int) Math.sqrt(parentsPull.size());
        double intervalStep = (fudsMinMaxHealth.getMaxHealth() - fudsMinMaxHealth.getMinHealth()) / quantityOfHealthsLevels;

        List<FUDSHealthLevel> healthLevels = generateHealthLevels(fudsMinMaxHealth, intervalStep);

        LinkedHashMap<FUDSHealthLevel, List<Individual>> individualsByTheirHealthLevel =
                populateHealthLevels(healthLevels, population);

        FUDSHealthLevel theMostPopulatedHealthLevel = findTheMostPopulatedHealthLevel(individualsByTheirHealthLevel);

        return individualsByTheirHealthLevel.get(theMostPopulatedHealthLevel);
    }

    public static List<FUDSHealthLevel> generateHealthLevels(FUDSMinMaxHealth range, double intervalStep ){
        List<FUDSHealthLevel> healthLevels = new ArrayList<>();

        for (double i = range.getMinHealth(); i <= range.getMaxHealth(); i+=intervalStep){
            healthLevels.add(new FUDSHealthLevel(round(i, 2), round(i+intervalStep,2)));
        }
        return healthLevels;
    }

    public static LinkedHashMap<FUDSHealthLevel, List<Individual>> populateHealthLevels(List<FUDSHealthLevel> healthLevels, Population population){
        LinkedHashMap<FUDSHealthLevel, List<Individual>> result = new LinkedHashMap<>();

        List<Map.Entry<FUDSHealthLevel, List<Individual>>> entries = healthLevels
                .stream()
                .map(population::findAllIndividualsByHealthLevel)
                .collect(Collectors.toList());

        for (Map.Entry<FUDSHealthLevel, List<Individual>> entry : entries){
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static FUDSHealthLevel findTheMostPopulatedHealthLevel(Map<FUDSHealthLevel, List<Individual>> healthLevels){
        List<FUDSHealthLevel> theMostPopulatedLevels = new ArrayList<>();
        int maxSize = -1;

        for (Map.Entry<FUDSHealthLevel, List<Individual>> entry : healthLevels.entrySet()){
            List<Individual> individuals = entry.getValue();

            if (individuals.size() >= maxSize){
                maxSize = individuals.size();
                theMostPopulatedLevels.add(entry.getKey());
            }
        }
        return theMostPopulatedLevels.get(0);
    }

}
