# Open Meteorite

[![build](https://img.shields.io/github/actions/workflow/status/Kelvinlby/open-meteorite/dev_build.yml?branch=main&style=for-the-badge&logo=github&label=Build)](https://github.com/Kelvinlby/open-meteorite/actions/workflows/dev_build.yml)

An addon for [Meteor Client](https://github.com/MeteorDevelopment/meteor-client) that adds performance-oriented
rendering modules.

## Compatibility

| Component           | Version             |
|---------------------|---------------------|
| Minecraft           | 1.21.11             |
| Meteor Client       | `26.1.2-SNAPSHOT`   |
| Fabric Loader       | `0.19.2` or newer   |
| Java                | 25 or newer         |

> Open Meteorite targets Minecraft 1.21.11. Each Minecraft release generally requires a matching build of both
> Meteor Client and this addon.

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/) for Minecraft 1.21.11.
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

Requires JDK 25.

```bash
./gradlew build
```

The compiled JAR is written to `build/libs/`. Copy it into your Minecraft `mods` folder alongside Meteor Client.

To run a development client with the addon loaded:

```bash
./gradlew runClient
```

## License

See [LICENSE](LICENSE).
