import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ToolTest {

    Tool testJackhammer1;
    Tool testLadder;
    Tool testChainsaw;
    Tool testJackhammer2;

    @BeforeAll
    void setup(){
        testJackhammer1 = new Tool("Jackhammer", "Ridgid", "JAKR");
        testLadder = new Tool("Ladder", "Werner", "LADW");
        testChainsaw = new Tool("Chainsaw", "Stihl", "CHNS");
        testJackhammer2 = new Tool("Jackhammer", "DeWalt", "JAKD");
    }

    @Test
    void checkoutProofTest1(){
        int rentalDays = 5;
        int discountPercent = 101;
        LocalDate checkoutDate = LocalDate.of(2015, 9, 3);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            testJackhammer1.checkout(rentalDays, discountPercent, checkoutDate);
        });

        String expectedExceptionMessage = "Discount percent must be between 0 and 100";
        String actualExceptionMessage = exception.getMessage();

        assertEquals(expectedExceptionMessage, actualExceptionMessage);
        assertEquals(IllegalArgumentException.class, exception.getClass());
    }

    @Test
    void checkoutProofTest2(){
        int rentalDays = 3;
        int discountPercent = 10;
        LocalDate checkoutDate = LocalDate.of(2020, 7, 2);

        RentalAgreement rentalAgreement = testLadder.checkout(rentalDays, discountPercent, checkoutDate);
        assertEquals(2, rentalAgreement.getChargeDays());
        assertEquals(LocalDate.of(2020, 7, 5), rentalAgreement.getDueDate());
        assertEquals(BigDecimal.valueOf(3.98).setScale(2), rentalAgreement.getPreDiscountCharge());
        assertEquals(BigDecimal.valueOf(0.40).setScale(2),rentalAgreement.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(3.58).setScale(2), rentalAgreement.getFinalCharge());
    }

    @Test
    void checkoutProofTest3(){
        int rentalDays = 5;
        int discountPercent = 25;
        LocalDate checkoutDate = LocalDate.of(2015, 7, 2);

        RentalAgreement rentalAgreement = testChainsaw.checkout(rentalDays, discountPercent, checkoutDate);
        assertEquals(3, rentalAgreement.getChargeDays());
        assertEquals(LocalDate.of(2015, 7, 7), rentalAgreement.getDueDate());
        assertEquals(BigDecimal.valueOf(4.47).setScale(2), rentalAgreement.getPreDiscountCharge());
        assertEquals(BigDecimal.valueOf(1.12).setScale(2),rentalAgreement.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(3.35).setScale(2), rentalAgreement.getFinalCharge());
    }

    @Test
    void checkoutProofTest4(){
        int rentalDays = 6;
        int discountPercent = 0;
        LocalDate checkoutDate = LocalDate.of(2015, 9,3);

        RentalAgreement rentalAgreement = testJackhammer2.checkout(rentalDays, discountPercent, checkoutDate);
        assertEquals(3, rentalAgreement.getChargeDays());
        assertEquals(LocalDate.of(2015, 9, 9), rentalAgreement.getDueDate());
        assertEquals(BigDecimal.valueOf(8.97).setScale(2), rentalAgreement.getPreDiscountCharge());
        assertEquals(BigDecimal.valueOf(0).setScale(2),rentalAgreement.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(8.97).setScale(2), rentalAgreement.getFinalCharge());
    }

    @Test
    void checkoutProofTest5(){
        int rentalDays = 9;
        int discountPercent = 0;
        LocalDate checkoutDate = LocalDate.of(2015, 7, 2);

        RentalAgreement rentalAgreement = testJackhammer1.checkout(rentalDays, discountPercent, checkoutDate);
        assertEquals(5, rentalAgreement.getChargeDays());
        assertEquals(LocalDate.of(2015, 7, 11), rentalAgreement.getDueDate());
        assertEquals(BigDecimal.valueOf(14.95).setScale(2), rentalAgreement.getPreDiscountCharge());
        assertEquals(BigDecimal.valueOf(0).setScale(2),rentalAgreement.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(14.95).setScale(2), rentalAgreement.getFinalCharge());
    }

    @Test
    void checkoutProofTest6(){
        int rentalDays = 4;
        int discountPercent = 50;
        LocalDate checkoutDate = LocalDate.of(2020, 7, 2);

        RentalAgreement rentalAgreement = testJackhammer1.checkout(rentalDays, discountPercent, checkoutDate);
        assertEquals(1, rentalAgreement.getChargeDays());
        assertEquals(LocalDate.of(2020, 7, 6), rentalAgreement.getDueDate());
        assertEquals(BigDecimal.valueOf(2.99).setScale(2), rentalAgreement.getPreDiscountCharge());
        assertEquals(BigDecimal.valueOf(1.50).setScale(2),rentalAgreement.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(1.49).setScale(2), rentalAgreement.getFinalCharge());
    }

    @Test
    void checkoutMultipleYearsTest(){
        int rentalDays = 800; // 2 years plus into March of another year
        int discountPercent = 0;
        LocalDate checkoutDate = LocalDate.of(2020, 1, 1);

        RentalAgreement rentalAgreement = testJackhammer1.checkout(rentalDays, discountPercent, checkoutDate);

        // Weekdays during rental days: 572
        // Holidays during rental days: 2 in 2020 + 2 in 2021 + 0 in 2022 up to March = 4
        assertEquals(568, rentalAgreement.getChargeDays());
        assertEquals(LocalDate.of(2022, 3, 11), rentalAgreement.getDueDate());
        assertEquals(BigDecimal.valueOf(1698.32).setScale(2), rentalAgreement.getPreDiscountCharge());
        assertEquals(BigDecimal.valueOf(0.00).setScale(2),rentalAgreement.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(1698.32).setScale(2), rentalAgreement.getFinalCharge());
    }

}
