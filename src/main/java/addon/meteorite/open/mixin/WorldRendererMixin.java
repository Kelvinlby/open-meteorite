package addon.meteorite.open.mixin;

import addon.meteorite.open.modules.Culling;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    @Unique private Culling culling;

    // If a world exists, Meteor is initialized.
    @Inject(method = "setWorld", at = @At("TAIL"))
    private void onSetWorld(ClientWorld world, CallbackInfo ci) {
        culling = Modules.get().get(Culling.class);
    }

    // Capture the per-frame view frustum so the Culling module can test block
    // entities, which are not handed a frustum directly.
    @Inject(method = "applyFrustum", at = @At("TAIL"))
    private void onApplyFrustum(Frustum frustum, CallbackInfo ci) {
        if (culling != null) culling.setFrustum(frustum);
    }
}
