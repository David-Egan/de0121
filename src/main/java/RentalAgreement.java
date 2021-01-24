import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Date;

public class RentalAgreement{

    private Tool rentedTool;
    private int rentalDays;
    private int discountPercent;
    private Date checkoutDate;

    private Date dueDate;
    private int chargeDays;
    private BigDecimal preDiscountCharge;
    private BigDecimal discountAmount;
    private BigDecimal finalCharge;


    public RentalAgreement(Tool tool, int rentalDays, int discountPercent, Date checkoutDate){
        this.rentedTool = tool;
        this.rentalDays = rentalDays;
        this.discountPercent = discountPercent;
        this.checkoutDate = checkoutDate;

        this.dueDate = new Date();
        this.chargeDays = 10;
        this.preDiscountCharge = this.rentedTool.getDailyRentalCharge().multiply(BigDecimal.valueOf(chargeDays))
                .setScale(2,  RoundingMode.HALF_UP);

        BigDecimal discountMultiplier = BigDecimal.valueOf(this.discountPercent).divide(BigDecimal.valueOf(100));
        this.discountAmount = this.preDiscountCharge.multiply(discountMultiplier)
                .setScale(2, RoundingMode.HALF_UP);

        this.finalCharge = this.preDiscountCharge.subtract(discountAmount);

    }


    public void prettyPrintAgreement(){
        System.out.println();

        System.out.printf("Tool code: %s%n", rentedTool.getToolCode());
        System.out.printf("Tool type: %s%n", rentedTool.getToolType());
        System.out.printf("Tool brand: %s%n", rentedTool.getBrand());
        System.out.printf("Rental Days: %d%n", rentalDays);
        System.out.printf("Check out date: %s%n", checkoutDate);
        System.out.printf("Due date: %s%n", dueDate);
        System.out.printf("Daily rental charge: %s%n", rentedTool.getDailyRentalCharge());
        System.out.printf("Charge days: %d%n", chargeDays);
        System.out.printf("Pre-discount charge: %s%n", preDiscountCharge);
        System.out.printf("Discount percent: %s%%%n", discountPercent);
        System.out.printf("Discount amount: %s%n", discountAmount.setScale(2, RoundingMode.HALF_UP));
        System.out.printf("Final charge: %s%n", finalCharge);
        System.out.println();


    }

}