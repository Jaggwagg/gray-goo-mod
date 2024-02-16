package jaggwagg.gray_goo.datagen.language;

import jaggwagg.gray_goo.GrayGoo;
import jaggwagg.gray_goo.block.ModBlocks;
import jaggwagg.gray_goo.item.ModItemGroups;
import jaggwagg.gray_goo.item.ModItems;
import jaggwagg.gray_goo.item.ModTraitItems;
import jaggwagg.gray_goo.screen.ModScreenHandlers;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Arrays;

public class ModLanguageEnglishProvider extends FabricLanguageProvider {
    public ModLanguageEnglishProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }

    @SuppressWarnings("deprecation")
    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        /* Blocks */
        Arrays.stream(ModBlocks.values()).forEach(value -> translationBuilder.add(value.getBlock(), WordUtils.capitalizeFully(value.getId().replace("_", " "))));

        /* Items */
        Arrays.stream(ModItems.values()).forEach(value -> translationBuilder.add(value.getItem(), WordUtils.capitalizeFully(value.getId().replace("_", " "))));
        Arrays.stream(ModTraitItems.values()).forEach(value -> translationBuilder.add(value.getItem(), WordUtils.capitalizeFully(value.getId().replace("_", " "))));

        /* Item Groups */
        Arrays.stream(ModItemGroups.values()).forEach(value -> translationBuilder.add(value.getItemGroup(), WordUtils.capitalizeFully(value.getId().replace("_", " "))));

        /* Screens */
        Arrays.stream(ModScreenHandlers.values()).forEach(value -> translationBuilder.add("container." + GrayGoo.MOD_ID + "." + value.getId(), WordUtils.capitalizeFully(value.getId().replace("_", " "))));
    }
}
