package addon.meteorite.open;

import addon.meteorite.open.modules.Culling;
import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.GithubRepo;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;

public class OpenMeteorite extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final Category CATEGORY = new Category("Open Meteorite");

    @Override
    public void onInitialize() {
        LOG.info("Initializing Open Meteorite");

        // Modules
        Modules.get().add(new Culling());
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "addon.meteorite.open";
    }

    @Override
    public GithubRepo getRepo() {
        return new GithubRepo("Kelvinlby", "open-meteorite");
    }
}
