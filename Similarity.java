import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 08-722 Data Structures for Application Programmers.
 *
 * Homework Assignment 5
 * HashMap Implementation
 *
 * Andrew ID: rushabhs
 * @author
 */

public class Similarity {
    /**
     * elements is to store the number of elements in the hashMap for each object.
     */
    private BigInteger elements = new BigInteger("0");

    /**
     * noDups is used to store non-duplicate words in the hashMap.
     */
    private BigInteger noDups = new BigInteger("0");

    /**
     * lines is used to store the number of lines in hashMap.
     */
    private BigInteger lines = new BigInteger("0");

    /**
     * map is the hashMap for each object to store key value pair.
     * I have used HashMap to store the string and their frequencies as it is the
     * best datastructure for this purpose plus there is a method that asks for
     * returning a Map. I have used BigInteger instead of int for storing number
     * number of words as it has to be used while calculating euclidean and dot
     * product and multiplication of 2 such big numbers might not fit in an int
     */
    private Map<String, BigInteger> map;

    /**
     * Similarity is the constructor to initialize the hashMap and add key value pairs.
     * lines is set to 1 in this case
     * @param string is the the string on which we need to perform split operation
     * worst-case running Time complexity: O(n)
     */
    public Similarity(String string) {
        if (string == null || string.length() == 0) {
            return;
        }
        map = new HashMap<>();
        String[] splitWords = string.toLowerCase().split("\\W");
        lines = new BigInteger("1");
        for (String word : splitWords) {
            if (validate(word)) {
                elements = elements.add(new BigInteger("1"));
                addElements(word.toLowerCase());
            }
        }
    }

    /**
     * Similarity is the constructor to initialize the hashMap and add key value pair from file.
     * lines is incremented by one each time a line is read
     * @param file is the file whose content needs to be read
     */
    public Similarity(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        Scanner scanner = null;
        map = new HashMap<>();
        try {
            scanner = new Scanner(file, "latin1");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines = lines.add(new BigInteger("1"));
                String[] wordsFromText = line.split("\\W");
                for (String word : wordsFromText) {
                    if (validate(word)) {
                        elements = elements.add(new BigInteger("1"));
                        addElements(word.toLowerCase());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Cannot find the file");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    /**
     * addElements method is a helper method to add the string in the hashMap in right way.
     * @param s is the string to be added to the hashMap
     * worst-case running Time complexity: O(1)
     */
    private void addElements(String s) {
        if (s == null) {
            return;
        }
        if (map.containsKey(s)) {
            map.put(s,  map.get(s).add(new BigInteger("1")));
        } else {
            map.put(s, new BigInteger("1"));
            noDups = noDups.add(new BigInteger("1"));
        }
    }

    /**
     * validate method is a helper method to validate the string to be added to the hashMap.
     * @param s is the string to be validated and added to the hashMap
     * @return check is the boolean value to check if its a valid word
     * worst-case running Time complexity: O(1)
     */
    private boolean validate(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        boolean check = s.matches("[a-zA-Z]+");
        return check;
    }

    /**
     * numOfLines method is a method to find the number of lines read.
     * from the file
     * @return lines is the number of lines read from the file
     * worst-case running Time complexity: O(1)
     */
    public int numOfLines() {
        return lines.intValue();
    }

    /**
     * numOfWords method is a method to find the number of words added to hashMap.
     * @return elements is the number of words added to hashMap
     * worst-case running Time complexity: O(1)
     */
    public BigInteger numOfWords() {
        return elements;
    }

    /**
     * numOfWordsNoDups method is a method to find the number of non-duplicate.
     * words added to the hashMap
     * @return noDups is the number of non-duplicate worfds added to hashMap
     * worst-case running Time complexity: O(1)
     */
    public int numOfWordsNoDups() {
        return noDups.intValue();
    }

    /**
     * euclideanNorm method is a method to find the euclideanNorm for hashMap.
     * @return euclidean is the euclideanNorm of the hashMap
     * worst-case running Time complexity: O(n)
     */
    public double euclideanNorm() {
        return euclideanNorm(map);
    }

    /**
     * euclideanNorm method is a helper method to find the euclideanNorm for hashMap.
     * @param map1 is the hashMap whose euclideanNorm needs to be found
     * @return answer is the euclideanNorm of the hashMap
     * worst-case running Time complexity: O(n)
     */
    private double euclideanNorm(Map<String, BigInteger> map1) {
        BigInteger e = new BigInteger("0");
        double answer;
        if (map1 == null || map1.isEmpty()) {
            return 0.0;
        }
        for (Map.Entry<String, BigInteger> entry : map1.entrySet()) {
            e = e.add(entry.getValue().pow(2));
        }
        answer  = Math.sqrt(e.doubleValue());
        return answer;
    }

    /**
     * dotProduct method is a method to find the dot product of frequency vectors of hashMaps.
     * @param map1 is one of the hashMaps whose euclideanNorm needs to be found
     * @return answer is the euclideanNorm of the hashMap
     * worst-case running Time complexity: O(n)
     */
    public double dotProduct(Map<String, BigInteger> map1) {
        if (map1 == null || map1.isEmpty()) {
            return 0.0;
        }
        BigInteger dot = new BigInteger("0");
        double answer;
        for (Map.Entry<String, BigInteger> entry : getMap().entrySet()) {
            if (map1.containsKey(entry.getKey())) {
                dot = dot.add(entry.getValue().multiply(map1.get(entry.getKey())));
            }
        }
        answer = dot.doubleValue();
        return answer;
    }

    /**
     * distance method is a method to find the distance between two files whose content.
     * is stored in hashMaps
     * @param map1 is the hashMap whose euclideanNorm needs to be found
     * @return distance is the euclideanNorm of the hashMap
     * worst-case running Time complexity: O(1)
     */
    public double distance(Map<String, BigInteger> map1) {
        if (map1 == null || map1.isEmpty()) {
            return (Math.PI / 2);
        }
        Map<String, BigInteger> mcurrent = getMap();
        if (mcurrent == null || mcurrent.isEmpty()) {
            return (Math.PI / 2);
        }
        double dot = this.dotProduct(map1);
        if (dot == 0.0) {
            return (Math.PI / 2);
        }
        if (mcurrent.equals(map1)) {
            return 0.0;
        }
        double distance;
        double euclidean2 = euclideanNorm(map1);
        distance = Math.acos(dot / ((euclideanNorm(mcurrent)) * euclidean2));
        return distance;
    }

    /**
     * getMap method is a method to get the hashMap for that object.
     * @return this.map is the hashMap for the current object
     * worst-case running Time complexity: O(1n)
     */
    public Map<String, BigInteger> getMap() {
        Map<String, BigInteger> temp = new HashMap<>();
        if (map != null || !map.isEmpty()) {
            for (Map.Entry<String, BigInteger> entry : map.entrySet()) {
                temp.put(entry.getKey(), entry.getValue());
            }
        }
        return temp;
    }
}

