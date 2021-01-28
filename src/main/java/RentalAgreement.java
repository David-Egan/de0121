import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Class to represent the rental agreement formed when checking out a Tool.
 */
public class RentalAgreement{

    private final Tool rentedTool;
    private final int rentalDays;
    private final int discountPercent;
    private final LocalDate checkoutDate;

    private LocalDate dueDate;
    private int chargeDays;
    private BigDecimal preDiscountCharge;
    private BigDecimal discountAmount;
    private BigDecimal finalCharge;

    /**
     * Construct a RentalAgreement instance including setting fields that must be calculated
     * based on the rental information.
     */
    public RentalAgreement(Tool tool, int rentalDays, int discountPercent, LocalDate checkoutDate){
        this.rentedTool = tool;
        this.rentalDays = rentalDays;
        this.discountPercent = discountPercent;
        this.checkoutDate = checkoutDate;

        calculateChargeDetails();
    }

    /**
     * Determine if a given rental day amount is valid in the context of a Rental Agreement.
     */
    public static boolean isRentalDaysValid(int rentalDays){
        return rentalDays >= 1;
    }

    /**
     * Determine if a given discount percent is valid in the context of a Rental Agreement.
     */
    public static boolean isRentalDiscountPercentValid(int discountPercent){
        return (discountPercent <= 100 && discountPercent >= 0);
    }

    /**
     * Print a rental agreement's information in a user friendly fashion.
     */
    public void prettyPrintAgreement(){
        System.out.println();

        NumberFormat usdFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yy");

        System.out.printf("Tool code: %s%n", rentedTool.getToolCode());
        System.out.printf("Tool type: %s%n", rentedTool.getToolType());
        System.out.printf("Tool brand: %s%n", rentedTool.getBrand());
        System.out.printf("Rental Days: %d%n", rentalDays);
        System.out.printf("Check out date: %s%n", dtf.format(checkoutDate));
        System.out.printf("Due date: %s%n", dtf.format(dueDate));
        System.out.printf("Daily rental charge: %s%n", usdFormatter.format(rentedTool.getDailyRentalCharge()));
        System.out.printf("Charge days: %d%n", chargeDays);
        System.out.printf("Pre-discount charge: %s%n", usdFormatter.format(preDiscountCharge));
        System.out.printf("Discount percent: %s%%%n", discountPercent);
        System.out.printf("Discount amount: %s%n", usdFormatter.format(discountAmount));
        System.out.printf("Final charge: %s%n", usdFormatter.format(finalCharge));
        System.out.println();
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public int getChargeDays() {
        return chargeDays;
    }

    public BigDecimal getPreDiscountCharge() {
        return preDiscountCharge;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public BigDecimal getFinalCharge() {
        return finalCharge;
    }

    /**
     * Calculate the discount and charges for this RentalAgreement.
     */
    private void calculateChargeDetails(){
        dueDate = checkoutDate.plusDays(rentalDays);
        chargeDays = calculateChargeDays();

        preDiscountCharge = rentedTool.getDailyRentalCharge().multiply(BigDecimal.valueOf(chargeDays))
                .setScale(2,  RoundingMode.HALF_UP);

        BigDecimal discountMultiplier = BigDecimal.valueOf(discountPercent).divide(BigDecimal.valueOf(100));
        discountAmount = preDiscountCharge.multiply(discountMultiplier)
                .setScale(2, RoundingMode.HALF_UP);

        finalCharge = preDiscountCharge.subtract(discountAmount);
    }

    /**
     * Calculate how many days should be charged in this rental agreement based on the Tool.
     */
    private int calculateChargeDays(){
        // start charging the day after checkout up to and including the due date
        LocalDate startDate = this.checkoutDate.plusDays(1); // inclusive
        LocalDate endDate = this.dueDate.plusDays(1); // exclusive

        return getChargedDays(startDate, endDate);
    }

    /**
     * Helper to get the count of days that are charged between two dates based on the Tool.
     */
    private int getChargedDays(LocalDate startInclusive, LocalDate endExclusive) {
        Set<LocalDate> holidays = getHolidays(startInclusive.getYear(), endExclusive.getYear());

        if (startInclusive.isAfter(endExclusive)) {
            throw new IllegalArgumentException("Start Date cannot occur after End Date");
        }

        int chargedDays = 0;

        LocalDate currDay = startInclusive;
        while (currDay.isBefore(endExclusive)) {
            DayOfWeek dw = currDay.getDayOfWeek();
            // all our holidays are observed in weekdays, we need to make sure the tools is being charged on weekdays as well
            if (
                    (!holidays.contains(currDay) || (rentedTool.getHasHolidayCharge())) &&
                    (
                        (rentedTool.getHasWeekendCharge() && (dw == DayOfWeek.SATURDAY || dw == DayOfWeek.SUNDAY)) ||
                        (rentedTool.getHasWeekdayCharge() && (dw != DayOfWeek.SATURDAY  && dw != DayOfWeek.SUNDAY))
                    )

            ) {
                chargedDays++;
            }
            currDay = currDay.plusDays(1);
        }
        return chargedDays;
    }

    /**
     * Get the holidays that occur between the two years.
     */
    private Set<LocalDate> getHolidays(int startYear, int endYear){
        Set<LocalDate> holidays = new HashSet<>();

        for(int currYear = startYear; currYear <= endYear; currYear++){
            holidays.add(HolidayUtility.getDateIndependenceDayObserved(currYear));
            holidays.add(HolidayUtility.getDateLaborDayObserved(currYear));
        }

        return holidays;
    }


}