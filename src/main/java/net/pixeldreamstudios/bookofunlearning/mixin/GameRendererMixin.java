package net.pixeldreamstudios.bookofunlearning.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.render.GameRenderer;
import net.pixeldreamstudios.bookofunlearning.util.ZoomHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
	@ModifyReturnValue(method = "getFov", at = @At("RETURN"))
	private double modifyFov(double original) {
		return ZoomHandler.applyZoom(original);
	}
}
