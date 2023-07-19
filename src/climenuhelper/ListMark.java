package climenuhelper;

/**
 *
 * @author Martin McLaren
 */
public class ListMark{
    /**
     * Converts the list mark format into a list mark for the menu.
     * @param keyIndex The key of the current item in
     * {@link climenuhelper.CLIMenu#keysAndValues keysAndValues}.
     * @param listMarkFormat The format wanted for the list mark in each line in the menu.
     * @return The full list mark as a string to prepend the line option in the menu.
     */
    public static String convertNumberedListMark(int keyIndex, String listMarkFormat)
    {
        int placeholderIndex = validateListMarkFormat(listMarkFormat);
        
        /**
         * New array with length of 3 created so the keyIndex
         * can fit in the middle of the split at index 1.
         */
        String[] listMarkFormatArray = new String[3]; 
        
        String[] listMarkFormatSplit = listMarkFormat.split("#");
        
        listMarkFormatArray[0] = listMarkFormatSplit[0];
        listMarkFormatArray[1] = Integer.toString(keyIndex);
        listMarkFormatArray[2] = listMarkFormatSplit[1];
        
        String listMark = String.join("", listMarkFormatArray);
        
        return listMark;
    }
    
    /**
     * Validates there is a placeholder character in the list mark format.
     * @param listMarkFormat The format the method user wants the list marker to have. Must include
     * the placeholder character '#'.
     * @return The current index of the iteration used for replacing it in {@link ListMark#convertNumberedListMark(int, java.lang.String) the converter}.
     */
    public static int validateListMarkFormat(String listMarkFormat)
    {
        int i = 0;
        //boolean doesPlaceholderExist = false;
        int placeholderCount = 0;
        
        for (char c : listMarkFormat.toCharArray())
        {
            if (c == '#')
            {
                //doesPlaceholderExist = true;
                placeholderCount++;
            }
            /**
             * This if is in the loop so the programme doesn't check all chars since more than 1
             * is enough to throw an error.
             */
//            if (placeholderCount > 1)
//            {
//                throw new IllegalArgumentException("More than one placeholder"
//                        + " characters ('#')."
//                        + " Expected 1, found " + placeholderCount + ".");
//            }
            i++;
        }
        
        
        
        if(placeholderCount == 1)
        {
            return i;
        }
        else
        {
            String error = String.format("Invalid amount of placeholder characters ('#'). "
                       + "Expected 1, found %d.", placeholderCount);
            throw new IllegalArgumentException(error);
        }
    }
}
