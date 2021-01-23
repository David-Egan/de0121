/**
 * Entry point for Tool Rental Application.
 */
public class Application{

    public static void main(String[] args) {
        ToolVendor toolVendor = ToolVendor.getInstance();
        toolVendor.run();
    }

}