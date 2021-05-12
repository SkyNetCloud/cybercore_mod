package ca.skynetcloud.cybercore.item.tools;

import java.util.List;

import ca.skynetcloud.cybercore.CyberCoreMain;
import ca.skynetcloud.cybercore.CyberCoreTab;
import ca.skynetcloud.cybercore.init.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class DarkSteelPickaxe extends PickaxeItem {

	public int coolDown = 0;
	public boolean canUse;

	public DarkSteelPickaxe(IItemTier material, float speed) {
		super(material, 1, speed,
				new Properties().tab(CyberCoreTab.instance).addToolType(ToolType.PICKAXE, material.getLevel()));
	}

	public DarkSteelPickaxe(IItemTier material, float speed, Properties properties) {
		super(material, 1, speed, properties.addToolType(ToolType.PICKAXE, material.getLevel()));
	}

	@Override
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

		if (stack.sameItem(new ItemStack(ItemInit.dark_steel_pickaxe))) {
			tooltip.add(new StringTextComponent(
					TextFormatting.DARK_PURPLE + "A shadow loom over you while you hold this tool"));

			tooltip.add(
					new StringTextComponent(TextFormatting.RED + "BLINDNESS" + " " + TextFormatting.AQUA + "12 Sec"));

			tooltip.add(new StringTextComponent(
					TextFormatting.GREEN + "HEALTH BOOST" + " " + TextFormatting.AQUA + "12 Sec"));

			tooltip.add(
					new StringTextComponent(TextFormatting.RED + "SLOWNESS" + " " + TextFormatting.AQUA + "12 Sec"));
		}

		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {

		playerIn.getCooldowns().addCooldown(this, 600);
		if (playerIn.getItemBySlot(EquipmentSlotType.MAINHAND).getItem() == ItemInit.dark_steel_pickaxe.getItem()) {
			if (worldIn.isClientSide) {
				playerIn.sendMessage(new StringTextComponent(TextFormatting.GREEN + "[" + CyberCoreMain.NAME + "] "
						+ TextFormatting.RED + "The Shadow Are Coming For You"), null);
			}
			playerIn.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, 255, 1, false, false, true));
			playerIn.addEffect(new EffectInstance(Effects.REGENERATION, 255, 1, false, false, true));
			playerIn.addEffect(new EffectInstance(Effects.BLINDNESS, 255, 1, false, false, true));
		}

		return super.use(worldIn, playerIn, handIn);
	}

}
