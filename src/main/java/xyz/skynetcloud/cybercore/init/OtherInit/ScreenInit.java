package xyz.skynetcloud.cybercore.init.OtherInit;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.skynetcloud.cybercore.util.screen.LunaGenScreen;
import xyz.skynetcloud.cybercore.util.screen.PowerStorageScreen;

public class ScreenInit {
	
	
	@OnlyIn(Dist.CLIENT)
	public static final void registerGUI() {
		ScreenManager.registerFactory(ContainersInit.LUNAGEN_CON, LunaGenScreen::new);
		ScreenManager.registerFactory(ContainersInit.POWERSTORAGE_CON, PowerStorageScreen::new);

	}
}
