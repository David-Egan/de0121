/**
 * Singleton class to...
 */
public class ToolVendor{

    private static ToolVendor toolVendor;

    private ToolVendor(){
        System.out.println("DEBUG: Constructing Tool Vendor");
        loadTools();
    }

    public static ToolVendor getInstance(){
        if(toolVendor == null){
            toolVendor = new ToolVendor();
        }

        return toolVendor;
    }

    private void loadTools(){

    }

    public void run(){
        promptForRentalRequests();
    }


    public void promptForRentalRequests(){
        while(true){
            System.out.println("Enter a tool code to make a rental...");
            System.out.println("Tool Code: ");
        }
    }


}