package computation;

import domain.Individual;
import domain.Population;
import utilities.GeneralSettings;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class GGSelectionProvider {

    public static BiConsumer<GeneralSettings, Population> TOUR_SELECTION = (settings, population) -> {

        Individual[] individuals = population.getIndividuals();

        Individual tournamentWinner = generateTournamentMembersByNumbersOfContendersAndComputeWinner(
                settings.getTournamentContenders(),
                population.getIndividuals()
        );

        Individual[] siblings = APPLY_GAUSSIAN_MUTATION_AND_GENERATE_SIBLINGS(settings,tournamentWinner);

        population.setParentsPull(
                Stream.concat(
                    Arrays.stream(individuals), Arrays.stream(siblings))
                .toArray(Individual[]::new)
        );
    };


    private static Individual[] APPLY_GAUSSIAN_MUTATION_AND_GENERATE_SIBLINGS(GeneralSettings settings, Individual tournamentWinner){
        //TODO Implement gaussian mutation do not forgret about mutation probability

        return new Individual[3];
    }

    private static Individual generateTournamentMembersByNumbersOfContendersAndComputeWinner(int numberOfContenders, Individual[] initialPopulation){

        Random random = new Random();
        Individual[] tournamentMembers = new Individual[numberOfContenders];

        for (int i = 0; i < numberOfContenders; i++){
            tournamentMembers[i] = initialPopulation[random.nextInt(initialPopulation.length)];
        }

        return findBest(tournamentMembers);
    }

    private static Individual findBest(Individual[] individuals){
        return Stream.of(individuals)
                .max(Comparator.comparing(Individual::getFitness))
                .orElseThrow(NoSuchElementException::new);
    }

}
