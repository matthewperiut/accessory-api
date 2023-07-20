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
    modImplementation 'com.github.matthewperiut:accessory-api:0.3.0'
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

    @Override
    public AccessoryType getAccessoryTypes(ItemInstance item)
    {
        return new AccessoryTypes[] { AccessoryTypes.ring };
        // return new AccessoryType[0]; // (if you don't want regular slots to apply)
        // return new AccessoryTypes[] { AccessoryTypes.gloves, AccessoryTypes.ring }; // define multiple slots it can go into
    }

    // other methods you can use, use @Override
    // void tickWhileWorn(PlayerBase player, ItemInstance accessory)
    // void onAccessoryAdded(PlayerBase player, ItemInstance accessory)
    // void onAccessoryRemoved(PlayerBase player, ItemInstance accessory)
    
    // To define custom trinket slots that your item can go into:
    @Override
    public String[] getCustomAccessoryTypes(ItemInstance item)
    {
        return new String[]{ "custom_accessory_type" };
    }
}
```

You can choose different types of default accessory types:  
AccessoryTypes.pendant  
AccessoryTypes.cape  
AccessoryTypes.shield  
AccessoryTypes.glove  
AccessoryTypes.ring  
AccessoryTypes.misc  

## Accessory Functions Explained

This is called when a default accessory slot is clicked on with an item, the item is passed to the function.  
This provides the slot information to see if it is compatible with it.  
`AccessoryType getAccessoryTypes(ItemInstance item)`  

This is called when a custom accessory slot is clicked on with an item, the item is passed to the function.  
This provides the slot type strings to see if it is compatible with it.  
`AccessoryType getCustomAccessoryTypes(ItemInstance item)`  

This is called in the tick function of the associated player, passing the accessory that is being ticked on which player.  
`void tickWhileWorn(PlayerBase player, ItemInstance accessory)`  

This is called when the inventory relating to the accessory has an item added/removed.  
The function provides what player added/removed an accessory as well as the item that was added/removed.  
`void onAccessoryAdded(PlayerBase player, ItemInstance accessory)`  
`void onAccessoryRemoved(PlayerBase player, ItemInstance accessory)`  

This is only called on the client when the player is being rendered.  
It is important to note you do not see yourself in first person,  
so it isn't called while in first person and worn.  
The java doc contains information on data inside playerRenderData.  
`void renderWhileWorn(PlayerBase player, PlayerRenderer renderer, ItemInstance accessory, Biped model, final Object[] playerRenderData)`  

## Helper functions
**__Cape__**  
__Description__:  
Capes are the one accessory that receives different variables than other accessories, therefore  
this function can only be called in a cape accessory.  
__Parameters:__ 
You define a player to render on, a standard asset location e.g. "assets/testmod/textures/capes/cape.png" with a standard cape png,  
the model provided in renderWhileWorn, and the objects provided in renderWhileWorn.  
Functionality:  
Replicates the regular Minecraft cape.  
`AccessoryRenderHelper.Cape(PlayerBase player, String texture, Biped model, Object[] objects)`  

**__Glove/Arm Overlay__**  
__Description__:  
These functions handle both rendering first person and rendering third person, given a standard minecraft skin png for a texture.  
It only takes the location of the arms of the minecraft skin texture and renders it on a layer slightly higher than the player's model.  
__Parameters:__ 
These functions largely share parameters, related player, standard minecraft skin texture, colour multiplier, onTickRender provided model and objects.  
`AccessoryRenderHelper.ArmOverlay(PlayerBase player, String texture, int colour, Biped model, Object[] objects)`  
You want to call these functions onAccessoryAdded and onAccessoryRemove by enabling and disabling the overlay respectively.  
`AccessoryRenderHelper.enableFirstPersonArmOverlayRender(String texture, int colour)`  
`AccessoryRenderHelper.disableFirstPersonArmOverlay();`  
Keep the texture and colour consistent, unless you're going for a special effect.  

**__Torso Overlay__**  
__Description__  
Similarly to Arm Overlay, this accepts a standard minecraft skin, and renders around the torso the skin section for the torso slightly above the player's model  
__Parameters:__ 
Related player, Minecraft skin asset (e.g. "assets/testmod/textures/armour/wings_skin.png"), colour multiplier, onTickRender provided model and objects.  
`AccessoryRenderHelper.TorsoOverlay(PlayerBase player, String texture, int colour, Biped model, Object[] objects);`  

**Rendering Note**   
The biped provided by the API inside renderWhileWorn is slightly bigger than the player, and copies players movements/positions/rotations  

## Custom Accessories
Within STAPI's/fabric's mod initializer call **__CustomAccessoryRegister.add()__**;  

This is the most complicated .add() call, but there's simpler permutations for no texture and no preferred location.  
__Description__  
(Required Slot Info)  
Adds a slot into the player's inventory with a string for the slot identifier (SlotName), and a string to define what items go into it (AccessoryType).  
(Texture Info)  
Provide an asset path to a texture (256x256), then define texPosX and texPosY, which will grab the image from texPosX, texPosY to texPosX + 16, texPosY + 16,  
The 16x16 image should have a transparent background, with an outline of the Accessory Slot's Background Image (preferably using rgb(95, 95, 95) matching vanilla, unless you want a special effect)  
Note that the texture can be shared, for example, you could have a custom inventory like "assets/fastfurnaces/textures/gui/iron_furnace.png", and store the slot image in the bottom right,  
as long as you provide the texture asset string location, and appropriate texture coordinates, that is fine.  
(Preferred Slot Info)  
Use these enum definitions to define preferred slot:  
`enum CustomAccessoryRegister.HorizontalPositions {Left, Middle, Right}`  
`enum CustomAccessoryRegister.VerticalPositions {Helmet, Chestplate, Leggings, Boots}`  
They are the last two parameters when used, horizontalPosition, verticalPosition, and are self-explanatory.  
There's a 3x4 grid to the right of the armour and default accessories. you can stay on the column with the Vertical Position armour type you choose.  
Additionally, you can choose your horizontal position, note that if you just want to fill the first available slot closest to the left, choose 'Left' and it will go as far left as possible.  
Parameters  
SlotName should be unique and helps identify a slot,  
AccessoryType can be shared with multiple slots defining what items can go into the slot by an accessory type string,  
Texture can be any 256x256 png resource location,  
int texPosX and int texPosY define the top left pixel position of a 16x16 texture (with transparent bg, and outline of accessory type in preferably rgb(95,95,95),  
horizontalPosition and verticalPosition define where you want your slot to be, and it will be provided (given another mod doesn't snag it first).  
`CustomAccessoryRegister.add(String SlotName, String AccessoryType, String Texture, int texPosX, int texPosY, HorizontalPositions h, VerticalPositions v)`

The next functions are available alternatives that do the same functionality, but with less information. The lack of information will cause default behavior which will be explained:  

`CustomAccessoryRegister.add(String SlotName, String AccessoryType, HorizontalPositions h, VerticalPositions v)`  
Not providing a texture causes the background of the slot to be blank, similarly to a regular inventory slot.  

`CustomAccessoryRegister.add(String SlotName, String AccessoryType, String Texture, int texPosX, int texPosY)`  
Not providing a preferred position will cause the slot to fill the first available slot, this will fill a complete column before moving onto the next row.  

`CustomAccessoryRegister.add(String SlotName, String AccessoryType)`  
Aforementioned behavior combined.  
