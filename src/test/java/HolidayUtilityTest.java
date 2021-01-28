import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class HolidayUtilityTest {

    @Test
    void testIndependenceDayOnWeekday(){
        int year = 2022;

        LocalDate result = HolidayUtility.getDateIndependenceDayObserved(year);

        assertEquals(result, LocalDate.of(2022, 7, 4) );
    }

    @Test
    void testIndependenceDayOnSaturday(){
        int year = 2026;

        LocalDate result = HolidayUtility.getDateIndependenceDayObserved(year);

        assertEquals(result, LocalDate.of(2026, 7, 3) );
    }

    @Test
    void testIndependenceDayOnSunday(){
        int year = 2021;

        LocalDate result = HolidayUtility.getDateIndependenceDayObserved(year);

        assertEquals(result, LocalDate.of(2021, 7, 5) );
    }

    @Test
    void testLaborDayWhenMonthStartsOnMonday(){
        int year = 2025;

        LocalDate result = HolidayUtility.getDateLaborDayObserved(year);

        assertEquals(result, LocalDate.of(2025, 9, 1) );
    }

    @Test
    void testLaborDayWhenMonthStartsOnWednesday(){
        int year = 2027;

        LocalDate result = HolidayUtility.getDateLaborDayObserved(year);

        assertEquals(result, LocalDate.of(2027, 9, 6) );
    }

    @Test
    void testLaborDayWhenMonthStartsOnSaturday(){
        int year = 2029;

        LocalDate result = HolidayUtility.getDateLaborDayObserved(year);

        assertEquals(result, LocalDate.of(2029, 9, 3) );
    }

    @Test
    void testLaborDayWhenMonthStartsOnSunday(){
        int year = 2024;

        LocalDate result = HolidayUtility.getDateLaborDayObserved(year);

        assertEquals(result, LocalDate.of(2024, 9, 2) );
    }

}
