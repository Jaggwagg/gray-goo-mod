package jaggwagg.gray_goo.datagen;

import jaggwagg.gray_goo.datagen.language.ModLanguageEnglishProvider;
import jaggwagg.gray_goo.datagen.tag.ModTagProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class GrayGooDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModLanguageEnglishProvider::new);
        pack.addProvider(ModTagProvider::new);
    }
}
