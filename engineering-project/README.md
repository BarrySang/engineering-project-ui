# Engineering Project UI

This project is a JavaFX desktop application for a ventilation control panel. It allows a user to select an activity, enter the expected number of people in a space, and calculate the required airflow rate based on a predefined reference table before sending the command to an Arduino-based ventilation controller.

## What the app does

- Presents a simple control panel with:
  - an activity selector
  - a people-count field
  - a button to update the ventilation speed
- Reads activity-to-airflow values from `src/projectui/activities.txt`
- Multiplies the selected activity's airflow requirement by the number of occupants
- Opens a serial connection to the configured Arduino/serial device and sends the value

This is intended for use with a ventilation or fan control system that needs to respond to room occupancy and usage type.

## Project structure

- `src/projectui/App.java` — JavaFX application entry point
- `src/projectui/Controller.java` — UI logic and serial communication
- `src/projectui/app.fxml` — JavaFX layout definition
- `src/projectui/styles.css` — lightweight styling
- `src/projectui/activities.txt` — activity-to-flow-rate lookup data
- `.vscode/launch.json` — VS Code launch configuration for running the app

## Technology stack

- Java
- JavaFX
- RXTX serial library (`gnu.io`)
- Arduino-compatible serial hardware

## Prerequisites

- JDK 11+
- JavaFX SDK
- RXTX library available in the project classpath
- A connected serial device (currently configured for Linux `/dev/ttyACM0` in the controller)

## Running the app

Open the project in VS Code with the Java extension installed and run the `App` launch configuration defined in `.vscode/launch.json`.

If you are launching it manually from the command line, set up the JavaFX module path and classpath first, then run:

```bash
java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -cp bin:lib/* projectui.App
```

## Notes

The project currently includes hardware-specific configuration such as the serial port `"/dev/ttyACM0"` and an absolute Linux path to the activity data file. If you run this on another machine or OS, update those values in `Controller.java` to match your environment.
