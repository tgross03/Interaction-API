![Maven Central Version](https://img.shields.io/maven-central/v/dev.edgetom/interaction-api?style=flat&logo=sonatype&color=%2300bf33&link=%3Curl%3Ehttps%3A%2F%2Fcentral.sonatype.com%2Fartifact%2Fdev.edgetom%2Finteraction-api%3C%2Furl%3E)
![example workflow](https://github.com/tgross03/Interaction-API/actions/workflows/maven.yml/badge.svg?event=push)

# Interaction-API

A Spigot API for managing interactions with items.

> [!WARNING]  
> At the moment the API is designed and tested for Spigot version `1.19.4`!
> Other versions will be usable in the future.

## Include the API in your project

### Maven (Recommended)

You can include the package as a dependency in Maven.
For that you have to include the following entry in your `pom.xml` in the surrounding
`<dependencies> ... </dependencies>`:

```xml
<dependency>
    <groupId>dev.edgetom</groupId>
    <artifactId>interaction-api</artifactId>
    <version>VERSION</version>
</dependency>
```
Replace the placeholder `VERSION` with the version you want to use. You can find the all versions of
the API in the [Maven Central Repository](https://central.sonatype.com/artifact/dev.edgetom/interaction-api).

### Gradle

Alternatively you can include the API via Gradle:

```Gradle
implementation group: 'dev.edgetom', name: 'interaction-api', version: 'VERSION'
```

Replace the placeholder `VERSION` with the version you want to use. You can find the all versions of
the API in the [Maven Central Repository](https://central.sonatype.com/artifact/dev.edgetom/interaction-api).

## Basic usage example

1. You will need to create an `InteractionManager` which will handle the calls to the Interactions.
   There should only be one `InteractionManager` for your project.
   Initialize it like this:
   
   ```java
   InteractionManager interactionManager = new InteractionManager(plugin);
   ```

2. Create your own Interaction by inheriting from the class `InteractionExecutor `.
   For example like this:

   ```java
   // Create a new class with an arbitrary name extending the class InteractionExecutor
   public class TestInteraction extends InteractionExecutor {

       // Create an constructor for the interaction fitting the use case
       public TestInteraction(@NotNull InteractionManager interactionManager, String interactionKey, boolean placeable, ActionClass actionClass) {
           super(interactionManager, interactionKey, placeable, actionClass);
       }

       // Override the execute method, which will be called if a player interacts with an item referencing this interaction
       @Override
       public void execute(PlayerInteractEvent event, Player player) {
   
           // Send a message to the player who caused the interaction
           event.getPlayer().sendMessage("Click!");
       }
   }
   ```

3. Initialize the `InteractionExecutor`, create an `ItemStack` and "connect" it to the `InteractionExecutor`.

    The following example assumes the usage of an ItemBuilder

    ```java
    TestInteraction testInteraction = new TestInteraction(plugin.getInteractionManager(), "test_interaction", false, ActionClass.RIGHT_CLICK);
    
    // Creating a stick
    ItemStack itemStack = new ItemStack(Material.STICK);
    
    // Adding the InteractionExecutor key to the ItemStack. This will make sure that the ItemStack is being assigned to the executor if someone interacts with it
    itemStack = testInteraction.addToItem(itemStack);
    
    ```

    Voilà, the item you just created will now trigger the `TestInteraction#execute()` method and will inform the user of the item of the fact he just clicked with your item.

> [!NOTE]  
> Your `ItemStack` has to have a valid `ItemMeta` in order to be connected to the `InteractionExecutor`.
> It is recommended to use a kind of ItemBuilder (like for example [this one](https://github.com/Acquized/ItemBuilder)) to ensure the
> existence of an `ItemMeta`.
