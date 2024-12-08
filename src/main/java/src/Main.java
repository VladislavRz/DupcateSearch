package src;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static src.DuplicateSearch.*;


public class Main {

    public static void main(String[] args) {
        try {
            int numHashFunctions = 100;

            // Чтение текстов из файлов
            String ex1 = Files.readString(Paths.get("texts/text1.txt"), StandardCharsets.UTF_8);
            String ex2 = Files.readString(Paths.get("texts/text2.txt"), StandardCharsets.UTF_8);
            String ex3 = Files.readString(Paths.get("texts/text3.txt"), StandardCharsets.UTF_8);

            // Обработка текста 1
            List<String> canon1 = getCanonicalText(ex1);            // Канонизация
            List<String> shing1 = getShingles(canon1, 3); // Построение Шинглов
            Set<String> uShing1 = new HashSet<>(shing1);
            int[] hash1 = computeHash(uShing1, numHashFunctions);   // Вычисление хешей

            // Обработка текста 2
            List<String> canon2 = getCanonicalText(ex2);
            List<String> shing2 = getShingles(canon2, 3);
            Set<String> uShing2 = new HashSet<>(shing2);
            int[] hash2 = computeHash(uShing2, numHashFunctions);

            // Обработка текста 3
            List<String> canon3 = getCanonicalText(ex3);
            List<String> shing3 = getShingles(canon3, 3);
            Set<String> uShing3 = new HashSet<>(shing3);
            int[] hash3 = computeHash(uShing3, numHashFunctions);

            // Поиск однообразных элементов
            double comparison12 = Compare(hash1, hash2);
            double comparison13 = Compare(hash1, hash3);
            double comparison23 = Compare(hash2, hash3);

            // Вывод результатов
            System.out.println("Пример 1: " + String.join(" ", canon1));
            System.out.println("Пример 2: " + String.join(" ", canon2));
            System.out.println("Пример 3: " + String.join(" ", canon3));

            System.out.println();
            System.out.println("Shingles для примера 1: " + uShing1);
            System.out.println("Shingles для примера 2: " + uShing2);
            System.out.println("Shingles для примера 3: " + uShing3);

            System.out.println();
            System.out.println("Однообразие примеров 1 и 2: " + comparison12);
            System.out.println("Однообразие примеров 1 и 3: " + comparison13);
            System.out.println("Однообразие примеров 2 и 3: " + comparison23);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
