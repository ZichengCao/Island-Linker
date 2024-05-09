package com.ada.federate.secure;

import java.math.BigInteger;
import java.util.Random;

public class Paillier {
    private BigInteger n; // 公钥
    private BigInteger lambda; // lambda = lcm(p-1, q-1)
    private BigInteger g; // 随机数


    // 构造函数，生成公钥私钥对
    public Paillier(BigInteger p, BigInteger q) {
        n = p.multiply(q);
        lambda = lcm(p.subtract(BigInteger.ONE), q.subtract(BigInteger.ONE));
        g = n.add(BigInteger.ONE);
        while (gcd(g.modPow(lambda, n.multiply(n)), n).compareTo(BigInteger.ONE) != 0) {
            g = g.add(BigInteger.ONE);
        }
    }

    public BigInteger getN() {
        return n;
    }


    // 加密函数
    public BigInteger encrypt(BigInteger m) {
        BigInteger r = new BigInteger(n.bitLength(), new Random());
        while (r.compareTo(n) >= 0 || gcd(r, n).compareTo(BigInteger.ONE) != 0) {
            r = new BigInteger(n.bitLength(), new Random());
        }
        return g.modPow(m, n.multiply(n)).multiply(r.modPow(n, n.multiply(n))).mod(n.multiply(n));
    }

    // 解密函数
    public BigInteger decrypt(BigInteger c) {
        BigInteger u = g.modPow(lambda, n.multiply(n)).subtract(BigInteger.ONE).divide(n).modInverse(n);
        return c.modPow(lambda, n.multiply(n)).subtract(BigInteger.ONE).divide(n).multiply(u).mod(n);
    }

    // 同态加法
    public BigInteger add(BigInteger c1, BigInteger c2) {
        return c1.multiply(c2).mod(n.multiply(n));
    }

    // 计算最小公倍数
    private BigInteger lcm(BigInteger a, BigInteger b) {
        return a.multiply(b).divide(a.gcd(b));
    }

    // 计算最大公约数
    private BigInteger gcd(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO))
            return a;
        else
            return gcd(b, a.mod(b));
    }

    public static void main(String[] args) {
        // 选择素数
        // BigInteger p = PrimeGenerator.generatePrime(512);
        // BigInteger q = PrimeGenerator.generatePrime(512);

        BigInteger p = new BigInteger("17");

        BigInteger q = new BigInteger("19");
        // 创建 Paillier 对象
        Paillier paillier = new Paillier(p, q);

        // 加密
        BigInteger plaintext = new BigInteger("0");
        BigInteger ciphertext = paillier.encrypt(plaintext);
        System.out.println("Plaintext: " + plaintext);
        System.out.println("Ciphertext: " + ciphertext);

        // 解密
        BigInteger decryptedText = paillier.decrypt(ciphertext);
        System.out.println("Decrypted Text: " + decryptedText);

        // 同态加法
        BigInteger plaintext2 = new BigInteger("1");
        BigInteger ciphertext2 = paillier.encrypt(plaintext2);
        System.out.println("Plaintext 2: " + plaintext2);
        System.out.println("Ciphertext 2: " + ciphertext2);

        BigInteger sum = paillier.add(ciphertext, ciphertext2);
        System.out.println("Sum of ciphertexts: " + sum);

        BigInteger decryptedSum = paillier.decrypt(sum);
        System.out.println("Decrypted Sum: " + decryptedSum);
    }
}
