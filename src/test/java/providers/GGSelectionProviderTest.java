package providers;

import domain.Individual;
import domain.Population;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import utilities.TournamentSettings;

import java.util.Random;
import java.util.function.BiConsumer;

public class GGSelectionProviderTest {

    private static BiConsumer<TournamentSettings, Population> TOURNAMENT_FUNCTION = GGSelectionProvider.TOUR_SELECTION;

    private TournamentSettings tournamentSettings;

    @Mock
    public Random fakeRandom = new Random();

    @Before
    public void before(){
        tournamentSettings = new TournamentSettings(2, parentsPullSize, 2, fakeRandom);
    }

    @Test
    public void test1(){
        Mockito.when(fakeRandom.nextInt(2))
                .thenReturn(0)
                .thenReturn(2)
                .thenReturn(1)
                .thenReturn(3);

        TOURNAMENT_FUNCTION.accept(tournamentSettings, initPopulation());
    }

    private Population initPopulation(){
        Individual[] individuals = {
                new Individual(new int[]{1,1,1,0}),
                new Individual(new int[]{0,0,0,1}),
                new Individual(new int[]{0,0,1,0}),
                new Individual(new int[]{1,1,1,1})
        };

        return new Population(individuals);
    }

}