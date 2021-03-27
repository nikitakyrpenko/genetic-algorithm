package providers;

import domain.Population;
import domain.utils.fuds.FUDSHealthLevel;
import domain.utils.fuds.FUDSMinMaxHealth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class NextGenerationSelectionProvider {

    public static Consumer<Population> WORST_SELECTION = population -> {

        population.setPopulation(Arrays.copyOfRange(
                population.getParentsPull(),
                1,
                population.getParentsPull().length));

        population.setParentsPull(null);
    };

    public static BiConsumer<FUDSMinMaxHealth, Population> FUDS_SELECTION = (fudsMinMaxHealth, population) -> {
        int quantityOfHealthsLevels = (int) Math.sqrt(population.size());
        double intervalStep = (fudsMinMaxHealth.getMaxHealth() - fudsMinMaxHealth.getMinHealth()) / quantityOfHealthsLevels;



    };

    public static List<FUDSHealthLevel> generateHealthLevels(FUDSMinMaxHealth range, double intervalStep ){
        List<FUDSHealthLevel> healthLevels = new ArrayList<>();

        for (double i = range.getMinHealth(); i <= range.getMaxHealth(); i+=intervalStep){
            healthLevels.add(new FUDSHealthLevel(i, i+intervalStep));
        }
        return healthLevels;
    }

}
