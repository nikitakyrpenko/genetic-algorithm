package utilities;

public class TournamentSettings {

    private int tournamentContenders;

    private int generationSize;

    public TournamentSettings(int tournamentContenders, int generationSize) {
        this.tournamentContenders = tournamentContenders;
        this.generationSize = generationSize;
    }

    public int getGenerationSize() {
        return generationSize;
    }

    public int getTournamentContenders() {
        return tournamentContenders;
    }
}
