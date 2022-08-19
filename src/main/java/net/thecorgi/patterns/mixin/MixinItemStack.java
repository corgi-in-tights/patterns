package net.thecorgi.patterns.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.thecorgi.patterns.item.BasePatternItem;
import net.thecorgi.patterns.item.FortunateScrollItem;
import net.thecorgi.patterns.utils.ValidateUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(ItemStack.class)
public class MixinItemStack {
	@Inject(method = "postMine", at = @At(value = "TAIL"))
	private void postMine(World world, BlockState state, BlockPos pos, PlayerEntity player, CallbackInfo ci) {
		ItemStack stack = player.getOffHandStack();
		if (!world.isClient() && stack.getItem() instanceof BasePatternItem) {
			NbtCompound compound = stack.getOrCreateNbt().getCompound("pattern");
			if (!compound.isEmpty()) {
				if (ValidateUtils.tryAll(compound, FortunateScrollItem.id, state.getBlock())) {
//					if (new Random().nextInt(10) == 1) {
					if (true) {
						player.getInventory().offerOrDrop(state.getDroppedStacks(new LootContext.Builder((ServerWorld) world)).get(0));
					}
				}
			}
		}
	}
}
