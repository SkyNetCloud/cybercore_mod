package ca.skynetcloud.cybercore.util.screen;

import ca.skynetcloud.cybercore.CyberCoreMain;
import ca.skynetcloud.cybercore.api.blocks.BlockInit;
import ca.skynetcloud.cybercore.util.TE.powerTE.CyberCoreEndPowerTE;
import ca.skynetcloud.cybercore.util.TE.powerTE.CyberCorePowerTE;
import ca.skynetcloud.cybercore.util.container.BaseContainerCore;
import ca.skynetcloud.cybercore.util.container.BaseContainerCore.SlotItemHandlerWithInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ScreenBaseCore<T extends BaseContainerCore> extends ContainerScreen<T> {
	protected static final ResourceLocation TEXTURES = new ResourceLocation(
			CyberCoreMain.MODID + ":textures/gui/container/lunagen.png");
	protected final PlayerInventory player;
	protected final CyberCorePowerTE te;

	@SuppressWarnings("unchecked")
	public ScreenBaseCore(BaseContainerCore inventorySlotsIn, PlayerInventory inventoryPlayer, ITextComponent title) {
		super((T) inventorySlotsIn, inventoryPlayer, title);
		this.te = inventorySlotsIn.getTE();
		this.player = inventoryPlayer;
	}

	@Override
	public void init() {
		super.init();
		this.xSize = 205;
		this.ySize = 202;
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.drawTooltips(mouseX, mouseY);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	protected void drawTooltips(int mouseX, int mouseY) {
		drawTooltip(te.getEnergyStored() + "/" + te.getMaxEnergyStored(), mouseX, mouseY, 158, 28, 16, 55);
	}

	public void drawTooltip(String lines, int mouseX, int mouseY, int posX, int posY, int width, int height) {

		posX += this.guiLeft;
		posY += this.guiTop;
		if (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height) {
			renderTooltip(lines, mouseX, mouseY);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		 
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

	}

	protected int getEnergyStoredScaled(int pixels) {
		int i = container.getValue(0);
		int j = container.getValue(1);
		return i != 0 && j != 0 ? i * pixels / j : 0;
	}

	protected int getFluidStoredScaled(int pixels) {
		int i = container.getValue(2);
		int j = container.getValue(3);
		return i != 0 && j != 0 ? i * pixels / j : 0;
	}

	@Override
	protected void renderHoveredToolTip(int x, int y) {
		if (this.minecraft.player.inventory.getItemStack().isEmpty() && this.hoveredSlot != null
				&& !this.hoveredSlot.getHasStack()
				&& this.hoveredSlot instanceof BaseContainerCore.SlotItemHandlerWithInfo) {
			this.renderTooltip(
					new TranslationTextComponent(((SlotItemHandlerWithInfo) this.hoveredSlot).getUsageString())
							.getUnformattedComponentText(),
					x, y);
		} else {
			super.renderHoveredToolTip(x, y);
		}
	}

}