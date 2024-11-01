import java.util.Arrays;

public class PlayfairCipher {
    static String toLowerCase(String plain) {
        return plain.toLowerCase();
    }

    static String removeSpaces(String plain) {
        return plain.replaceAll(" ", "");
    }
    static char[][] generateKeyTable(String key) {
        char[][] keyT = new char[5][5];
        int[] dicty = new int[26];
        Arrays.fill(dicty, 0);

        for (int i = 0; i < key.length(); i++) {
            if (key.charAt(i) != 'j')
                dicty[key.charAt(i) - 97] = 2;
        }
        dicty['j' - 97] = 1;

        int i = 0, j = 0;
        for (int k = 0; k < key.length(); k++) {
            if (dicty[key.charAt(k) - 97] == 2) {
                dicty[key.charAt(k) - 97] -= 1;
                keyT[i][j] = key.charAt(k);
                j++;
                if (j == 5) {
                    i++;
                    j = 0;
                }
            }
        }

        for (int k = 0; k < 26; k++) {
            if (dicty[k] == 0) {
                keyT[i][j] = (char) (k + 97);
                j++;
                if (j == 5) {
                    i++;
                    j = 0;
                }
            }
        }
        return keyT;
    }
    static void search(char[][] keyT, char a, char b, int[] arr) {
        if (a == 'j')
            a = 'i';
        else if (b == 'j')
            b = 'i';

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (keyT[i][j] == a) {
                    arr[0] = i;
                    arr[1] = j;
                } else if (keyT[i][j] == b) {
                    arr[2] = i;
                    arr[3] = j;
                }
            }
        }
    }
    static void decrypt(char[] str, char[][] keyT) {
        for (int i = 0; i < str.length; i += 2) {
            int[] a = new int[4];
            search(keyT, str[i], str[i + 1], a);
            if (a[0] == a[2]) {
                str[i] = keyT[a[0]][mod5(a[1] - 1)];
                str[i + 1] = keyT[a[0]][mod5(a[3] - 1)];
            } else if (a[1] == a[3]) {
                str[i] = keyT[mod5(a[0] - 1)][a[1]];
                str[i + 1] = keyT[mod5(a[2] - 1)][a[1]];
            } else {
                str[i] = keyT[a[0]][a[3]];
                str[i + 1] = keyT[a[2]][a[1]];
            }
        }
    }
    static int mod5(int a) {
        if (a < 0)
            a += 5;
        return (a % 5);
    }
    static void decryptByPlayfairCipher(char[] str, String key) {

        key = removeSpaces(toLowerCase(key));

        char[][] keyT = generateKeyTable(key);

        decrypt(str, keyT);
    }

    public static void main(String[] args) {

        String key = "Monarchy";
        System.out.println("Key Text: " + key);

        char[] str = "talha".toCharArray();
        System.out.println("Ciphertext: " + String.valueOf(str));

        decryptByPlayfairCipher(str, key);

        System.out.println("Deciphered text: " + String.valueOf(str));
    }
}