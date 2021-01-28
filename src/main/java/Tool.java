import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a Tool that can be checked out. Tools include information on their pricing
 * and how customers would be charged for their rental.
 */
public class Tool{

    /* future improvements could include moving these to a properties file */
    // Ladder Tool charge details
    private static final BigDecimal LADDER_DAILY_CHARGE = BigDecimal.valueOf(1.99);
    private static final boolean LADDER_HAS_WEEKDAY_CHARGE = true;
    private static final boolean LADDER_HAS_WEEKEND_CHARGE = true;
    private static final boolean LADDER_HAS_HOLIDAY_CHARGE = false;

    // Chainsaw Tool charge details
    private static final BigDecimal CHAINSAW_DAILY_CHARGE = BigDecimal.valueOf(1.49);
    private static final boolean CHAINSAW_HAS_WEEKDAY_CHARGE = true;
    private static final boolean CHAINSAW_HAS_WEEKEND_CHARGE = false;
    private static final boolean CHAINSAW_HAS_HOLIDAY_CHARGE = true;

    // Jackhammer Tool charge details
    private static final BigDecimal JACKHAMMER_DAILY_CHARGE = BigDecimal.valueOf(2.99);
    private static final boolean JACKHAMMER_HAS_WEEKDAY_CHARGE = true;
    private static final boolean JACKHAMMER_HAS_WEEKEND_CHARGE = false;
    private static final boolean JACKHAMMER_HAS_HOLIDAY_CHARGE = false;

    /**
     * The types of tools a Tool can be.
     */
    enum ToolType{
        Ladder,
        Chainsaw,
        Jackhammer
    }

    private ToolType toolType;
    private final String brand;
    private final String toolCode;
    private BigDecimal dailyRentalCharge;
    private boolean hasWeekdayCharge;
    private boolean hasWeekendCharge;
    private boolean hasHolidayCharge;

    /**
     * Construct a new Tool with many of its properties based on its ToolType.
     */
    public Tool(String toolType, String brand, String toolCode){
        this.brand = brand;
        this.toolCode = toolCode;

        setToolSpecificFields(toolType);
    }

    /**
     * Checkout a tool which will create a RentalAgreement based on the Tool and its rental details.
     */
    public RentalAgreement checkout(int rentalDays, int discountPercent, LocalDate checkoutDate){
        if(!RentalAgreement.isRentalDaysValid(rentalDays)){
            throw new IllegalArgumentException("Rental Days must be 1 or greater");
        }
        if(!RentalAgreement.isRentalDiscountPercentValid(discountPercent)){
            throw new IllegalArgumentException("Discount percent must be between 0 and 100");
        }

        RentalAgreement rentalAgreement = new RentalAgreement(this, rentalDays, discountPercent, checkoutDate);
        rentalAgreement.prettyPrintAgreement();

        /* we're not using the returned object for anything right now,
        but it helps us test and would likely be useful to return the agreement in the future */
        return rentalAgreement;
    }

    public String getToolCode() {
        return toolCode;
    }

    public String getBrand(){
        return brand;
    }

    public ToolType getToolType(){
        return toolType;
    }

    public BigDecimal getDailyRentalCharge(){
        return dailyRentalCharge;
    }

    public boolean getHasWeekdayCharge() {
        return hasWeekdayCharge;
    }

    public boolean getHasWeekendCharge() {
        return hasWeekendCharge;
    }

    public boolean getHasHolidayCharge() {
        return hasHolidayCharge;
    }

    /**
     * Get a string representation of the Tool.
     * @return
     */
    @Override
    public String toString(){
        return String.format("Tool Type: '%s' Brand: '%s'  Tool Code: '%s'   ", toolType, brand, toolCode);
    }

    /**
     * Compare the current Tool with another Tool based on the properties of the Tools.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tool tool = (Tool) o;
        return toolType == tool.toolType && Objects.equals(brand, tool.brand) && Objects.equals(toolCode, tool.toolCode);
    }

    /**
     * Set fields for a Tool based on a tool type.
     */
    private void setToolSpecificFields(String toolType){
        switch(toolType){
            case "Ladder":
                this.toolType = ToolType.Ladder;
                this.hasWeekdayCharge = LADDER_HAS_WEEKDAY_CHARGE;
                this.hasWeekendCharge = LADDER_HAS_WEEKEND_CHARGE;
                this.hasHolidayCharge = LADDER_HAS_HOLIDAY_CHARGE;
                this.dailyRentalCharge = LADDER_DAILY_CHARGE;
                break;
            case "Chainsaw":
                this.toolType = ToolType.Chainsaw;
                this.hasWeekdayCharge = CHAINSAW_HAS_WEEKDAY_CHARGE;
                this.hasWeekendCharge = CHAINSAW_HAS_WEEKEND_CHARGE;
                this.hasHolidayCharge = CHAINSAW_HAS_HOLIDAY_CHARGE;
                this.dailyRentalCharge = CHAINSAW_DAILY_CHARGE;
                break;
            case "Jackhammer":
                this.toolType = ToolType.Jackhammer;
                this.hasWeekdayCharge = JACKHAMMER_HAS_WEEKDAY_CHARGE;
                this.hasWeekendCharge = JACKHAMMER_HAS_WEEKEND_CHARGE;
                this.hasHolidayCharge = JACKHAMMER_HAS_HOLIDAY_CHARGE;
                this.dailyRentalCharge = JACKHAMMER_DAILY_CHARGE;
                break;
        }

    }

}