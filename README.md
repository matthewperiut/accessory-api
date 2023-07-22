# Developer Usage

## Workspace

Add Accessory API to your `build.gradle`
```gradle
repositories {
    maven {
        name = "Jitpack"
        url "https://jitpack.io/"
    }
}

dependencies {
    modImplementation('com.github.matthewperiut:accessory-api:0.4.0') {
        transitive false
    }
}
```

## Recommendation

If you want to quickly see how to use this API, clone the repo (in an ide preferably) and navigate to src/test/java/com/matthewperiut/testmod.  
Also view API javadocs / documentation  

## Implementation

Implement "Accessory" inside your item class
(with STAPI, will work with any API/mixins)
```java
public class ExampleRing extends TemplateItemBase implements Accessory
{
    public Pendant(Identifier identifier)
    {
        super(identifier);
    }
    
    // To define trinket slots that your item can go into:
    @Override
    public String[] getAccessoryTypes(ItemInstance item)
    {
        return new String[]{ "ring" };
        // return new String[0]; // returns no slots
        // return new String[]{ "ring", "cape" }; // can be put in multiple types of slots
        // return new String[]{ "all" }; // can be in any accessory slot
    }

    // other methods you can use, use @Override
    // void tickWhileWorn(PlayerBase player, ItemInstance accessory)
    // void onAccessoryAdded(PlayerBase player, ItemInstance accessory)
    // void onAccessoryRemoved(PlayerBase player, ItemInstance accessory)
}
```

You can choose different types of default accessory types:  
"pendant"  
"cape"  
"shield"  
"ring"  
"gloves"  
"misc"  
"all"  
or use your own custom accessory type

## Accessory Functions Explained

This is called when an accessory slot is clicked on with an item, the item is passed to the function.  
This provides the slot information to see if it is compatible with it.  
`String[] getAccessoryTypes(ItemInstance item)`

This is called in the tick function of the associated player, passing the item that is being ticked on which player.  
`void tickWhileWorn(PlayerBase player, ItemInstance item)`  

This is called when the inventory relating to the accessory has an item added/removed.  
The function provides what player added/removed an accessory as well as the item that was added/removed.  
`void onAccessoryAdded(PlayerBase player, ItemInstance accessory)`  
`void onAccessoryRemoved(PlayerBase player, ItemInstance accessory)`  

Provide rendering functions with getRenderer:  
`public AccessoryRenderer getRenderer()`

## Creating Accessory Renderer
example:
```java
public class CustomRenderer implements AccessoryRenderer {
    void renderThirdPerson(PlayerBase player, PlayerRenderer renderer, ItemInstance itemInstance, double x, double y, double z, float h, float v)
    {
        // do entity rendering magic
    }

   void renderFirstPerson(PlayerBase player, PlayerRenderer renderer, ItemInstance itemInstance)
   {
       // do entity first person rendering magic
   }
}
```
Similar usage as EntityRenderers  

Built-in AccessoryRenderers:  
`CapeRenderer`  
`GloveRenderer`  
`NecklaceRenderer`  