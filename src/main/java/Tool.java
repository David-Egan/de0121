import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.Objects;

public class Tool{

    enum ToolType{
        Ladder,
        Chainsaw,
        Jackhammer
    }

    private ToolType toolType;
    private String brand;
    private String toolCode;
    private BigDecimal dailyRentalCharge;

    private boolean hasWeekdayCharge;
    private boolean hasWeekendCharge;
    private boolean hasHolidayCharge;

    private Currency dailyCharge;

    public Tool(String toolType, String brand, String toolCode){
        /* this is messy, we can do better */
        switch(toolType){
            case "Ladder":
                this.toolType = ToolType.Ladder;
                this.hasWeekendCharge = true;
                this.hasHolidayCharge = false;
                this.dailyRentalCharge = BigDecimal.valueOf(1.99);
            case "Chainsaw":
                this.toolType = ToolType.Chainsaw;
                this.hasWeekendCharge = false;
                this.hasHolidayCharge = true;
                this.dailyRentalCharge = BigDecimal.valueOf(1.49);
            case "Jackhammer":
                this.toolType = ToolType.Jackhammer;
                this.hasWeekendCharge = false;
                this.hasHolidayCharge = false;
                this.dailyRentalCharge = BigDecimal.valueOf(2.99);
        }

        this.hasWeekdayCharge = true;

        this.brand = brand;
        this.toolCode = toolCode;
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

    public void checkout(int rentalDays, int discountPercent, Date checkoutDate){
        RentalAgreement rentalAgreement = new RentalAgreement(this, rentalDays, discountPercent, checkoutDate);
        rentalAgreement.prettyPrintAgreement();
    }

    @Override
    public String toString(){
        return this.brand + "  " + this.toolCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tool tool = (Tool) o;
        return toolType == tool.toolType && Objects.equals(brand, tool.brand) && Objects.equals(toolCode, tool.toolCode);
    }

}