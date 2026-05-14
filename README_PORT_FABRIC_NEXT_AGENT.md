# Psi Fabric 1.21.1 Port Handoff

This repository is a work-in-progress port of VazkiiMods/Psi from the 1.21.1 NeoForge branch to Fabric 1.21.1.

## Current State

- Branch: `port/fabric-1.21.1`
- Main build system was moved to Fabric Loom.
- Java 21 is required.
- A local JDK 21 was downloaded at:
  - `.gradle/jdks/jdk21/jdk-21.0.11+10`
- The project currently builds:
  - `build/libs/psi-1.21.1-109-fabric.jar`
- The client starts with `runClient`.
- JEI can see Psi items, which means many item registries are alive.
- Generated resources were added to the main resource set, fixing several missing block/item model files.

Use this PowerShell prefix for Gradle commands:

```powershell
$env:JAVA_HOME=(Resolve-Path '.gradle\jdks\jdk21\jdk-21.0.11+10').Path
$env:Path="$env:JAVA_HOME\bin;$env:Path"
```

Useful commands:

```powershell
.\gradlew.bat build -x pmdMain --console=plain
.\gradlew.bat runClient --console=plain
.\gradlew.bat spotlessApply --console=plain
```

## 2026-05-14 Runtime Checkpoint

The older sections below still describe the original handoff state. The current
port is further along:

- Creative tab is visible on Fabric.
- CAD Assembler opens through Fabric screen handler registration.
- Spell Programmer opens and edits are sent to the server with
  `MessageSpellModified`.
- CAD item models render through the Fabric renderer path and have a first-person
  pistol-like orientation.
- Psimetal exosuit rendering uses Fabric armor rendering.
- Client item color providers are registered for CADs, exosuit pieces, sensors,
  and CAD colorizers.
- Keybind/client tick handling is registered through Fabric.
- Server/client player data ticking is registered through Fabric, including
  delayed spell contexts and login data sync.
- Psi player attributes (`total_psi` and `regen`) are registered on Fabric
  players.
- Core S2C/C2S Psi payloads are registered with Fabric networking and basic send
  helpers are wired.
- The psi bar HUD is registered with Fabric HUD rendering, and its custom shader
  is registered with `CoreShaderRegistrationCallback`.

Last successful verification command:

```powershell
.\gradlew.bat build -x pmdMain --console=plain
```

Known notes from the latest runtime logs:

- If casts appear to fail, first check the chat/log for the vanilla Psi rule:
  `The other CAD in your inventory is interfering with your cast. You can only
  have one CAD on you at a time.`
- Several recipes are still missing, especially CAD colorizers and psimetal
  tools.
- The active build still emits warnings for old client mixin targets
  (`HumanoidArmorLayerMixin`, `ParticleEngineMixin`) and the deprecated Fabric
  renderer fallback consumer in `ModelCAD`.

Next recommended steps:

1. Smoke test casting after the Fabric player tick, attribute, networking, HUD,
   and shader fixes:
   - one CAD only in inventory,
   - one spell bullet,
   - basic redstone to psidust cast,
   - repeated casts after regen,
   - psi bar movement on the right side.
2. Verify CAD colorizers in the CAD Assembler now that item color providers and
   C2S/S2C packets are registered.
3. Add missing recipes for CAD colorizers and psimetal tools, or intentionally
   hide unavailable entries from recipe lookups.
4. Continue porting NeoForge event subscribers that are still inert on Fabric:
   damage/jump/interaction hooks, world render hooks, FOV updates, remaining
   armor events, and particle hooks.
5. Revisit the temporary capability/component strategy. The current helper-based
   approach works for some Psi-only behavior but is not a final Fabric component
   model.
6. Fix or remove the stale mixins one at a time and keep runtime startup green.

If Gradle/Loom locks are held by VS Code Java/Gradle daemons, stop the Java daemon processes or run:

```powershell
.\gradlew.bat --stop --console=plain
```

In this workspace, VS Code Java processes have repeatedly held locks in:

```text
C:\Users\Ultra cursos\.gradle\caches\fabric-loom
```

## Important Files Changed/Added

- `build.gradle`
  - Uses `fabric-loom`.
  - Includes Fabric Loader/API.
  - Includes Patchouli Fabric and JEI Fabric runtime.
  - Includes `src/generated/resources` as main resources.
  - Excludes NeoForge datagen Java classes from compilation.
- `gradle.properties`
  - Minecraft version is `1.21.1`.
  - `minecraft_version_range=1.21.1` is intentional. Fabric rejected `[1.21,1.21.1]`.
- `src/main/resources/fabric.mod.json`
  - Fabric mod metadata and entrypoints.
- `src/main/resources/psi.mixins.json`
  - Client mixins `HumanoidArmorLayerMixin` and `ParticleEngineMixin` were removed from the active mixin list because their targets no longer exist in 1.21.1 and would likely crash at runtime.
- `src/main/java/vazkii/psi/common/Psi.java`
  - Now implements Fabric `ModInitializer`.
- `src/main/java/vazkii/psi/client/PsiClient.java`
  - Placeholder Fabric client entrypoint.
- `src/main/java/net/neoforged/...`
  - Many temporary NeoForge compatibility stubs exist only to make the old code compile. They are not real Fabric integrations.

## Known Runtime Problems

### 1. Creative Tab Still Does Not Appear

Symptoms:

- Psi items show in JEI.
- The Psi creative tab/page does not appear in the creative inventory UI.

Current implementation:

- `PsiCreativeTab.register()` registers a tab using `FabricItemGroup.builder()`.
- `Psi.onInitialize()` calls `PsiCreativeTab.register()` after item/block registration.

Likely next steps:

1. Verify at runtime that `BuiltInRegistries.CREATIVE_MODE_TAB` contains `psi:creative_tab`.
2. Add a log in `PsiCreativeTab.register()` before/after `Registry.register`.
3. Try registering entries via Fabric API:

```java
ItemGroupEvents.modifyEntriesEvent(PsiCreativeTab.PSI_CREATIVE_TAB).register(entries -> {
    entries.accept(ModItems.psidust.get());
});
```

4. Check if `FabricItemGroup.builder()` should use `displayName(...)` instead of `title(...)` in this mapped environment. The source jar is intermediary-named but examples use display name semantics.
5. Check creative tab pagination. The vanilla creative UI may hide extra custom tabs behind page arrows depending on row/index.

### 2. Interactive Blocks Do Not Work

Symptoms:

- Blocks exist, but interactive behavior is not working properly.
- Likely affected blocks:
  - CAD Assembler
  - Programmer

Important context:

- The NeoForge event bus and menu/screen registration are currently mostly stubs.
- `BlockCADAssembler.useWithoutItem(...)` calls vanilla `player.openMenu(container)`, but Fabric extended screen opening with block position data is not wired.
- `IMenuTypeExtension.create(...)` is a stub that passes `null` for the network buffer.
- `ClientProxy.registerMenuScreens(...)` is still NeoForge-style and is probably never called on Fabric.

Likely next steps:

1. Replace menu registration with Fabric screen handler registration.
   - Use Fabric API screen handler helpers, likely `ExtendedScreenHandlerType` or the 1.21.1 equivalent.
   - Wire `ContainerCADAssembler.fromNetwork(...)` with real block pos data.
2. Replace block `openMenu` usage with Fabric-compatible opening that sends the block pos.
3. Register client screens in the Fabric client entrypoint:
   - CAD Assembler screen
   - Programmer screen if applicable
4. Move relevant client setup out of NeoForge event subscribers and into `PsiClient.onInitializeClient()`.
5. Audit `BlockProgrammer` and `TileProgrammer` for old NeoForge packet/menu assumptions.

### 3. Capabilities Are Temporary Helpers

Current code replaced many direct NeoForge `getCapability` calls with helpers in `PsiAPI`:

- `PsiAPI.getItemCapability(...)`
- `PsiAPI.getEntityCapability(...)`

These return direct wrapper objects for known Psi items/entities. This is enough for compilation and some local behavior, but it is not a general Fabric component/capability solution.

Likely next steps:

1. Decide whether to use:
   - Fabric API lookup APIs,
   - Cardinal Components,
   - Data components only,
   - or local helper methods for Psi-only objects.
2. Replace the `net.neoforged.neoforge.capabilities.*` stubs with real Fabric patterns.
3. Revisit:
   - CAD data
   - Socketable tools/armor
   - Spell acceptors
   - Detonation handlers
   - Item handler/inventory wrappers

### 4. Networking Is Stubbed

`MessageRegister` is mostly not a real Fabric networking implementation yet.

Likely next steps:

1. Implement payload registration using Fabric networking:
   - `PayloadTypeRegistry.playC2S()`
   - `PayloadTypeRegistry.playS2C()`
   - `ServerPlayNetworking`
   - `ClientPlayNetworking`
2. Wire `sendToServer`, `sendToPlayer`, and tracking broadcasts.
3. Retest:
   - socket selection
   - spell sync
   - CAD/programmer interactions
   - additive motion packets

### 5. NeoForge Event Subscribers Do Not Fire

Many classes still use:

```java
@EventBusSubscriber
@SubscribeEvent
```

The local event bus stubs do not connect to Fabric. Anything depending on those callbacks is probably inert.

Likely next steps:

1. Search:

```powershell
rg "@SubscribeEvent|EventBusSubscriber" src/main/java/vazkii/psi
```

2. Port handlers to Fabric callbacks or direct init:
   - creative tab registration
   - screen registration
   - tick handlers
   - key bindings
   - entity attributes
   - color handlers
   - particles
   - shaders
   - recipes/conditions

### 6. Mixins Need Real Porting

Inactive mixins:

- `vazkii.psi.mixin.client.HumanoidArmorLayerMixin`
- `vazkii.psi.mixin.client.ParticleEngineMixin`

They compile but target old signatures. They were removed from `psi.mixins.json` active client list.

Next steps:

1. Update targets for Minecraft 1.21.1 official mappings.
2. Re-enable one at a time.
3. Test startup after each.

## Texture/Model Status

Known improvement:

- `src/generated/resources` is now included in `sourceSets.main.resources`, so generated block/item models are present in `build/resources/main`.
- After this change, the client log no longer showed the earlier `Unable to load model` lines for generated block items.

Still likely broken:

- Some CAD dynamic models may be simplified or missing due `ModelCAD` being temporarily reduced.
- Some custom armor/particle rendering is disabled because related mixins are inactive.

## Things That Were Simplified To Make It Compile

These are not final-quality Fabric ports:

- `ModelCAD` currently returns the missing model path instead of resolving CAD assembly models dynamically.
- `MessageSpamlessChat.deleteMessage(...)` is no-op because vanilla chat internals are private.
- `PieceSelectorTickTime.getMspt(...)` returns `50` instead of real server MSPT.
- `PieceOperatorBlockComparatorStrength` uses a simpler vanilla signal lookup.
- `CapabilityTriggerSensor` and `PlayerDataHandler` use in-memory maps instead of persistent Fabric components.
- `BlockCADAssembler.getAnalogOutputSignal(...)` reads local BE inventory instead of a world capability.
- Armor texture/color hooks are not properly Fabric-ported.

## Suggested Next Order Of Work

1. Fix creative tab visibility.
   - Confirm registry entry exists.
   - Use `ItemGroupEvents.modifyEntriesEvent`.
   - Log registration.
2. Fix screen/menu opening for CAD Assembler and Programmer.
   - Use Fabric screen handler APIs.
   - Register client screens in `PsiClient`.
3. Port networking.
   - Start with the packets needed by the two interactive blocks.
4. Port event subscribers to Fabric callbacks.
   - Keybinds, ticks, particles, color handlers, entity attributes.
5. Replace temporary capability stubs with a chosen Fabric strategy.
6. Re-enable/fix mixins one at a time.
7. Run a gameplay smoke test:
   - Creative tab visible.
   - Items have textures.
   - CAD assembler opens.
   - Programmer opens.
   - CAD item can be assembled.
   - Spell bullet accepts/saves spells.
   - Basic spell cast works client/server.

## Current Verification

Last known successful build:

```text
.\gradlew.bat build --console=plain
BUILD SUCCESSFUL
```

Last known successful client start:

```text
.\gradlew.bat runClient --console=plain
Loading Minecraft 1.21.1 with Fabric Loader 0.16.14
Loaded 1389 recipes
Loaded 1492 advancements
JEI started
```

Known visible runtime status:

- Psi items appear in JEI.
- Psi creative tab still does not appear.
- Interactive blocks still do not work.
