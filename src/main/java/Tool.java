import java.util.Currency;
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

    private boolean hasWeekdayCharge;
    private boolean hasWeekendCharge;
    private boolean hasHolidayCharge;

    private Currency dailyCharge;

    public Tool(String toolType, String brand, String toolCode){
        /* this is messy, we can do better */
        switch(toolType){
            case "Ladder":
                this.toolType = ToolType.Ladder;
//                hasWeekdayCharge =
            case "Chainsaw":
                this.toolType = ToolType.Chainsaw;
            case "Jackhammer":
                this.toolType = ToolType.Jackhammer;
        }

        this.brand = brand;
        this.toolCode = toolCode;
    }

    public String getToolCode() {
        return toolCode;
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