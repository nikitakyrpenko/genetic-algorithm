package computation;

import domain.Individual;
import domain.Population;
import utilities.TournamentSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class GGSelectionProvider {

    public static BiConsumer<TournamentSettings, Population> TOUR_SELECTION = (settings, population) -> {
        List<Individual> winners = new ArrayList<>();

        for (int i = 0; i < settings.getGenerationSize(); i++){
            Individual[] tournamentMembers =
                    generateTournamentMembersByNumbersOfContenders(settings.getTournamentContenders(), population.getIndividuals());

            winners.add(findBest(tournamentMembers));
        }
        population.appendIndividuals((Individual[]) winners.toArray());
    };


    private static Individual[] generateTournamentMembersByNumbersOfContenders(int size, Individual[] initialPopulation){
        Random random = new Random();
        Individual[] tournamentMembers = new Individual[size];

        for (int i = 0, count = 0; i < size; i++, count++){
            Individual chosen = initialPopulation[random.nextInt(size)];
            tournamentMembers[count] = chosen;
        }

        return tournamentMembers;
    }

    private static Individual findBest(Individual[] individuals){
        return new Population(individuals).getFittest(0);
    }
}
