package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class LoginFormController {
    public AnchorPane loginpane;
    public JFXTextField txtUserName;
    private static String username;
    public AnchorPane clientPane;
    public AnchorPane LoginContext;
    public JFXTextField txtwriteMassage;

    public void btnJoinOnAction(ActionEvent event) {
       username = txtUserName.getText();
        try {
            loadClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadClient() throws IOException {
        URL resource = getClass().getResource("/view/ClientForm.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        LoginContext.getChildren().clear();
        LoginContext.getChildren().add(load);
    }

    public static String getUserName(){
        return username;
    }

    public void btnSentMassageOnAction(ActionEvent actionEvent) {
    }

    public void cameraBtnOnAction(ActionEvent actionEvent) {
    }

    public void btnSendImage(ActionEvent actionEvent) {
    }
}
