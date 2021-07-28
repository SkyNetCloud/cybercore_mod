package ca.skynetcloud.cybercore.init.material;

import java.util.function.Supplier;

import javax.swing.UIDefaults.LazyValue;

import ca.skynetcloud.cybercore.init.ItemInit;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class BasisToolMaterial {

	public static final Tier cyber_ingot = new ToolMaterial(5, 2059, 10.0F, 5.0F, 16,
			() -> Ingredient.of(ItemInit.cyber_ingot));
	public static final Tier ruby_gem = new ToolMaterial(3, 1482, 2.9F, 2.9F, 11,
			() -> Ingredient.of(ItemInit.ruby_ingot));
	public static final Tier dark_steel_ingot = new ToolMaterial(3, 2021, 6.5F, 2.1F, 9,
			() -> Ingredient.of(ItemInit.dark_steel_ingot));

	private static class ToolMaterial implements Tier {

		private final int harvestLevel;
		private final int maxUses;
		private final float efficiency;
		private final float attackDamage;
		private final int enchantability;

		public ToolMaterial(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability,
				Supplier<Ingredient> supplier) {
			this.harvestLevel = harvestLevel;
			this.maxUses = maxUses;
			this.efficiency = efficiency;
			this.attackDamage = (float) attackDamage;
			this.enchantability = enchantability;

		}

		@Override
		public int getUses() {
			return maxUses;
		}

		@Override
		public float getSpeed() {
			return efficiency;
		}

		@Override
		public float getAttackDamageBonus() {
			return attackDamage;
		}

		@Override
		public int getLevel() {
			return harvestLevel;
		}

		@Override
		public int getEnchantmentValue() {
			return enchantability;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return repair.get();
		}
	}

}