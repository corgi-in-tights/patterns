package net.thecorgi.patterns;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.thecorgi.patterns.item.BasePatternItem;
import net.thecorgi.patterns.utils.ValidateUtils;
import org.apache.commons.lang3.StringUtils;

public class PatternEvents {
	public static void blockBreak() {

		PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, entity) -> {
			ItemStack stack = player.getOffHandStack();
			if (stack.getItem() instanceof BasePatternItem) {
				NbtCompound compound = stack.getOrCreateNbt().getCompound("pattern");

				if (!compound.isEmpty()) {
					if (!ValidateUtils.isCountCompleted(compound) &&
						ValidateUtils.doesBoundMatch(compound, state.getBlock())) {

						compound.putInt("count", compound.getInt("count") + 1);
						stack.setSubNbt("pattern", compound);
					}
				}
			}
		});
	}

	public static void killEntity() {
	}

	public static void init() {
		blockBreak();
		killEntity();
	}
}
