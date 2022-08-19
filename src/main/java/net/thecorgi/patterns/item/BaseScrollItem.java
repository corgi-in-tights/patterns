package net.thecorgi.patterns.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseScrollItem extends Item {
	String id;

	public BaseScrollItem(Settings settings, String id) {
		super(settings);
		this.id = id;
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		NbtCompound nbtCompound = stack.getOrCreateNbt();
		if (nbtCompound.contains("scroll")) {
			tooltip.add(Text.translatable("tooltip.patterns.scroll", nbtCompound.getString("scroll"))
					.formatted(Formatting.GOLD));
		}

		super.appendTooltip(stack, world, tooltip, context);
	}

	public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
		ItemStack stack = new ItemStack(this);
		NbtCompound nbtCompound = stack.getOrCreateNbt();
		nbtCompound.putString("scroll", this.id);
		stack.setNbt(nbtCompound);

		if (this.isInGroup(group)) {
			stacks.add(stack);
		}
	}
}
