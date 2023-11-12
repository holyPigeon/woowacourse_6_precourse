package christmas.domain.discount;

import christmas.Menu;
import christmas.domain.Day;
import christmas.domain.Quantity;
import christmas.util.DateUtil;

import java.time.LocalDate;
import java.util.Map;

public class SpecialDiscount implements Discount{
    @Override
    public int calculateDiscountAmount(Map<Menu, Quantity> customerMenus, Day day) {
        if (isAvailableDiscount(customerMenus, day)) {
            return 1000;
        }
        return 0;
    }

    @Override
    public boolean isAvailableDiscount(Map<Menu, Quantity> customerMenus, Day day) {
        return isSunday(day) || isChristmasDay(day);
    }

    private boolean isSunday(Day day) {
        LocalDate localDate = DateUtil.convertDayToLocalDate(day);
        return localDate.getDayOfWeek().getValue() == 7; // 1: Monday, 2: Tuesday, ..., 7: Sunday
    }

    private boolean isChristmasDay(Day day) {
        return day.getPrimitiveDay() == 25;
    }
}
