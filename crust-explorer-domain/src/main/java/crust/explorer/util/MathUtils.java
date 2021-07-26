package crust.explorer.util;

import java.math.BigDecimal;
import java.util.Objects;

public class MathUtils {

    public static final Integer ZERO = 0;
    public static final Integer ONE = 1;
    public static final Integer NEGATIVE = -1;
    public static final Integer TEN = 10;
    public static final Integer FIFTEEN = 15;
    public static final Integer TWENTY = 20;
    public static final Integer THIRTY = 30;
    public static final Integer FORTY = 40;
    public static final Integer FIVE_TEN = 50;
    public static final Integer ONE_HUNDRED = 100;

    public static boolean gtZero(Integer target) {
        return Objects.nonNull(target) && target > ZERO;
    }

    public static boolean geZero(Integer target) {
        return Objects.nonNull(target) && target >= ZERO;
    }

    public static boolean ltZero(Integer target) {
        return Objects.nonNull(target) && target < ZERO;
    }

    public static boolean leZero(Integer target) {
        return Objects.nonNull(target) && target <= ZERO;
    }

    public static boolean eqZero(Integer target) {
        return Objects.nonNull(target) && target.equals(ZERO);
    }

    public static boolean gtZero(Long target) {
        return Objects.nonNull(target) && target > ZERO;
    }

    public static boolean geZero(Long target) {
        return Objects.nonNull(target) && target >= ZERO;
    }

    public static boolean ltZero(Long target) {
        return Objects.nonNull(target) && target < ZERO;
    }

    public static boolean leZero(Long target) {
        return Objects.nonNull(target) && target <= ZERO;
    }

    public static boolean eqZero(Long target) {
        return Objects.nonNull(target) && target.intValue() == ZERO;
    }

    public static boolean gtZero(BigDecimal target) {
        return Objects.nonNull(target) && BigDecimal.ZERO.compareTo(target) == NEGATIVE;
    }

    public static boolean geZero(BigDecimal target) {
        return Objects.nonNull(target) && BigDecimal.ZERO.compareTo(target) < ONE;
    }

    public static boolean ltZero(BigDecimal target) {
        return Objects.nonNull(target) && BigDecimal.ZERO.compareTo(target) == ONE;
    }

    public static boolean leZero(BigDecimal target) {
        return Objects.nonNull(target) && BigDecimal.ZERO.compareTo(target) > NEGATIVE;
    }

    public static boolean eqZero(BigDecimal target) {
        return Objects.nonNull(target) && BigDecimal.ZERO.compareTo(target) == ZERO;
    }

    /**
     * source < target
     * @param source
     * @param target
     * @return
     */
    public static boolean lt(BigDecimal source, BigDecimal target) {
        return Objects.nonNull(source) && Objects.nonNull(target) && source.compareTo(target) == NEGATIVE;
    }

    /**
     * source > target
     * @param source
     * @param target
     * @return
     */
    public static boolean gt(BigDecimal source, BigDecimal target) {
        return Objects.nonNull(source) && Objects.nonNull(target) && source.compareTo(target) == ONE;
    }

    public static boolean eq(BigDecimal source, BigDecimal target) {
        return Objects.nonNull(source) && Objects.nonNull(target) && source.compareTo(target) == ZERO;
    }

    public static void main(String[] args) {
        // BigDecimal
        System.out.println(gtZero(new BigDecimal("22")));
        System.out.println(geZero(new BigDecimal("22")) && geZero(new BigDecimal("0")) && geZero(BigDecimal.ZERO));

        System.out.println(ltZero(new BigDecimal("-22")));
        System.out.println(leZero(new BigDecimal("-22")) && leZero(new BigDecimal("0")) && leZero(BigDecimal.ZERO));

        System.out.println(eqZero(new BigDecimal("0")) && eqZero(BigDecimal.ZERO));
        // long
        System.out.println(gtZero((long) 22) && gtZero((long) 22l) && gtZero((Long) 22L));
        System.out.println(geZero((long) 22) && geZero((long) 22l) && geZero((Long) 22L) && geZero(0L) && geZero(0l));

        System.out.println(ltZero((long) -22) && ltZero((long) -22l) && ltZero(-22L));
        System.out.println(leZero((long) -22) && leZero((long) -22l) && leZero(-22L) && leZero(0L) && leZero(0l));

        System.out.println(eqZero(0L) && eqZero(0l));
        // int
        System.out.println(gtZero((int) 22) && gtZero((Integer) 22) && gtZero(new Integer(22)));
        System.out.println(geZero((int) 22) && geZero((Integer) 22) && geZero(0));

        System.out.println(ltZero((int) -22) && ltZero(-22) && ltZero(new Integer(-22)));
        System.out.println(leZero((int) -22) && leZero(-22) && leZero(0));

        System.out.println(eqZero(0) && eqZero(new Integer(0)));
        System.out.println(eq(new BigDecimal("22.000"), new BigDecimal("22.000")));
        System.out.println(eq(new BigDecimal("022.000"), new BigDecimal("22.000")));
        System.out.println(eq(new BigDecimal("22.0000000000000000"), new BigDecimal("22.000")));
        System.out.println(eq(new BigDecimal("022.000000000000000"), new BigDecimal("22.000")));
        System.out.println(eq(new BigDecimal("22.000000000000001"), new BigDecimal("22.000")));
        System.out.println(eq(new BigDecimal("022.000000000000001"), new BigDecimal("22.000")));
        BigDecimal source = new BigDecimal("150.00000000");
        Integer q = 3;
        BigDecimal quantity = new BigDecimal(q);
        BigDecimal sourceTotal = source.multiply(quantity);
        BigDecimal target = new BigDecimal("450");
        System.out.println(eq(sourceTotal, target));
        System.out.println(sourceTotal.equals(target));
        System.out.println(lt(new BigDecimal("021.00000000000000"), new BigDecimal("22.000")));
        System.out.println(gt(new BigDecimal("022.000000000000001"), new BigDecimal("22.000")));

    }
}
