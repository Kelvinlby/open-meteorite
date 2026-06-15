package addon.kelvinlby.openmeteorite.mixin;

import addon.kelvinlby.openmeteorite.modules.Culling;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity, S extends EntityRenderState> {
    @Unique private Culling culling;

    // meteor is already initialised at this point
    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(EntityRendererProvider.Context context, CallbackInfo ci) {
        culling = Modules.get().get(Culling.class);
    }

    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private void shouldRender(T entity, Frustum frustum, double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
        if (culling.cullEntity(entity, frustum)) cir.setReturnValue(false);
    }
}
