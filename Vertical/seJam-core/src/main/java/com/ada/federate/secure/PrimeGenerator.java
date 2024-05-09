package com.ada.federate.secure;

import java.math.BigInteger;
import java.util.Random;

public class PrimeGenerator {
    private static final int CERTAINTY = 100; // 素数检测的确定性参数

    public static BigInteger generatePrime(int bitLength) {
        Random rnd = new Random();
        BigInteger prime;
        do {
            prime = new BigInteger(bitLength, CERTAINTY, rnd);
        } while (!isPrime(prime));
        return prime;
    }

    // Miller-Rabin素数检测算法
    private static boolean isPrime(BigInteger n) {
        if (n.compareTo(BigInteger.ONE) <= 0) return false;
        if (n.compareTo(new BigInteger("3")) <= 0) return true;
        if (n.mod(new BigInteger("2")).equals(BigInteger.ZERO)) return false;

        int s = 0;
        BigInteger d = n.subtract(BigInteger.ONE);
        while (d.mod(new BigInteger("2")).equals(BigInteger.ZERO)) {
            d = d.divide(new BigInteger("2"));
            s++;
        }

        for (int i = 0; i < CERTAINTY; i++) {
            BigInteger a = getRandomBigInteger(BigInteger.TWO, n.subtract(BigInteger.ONE));
            if (!millerRabinTest(a, d, n, s))
                return false;
        }
        return true;
    }

    private static boolean millerRabinTest(BigInteger a, BigInteger d, BigInteger n, int s) {
        BigInteger x = a.modPow(d, n);
        if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE)))
            return true;

        for (int r = 1; r < s; r++) {
            x = x.modPow(BigInteger.TWO, n);
            if (x.equals(BigInteger.ONE))
                return false;
            if (x.equals(n.subtract(BigInteger.ONE)))
                return true;
        }
        return false;
    }

    private static BigInteger getRandomBigInteger(BigInteger min, BigInteger max) {
        BigInteger range = max.subtract(min).add(BigInteger.ONE);
        Random rnd = new Random();
        int bits = range.bitLength();
        BigInteger res;
        do {
            res = new BigInteger(bits, rnd);
        } while (res.compareTo(range) >= 0);
        return res.add(min);
    }

    public static void main(String[] args) {
        int bitLength = 512; // 指定位数
        BigInteger prime = generatePrime(bitLength);
        System.out.println("Generated prime number with " + bitLength + " bits:\n" + prime);
    }
}
