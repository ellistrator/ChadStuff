import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

class Permutations {
    /**
     * Does the string contain all unique characters?
     * @param str
     * @return
     */
    public boolean isUnique(String str) {
        Set<Character> set = new HashSet<>();
        int size = 0;
        for (Character c : str.toCharArray()) {
            set.add(c);
            if (set.size() != ++size) {
                return false;
            }
        }
        return true;
    }

    public boolean isUniqueNoStructs(String str) {
        for (int i = 0; i < str.length(); i++) {
            for (int j = i+1; j < str.length(); j++) {
                if (str.charAt(i) == str.charAt(j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isUniqueNoStructs2(String str) {
        Character[] alphas = new Character[26];

        for (int i = 0; i < str.length(); i++) {
            int index = str.charAt(i) - 97;
            if (alphas[index] != null) {
                return false;
            }
            alphas[index] = str.charAt(i);
        }
        return true;
    }

    // O(N*M)
    public boolean isPermutation(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() != str2.length()) {
            return false;
        }

        List list = new ArrayList<>();
        for (Character c : str2.toCharArray()) {
            list.add(c);
        }

        for (int j = 0; j < str1.length(); j++) {
            int i = list.indexOf(str1.charAt(j));
            if (i == -1) {
                return false;
            }
            list.remove(i);
        }
        return true;
    }

    // O(N*M)
    public boolean isPallindrome(String str) {
        if (str == null) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != str.charAt(str.length()-i-1)) {
                return false;
            }
        }
        return true;
    }

    public String urlify(String str) {
        if (str == null) {
            return str;
        }
        int spaceCount = 0;
        for (Character c : str.toCharArray()) {
            if (c == ' ') {
                spaceCount++;
            }
        }

        char[] chars = new char[str.length() + spaceCount*2];
        int charIndex = 0;
        for (Character c : str.toCharArray()) {
            if (c == ' ') {
                chars[charIndex++] = '%';
                chars[charIndex++] = '2';
                chars[charIndex++] = '0';
            } else {
                chars[charIndex++] = c;
            }
        }
        return new String(chars);
    }

    public boolean oneAway(String str1, String str2) {
        // pale ale
        // ale pale
        // pale tale

        // Gotta be within one character in length to bother
        if (str1 == null || str2 == null) {
            return false;
        }

        String longer = str1.length() >= str2.length() ? str1 : str2;
        String shorter = str1.length() >= str2.length() ? str2 : str1;
        int llen = longer.length();
        int slen = shorter.length();
    
        if (Math.abs(llen - slen) > 1) {
            return false;
        }

        int diffs = 0;
        for (int l=0,s=0; diffs < 2 && l < llen && s < slen; l++) {
            if (longer.charAt(l) == shorter.charAt(s)) {
                s++;
            } else {
                diffs++;
                if (slen == llen) {
                    s++;
                }
            }
        }
        return diffs < 2;
    }

    public String compress(String str) {
        // aabccccaaa -> a2b1c5a3
        String result = "";
        char letter = 0;
        int count = 0;
        for (Character c : str.toCharArray()) {
            if (letter == c) {
                count++;
            } else {
                if (count > 0) {
                    result += String.format("%d%c", count, letter);
                }
                count = 1;
                letter = c;
            }
        }
        if (count > 0) {
            result += String.format("%d%c", count, letter);
        }
        return result;
    }

    public int[][] rotate(int[][] matrix, int width) {
        int[][] newMatrix = new int[width][width];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                // [0][0] = [2][0] -> [width-1-j][i]
                newMatrix[i][j] = matrix[width-1-j][i];
            }
        }
        return newMatrix;
    }

    public int[][] rotateInPlace(int[][] matrix) {
        for (int i = 0; i < matrix.length / 2; i++) {
            for (int j = i; j < matrix.length - 1; j++) {
                // [0][0] = [2][0] -> [width-1-j][i]
                int x = 0;
                int y = 0;
                int temp = matrix[x][y];
                matrix[i][j] = matrix[matrix.length-1-j][i];
            }
        }
        return matrix;
    }
}

public final class Permutation {
    private Permutation() {
    }

    private static String printMatrix(int[][] matrix) {
        String foo = "";
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                foo += String.format("%02d", matrix[i][j]);
            }
            foo += "\n";
        }
        return foo;
    }

    private static int[][] createMatrix(int width) {
        int[][] matrix = new int[width][width];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                matrix[i][j] = i*width + j + 1;
            }
        }
        return matrix;
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        Permutations p = new Permutations();
        System.out.println("Permutation " + p.isPermutation("abcdefgabcdefg", "abcdefgabcdefg"));
        System.out.println("Pallindrome " + p.isPallindrome("tacocat"));
        System.out.println("Unique " + p.isUnique("chad"));
        System.out.println("UniqueNoStruct " + p.isUniqueNoStructs("chadd"));
        System.out.println("UniqueNoStruct2 " + p.isUniqueNoStructs2("chad"));
        System.out.println("Urlify " + p.urlify("Chad Ellis is awesome"));

        System.out.println("OneAway(pale, ple) = " + p.oneAway("pale", "ple"));
        System.out.println("OneAway(pales, pale) = " + p.oneAway("pales", "pale"));
        System.out.println("OneAway(pale, bale) = " + p.oneAway("pale", "bale"));
        System.out.println("OneAway(pale, bake) = " + p.oneAway("pale", "bake"));
        System.out.println("Compress(aabccccaaa) = " + p.compress("aabccccaaa"));
        System.out.println("Compress(aaabbbbbaaaaccccaaabbbb) = " + p.compress("aaabbbbbaaaaccccaaabbbb"));
        System.out.println("Compress(aaaaaaaaaa) = " + p.compress("aaaaaaaaaa"));

        int[][] matrix = createMatrix(3);
        System.out.println("RotateMatrix(\n" + printMatrix(matrix) + ") = (\n" + printMatrix(p.rotate(matrix, 3)) + ")");

        int[][] matrix4 = createMatrix(4);
        System.out.println("RotateMatrix(\n" + printMatrix(matrix4) + ") = (\n" + printMatrix(p.rotate(matrix4, 4)) + ")");

        int[][] matrix5 = createMatrix(5);
        System.out.println("RotateMatrix(\n" + printMatrix(matrix5) + ") = (\n" + printMatrix(p.rotate(matrix5, 5)) + ")");
    }
}
