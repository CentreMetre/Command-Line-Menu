package climenuhelper;

/**
 * A TRUE and FALSE enum for {@link climenuhelper.CLIMenu#CLIMenu(java.util.ArrayList, java.lang.String, climenuhelper.HasZeroStartOption, climenuhelper.PrintMenuNowOption...) CLIMenu constructor}. 
 * @author Martin McLaren
 */
public enum HasZeroStartOption {
    TRUE(true),
    FALSE(false);

    private final boolean value;

    HasZeroStartOption(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }
}