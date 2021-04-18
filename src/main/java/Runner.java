import domain.Individual;
import domain.utils.fitness.FitnessFunctionDescription;
import drawing.Plotter;
import flow.GeneticAlgorithm;
import providers.FitnessFunctionProvider;
import providers.enums.GGSelectionType;
import providers.enums.NewGenerationSelectionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Runner {
    public static void main(String[] args) {

        FitnessFunctionDescription fitnessFunctionDescription = new FitnessFunctionDescription(FitnessFunctionProvider.DEBA, 0 ,1);



        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(5000, 1, fitnessFunctionDescription,
                GGSelectionType.TOUR2, NewGenerationSelectionType.WORST);

        List<Individual> l = geneticAlgorithm.start();

        ArrayList<Individual> a = new ArrayList(l);
        Plotter p = new Plotter();
        p.setFunction(fitnessFunctionDescription);
        p.setIndividuals(a);
        p.setYBounds(-0.1, 1.2);
        p.draw();
        // For screenshot
        // p.capture();

    }
}
