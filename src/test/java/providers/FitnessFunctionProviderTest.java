package providers;

import domain.Individual;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.function.Function;

public class FitnessFunctionProviderTest{

    private static Function<Individual, Double> FITNESS_FUNCTION;

    @BeforeClass
    public static void before(){
        FITNESS_FUNCTION = FitnessFunctionProvider.SIMPLE_FITNESS_FUNCTION;
    }

    @Test
    public void test1(){
        int size = 26;
        int goodGeneAppears = 10;

        double expected = (double) goodGeneAppears / size;

        Individual individual = new Individual(new int[]{0,1,0,1,0,0,1,1,0,1,1,0,0,0,1,0,0,0,0,1,1,1,0,0,0,0});

        Double actual = FITNESS_FUNCTION.apply(individual);

        Assert.assertEquals(expected, actual,3 );
        Assert.assertEquals(expected, individual.getFitness(), 3);
    }
}