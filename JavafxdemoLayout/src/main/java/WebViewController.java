import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.fxml.FXML;
public class WebViewController {
    @FXML
    private WebView webView;

    @FXML
    private void initialize()
    {
        WebEngine engine = webView.getEngine();
        engine.load("https://www.youtube.com/");
    }
}