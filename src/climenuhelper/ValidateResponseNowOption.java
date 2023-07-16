package climenuhelper;

/**
 *
 * @author Martin McLaren
 */
public enum ValidateResponseNowOption
{
    TRUE(true),
    FALSE(false);
    
    private final boolean value; 
    
    ValidateResponseNowOption(boolean value)
    {
        this.value = value;
    }
    
    public boolean getValue() {
        return value;
    }
}
