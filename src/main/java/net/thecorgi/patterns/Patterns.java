package net.thecorgi.patterns;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import net.thecorgi.patterns.item.BasePatternItem;
import net.thecorgi.patterns.item.EfficientScrollItem;
import net.thecorgi.patterns.item.FortunateScrollItem;
import net.thecorgi.patterns.registry.ItemRegistry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;
import org.quiltmc.qsl.resource.loader.api.ResourceLoader;

import static net.thecorgi.patterns.registry.ItemRegistry.PATTERN;

public class Patterns implements ModInitializer {
	public static final String ModID = "patterns";
	public static Identifier id(String key) {
		return new Identifier(ModID, key);
	}

	public static final ItemGroup MAIN_GROUP = QuiltItemGroup.createWithIcon(
			id("main"), PATTERN::getDefaultStack);

	@Override
	public void onInitialize(ModContainer mod) {
		ResourceLoader.get(ResourceType.SERVER_DATA).registerReloader(new PatternsJsonDataLoader());

		ItemRegistry.init();
		PatternEvents.init();
	}
}

