package climenuhelper;

import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;

/**
 * A class that aides in creating a menu and accepting input in the menu.
 * @author Martin McLaren
 */
public class CLIMenu {
    /**
     * Used to start the menu at zero if the user so wishes and if there is no custom min and max.
     */
    //private HasZeroStartOption hasZeroStart;
            
    /**
     * The amount of options that the user can choose from.
     */
    private int optionsAmount;
    
    /**
     * The starting option number.
     */
    //private int optionsIntStart;
    
    /**
     * 
     */
    //private int optionsIntEnd; //Not needed since optionsAmount and step is the limit. 
    
    /**
     * The amount of numbers each option increases by.
     */
    //private int optionsStep;
    
    /**
     * The format of the list marker wanted by the user e.g. [#]. Uses # as placeholder.
     */
    private String listMarkerFormat;
    
    /**
     * Holds the keys.
     */
    private ArrayList<Integer> optionKeys = new ArrayList<>();
    
    /**
     * Holds the values.
     */
    private ArrayList<String> optionValues = new ArrayList<>();
    
    /**
     * Holds the prefix (keys) numbers and the options (values) lines.
     */
    final private HashMap<Integer, String> keysAndValues = new HashMap<>();
    
    /**
     * Used to determine whether to print the menu when the object is instantiated or not yet.
     */
    private PrintMenuNowOption doPrintMenuNow;
    
    /**
     * The basic CLI menu. Has no custom numbers.
     * @param values The option strings, such as start, settings, exit etc.
     * @param listMarkerFormat The marker for the number to be
     * next to or surrounded by, e.g. [1].
     * @param hasZeroStart If the list should start with 0 or 1. True for 0, False for 1.
     * @param doPrintMenuNow An optional argument on if the menu should be
     * printed straight after it is created. Most likely to only be used with TRUE.
     */
    public CLIMenu(ArrayList<String> values, String listMarkerFormat, 
            HasZeroStartOption hasZeroStart, PrintMenuNowOption... doPrintMenuNow)
    {   
        //validateKeysAndValuesSizes(values);
        this.doPrintMenuNow = validateOptionalPrintNowSize(doPrintMenuNow);
        
        setOptionsAmount(values);
        
        /**
         * This needs to be before populateKeysAndValues() since it uses optionKeys.size()
         */
        this.optionKeys = defaultRangeCreator(hasZeroStart, optionsAmount);
        this.optionValues = values;
        
        populateKeysAndValues();
        
        this.listMarkerFormat = listMarkerFormat;
        
        switch (this.doPrintMenuNow)
        {
            case TRUE:
                printMenu();
                break;
            case FALSE:
                break;
            default:
                throw new AssertionError();
        }
    }
    
    /**
     * A CLI menu that lets you choose a custom starting value and the step between values.
     * @param values The option strings, such as start, settings, exit etc.
     * @param listMarkerFormat The list marker format at the start of the line.
     * Use # to represent the number e.g. [#], #) 
     * @param startingKeyNum The number to start the list at.
     * @param step The amount to increase between list items. Cannot be 0.
     * @param doPrintMenuNow An optional argument on if the menu should be printed straight after
     * it is created. Most likely to only be used with TRUE.
     */
    public CLIMenu(ArrayList<String> values, String listMarkerFormat,
            int startingKeyNum, int step, PrintMenuNowOption... doPrintMenuNow)
    {
        //validateKeysAndValuesSizes(values);
        validateStepSize(step);
        
        populateKeysAndValues();
        
        this.doPrintMenuNow = validateOptionalPrintNowSize(doPrintMenuNow);
        
        this.listMarkerFormat = listMarkerFormat;
        
        switch (this.doPrintMenuNow)
        {
            case TRUE:
                printMenu();
                break;
            case FALSE:
                break;
            default:
                throw new AssertionError(); //Shouldnt be possible
        }
    }
    
    
//    public CLIMenu(MenuPresets preset, String listMarker)
//    {
//        
//    }
    
    //Getters & Setters

    /**
     * Sets {@link CLIMenu#optionsAmount} via the array list of values from the constructor.
     * @param values Size of this used to set options amount.
     */
    public final void setOptionsAmount(ArrayList<String> values)
    {
        this.optionsAmount = values.size();
    }
    
    //Validation
    
    /**
     * Validates that the keys and values sizes are the same.
     * @param optionsAmount The amount of options.
     * @param values The amount of string values.
     * 
     * @deprecated this method is not needed since the size of the keys or
     * values don't need to be validated since they are sterile in other ways.
     */
    @Deprecated 
    private void validateKeysAndValuesSizes(ArrayList<String> values)
    {
        if (this.optionsAmount != values.size())
        {
            throw new IllegalArgumentException(String.format("Amount of options and list of"
                    + "options are not an equal size. There are %d keys and %d options.",
                    optionsAmount, values.size()));
        }
    }
    
    /**
     * Validate the step size to check it isn't 0
     * @param step The amount to increase between list items.
     * @throws IllegalArgumentException If step = 0.
     */
    private void validateStepSize(int step)
    {
        if (step == 0)
        {
            throw new IllegalArgumentException("Step cannot be 0.");
        }
    }
    
    /**
     * Checks to see if the doPrintMenuNow in the constructor = 0 or > 1. If 0 it catches the
     * IndexOutOfBoundsException that would be thrown. If greater than 1 it will show a warning
     * about there being too many parameter inputs and only the first one will be used.
     * @param doPrintMenuNow
     * @return True if there is an input.
     */
    private PrintMenuNowOption validateOptionalPrintNowSize(PrintMenuNowOption[] doPrintMenuNow)
    {
        if (doPrintMenuNow.length > 1)
        {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            Logger logger = Logger.getLogger(CLIMenu.class.getName());
            StackTraceElement constructorLine = stackTrace[2];
            StackTraceElement constructorLineCalled = stackTrace[3];
            
            String warning = "You have passed multiple arguments for doPrintMenuNow in constructor"
            + constructorLine + ".\nThis was called on " + constructorLineCalled +
                    ". Only the first argument for doPrintMenuNow will be used.";
            
            logger.log(Level.WARNING, warning);
            switch (doPrintMenuNow[0])
            {
                case TRUE:
                    return PrintMenuNowOption.TRUE;
                case FALSE:
                    return PrintMenuNowOption.FALSE;
                default:
                    throw new AssertionError();
            }
        }
        
        //try catch needed incase there isn't any option arguments.
        try
        {
            this.doPrintMenuNow = doPrintMenuNow[0];
        }
        catch (IndexOutOfBoundsException e)
        {System.out.println("No print menu options");}
        
        switch (doPrintMenuNow[0])
        {
            case TRUE:
                return PrintMenuNowOption.TRUE;
            case FALSE:
                return PrintMenuNowOption.FALSE;
            default:
                throw new AssertionError();
        }
    }
    
    /**
     * Fills {@link climenuhelper.CLIMenu#keysAndValues keysAndValues} with the keys and the values
     * constructed with.
     */
    private void populateKeysAndValues()
    {
        keysAndValues.clear(); //Clear incase the user wants to use the object again.
        
        for (int i = 0; i < optionKeys.size(); i++)
        {
            keysAndValues.put(optionKeys.get(i), optionValues.get(i));
        }
    }
    
    /**
     * Prints the menu with the keys and values provided to the constructor.
     */
    public final void printMenu()
    {
        keysAndValues.forEach((key, option) ->{
            
            String listMarker = ListMark.convertNumberedListMark(key, listMarkerFormat);
            String listOption = option;
            System.out.printf("%s %s\n", listMarker, listOption);
        });
    }
    
    //Range Creators
    /**
     * Creates an Integer type array to be used for the menu keys.
     * @param startingKeyNum The number to start the range from.
     * @param optionsAmount The amount of options the range will have.
     * @param step The gap between each option.
     * @return An Integer type array with all the keys created.
     */
    public ArrayList<Integer> rangeCreator(int startingKeyNum, int optionsAmount, int step)
    {
        ArrayList<Integer> numArray = new ArrayList<>();
        int currValue = startingKeyNum;
        
        for (int i = 0; i < optionsAmount; i++)
        {
            numArray.add(currValue);
            currValue = currValue + step;
        }
        
        return numArray;
    }
    
    /**
     * Creates a key array with a step of 1, starting with either 0 or 1.
     * @param hasZeroStart
     * @return The created array.
     */
    private ArrayList<Integer> defaultRangeCreator(HasZeroStartOption hasZeroStart,
            int optionsAmount)
    {
        int startingkKeyNum;
        switch (hasZeroStart)
        {
            case TRUE:
                startingkKeyNum = 0;
                break;
            case FALSE:
                startingkKeyNum = 1;
                break;
            default:
                //shouldnt be possible 
                throw new AssertionError("Error in switch statement. (How tf did this happen‽)");
        }
        
        ArrayList<Integer> keys = rangeCreator(startingkKeyNum, optionsAmount, 1);
        return keys;
    }
    
    /**
     * Validates input via parameter to check it equals and entry in
     * {@link CLIMenu#optionKeys optionKeys}.
     * @param userInput The input from the user when they want to select an item of the list. 
     * @return True if the input is valid. False if it isn't. Up to the dev user of the method to
     * decide what to do after return.
     */
    public boolean isInputEqualToListItem(int userInput)
    {
        if (checkUserInput(userInput))
        {
            return true;
        }
        
        System.out.printf("Input invalid, not found in list. Your input: %d.", userInput);
        return false;
    }
    
    /**
     * Validates input via parameter to check it equals and entry in
     * {@link CLIMenu#optionKeys optionKeys}. Can have custom error message. 
     * @param userInput The input from the user when they want to select an item of the list. 
     * @param errorMessage Custom error message to show the user if the input isnt valid.
     * @return True if the input is valid. False if it isn't. Up to the dev user of the method to
     * decide what to do after return.
     */
    public boolean isInputEqualToListItem(int userInput, String errorMessage)
    {
        if (checkUserInput(userInput))
        {
            return true;
        }
        
        System.out.println(errorMessage);
        return false;
    }
    
    /**
     * When called it asks for input via CLI while the input doesn't equal any entries in
     * {@link CLIMenu#optionKeys optionKeys}.
     * @return The int the user input that is valid.
     */
    public int askForInputLoop()
    {
        boolean isUserInputValid = false;
        boolean isFirst = true;
        
        String errorMessage;
        
        Scanner inputScanner = new Scanner(System.in);
        
        int userInput;
        
        do
        {
            isFirst = false;
            
            userInput = inputScanner.nextInt();
            
            if (!checkUserInput(userInput))
            {
                System.out.printf("Input invalid, not found in list. Your input: %d. \n",
                        userInput);
            }
            
        } while (!checkUserInput(userInput));
        
        return userInput;
    }
    
    /**
     * When called it asks for input via CLI while the input doesn't equal any entries in
     * {@link CLIMenu#optionKeys optionKeys}.
     * @param errorMessage Custom error message.
     * @return The int the user input that is valid.
     */
    public int askForInputLoop(String errorMessage)
    {
        
        Scanner inputScanner = new Scanner(System.in);
        
        int userInput;
        
        do
        {
            userInput = inputScanner.nextInt();
            
            if (!checkUserInput(userInput))
            {
                System.out.println(errorMessage);
            }
            
        } while (!checkUserInput(userInput));
        
        return userInput;
    }
    
    /**
     * Iterates through {@link CLIMenu#optionKeys optionKeys} to check if the input was valid.
     * @param userInput
     * @return 
     */
    private boolean checkUserInput(int userInput)
    {
        for (int num : optionKeys)
        {
            if (num == userInput)
            {
                return true;
            }
        }
        //else return false
        return false;
    }
}

//It's PayDay Fellas