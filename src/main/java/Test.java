import providers.GGSelectionProvider;
import domain.Individual;
import domain.Population;
import utilities.TournamentSettings;

import java.util.function.BiConsumer;

public class Test {
    public static void main(String[] args) {
        BiConsumer<TournamentSettings, Population> tourSelection = GGSelectionProvider.TOUR_SELECTION;

        Population population = initPopulation();

        tourSelection.accept(new TournamentSettings(12, parentsPullSize, 10), population);

        System.out.println(population);
    }

    Members

    private static Population initPopulation(){
        Individual[] individuals = {
                new Individual(new int[]{1,1,1,0}),
                new Individual(new int[]{0,0,0,1}),
                new Individual(new int[]{0,0,1,0}),
                new Individual(new int[]{1,1,1,1})
        };

        return new Population(individuals);
    }
}
