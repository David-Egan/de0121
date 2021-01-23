import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
        String userInput = "";

        Scanner inputReader = new Scanner(System.in);  // Reading from System.in

        while(!userInput.equalsIgnoreCase("exit")){
            System.out.print("Enter a Tool Code to make a rental ('exit to close the application'): ");
            userInput = inputReader.next();
            // put some validation here potentially
            System.out.println(userInput);

            // find tool with tool codd
            Tool matchingTool = findToolWithToolCode(userInput);
            if(matchingTool == null){
                System.out.println("No tool found with that Tool Code, please enter a valid Tool Code");
            } else{
                System.out.println(matchingTool);
                // handle checkout
            }
        }

        inputReader.close();
    }

    private Tool findToolWithToolCode(String toolCode){
        List<Tool> result = availableTools.stream()
                .filter(tool -> toolCode.equals(tool.getToolCode()))
                .collect(Collectors.toList());

        if (result.size() == 0){
            return null;
        } else{
            return result.get(0);
        }

    }


}