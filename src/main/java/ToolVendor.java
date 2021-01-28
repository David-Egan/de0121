import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Singleton class to handle checking out one or more tools, communicates with users.
 */
public class ToolVendor{

    private static final String TOOLS_LISTING_PATH = "/tools.csv";

    private static ToolVendor toolVendor;
    private List<Tool> availableTools;

    /**
     * Construct a ToolVendor object. This includes loading in the tools to be
     * available in the system.
     */
    private ToolVendor() throws IOException{
        loadTools();
    }

    /**
     * Get an instance of a ToolVendor ensuring more than one instance can exist at once.
     */
    public static ToolVendor getInstance() throws IOException{
        if(toolVendor == null){
            toolVendor = new ToolVendor();
        }

        return toolVendor;
    }

    /**
     * Run the ToolVendor. Prompt the user for a rental and process that request.
     */
    public void run(){
        promptForRentalRequests();
    }

    public List<Tool> getAvailableTools(){
        return availableTools;
    }

    /**
     * Load in tools from a file to set availableTools field.
     */
    private void loadTools() throws IOException{
        availableTools = readToolsFromFile();
    }

    /**
     * Read in Tools from a .csv file and compose them into a list of Tool instances.
     */
    private List<Tool> readToolsFromFile() throws IOException {
        List<Tool> loadedTools = new ArrayList<>();

        InputStream is = this.getClass().getResourceAsStream(TOOLS_LISTING_PATH);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = null;

        while ((line = reader.readLine()) != null) {
            String[] toolValues = line.split(",");
            Tool newTool = new Tool(toolValues[0], toolValues[1], toolValues[2]);
            loadedTools.add(newTool);
        }

        return loadedTools;
    }

    /**
     * Ask user for details on a rental and checkout that Tool.
     */
    private void promptForRentalRequests(){
        String toolCode = "";

        Scanner inputReader = new Scanner(System.in);  // Reading from System.in
        boolean isUserDone = false;

        do{
            toolCode = promptForToolCode(inputReader);

            // find tool with tool code
            Tool matchingTool = findToolWithToolCode(toolCode);
            if(matchingTool == null){
                System.out.println("No tool found with that Tool Code, please enter a valid Tool Code");
            } else{
                try{
                    int rentalDays = promptForRentalDays(inputReader);
                    int discountPercent = promptForDiscountRate(inputReader);
                    LocalDate checkoutDate = promptForCheckoutDate(inputReader);

                    matchingTool.checkout(rentalDays, discountPercent, checkoutDate);
                } catch(InputMismatchException ex){
                    System.out.printf("\nInvalid Input: '%s'\n", ex.getMessage());
                    System.out.println("Please try checking out the rental again...\n");
                    continue;
                } catch(Exception ex){
                    System.out.printf("\nAn exception occurred processing user input: '%s'\n", ex.getMessage());
                    System.out.println("Please try checking out the rental again...\n");
                    continue;
                }

                String userDoneInput = "";
                while(!userDoneInput.equalsIgnoreCase("n") && !userDoneInput.equalsIgnoreCase("y")){
                    System.out.print("Checkout another tool? (y/n): ");
                    userDoneInput = inputReader.next();
                }

                isUserDone = userDoneInput.equalsIgnoreCase("n");
                System.out.println();
            }

        }
        while(!isUserDone);

        inputReader.close();
    }

    /**
     * Ask user for tool code.
     */
    private String promptForToolCode(Scanner inputReader){
        System.out.print("Enter a Tool Code to make a rental: ");
        return inputReader.next();
    }

    /**
     * Ask user for valid rental days.
     */
    private int promptForRentalDays(Scanner inputReader) throws InputMismatchException{
        System.out.print("How many days is the customer renting the tool for: ");
        int rentalDays = inputReader.nextInt();
        if(!RentalAgreement.isRentalDaysValid(rentalDays)){
            throw new InputMismatchException("Rental Days must be 1 or greater");
        }

        return rentalDays;
    }

    /**
     * Ask user for valid discount rate.
     */
    private int promptForDiscountRate(Scanner inputReader) throws InputMismatchException{
        System.out.print("What is the Discount percent (0-100): ");
        int discountPercent = inputReader.nextInt();
        if(!RentalAgreement.isRentalDiscountPercentValid(discountPercent)){
            throw new InputMismatchException("Discount percent must be between 0 and 100");
        }

        return discountPercent;
    }

    /**
     * Ask user for valid checkout date.
     */
    private LocalDate promptForCheckoutDate(Scanner inputReader) throws DateTimeParseException {
        System.out.print("What is the checkout date (mm-dd-YY): ");
        String checkoutDateStr = inputReader.next();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yy");
        formatter = formatter.withLocale( Locale.ENGLISH );

        return LocalDate.parse(checkoutDateStr, formatter);
    }

    /**
     * Look for tool code within Tools loaded into ToolVendor. Return the matching tool.
     */
    private Tool findToolWithToolCode(String toolCode){
        if(availableTools == null || availableTools.size() == 0) return null;

        List<Tool> result = availableTools.stream()
                .filter(tool -> toolCode.equals(tool.getToolCode()))
                .collect(Collectors.toList());

        return (result.size() > 0) ? result.get(0) : null;
    }

}