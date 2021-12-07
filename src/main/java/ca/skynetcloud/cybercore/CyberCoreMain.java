package ca.skynetcloud.cybercore;

import ca.skynetcloud.cybercore.client.init.CoreInit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.skynetcloud.cybercore.common.item.enchantment.EnchantmentSoulbound;
import ca.skynetcloud.cybercore.client.crafting.ModedRecipeTypes;
import ca.skynetcloud.cybercore.client.networking.config.CyberConfig;
import ca.skynetcloud.cybercore.client.networking.helper.NameHelper;
import ca.skynetcloud.cybercore.client.world.gen.OreGeneration;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(NameHelper.MODID)
public class CyberCoreMain {
	public static final String NAME = NameHelper.NAME;
	public static final String MODID = NameHelper.MODID;
	public static final Logger LOGGER = LogManager.getLogger();

	public static EnchantmentSoulbound soulbound;

	public CyberCoreMain() {

		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, CyberConfig.CONFIG_SPEC);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CyberConfig.CONFIG_SPEC);

		MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, OreGeneration::generateOres);
		modBus.addListener(this::setup);
		modBus.addListener(this::clientSetup);
	}

	private void clientSetup(final FMLClientSetupEvent event) {

		CoreInit.ScreensInit.registerGUI();
		ItemBlockRenderTypes.setRenderLayer(CoreInit.BlockInit.LETTUCE_CROP, RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(CoreInit.BlockInit.TOMATO_CROP, RenderType.cutout());
		CyberCoreMain.LOGGER.info("Client Event Loadded");
	}

	private void setup(final FMLCommonSetupEvent event) {

		new ModedRecipeTypes();
		CyberCoreMain.LOGGER.info("Common Event Loadded");

	}
}



