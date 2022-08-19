package net.thecorgi.patterns.registry;

import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import net.thecorgi.patterns.item.BasePatternItem;
import net.thecorgi.patterns.item.EfficientScrollItem;
import net.thecorgi.patterns.item.FortunateScrollItem;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import static net.thecorgi.patterns.Patterns.MAIN_GROUP;
import static net.thecorgi.patterns.Patterns.id;

public class ItemRegistry {
	public static final Item PATTERN = new BasePatternItem(new QuiltItemSettings().maxCount(1).group(MAIN_GROUP), 1.0F);
	public static final Item EFFICIENT_SCROLL = new EfficientScrollItem(new QuiltItemSettings().maxCount(1).group(MAIN_GROUP));
	public static final Item FORTUNATE_SCROLL = new FortunateScrollItem(new QuiltItemSettings().maxCount(1).group(MAIN_GROUP));

	public static void init() {
		Registry.register(Registry.ITEM, id("pattern"), PATTERN);
		Registry.register(Registry.ITEM, id("fortunate_scroll"), FORTUNATE_SCROLL);
		Registry.register(Registry.ITEM, id("efficient_scroll"), EFFICIENT_SCROLL);
	}
}
