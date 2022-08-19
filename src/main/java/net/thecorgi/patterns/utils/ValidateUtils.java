package net.thecorgi.patterns.utils;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.thecorgi.patterns.item.BasePatternItem;
import net.thecorgi.patterns.item.EfficientScrollItem;

import java.util.Objects;

public class ValidateUtils {
	public static boolean doesBoundMatch(NbtCompound compound, Block block) {
		return Registry.BLOCK.get(new Identifier(compound.getString(BasePatternItem.NBT_BOUND_KEY))) == block;
	}

	public static boolean doesScrollMatch(NbtCompound compound, String id) {
		return Objects.equals(compound.getString(BasePatternItem.NBT_SCROLL_KEY), id);
	}

	public static boolean isCountCompleted(NbtCompound compound) {
		int maxCount = compound.getInt(BasePatternItem.NBT_MAX_COUNT_KEY);
		return maxCount != 0 && compound.getInt(BasePatternItem.NBT_COUNT_KEY) >= maxCount;
	}

	public static boolean tryAll(NbtCompound compound, String id, Block block) {
		return isCountCompleted(compound) && doesScrollMatch(compound, id) && doesBoundMatch(compound, block);
	}
}
