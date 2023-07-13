package controller;

        import com.jfoenix.controls.JFXTextArea;
        import com.jfoenix.controls.JFXTextField;
        import javafx.event.ActionEvent;
        import javafx.scene.control.Label;
        import javafx.scene.layout.AnchorPane;

        import java.io.DataInputStream;
        import java.io.DataOutputStream;
        import java.io.File;
        import java.io.IOException;
        import java.net.Socket;

public class ChatWallForm {
    public AnchorPane clientPane;
    public JFXTextArea txtClientAreamassage;
    public JFXTextField txtwriteMassage;
    public Label lblUserName;

    private String username;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    Socket socket;

    public ChatWallForm(){
        new Thread(()-> {
            try {
                socket = new Socket("localhost", 5000);
                this.username = LoginFormController.getUserName();
                System.out.println(username);
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());
                Client client = new Client(socket,username, dataOutputStream,dataInputStream);
                //lblUserName.setText(LoginForm.getUserName());
                listenForMessages();
                client.sendUserName();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void btnSentMassageOnAction(ActionEvent event) {
        String clientMessage = txtwriteMassage.getText();

        try {
            dataOutputStream.writeUTF(username +" : "+clientMessage);
            dataOutputStream.flush();
            txtClientAreamassage.appendText("\n"+clientMessage);
            txtwriteMassage.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void listenForMessages() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageFromGroupChat;
                while (socket.isConnected()) {
                    try {
                        messageFromGroupChat = dataInputStream.readUTF();
                        txtClientAreamassage.appendText("\n"+messageFromGroupChat);
                    } catch (IOException e) {
                        closeAll(socket, dataInputStream, dataOutputStream);
                    }
                }
            }
        }).start();
    }

    public void closeAll(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        try {
            if (dataInputStream != null) {
                dataInputStream.close();
            }
            if (dataOutputStream != null) {
                dataOutputStream.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cameraBtnOnAction(ActionEvent event) {

    }

    public void btnSendImage(ActionEvent actionEvent) {
    }

    public void txtOnAction(ActionEvent actionEvent) {
    }
}
