package moe.ally.explode;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.explosion.Explosion;

public class ExplodeMod implements ModInitializer {
	public static final Identifier BRUH_SOUND_ID = new Identifier("explode_mod:bruh");
	public static SoundEvent BRUH_SOUND_EVENT = new SoundEvent(BRUH_SOUND_ID);
	@Override
	public void onInitialize() {
		Registry.register(Registry.SOUND_EVENT, BRUH_SOUND_ID, BRUH_SOUND_EVENT);
		AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
			if (world.isClient()) return ActionResult.PASS;
			ItemStack held = player.getStackInHand(hand);
			int explosionPower = 1;
			if (held.isOf(Items.WOODEN_PICKAXE) || held.isOf(Items.WOODEN_SWORD) || held.isOf(Items.WOODEN_AXE) || held.isOf(Items.WOODEN_SHOVEL) || held.isOf(Items.WOODEN_HOE)) {
				explosionPower = 2;
			} else if (held.isOf(Items.STONE_PICKAXE) || held.isOf(Items.STONE_SWORD) || held.isOf(Items.STONE_AXE) || held.isOf(Items.STONE_SHOVEL) || held.isOf(Items.STONE_HOE)) {
				explosionPower = 3;
			} else if (held.isOf(Items.IRON_PICKAXE) || held.isOf(Items.IRON_SWORD) || held.isOf(Items.IRON_AXE) || held.isOf(Items.IRON_SHOVEL) || held.isOf(Items.IRON_HOE)) {
				explosionPower = 4;
			} else if (held.isOf(Items.DIAMOND_PICKAXE) || held.isOf(Items.DIAMOND_SWORD) || held.isOf(Items.DIAMOND_AXE) || held.isOf(Items.DIAMOND_SHOVEL) || held.isOf(Items.DIAMOND_HOE)) {
				explosionPower = 5;
			} else if (held.isOf(Items.NETHERITE_PICKAXE) || held.isOf(Items.NETHERITE_SWORD) || held.isOf(Items.NETHERITE_AXE) || held.isOf(Items.NETHERITE_SHOVEL) || held.isOf(Items.NETHERITE_HOE)) {
				explosionPower = 6;
			} else if (held.isOf(Items.TNT)) {
				explosionPower = 10;
				held.decrement(1);
			}

			if (held.isOf(Items.GOLDEN_PICKAXE) || held.isOf(Items.GOLDEN_SWORD) || held.isOf(Items.GOLDEN_AXE) || held.isOf(Items.GOLDEN_SHOVEL) || held.isOf(Items.GOLDEN_HOE)) {
				explosionPower = 10;
				held.decrement(1);
			}

			if (held.hasEnchantments()) {
				for (int i = 0; i < EnchantmentHelper.getLevel(Enchantments.FORTUNE, held); i++) {
					explosionPower++;
				}
				for (int i = 0; i < EnchantmentHelper.getLevel(Enchantments.SHARPNESS, held); i++) {
					explosionPower++;
				}
				for (int i = 0; i < EnchantmentHelper.getLevel(Enchantments.EFFICIENCY, held); i++) {
					explosionPower++;
				}
			}

			if (held.isIn(FabricToolTags.HOES)) {
				explosionPower *= 4;
				held.decrement(1);
			}

			if (world.getDimension().isUltrawarm()) {
				explosionPower *= 2;
			}

			world.createExplosion(player, pos.getX(), pos.getY(), pos.getZ(), explosionPower, Explosion.DestructionType.BREAK);
			return ActionResult.PASS;
		});
	}
}
