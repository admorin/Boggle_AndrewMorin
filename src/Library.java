import javafx.scene.control.Alert;
import java.io.*;

public class Library {
    private int made;
    private int lineCount = 0;

    public Library (int made){
        this.made = made;
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
