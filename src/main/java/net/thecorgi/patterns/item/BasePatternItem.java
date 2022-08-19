package net.thecorgi.patterns.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.thecorgi.patterns.PatternsJsonDataLoader;
import net.thecorgi.patterns.utils.ValidateUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class BasePatternItem extends Item {
	public static String NBT_KEY = "pattern";
	public static String NBT_COUNT_KEY = "count";
	public static String NBT_MAX_COUNT_KEY = "maxCount";
	public static String NBT_BOUND_KEY = "bound";
	public static String NBT_SCROLL_KEY = "scroll";

	public BasePatternItem(Settings settings, float modifier) {
		super(settings);
	}

	@Override
	public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
		if (otherStack.getItem() instanceof BaseScrollItem) {
			NbtCompound compound = stack.getOrCreateNbt().getCompound(NBT_KEY);

			compound.putString("scroll", otherStack.getOrCreateNbt().getString("scroll"));
			stack.setSubNbt("pattern", compound);
			otherStack.decrement(1);

			return true;
		}
		return false;
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		PlayerEntity player = context.getPlayer();
		if (player != null) {
			ItemStack stack = player.getMainHandStack();
			NbtCompound nbtCompound = stack.getOrCreateNbt();

			if (!nbtCompound.contains(NBT_KEY)) {
				Block block = context.getWorld().getBlockState(context.getBlockPos()).getBlock();
				Identifier blockId = Registry.BLOCK.getId(block);

				// name
				stack.setCustomName(
						Text.literal(I18n.translate(block.getTranslationKey()))
								.append(Text.translatable("item.patterns.pattern.bound"))
								.setStyle(stack.getName().getStyle().withItalic(false)));

				// pattern data
				NbtCompound compound = new NbtCompound();
				compound.putInt("count", 0);
				compound.putInt("maxCount",
						100 - 10 * PatternsJsonDataLoader.map.getOrDefault(blockId, 1));

				compound.putString("type", "block");
				compound.putString("bound", blockId.toString());
				nbtCompound.put("pattern", compound);

				stack.setNbt(nbtCompound);
				return ActionResult.SUCCESS;
			}
		}
		return ActionResult.FAIL;
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		NbtCompound compound = stack.getOrCreateNbt().getCompound("pattern");

		if (!compound.isEmpty()) {

			String type = compound.getString("type");
			int count = compound.getInt("count");
			int maxCount = compound.getInt("maxCount");
			String scroll = compound.getString("scroll");

			if (!Objects.equals(type, "")) {

				tooltip.add(Text.translatable("tooltip.patterns.type", StringUtils.capitalize(type))
						.formatted(Formatting.DARK_GRAY));

				if (count < maxCount) {
					tooltip.add(Text.translatable("tooltip.patterns.count." + type, count, maxCount)
							.formatted(Formatting.GRAY));
				} else if (!Objects.equals(scroll, "")) {
					tooltip.add(Text.translatable("tooltip.patterns.scroll", StringUtils.capitalize(scroll))
							.formatted(Formatting.GOLD));
				} else {
					tooltip.add(Text.translatable("tooltip.patterns.no_scroll")
							.formatted(Formatting.GRAY));
				}
			}
		} else {
			tooltip.add(Text.translatable("tooltip.patterns.unbound").formatted(Formatting.GRAY));
		}

		super.appendTooltip(stack, world, tooltip, context);
	}

	@Override
	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		NbtCompound compound = stack.getOrCreateNbt().getCompound("pattern");
		if (!compound.isEmpty() && ValidateUtils.doesScrollMatch(compound, FortunateScrollItem.id)) {
			if (new Random().nextInt(10) == 1) {
				return true;
			}
		}
		return super.postMine(stack, world, state, pos, miner);
	}
}
