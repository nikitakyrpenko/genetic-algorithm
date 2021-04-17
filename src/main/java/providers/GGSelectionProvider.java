package providers;

import domain.Individual;
import domain.Population;
import domain.utils.GeneRange;
import utilities.GeneralSettings;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GGSelectionProvider {

    public static BiConsumer<GeneralSettings, Population> TOUR_SELECTION = (settings, population) -> {

        List<Individual> individuals = population.getIndividuals();

        Individual tournamentWinner = generateTournamentMembersByNumbersOfContendersAndComputeWinner(
                settings.getTournamentContenders(),
                population.getIndividuals()
        );

        Individual[] siblings = APPLY_GAUSSIAN_MUTATION_AND_GENERATE_SIBLINGS(settings,tournamentWinner);

        population.setParentsPull(
                Stream.concat(
                    individuals.stream(), Arrays.stream(siblings))
                .collect(Collectors.toList())
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

    private static Individual generateTournamentMembersByNumbersOfContendersAndComputeWinner(int numberOfContenders, List<Individual> initialPopulation){

        Random random = new Random();
        Individual[] tournamentMembers = new Individual[numberOfContenders];

        for (int i = 0; i < numberOfContenders; i++){
            tournamentMembers[i] = initialPopulation.get(random.nextInt(initialPopulation.size()));
        }

        return findBest(tournamentMembers);
    }

    private static Individual findBest(Individual[] individuals){
       return Collections.max(Arrays.asList(individuals), Comparator.comparing(Individual::getFitness));
    }

}
