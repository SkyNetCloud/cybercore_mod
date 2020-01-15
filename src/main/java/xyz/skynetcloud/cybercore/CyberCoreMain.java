package xyz.skynetcloud.cybercore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import xyz.skynetcloud.cybercore.api.items.ItemNames;
import xyz.skynetcloud.cybercore.init.ClientEventInit;
import xyz.skynetcloud.cybercore.init.ScreenInit;
import xyz.skynetcloud.cybercore.util.CyberCoreConfig;

@Mod("cybercore")
public class CyberCoreMain {

	public static boolean hasSendUpdateAvailable = false;

	public static final String MODID = "cybercore";

	public static final Logger LOGGER = LogManager.getLogger();

	public CyberCoreMain() {

		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, CyberCoreConfig.COMMON, "cybercore-server.toml");
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CyberCoreConfig.CLIENT, "cybercore-client.toml");

		CyberCoreConfig.loadConfig(CyberCoreConfig.CLIENT,
				FMLPaths.CONFIGDIR.get().resolve("cybercore-client.toml").toString());
		CyberCoreConfig.loadConfig(CyberCoreConfig.COMMON,
				FMLPaths.CONFIGDIR.get().resolve("cybercore-server.toml").toString());

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
			FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientEventInit::DiscordLinkOnWorldLoad);
			FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientEventInit::onWorldStart);
		});

		MinecraftForge.EVENT_BUS.register(this);

	}

	private void setup(FMLCommonSetupEvent event) {

	}

	private void doClientStuff(final FMLClientSetupEvent event) {

		ScreenInit.registerGUI();

	}

	public static class CyberCoreTab extends ItemGroup {

		public static final CyberCoreTab instance = new CyberCoreTab(ItemGroup.GROUPS.length, "cybercore");

		private CyberCoreTab(int index, String label) {
			super(index, label);
		}

		@Override
		public ItemStack createIcon() {
			return new ItemStack(ItemNames.lunar_upgrade_lvl_1);
		}
	}

}
