import domain.utils.fuds.FUDSHealthLevel;
import domain.utils.fuds.FUDSMinMaxHealth;
import providers.NextGenerationSelectionProvider;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<FUDSHealthLevel> healthLevels = NextGenerationSelectionProvider.generateHealthLevels(new FUDSMinMaxHealth(), 0.07);

        System.out.println(healthLevels);
    }

}
