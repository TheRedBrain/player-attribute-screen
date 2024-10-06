# Player Attribute Screen

Adds a button to the player inventory screen, which toggles an additional info panel, showing a configurable list of attribute values.

This mod is optional on both the client and the server side.

When this mod is installed on the server, it can be disabled for all connected players.

The server also controls which configuration for the attribute screen is used:
- the server side configuration -> all players have the same attribute screen and can't change that
- the client side configuration -> each player can individually customize the attribute screen

Clients that don't have the mod installed, can connect like normal and are not affected.

Clients that have the mod installed can connect to all servers, whether the mod is installed on the server or not.

### Attribute Screen Configuration

The attribute screen displays a list of text strings. Each text string is defined in the "attribute_screen_configuration" in the server/client config file.

The primary use of the attribute screen is to display attribute values.

But it can also display for example the relation between two attribute values.

Or a attribute value is only shown, when there is a difference between the current attribute value and its base value.

All this can be configured in the config files.

To tell the game what exactly should be displayed, each string in the "attribute_screen_configuration" list must conform to one of these formats:

- EMPTY_LINE
- STRING:translation_key
- ATTRIBUTE_VALUE:attribute_id_namespace:attribute_id_path
- ATTRIBUTE_VALUE_DIFFERENCE_TO_BASE:attribute_id_namespace:attribute_id_path
- ATTRIBUTE_VALUE_PERCENT:attribute_id_namespace:attribute_id_path
- ATTRIBUTE_VALUE_DIFFERENCE_TO_BASE_PERCENT:attribute_id_namespace:attribute_id_path
- CUSTOM_ATTRIBUTE_VALUE:translation_key:attribute_id_namespace:attribute_id_path
- CUSTOM_ATTRIBUTE_VALUE_DIFFERENCE_TO_BASE:translation_key:attribute_id_namespace:attribute_id_path
- CUSTOM_ATTRIBUTE_VALUE_PERCENT:translation_key:attribute_id_namespace:attribute_id_path
- CUSTOM_ATTRIBUTE_VALUE_DIFFERENCE_TO_BASE_PERCENT:translation_key:attribute_id_namespace:attribute_id_path
- STRING_REQUIRES_ATTRIBUTE:translation_key:attribute_id_namespace:attribute_id_path
- CUSTOM_ATTRIBUTE_VALUE_POST_STRING:translation_key:attribute_id_namespace:attribute_id_path:translation_key
- CUSTOM_ATTRIBUTE_VALUE_DIFFERENCE_TO_BASE_POST_STRING:translation_key:attribute_id_namespace:attribute_id_path:translation_key
- ATTRIBUTE_RELATION:translation_key:attribute_id_namespace:attribute_id_path:attribute_id_namespace:attribute_id_path

**Strings with other formats are ignored.**

The strings will be split at the positions marked by the colons (":").

**Two colons should always be separated!**

The uppercase string before the first colon determines how the line is displayed.
Every string after the first colon is a placeholder.

> "translation_key" means that the game tries to find a translation key for that string. Those can be added by [resource packs](https://minecraft.wiki/w/Resource_pack#Language)
> 
> If no translation is found, the string is displayed as is.

> A "_PERCENT" at the end of the first sub-string means that the value is divided by 100 and appended with a "%".

> A "ATTRIBUTE_VALUE_" means that the attribute name and the value are displayed.
>
> A "CUSTOM_ATTRIBUTE_VALUE_" means that a custom string is displayed instead of the attribute name.
