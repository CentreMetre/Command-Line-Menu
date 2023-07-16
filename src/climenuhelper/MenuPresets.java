package climenuhelper;

/**
 * An enum for menu presets that can be used by the user of the class.
 * Can be used to use a specific format of menu without having to manually make it.
 * @author Martin McLaren
 */
public enum MenuPresets {
    /**
     * Contains the options:
     * Yes
     * No
     * Useful for confirmation menus.
     */
    YESNO,
    /**
     * Contains the options:
     * Start
     * Settings
     * Exit
     */
    STANDARDMENU;
    
    /**
     * Retrieves the enum constant representing the output preset.
     * @return The output preset enum constant.
     */
    public MenuPresets getValue() {
        return this;
    }
}
