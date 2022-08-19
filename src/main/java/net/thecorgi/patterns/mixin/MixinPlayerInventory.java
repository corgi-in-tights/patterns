package net.thecorgi.patterns.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Nameable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import net.thecorgi.patterns.Patterns;
import net.thecorgi.patterns.item.BasePatternItem;
import net.thecorgi.patterns.item.BaseScrollItem;
import net.thecorgi.patterns.item.EfficientScrollItem;
import net.thecorgi.patterns.utils.ValidateUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(PlayerInventory.class)
public abstract class MixinPlayerInventory implements Inventory, Nameable {
	@Shadow
	@Final
	public DefaultedList<ItemStack> offHand;

	@Shadow
	@Final
	public DefaultedList<ItemStack> main;

	@Shadow
	public int selectedSlot;

	@Shadow
	@Final
	public PlayerEntity player;

	@Inject(method = "getBlockBreakingSpeed", at = @At(value = "RETURN"), cancellable = true)
	void patterns$blockBreakingSpeed(BlockState state, CallbackInfoReturnable<Float> cir) {
		for (ItemStack stack : this.offHand) {
			if (stack.getItem() instanceof BasePatternItem) {
				NbtCompound compound = stack.getOrCreateNbt().getCompound(BasePatternItem.NBT_KEY);

				if (!compound.isEmpty()) {
					if (ValidateUtils.tryAll(compound, EfficientScrollItem.id, state.getBlock())) {
						this.player.swingHand(Hand.OFF_HAND);
						cir.setReturnValue(this.main.get(this.selectedSlot).getMiningSpeedMultiplier(state) * 3.15F);
					}
				}
			}
		}
	}
}
