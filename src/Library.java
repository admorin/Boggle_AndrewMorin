/**
 * Andrew Morin
 * October 10, 2017
 */

import java.io.*;
import java.util.LinkedList;

class Library {
    /**
     * Library just turns the txt file given to us and turns it into a linked list of strings. This is essential
     * to my process of finding the correct words.
     */

    String[] grabAll(){
        LinkedList<String> words = new LinkedList<>();
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("words/OpenEnglishWordList.txt")));
            String str;
            while ((str = reader.readLine()) != null){
                words.add(str.toLowerCase());
            }
        } catch (IOException e) {
            System.out.print("File not found!");
        }

        return words.toArray(new String[words.size()]);
    }
}
