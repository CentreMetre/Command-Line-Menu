package climenuhelper;

import java.util.ArrayList;

/**
 *
 * @author Martin McLaren
 */
public class Test
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        //String[] options = {"Start", "Settings", "Exit"};
        
        ArrayList<String> options = new ArrayList<>();
        
        options.add("Start");
        options.add("Settings");
        options.add("Exit");
        
        String listMarkerFormat = "[#]";
        
        CLIMenu mainMenu = new CLIMenu(options, listMarkerFormat, HasZeroStartOption.TRUE, PrintMenuNowOption.TRUE);
        
        //mainMenu.printMenu();
    }
    
}
