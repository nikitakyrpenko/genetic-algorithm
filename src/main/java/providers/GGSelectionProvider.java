package providers;

import domain.Individual;
import domain.Population;
import domain.utils.GeneRange;
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
        Individual[] siblings = new Individual[settings.getQuantityOfSiblings()];

        for (int i = 0; i < settings.getQuantityOfSiblings(); i++)
           siblings[i] = generateSibling(settings, tournamentWinner);

        return siblings;
    }

    private static Individual generateSibling(GeneralSettings settings, Individual parent){
        Random random = new Random();

        double mutationProbability = settings.getMutationProbability();
        double sigma = settings.getEuclidAverageDistance() / 16;

        double[] parentGenes = parent.getChromosome();
        double[] siblingsGene = Arrays.copyOf(parentGenes, parentGenes.length);

        for (int i = 0; i < siblingsGene.length; i++){
            //do mutation
            if (random.nextDouble() <= mutationProbability) {
                double deviation = random.nextGaussian() * sigma;
                siblingsGene[i] = checkIsSiblingGeneInBound(settings.getGeneRange(), siblingsGene[i] + deviation);
            }
        }
        return  new Individual(siblingsGene, settings.getFitnessFunction());
    }

    private static double checkIsSiblingGeneInBound(GeneRange geneRange, double gene){
        if (gene < geneRange.getBottomRange()){
            return geneRange.getBottomRange();
        }
        else if (gene > geneRange.getTopRange()){
            return geneRange.getTopRange();
        }
        return gene;
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
