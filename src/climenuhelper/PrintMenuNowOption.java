package climenuhelper;

/**
 * Used for {@link climenuhelper.CLIMenu#doPrintMenuNow doPrintMenuNow}
 * @author Martin McLaren
 */
public enum PrintMenuNowOption {
    TRUE(true),
    FALSE(false);

    private final boolean value;

    PrintMenuNowOption(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }
}
