import java.io.IOException;

/**
 * Entry point for Tool Rental Application.
 */
public class Application{

    /**
     * Run ToolVendor to start the application
     */
    public static void main(String[] args) {
        try{
            ToolVendor toolVendor = ToolVendor.getInstance();
            toolVendor.run();
        } catch(IOException ex){
            System.out.println("Error reading in tools from 'tools.csv'");
            System.out.println("Closing Tool Rental Application...");
        }

    }

}