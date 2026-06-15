package addon.kelvinlby.openmeteorite.modules;

import addon.kelvinlby.openmeteorite.OpenMeteorite;
import meteordevelopment.meteorclient.events.render.RenderBlockEntityEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.PlayerUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.BeaconBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EnchantingTableBlock;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Set;

public class Culling extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgEntity = settings.createGroup("Entity");

    // General

    private final Setting<Boolean> applyDistance = sgGeneral.add(new BoolSetting.Builder()
        .name("apply-distance-culling")
        .description("Also cull elements that are farther away than the distance threshold.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Double> distance = sgGeneral.add(new DoubleSetting.Builder()
        .name("distance-threshold")
        .description("Elements farther than this many blocks from the camera are culled.")
        .defaultValue(256)
        .min(0)
        .sliderRange(0, 512)
        .visible(applyDistance::get)
        .build()
    );

    // Entity

    private final Setting<Set<EntityType<?>>> entities = sgEntity.add(new EntityTypeListSetting.Builder()
        .name("entities")
        .description("Entities to cull when they are off-screen or too far away.")
        .defaultValue(EntityType.ITEM_FRAME)
        .build()
    );

    private final Setting<Boolean> cullEnchantBook = sgEntity.add(new BoolSetting.Builder()
        .name("enchantment-table-book")
        .description("Culls the book above enchanting tables when off-screen or too far away.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> cullBeaconBeams = sgEntity.add(new BoolSetting.Builder()
        .name("beacon-beams")
        .description("Culls beacon beams when off-screen or too far away.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> cullFallingBlocks = sgEntity.add(new BoolSetting.Builder()
        .name("falling-blocks")
        .description("Culls falling blocks when off-screen or too far away.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> cullSpawnerEntities = sgEntity.add(new BoolSetting.Builder()
        .name("spawner-entities")
        .description("Culls the spinning mob inside of mob spawners when off-screen or too far away.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> cullSignText = sgEntity.add(new BoolSetting.Builder()
        .name("sign-text")
        .description("Culls text on signs when off-screen or too far away.")
        .defaultValue(false)
        .build()
    );

    public Culling() {
        super(OpenMeteorite.CATEGORY, "culling", "Smartly culls selected in-game elements when they are off-screen (out of FOV) or too far away.");
    }

    // The view frustum captured each frame from LevelRenderer#applyFrustum,
    // used to cull block entities (which aren't passed a frustum directly).
    private Frustum frustum;

    public void setFrustum(Frustum frustum) {
        this.frustum = frustum;
    }

    // Entities

    public boolean cullEntity(Entity entity, Frustum frustum) {
        if (!isActive()) return false;

        boolean target = entities.get().contains(entity.getType())
            || (cullFallingBlocks.get() && entity instanceof FallingBlockEntity);

        return target && shouldCull(frustum, entity.getBoundingBox(), entity.position());
    }

    // Block entities

    @EventHandler
    private void onRenderBlockEntity(RenderBlockEntityEvent event) {
        if (!isActive() || frustum == null) return;

        Block block = event.blockEntityState.blockState.getBlock();

        boolean target;
        if (block instanceof EnchantingTableBlock) target = cullEnchantBook.get();
        else if (block instanceof BeaconBlock) target = cullBeaconBeams.get();
        else if (block instanceof SpawnerBlock) target = cullSpawnerEntities.get();
        else if (block instanceof SignBlock) target = cullSignText.get();
        else return;

        BlockPos pos = event.blockEntityState.blockPos;
        if (target && shouldCull(frustum, new AABB(pos), Vec3.atCenterOf(pos))) event.cancel();
    }

    // Returns true if the element should be hidden. It is culled when its entire
    // bounding box is outside the view frustum (so entities on the edge of the
    // screen, or partially visible, are still rendered), or - when distance
    // culling is enabled - when it is farther than the threshold.
    private boolean shouldCull(Frustum frustum, AABB box, Vec3 center) {
        if (applyDistance.get() && !PlayerUtils.isWithinCamera(center, distance.get())) return true;
        return frustum != null && !frustum.isVisible(box);
    }
}
