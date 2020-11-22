# Deadly Boundaries
Top-down multiplayer coop Defend-Your-Base game.

![early_demo](https://raw.githubusercontent.com/payne911/marvelous-bob/master/utils/media/early_demo.gif)

## Project structure
This repository contains 4 modules:
  1) client
  2) server
  3) shared
  4) desktop

The ``shared`` is used by both the ``server`` and the ``client``.

The ``desktop`` is the launcher for the ``client``.

### Other folders
* ``.github``: CI/CD (GitHub Actions)
* ``.run``: the provided IntelliJ `Run Configuration`
* ``utils``: miscellaneous stuff for the developers

## Tech Stack
### Common to all modules
* Java 14 (language)
* Gradle (build)
* JPackage + JLink (lightweight releases)
* [libGDX](https://libgdx.badlogicgames.com/download.html) (framework)
* [KryoNet](https://github.com/crykn/kryonet) (networking)
* Lombok (boilerplate)
* Slf4j (logging)
### Server module
* AWS ECS (cloud)
* Docker (container)
* GitHub Actions (CI/CD)

## Assets
Most of them come from [Kenney](https://kenney.nl/assets?t=platformer), licensed ``CC0 1.0 Universal``. Thank you so much!

## Useful references concerning real-time multiplayer games
* Short and sweet, a high-level overview of the different challenges, with images: [Client-Server Game Architecture](https://www.gabrielgambetta.com/client-server-game-architecture.html)
* Very detailed step-by-step coding guide specifically for a libGDX game: [Developing Lag Compensated Multiplayer Game](https://www.schibsted.pl/blog/developing-lag-compensated-multiplayer-game-pt-1/)
* Deep dive into the complexities of OverWatch's NetCode: [Overwatch Gameplay Architecture and Netcode](https://www.youtube.com/watch?v=W3aieHjyNvw&ab_channel=GDC)