import java.io.IOException;
import java.util.List;

/**
 * Entry point for Tool Rental Application.
 */
public class Application{

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