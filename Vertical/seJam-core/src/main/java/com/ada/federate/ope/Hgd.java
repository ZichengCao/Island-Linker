package com.ada.federate.ope;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Hgd {

    private final static double TWO_32 = Math.pow(2, 32) - 1;

    private static final int bdPrecision = 20;



    /**
     * @param coins
     * @return
     */
    public static double prngDraw(Coins coins) {
        long out = 0;
        for (int i = 0; i < 32; i++)
            // TODO don't calculate powers every time
            out += coins.next() ? Math.pow(2, i) : 0;

        return out / TWO_32;
    }

    public static Long rhyper(Long kk, Long nn1, Long nn2, Coins coins) {
        if (kk > 10)
            return hypergeometricHrua(coins, nn1, nn2, kk);
        else
            return hypergeometricHyp(coins, nn1, nn2, kk);
    }

    public static boolean equalDouble(double a, double b) {
        double epsilon = 0.0001;
        return Math.abs(a - b) < epsilon;
    }

    private static Long hypergeometricHyp(Coins coins, Long good, Long bad,
                                          Long sample) {


        Double d1 = (double) (bad + good - sample);
        Double d2 = (double) Math.min(bad, good);

        Double Y = d2;
        Double K = Double.valueOf(sample);

        while (Y > 0.0) {
            Double U = prngDraw(coins);
            Y -= Math.floor(U + Y / (d1 + K));
            K -= 1.0;

            if (equalDouble(K, 0.0))
                break;
        }

        Long Z = (long) (d2 - Y);
        if (good.compareTo(bad) > 0)
            Z = sample - Z;

        return Z;
    }

    private final static Double D1 = 1.7155277699214135;
    private final static Double D2 = 0.8989161620588988;

    private static Long hypergeometricHrua(Coins coins, Long good, Long bad,
                                           Long sampleBI) {

        boolean moreGood;
        Double badBD = Double.valueOf(bad);
        Double goodBD = Double.valueOf(good);
        Double mingoodbad;
        Double maxgoodbad;
        if (good.compareTo(bad) > 0) {
            moreGood = true;
            mingoodbad = badBD;
            maxgoodbad = goodBD;

        } else {
            moreGood = false;
            mingoodbad = goodBD;
            maxgoodbad = badBD;
        }

        Double popsize = (double) (good + bad);
        Double sample = Double.valueOf(sampleBI);
        Double m = Double.min(sample, popsize - sample);

        Double d4 = mingoodbad / popsize;
        Double d5 = 1 - d4;
        Double d6 = m * d4 + 0.5;

        Double d7a = (popsize - m) * sample * d4 * d5 / (popsize - 1) + 0.5;

        Double d7 = Math.sqrt(d7a);

        Double d8 = D1 * d7 + D2;

        Double mingoodbadplus1 = mingoodbad + 1;
        Double d9 = Math.floor((m + 1) * (mingoodbad + 1) / (popsize + 2));

        Double d10 = loggam(d9 + 1) + loggam(mingoodbad - d9 + 1) + loggam(m - d9 + 1) + loggam(maxgoodbad - m + d9 + 1);

        Double d11 = Math.min(Math.min(m, mingoodbad) + 1.0, Math.floor(d6 + 16 * d7));

        Double Z;
        while (true) {
            Double X = prngDraw(coins);
            Double Y = prngDraw(coins);
            Double W = d6 + d8 * (Y - 0.5) / X;


            if (W < 0.0 || W >= d11)
                continue;

            Z = Math.floor(W);
            Double T = d10 - (loggam(Z + 1) + loggam(mingoodbad - Z + 1) + loggam(m - Z + 1) + loggam(maxgoodbad - m + Z + 1));
            if (X * (4.0 - X) - 3.0 <= T)
                break;

            if (X * (X - T) >= 1)
                continue;
            if (2.0 * Math.log(X) <= T)
                break;
        }

        if (moreGood)
            Z = m - Z;

        if (m.compareTo(sample) < 0)
            Z = goodBD - Z;

        return Z.longValue();
    }

    public static double loggam(double x) {
        double[] a = {8.333333333333333e-02, -2.777777777777778e-03,
                7.936507936507937e-04, -5.952380952380952e-04,
                8.417508417508418e-04, -1.917526917526918e-03,
                6.410256410256410e-03, -2.955065359477124e-02,
                1.796443723688307e-01, -1.39243221690590e+00};
        double x0 = x;
        long n = 0;
        if (x == 1.0 || x == 2.0) {
            return 0.0;
        } else if (x <= 7.0) {
            n = (long) (7 - x);
            x0 = x + n;
        }
        double x2 = 1.0 / (x0 * x0);
        double xp = 2 * Math.PI;
        double gl0 = a[9];
        for (int k = 8; k >= 0; k--) {
            gl0 *= x2;
            gl0 += a[k];
        }
        double gl = gl0 / x0 + 0.5 * Math.log(xp) + (x0 - 0.5) * Math.log(x0) - x0;
        if (x <= 7.0) {
            for (long k = 1; k <= n; k++) {
                gl -= Math.log(x0 - 1.0);
                x0 -= 1.0;
            }
        }
        return gl;
    }
}
