import domain.utils.fitness.FitnessFunctionDescription;
import drawing.Plotter;
import flow.GeneticAlgorithm;
import providers.FitnessFunctionProvider;
import providers.enums.GGSelectionType;
import providers.enums.NewGenerationSelectionType;

public class Runner {
    public static void main(String[] args) {

        FitnessFunctionDescription fitnessFunctionDescription = new FitnessFunctionDescription(FitnessFunctionProvider.DEBA4, 0 ,1);

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(5000, 1, fitnessFunctionDescription,
                GGSelectionType.MOD_TOUR2, NewGenerationSelectionType.WORST);

        Plotter p = new Plotter();
        p.setYBounds(-0.1, 1.2);
        p.setTitle("DEBA4");

        geneticAlgorithm.start(p);
    }
}
