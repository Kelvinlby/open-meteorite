package addon.kelvinlby.openmeteorite.mixin;

import addon.kelvinlby.openmeteorite.modules.Culling;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class WorldRendererMixin {
    @Unique private Culling culling;

    // if a level exists, meteor is initialized
    @Inject(method = "setLevel", at = @At("TAIL"))
    private void onSetLevel(ClientLevel level, CallbackInfo ci) {
        culling = Modules.get().get(Culling.class);
    }

    @Inject(method = "applyFrustum", at = @At("TAIL"))
    private void onApplyFrustum(Frustum frustum, CallbackInfo ci) {
        culling.setFrustum(frustum);
    }
}
