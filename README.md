# Example Ring accessory
demonstrates how to use accessory api

# Developer Usage

Put the accessory-(version).jar inside of "(mod directory)/lib/"

And then to add Trinkets you add it as a dependency in your build.gradle
```gradle
dependencies {
    modImplementation files("lib/accessoryapi-0.1.0.jar")
}
```

Implement "Accessory" inside of your item class
(with STAPI, will work with any API/mixins)
```
public class ExampleRing extends TemplateItemBase implements Accessory
{
    public Pendant(Identifier identifier)
    {
        super(identifier);
    }

    @Override
    public Type getType()
    {
        return Type.ring;
    }

    // other methods required for Accessory
}
```

You can choose different types of slots:
Type.pendant
Type.cape
Type.shield
Type.glove
Type.ring
Type.misc

Methods to implement functionality for accessory:
```
    Type getType();
    void tickWhileWorn(PlayerBase player, ItemInstance item);
    void onAccessoryAdded(PlayerBase player, ItemInstance item);
    void onAccessoryRemoved(PlayerBase player, ItemInstance item);

    // Will only be called on client
    void renderWhileWorn(PlayerBase player, PlayerRenderer renderer, ItemInstance item, Biped related_model, Object[] additional_objects);
```
All the methods must exist, but you can leave them empty.

renderWhileWorn is complicated, see ![Aether STAPI](https://github.com/matthewperiut/The-Aether) for usage (until I make a tutorial)

