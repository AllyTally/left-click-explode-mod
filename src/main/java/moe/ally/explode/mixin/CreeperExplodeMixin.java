package moe.ally.explode.mixin;

import moe.ally.explode.ExplodeMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreeperEntity.class)
public abstract class CreeperExplodeMixin extends HostileEntity {

	protected CreeperExplodeMixin(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	/**
	 * @author AllyTally
	 */
	@Overwrite
	private void explode() {
		if (!world.isClient) {
			world.playSound(
					null, // Player - if non-null, will play sound for every nearby player *except* the specified player
					this.getBlockPos(), // The position of where the sound will come from
					ExplodeMod.BRUH_SOUND_EVENT, // The sound that will play
					SoundCategory.HOSTILE, // This determines which of the volume sliders affect this sound
					1f, //Volume multiplier, 1 is normal, 0.5 is half volume, etc
					1f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
			);
		}
		this.discard();
	}
}
