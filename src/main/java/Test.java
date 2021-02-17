import domain.Population;
import flow.GeneticAlgorithm;

public class Test {
    public static void main(String[] args) {
        GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.001, 0.95);

        // Initialize population
        Population population = ga.initPopulation(50);

        System.out.println(population);
    }
}
