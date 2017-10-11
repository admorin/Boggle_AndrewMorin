import javafx.scene.control.Alert;
import java.io.*;
import java.util.LinkedList;

public class Library {
    private int made;
    private int lineCount = 0;

    public Library (int made){
        this.made = made;
    }

    public String[] grabAll(){
        LinkedList<String> words = new LinkedList<>();
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("words/OpenEnglishWordList.txt")));
            String str;
            while ((str = reader.readLine()) != null){
                words.add(str.toLowerCase());
            }
        } catch (IOException e) {
            System.out.print("nope");
        }

        String[] array = words.toArray(new String[words.size()]);

        return array;
    }

    public boolean isWord(String word){
        try{
            System.out.println(word);
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("words/OpenEnglishWordList.txt")));
            String str;
            while ((str = reader.readLine()) != null){
                if(str.equals(word)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.print("nope");
        }

        return false;
    }

    private void showErrorDialog(String msg)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
