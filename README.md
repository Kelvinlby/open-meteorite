# Open Meteorite

[![build](https://img.shields.io/github/actions/workflow/status/Kelvinlby/open-meteorite/build.yml?branch=master&style=for-the-badge&logo=github&label=Build)](https://github.com/Kelvinlby/open-meteorite/actions/workflows/build.yml)

An addon for [Meteor Client](https://github.com/MeteorDevelopment/meteor-client) that adds performance-oriented
rendering modules.

## Compatibility

| Component           | Version                |
|---------------------|------------------------|
| Minecraft           | 1.21.11 (also 1.21.4)  |
| Meteor Client       | `1.21.11-SNAPSHOT`     |
| Fabric Loader       | `0.19.0` or newer      |
| Java                | 21 or newer            |

> Open Meteorite is built against Minecraft 1.21.11 and its matching Meteor Client snapshot. Because the relevant
> mappings are shared, the same JAR also loads on 1.21.4 — pair it with the Meteor Client build for whichever version
> you run.

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/) `0.19.0` or newer for Minecraft 1.21.11 (or 1.21.4).
2. Place the matching build of [Meteor Client](https://meteorclient.com/) in your `mods` folder.
3. Download the latest Open Meteorite JAR from the
   [Releases](https://github.com/Kelvinlby/open-meteorite/releases) (or build it yourself, see below) and drop it into
   the same `mods` folder.
4. Launch the game. The addon's modules appear under the **Open Meteorite** category in the Meteor Client GUI.

## Modules

### Culling — *Render*

Smartly hides selected in-game elements when they are off-screen (outside your field of view) or too far away, reducing
the amount of work the renderer has to do.

An element is culled when its **entire bounding box** lies outside the camera's view frustum, so anything on the edge of
the screen or only partially visible is still drawn. Optionally, elements can also be culled by distance.

**General**

| Setting                   | Default | Description                                                                          |
|---------------------------|---------|--------------------------------------------------------------------------------------|
| `apply-distance-culling`  | `false` | Also cull elements that are farther away than the distance threshold.                |
| `distance-threshold`      | `256`   | Elements farther than this many blocks from the camera are culled (0–512).           |

**Entity**

| Setting                    | Default      | Description                                                              |
|----------------------------|--------------|-------------------------------------------------------------------------|
| `entities`                 | Item Frame   | The entity types to cull when off-screen or too far away.               |
| `enchantment-table-book`   | `true`       | Culls the floating book above enchanting tables.                        |
| `beacon-beams`             | `false`      | Culls beacon beams.                                                      |
| `falling-blocks`           | `false`      | Culls falling blocks (sand, gravel, etc.).                              |
| `spawner-entities`         | `false`      | Culls the spinning mob displayed inside mob spawners.                   |
| `sign-text`                | `false`      | Culls the text rendered on signs.                                       |

## Building

Requires JDK 21.

```bash
./gradlew build
```

The compiled JAR is written to `build/libs/`. Copy it into your Minecraft `mods` folder alongside Meteor Client.

To run a development client with the addon loaded:

```bash
./gradlew runClient
```

## Authors

Kelvin_LBY, HUI_73rd and Toaru.

## License

See [LICENSE](LICENSE).
