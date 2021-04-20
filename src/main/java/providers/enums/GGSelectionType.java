package providers.enums;

public enum  GGSelectionType {

    TOUR2(2),
    TOUR12(12),
    MOD_TOUR2(2);

    public final int numberOfContenders;

    private GGSelectionType(int numberOfContenders) {
        this.numberOfContenders = numberOfContenders;
    }

}
