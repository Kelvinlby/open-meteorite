package addon.kelvinlby.openmeteorite;

import addon.kelvinlby.openmeteorite.commands.CommandExample;
import addon.kelvinlby.openmeteorite.hud.HudExample;
import addon.kelvinlby.openmeteorite.modules.Culling;
import addon.kelvinlby.openmeteorite.modules.ModuleExample;
import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.GithubRepo;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.commands.Commands;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.hud.HudGroup;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;

public class OpenMeteorite extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final Category CATEGORY = new Category("Open Meteorite");
    public static final HudGroup HUD_GROUP = new HudGroup("Open Meteorite");

    @Override
    public void onInitialize() {
        LOG.info("Initializing Open Meteorite");

        // Modules
        Modules.get().add(new ModuleExample());
        Modules.get().add(new Culling());

        // Commands
        Commands.add(new CommandExample());

        // HUD
        Hud.get().register(HudExample.INFO);
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "addon.kelvinlby.openmeteorite";
    }

    @Override
    public GithubRepo getRepo() {
        return new GithubRepo("kelvinlby", "open-meteorite");
    }
}
