import java.time.LocalDate;

/**
 * A utility class with ability to find when holidays occur and other supporting methods.
 */
public class HolidayUtility {

    /**
     * Get the date the 4th of July is observed in a given year. If the 4th
     * occurs on a Saturday it's observed the day before on Friday if it's
     * on a Sunday it's observed the day after on Monday.
     */
    public static LocalDate getDateIndependenceDayObserved(int year) {
        final int JULY_MONTH_INT = 7;

        LocalDate actualIndependenceDayDate =  LocalDate.of(year, JULY_MONTH_INT, 4);
        int dayOfWeekInt = actualIndependenceDayDate.getDayOfWeek().getValue();

        switch(dayOfWeekInt) {
            case 7 : // Sunday - observe on Monday
                return LocalDate.of(year, JULY_MONTH_INT, 5);
            case 1 : // Monday
            case 2 : // Tuesday
            case 3 : // Wednesday
            case 4 : // Thursday
            case 5 : // Friday
                return LocalDate.of(year, JULY_MONTH_INT, 4);
            default :
                // Saturday - observe on Friday
                return LocalDate.of(year, JULY_MONTH_INT, 3);
        }
    }

    /**
     * Get the day Labor Day is observed. It is always on the first Monday of
     * September so get the first day of September and find which day of the
     * month the first Monday occurs based on that.
     */
    public static LocalDate getDateLaborDayObserved(int year) {
        final int SEPTEMBER_MONTH_INT = 9;

        LocalDate firstOfSeptember = LocalDate.of(year, SEPTEMBER_MONTH_INT, 1);
        int dayOfWeekInt = firstOfSeptember.getDayOfWeek().getValue();

        /* Get Labor day based on how far the 1st of the month is from the first Monday */
        switch(dayOfWeekInt) {
            case 7: // Sunday
                return LocalDate.of(year, SEPTEMBER_MONTH_INT, 2);
            case 1 : // Monday
                return LocalDate.of(year, SEPTEMBER_MONTH_INT, 1);
            case 2 : // Tuesday
                return LocalDate.of(year, SEPTEMBER_MONTH_INT, 7);
            case 3 : // Wednesday
                return LocalDate.of(year, SEPTEMBER_MONTH_INT, 6);
            case 4 : // Thursday
                return LocalDate.of(year, SEPTEMBER_MONTH_INT, 5);
            case 5 : // Friday
                return LocalDate.of(year, SEPTEMBER_MONTH_INT, 4);
            default : // Saturday
                return LocalDate.of(year, SEPTEMBER_MONTH_INT, 3);
        }
    }


}
