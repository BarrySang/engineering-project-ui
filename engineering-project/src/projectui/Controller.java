package projectui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
// import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gnu.io.*;
// import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller implements Initializable {

    @FXML
    private ChoiceBox<String> activityInput;
    @FXML
    private Label titleLabel, activityLabel, numberPeopleLabel;
    @FXML
    private Button changeSpeedBtn;
    @FXML
    private TextField numberPeopleInput;
    
    private List<String> activities = new ArrayList<>();

    private String filePath = "/home/sang/coding/javafx-projects/engineering-project/engineering-project/src/projectui/activities.txt";
    private String PORT_NAME = "/dev/ttyACM0";
    private int BAUD_RATE = 9600;

    private void changeSpeed(ActionEvent event) {
        // System.out.println("button clicked");

        // get value of the 'activity' input
        String activity = activityInput.getValue();

        // get value of 'number of people' input
        int numberPeople = Integer.parseInt(numberPeopleInput.getText());

        // get the corresponding flow rate
        try (BufferedReader bfRd = new BufferedReader(new FileReader(filePath))) {
            // hold contents of the 'current' line
            String currentLine;
            // System.out.println("hey");
        
            // check if the current line, if it does - store the contents in the list 'activities'
            while ((currentLine = bfRd.readLine()) != null) {
                if (currentLine.contains(activity)) {
                    // System.out.println(currentLine);
                    String[] lineParts = currentLine.split(",");

                    if(lineParts.length > 1) {
                        // get flow rate
                        int flowRatePart = Integer.parseInt(lineParts[1].trim());

                        // multiply 'flow rate' with 'number of people'
                        int requiredFlowRate = flowRatePart * numberPeople;

                        System.out.println(requiredFlowRate);

                        // open the serial port connection
                        try {
                            SerialPort serialPort = (SerialPort) CommPortIdentifier.getPortIdentifier(PORT_NAME).open(Controller.class.getName(), BAUD_RATE);

                            // set serial port parameters
                            serialPort.setSerialPortParams(BAUD_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

                            // get input and output streams
                            // InputStream inputStream = serialPort.getInputStream();
                            OutputStream outputStream = serialPort.getOutputStream();

                            // send data to arduino
                            outputStream.write((String.valueOf(numberPeople)).getBytes());
                            

                            // close the serial port connection
                            serialPort.close();

                        } catch (Exception e) {
                            // print errors
                            e.printStackTrace();
                        }
                    }
                }
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set component properties
        titleLabel.setText("Ventilation Control Panel");
        activityLabel.setText("Select activity: ");
        numberPeopleLabel.setText("Expected number of people: ");
        changeSpeedBtn.setText("Change Speed");
        changeSpeedBtn.setOnAction(this::changeSpeed);


        // get activities list
        try (BufferedReader bfRd = new BufferedReader(new FileReader(filePath))) {
            // declare variable to hold contents of the 'current' line
            String activity;
        
            // check if the current line, if it does - store the contents in the list 'activities'
            while ((activity = bfRd.readLine()) != null) {
                String[] lineParts = activity.split(",", 2);

                if(lineParts.length > 0) {
                    String activityPart = lineParts[0].trim();

                    activities.add(activityPart);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // send values to 'activityInput' choicebox
        activityInput.getItems().addAll(activities);
    }
}