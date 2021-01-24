import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Singleton class to...
 */
public class ToolVendor{

    private static String TOOLS_LISTING_PATH = "./src/main/resources/tools.csv";

    private static ToolVendor toolVendor;
    private List<Tool> availableTools;

    private ToolVendor() throws IOException{
        loadTools();
    }

    public static ToolVendor getInstance() throws IOException{
        if(toolVendor == null){
            toolVendor = new ToolVendor();
        }

        return toolVendor;
    }

    public List<Tool> getAvailableTools(){
        return availableTools;
    }

    private void loadTools() throws IOException{
        availableTools = readToolsFromFile();
    }

    private List<Tool> readToolsFromFile() throws IOException {
        List<Tool> loadedTools = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(TOOLS_LISTING_PATH));
        String line = null;

        while ((line = reader.readLine()) != null) {
            String[] toolValues = line.split(",");
            Tool newTool = new Tool(toolValues[0], toolValues[1], toolValues[2]);
            loadedTools.add(newTool);
        }

        return loadedTools;
    }

    public void run(){
        promptForRentalRequests();
    }


    private void promptForRentalRequests(){
        String toolCode = "";

        Scanner inputReader = new Scanner(System.in);  // Reading from System.in

        while(!toolCode.equalsIgnoreCase("exit")){
            System.out.print("Enter a Tool Code to make a rental ('exit to close the application'): ");
            toolCode = inputReader.next();
            // put some validation here potentially

            // find tool with tool codd
            Tool matchingTool = findToolWithToolCode(toolCode);
            if(matchingTool == null){
                System.out.println("No tool found with that Tool Code, please enter a valid Tool Code");
            } else{
                System.out.print("How many days is the customer renting the tool for: ");
                int rentalDays = inputReader.nextInt();

                System.out.print("What is the Discount percent (0-100): ");
                int discountPercent = inputReader.nextInt();

                System.out.print("What is the checkout date (mm-dd-YY): ");
                String checkoutDateStr = inputReader.next();
                DateFormat format = new SimpleDateFormat("MM-dd-YY", Locale.ENGLISH);
                try{
                    Date checkoutDate = format.parse(checkoutDateStr);
                    matchingTool.checkout(rentalDays, discountPercent, checkoutDate);
                } catch(ParseException e) {
                    System.out.println("Invalid checkout date. Use format MM-dd-YY ex. 12-25-21");
                }
            }

        }

        inputReader.close();
    }

    private Tool findToolWithToolCode(String toolCode){
        List<Tool> result = availableTools.stream()
                .filter(tool -> toolCode.equals(tool.getToolCode()))
                .collect(Collectors.toList());

        return (result.size() > 0) ? result.get(0) : null;
    }


}