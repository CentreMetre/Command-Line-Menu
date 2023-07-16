package climenuhelper;

/**
 * Used for {@link climenuhelper.CLIMenu#hasZeroStart hasZeroStart}
 * so a switch statement can be used
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