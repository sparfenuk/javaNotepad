import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Program extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

        Scene scene = new Scene(root, 750, 600);

        stage.setTitle("Notepad");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {


        Application.launch();

//        SearchX searchX = new SearchX(200);
//        searchX.run();
//        while (!searchX.isEnded){}
//        System.out.println(searchX.fibonachi.get(100));
    }
}