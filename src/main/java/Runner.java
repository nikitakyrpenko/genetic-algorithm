import domain.utils.fitness.FitnessFunctionDescription;
import flow.GeneticAlgorithm;
import providers.FitnessFunctionProvider;
import providers.enums.GGSelectionType;
import providers.enums.NewGenerationSelectionType;

public class Runner {
    public static void main(String[] args) {

        FitnessFunctionDescription fitnessFunctionDescription = new FitnessFunctionDescription(FitnessFunctionProvider.DEBA, 0 ,1);

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(50000, 1, fitnessFunctionDescription,
                GGSelectionType.TOUR12, NewGenerationSelectionType.FUDS);

        geneticAlgorithm.start();

    }
}
