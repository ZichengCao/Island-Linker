package com.ada.federate.ope;

import com.ada.federate.utils.Tools;

import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.*;

public class OPE {

    String key;
    ValueRange inRange;
    ValueRange outRange;

    public static long calculatePower(int base, int exponent) {
        return calculatePower(base, exponent, false);
    }

    public static long calculatePower(int base, int exponent, boolean negateFlag) {
        if (exponent < 0) {
            throw new IllegalArgumentException("Exponent must be non-negative");
        }

        long result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= base;
        }

        if (negateFlag) {
            result = -result;
        }

        return result;
    }

    public OPE(String key) {

        this.key = key;
        this.inRange = new ValueRange(calculatePower(2, 31, true), calculatePower(2, 31));
        this.outRange = new ValueRange(calculatePower(2, 47, true), calculatePower(2, 47));
    }

    public OPE() {
        this.key = generateKey(32);
        this.inRange = new ValueRange(calculatePower(2, 32, true), calculatePower(2, 32));
        // this.inRange = new ValueRange(calculatePower(2, 31, true), calculatePower(2, 31));
        // this.outRange = new ValueRange(calculatePower(2, 47, true), calculatePower(2, 47));
        this.outRange = new ValueRange(calculatePower(2, 48, true), calculatePower(2, 48));
    }

    public static String generateKey(int block_size) {
        if (block_size % 8 != 0) {
            throw new IllegalArgumentException("Block size must be a multiple of 8.");
        }

        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[block_size];
        secureRandom.nextBytes(randomBytes);

        // Using Apache Commons Codec for Base64 encoding
        String randomKey = new String(Base64.getEncoder().encode(randomBytes));
        return randomKey;
    }

    public Long encrypt(Long ptxt) {

        if (!this.inRange.contains(ptxt))
            throw new RuntimeException("Plaintext is not within the input range");

        return this.encryptRecursive(ptxt, this.inRange, this.outRange);
    }

    private Long encryptRecursive(Long ptxt, ValueRange inRange, ValueRange outRange) {
//        System.out.println("D:" + inRange + " R:" + outRange);
        Long inSize = inRange.size();
        Long outSize = outRange.size();

        assert inSize.compareTo(outSize) <= 0;

        if (inRange.size() == 1) {
            Coins coins = new Coins(this.key, ptxt);
            return sampleUniform(outRange, coins);
        }

        Long inEdge = inRange.start - 1;
        Long outEdge = outRange.start - 1;

        Long m = (long) Math.ceil(outSize / 2.0);

        Long mid = outEdge + m;

        Coins coins = new Coins(this.key, mid);

        Long x = sampleHGD(inRange, outRange, mid, coins);

        if (ptxt.compareTo(x) <= 0) {
            inRange = new ValueRange(inEdge + 1, x);
            outRange = new ValueRange(outEdge + 1, mid);
        } else {
            inRange = new ValueRange(x + 1, inEdge + inSize);
            outRange = new ValueRange(mid + 1, outEdge + outSize);
        }

        return this.encryptRecursive(ptxt, inRange, outRange);
    }

    public Long decrypt(Long ctxt) {

        if (!this.outRange.contains(ctxt))
            throw new RuntimeException("Ciphertext is not within the input range");

        return this.decryptRecursive(ctxt, this.inRange, this.outRange);
    }

    private Long decryptRecursive(Long ctxt, ValueRange inRange, ValueRange outRange) {

        Long inSize = inRange.size();
        Long outSize = outRange.size();

        assert inSize.compareTo(outSize) <= 0;

        if (inRange.size() == 1) {
            Long inRangeMin = inRange.start;
            Coins coins = new Coins(this.key, inRangeMin);
            Long sampledCtxt = sampleUniform(outRange, coins);

            if (sampledCtxt.compareTo(ctxt) == 0)
                return inRangeMin;
            else
                throw new RuntimeException("Invalid ciphertext");

        }

        Long inEdge = inRange.start - 1;
        Long outEdge = outRange.start - 1;

        Long m = (long) Math.ceil(outSize / 2.0);

        Long mid = outEdge + m;

        Coins coins = new Coins(this.key, mid);
        Long x = sampleHGD(inRange, outRange, mid, coins);

        if (ctxt.compareTo(mid) <= 0) {
            inRange = new ValueRange(inEdge + 1, x);
            outRange = new ValueRange(outEdge + 1, mid);
        } else {
            inRange = new ValueRange(x + 1, inEdge + inSize);
            outRange = new ValueRange(mid + 1, outEdge + outSize);
        }

        return this.decryptRecursive(ctxt, inRange, outRange);
    }

    /**
     * Uniformly select a number from the range using the bit list as a source of randomness
     *
     * @param inRange
     * @param coins
     * @return
     */
    private static Long sampleUniform(ValueRange inRange, Coins coins) {

        ValueRange curRange = new ValueRange(inRange);

        assert curRange.size() != 0;

        while (curRange.size() > 1) {

            // System.out.println(curRange.start + " " + curRange.end);

            Long mid = (curRange.start + curRange.end) / 2;


            boolean bit = coins.next();
            if (bit == false)
                curRange.end = mid;
            else if (bit == true)
                curRange.start = mid + 1;
            else
                throw new RuntimeException("Unexpected bit value");
        }

        assert curRange.size() != 0;

        return curRange.start;
    }

    private static Long sampleHGD(ValueRange inRange, ValueRange outRange,
                                  Long nSample, Coins coins) {

        Long inSize = inRange.size();
        Long outSize = outRange.size();

        assert inSize > 0 && outSize > 0;
        assert inSize.compareTo(outSize) <= 0;
        assert outRange.contains(nSample);

        Long nSampleIndex = nSample - outRange.start + 1;

        if (inSize.compareTo(outSize) == 0)
            return inRange.start + nSampleIndex - 1;

        Long inSampleNum = Hgd.rhyper(nSampleIndex, inSize, outSize, coins);

        if (inSampleNum == 0)
            return inRange.start;
        else if (inSampleNum.compareTo(inSize) == 0)
            return inRange.end;
        else {
            Long inSample = inRange.start + inSampleNum;

            assert inRange.contains(inSample);

            return inSample;
        }
    }

    public static void testPreservingOrderEncryption() {
        // 初始化加密器
        OPE o = new OPE();
        Random random = new Random();

        // 生成随机数列表
        List<Long> originalNumbers = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            long randomNumber = random.nextLong() % 20000;
            originalNumbers.add(randomNumber < 0 ? -randomNumber : randomNumber);
        }

        // 对随机数列表进行排序
        List<Long> sortedOriginalNumbers = new ArrayList<>(originalNumbers);
        Collections.sort(sortedOriginalNumbers);

        System.out.println(sortedOriginalNumbers);

        // 加密原始随机数列表
        List<Long> encryptedNumbers = new ArrayList<>();
        for (long number : sortedOriginalNumbers) {
            encryptedNumbers.add(o.encrypt(number));
        }

        System.out.println(encryptedNumbers);

        // 验证加密后的顺序是否保持一致
        for (int i = 0; i < encryptedNumbers.size() - 1; i++) {
            long currentEncrypted = encryptedNumbers.get(i);
            long nextEncrypted = encryptedNumbers.get(i + 1);

            // 检查加密后的顺序是否与原始顺序保持一致
            if (currentEncrypted > nextEncrypted) {
                System.out.println("Encryption order violated!");
                return;
            }
        }
        List<Long> decryptedList = new ArrayList<>();
        for (int i = 0; i < encryptedNumbers.size(); i++) {
            decryptedList.add(o.decrypt(encryptedNumbers.get(i)));
            if (!o.decrypt(encryptedNumbers.get(i)).equals(sortedOriginalNumbers.get(i))) {
                System.out.println("Encryption order violated!");
                return;
            }
        }
        System.out.println(decryptedList);
        System.out.println("Encryption order preserved!");
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        testPreservingOrderEncryption();
        long endTime = System.currentTimeMillis();
        Tools.formatPrintTestInfo(startTime, endTime);

        // OPE o = new OPE("long key");
        // Random random = new Random();
        //
        // for (long i = 0; i < 100; i++) {
        //     long k = random.nextLong() % 200000;
        //     long e = o.encrypt(k);
        //     long d = o.decrypt(e);
        //     System.out.println(d + ": " + e);
        // }
        // System.out.println("done");
    }
}
