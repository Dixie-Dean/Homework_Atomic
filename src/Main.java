import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger five = new AtomicInteger(0);
    public static AtomicInteger four = new AtomicInteger(0);
    public static AtomicInteger three = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread isAlphabetOrderThread = new Thread(() -> {
            for (String text : texts) {
                if (isAlphabetOrder(text)) {
                    switch (text.length()) {
                        case 3 -> three.incrementAndGet();
                        case 4 -> four.incrementAndGet();
                        case 5 -> five.incrementAndGet();
                    }
                }
            }
        });

        Thread isSameCharThread = new Thread(() -> {
            for (String text : texts) {
                if (isSameChar(text)) {
                    switch (text.length()) {
                        case 3 -> three.incrementAndGet();
                        case 4 -> four.incrementAndGet();
                        case 5 -> five.incrementAndGet();
                    }
                }
            }
        });

        Thread isPalindromeThread = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    switch (text.length()) {
                        case 3 -> three.incrementAndGet();
                        case 4 -> four.incrementAndGet();
                        case 5 -> five.incrementAndGet();
                    }
                }
            }
        });

        isAlphabetOrderThread.start();
        isSameCharThread.start();
        isPalindromeThread.start();

        isAlphabetOrderThread.join();
        isSameCharThread.join();
        isPalindromeThread.join();

        System.out.printf("Красивых слов с длиной 3: %s шт.\n", three);
        System.out.printf("Красивых слов с длиной 4: %s шт.\n", four);
        System.out.printf("Красивых слов с длиной 5: %s шт.\n", five);
    }

    public static boolean isAlphabetOrder(String word) {
        char[] wordArray = new char[word.length()];
        for (int i = 0; i < word.length(); i++) {
            wordArray[i] = word.charAt(i);
        }

        Arrays.sort(wordArray);

        for (int i = 0; i < word.length(); i++) {
            if (wordArray[i] != word.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSameChar(String word) {
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(0) != word.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPalindrome(String word) {
        StringBuilder reverse = new StringBuilder();
        for (int i = word.length() - 1; i >= 0; i--) {
            reverse.append(word.charAt(i));
        }
        return word.contentEquals(reverse);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}