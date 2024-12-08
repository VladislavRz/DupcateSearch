package src;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

public class DuplicateSearch {

    // Канонизация текста
    public static List<String> getCanonicalText(String text) {
        return Arrays.stream(text.toLowerCase().
                replaceAll("[^a-zа-я0-9]", " ").
                replaceAll("\\s+", " ").split(" ")).toList();
    }

    // Построение Shingles
    public static List<String> getShingles(List<String> text, int shingleSize) {
        List<String> shingles = new ArrayList<>();

        for (int i = 0; i <= text.size() - shingleSize; i++) {
            StringBuilder shingle = new StringBuilder();

            for (int j = 0; j < shingleSize; j++) {
                shingle.append(text.get(i + j));
            }

            shingles.add(shingle.toString());
        }
        return shingles;
    }

    // Вычисление хешей
    public static int[] computeHash(Set<String> shingles, int numHashFunctions) {
        int[] signatures = new int[numHashFunctions];
        Arrays.fill(signatures, Integer.MAX_VALUE);

        for (String shingle : shingles) {
            for (int i = 0; i < numHashFunctions; i++) {
                int hash = getHashInstance(shingle, i);
                signatures[i] = Math.min(signatures[i], hash);
            }
        }

        return signatures;
    }

    public static int getHashInstance(String value, int seed) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((seed + value).getBytes(StandardCharsets.UTF_8));
            return Arrays.hashCode(md.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static double Compare(int[] sig1, int[] sig2) {
        if (sig1.length != sig2.length)
            throw new IllegalArgumentException("Signatures length must be the same!");

        int matches = 0;
        for (int i = 0; i < sig1.length; i++) {
            if (sig1[i] == sig2[i]) {
                matches++;
            }
        }

        return (double) matches / sig1.length;
    }
}
