package xyz.skynetcloud.cybercore;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.items.ItemHandlerHelper;
import xyz.skynetcloud.cybercore.api.Names;
import xyz.skynetcloud.cybercore.api.blocks.BlockInit;
import xyz.skynetcloud.cybercore.api.items.ItemInit;
import xyz.skynetcloud.cybercore.event.ModClientEvent;
import xyz.skynetcloud.cybercore.event.ModSoulBoundEvent;
import xyz.skynetcloud.cybercore.handlers.CapabilityHandler;
import xyz.skynetcloud.cybercore.init.RendererInit;
import xyz.skynetcloud.cybercore.init.ScreenInit;
import xyz.skynetcloud.cybercore.packets.CyberCorePacketHandler;
import xyz.skynetcloud.cybercore.util.networking.config.ConfigLoadder;
import xyz.skynetcloud.cybercore.util.networking.config.CyberCoreConfig;
import xyz.skynetcloud.cybercore.world.gen.OreGen;

@Mod("cybercore")
public class CyberCoreMain {

	public static final String NAME = Names.NAME;
	public static final String MODID = Names.MODID;
	public static final Logger LOGGER = LogManager.getLogger();

	public CyberCoreMain() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigLoadder.COMMON, Names.Server_CONFIG);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigLoadder.CLIENT, Names.Client_CONFIG);
		
		ConfigLoadder.loadConfig(ConfigLoadder.CLIENT, 
		FMLPaths.CONFIGDIR.get().resolve(Names.Client_CONFIG).toString());
		ConfigLoadder.loadConfig(ConfigLoadder.COMMON, 
		FMLPaths.CONFIGDIR.get().resolve(Names.Server_CONFIG).toString());
		
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		modBus.addListener(this::setup);
		
		MinecraftForge.EVENT_BUS.register(ModSoulBoundEvent.DEATH_INSTANCE);
		MinecraftForge.EVENT_BUS.addListener(this::entityJoinWorld);
		MinecraftForge.EVENT_BUS.addListener(this::cpotd);
		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
			MinecraftForge.EVENT_BUS.register(ModClientEvent.INSTANCE);
			modBus.addListener(this::doClientStuff);
		});
	}

	private void setup(FMLCommonSetupEvent event) {

		CapabilityHandler.registerAll();
		CyberCorePacketHandler.register();

	}

	private void doClientStuff(final FMLClientSetupEvent event) {

		RendererInit.registerEntityRenderer();
		ScreenInit.registerGUI();
		OreGen.setupOreGeneration();
		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
			RenderTypeLookup.setRenderLayer(BlockInit.LETTUCE_CROP, RenderType.func_228643_e_());
			RenderTypeLookup.setRenderLayer(BlockInit.TOMATO_CROP, RenderType.func_228643_e_());
		});

	}

	public static class CyberCoreTab extends ItemGroup {

		public static final CyberCoreTab instance = new CyberCoreTab(ItemGroup.GROUPS.length, Names.CyberTAB);

		private CyberCoreTab(int index, String label) {
			super(index, label);
		}

		@Override
		public ItemStack createIcon() {

			return new ItemStack(ItemInit.power_box);
		}
	}

	private void entityJoinWorld(PlayerLoggedInEvent event) {
		
		
		PlayerEntity e = event.getPlayer();
		if (!CyberCoreConfig.GivenOnFirstJoin.get() == true) {

			ItemHandlerHelper.giveItemToPlayer(e, new ItemStack(ItemInit.lettuce_seed));
			ItemHandlerHelper.giveItemToPlayer(e, new ItemStack(ItemInit.tomato_seed));

			event.getPlayer()
					.sendMessage(new StringTextComponent(TextFormatting.BLUE + "[" + TextFormatting.WHITE + Names.INFO
							+ TextFormatting.BLUE + "] " + TextFormatting.WHITE
							+ "You will be given Lettuce Seeds and Tomato Seeds. Everytime you log into this world"));

		} else if (!CyberCoreConfig.GivenOnFirstJoin.get() == false) {

			event.getPlayer()
					.sendMessage(new StringTextComponent(
							TextFormatting.RED + "[" + TextFormatting.WHITE + Names.INFO + TextFormatting.RED + "] "
									+ TextFormatting.WHITE + "Login Item Has Been Disable in config"));

		}

	}
	

	private void cpotd(PlayerLoggedInEvent event) {
		if (event.getEntity() instanceof PlayerEntity
				&& UUID.fromString("283502a2-4134-454c-bb47-39c3875b0dd4").equals(((PlayerEntity)event.getEntity()).getUniqueID())) {
			event.getPlayer()
			.sendMessage(new StringTextComponent(
					TextFormatting.RED + "[" + TextFormatting.WHITE + Names.INFO + TextFormatting.RED + "] "
							+ TextFormatting.WHITE + "Hello Alex Hope you like the mod"));
			

		}
	}

}
