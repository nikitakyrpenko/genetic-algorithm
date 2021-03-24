package computation;

import domain.Individual;
import domain.Population;

import java.util.Arrays;
import java.util.function.Consumer;

public class NextGenerationSelectionProvider {

    public static Consumer<Population> WORST_SELECTION = population -> {

        population.setPopulation(Arrays.copyOfRange(
                population.getParentsPull(),
                3,
                population.getParentsPull().length));

        population.setParentsPull(null);
    };

}
