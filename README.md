# Engineering Project UI

This repository contains a JavaFX-based ventilation control interface for an engineering project. The application helps a user choose a room activity, enter the expected occupancy, and calculate a target airflow rate before sending that value to an Arduino-compatible device over a serial connection.

## Project contents

The main application lives in the `engineering-project` folder and includes:

- JavaFX UI code for the control panel
- lookup data for activity-based airflow settings
- hardware serial communication logic for controlling a ventilation system

## App behavior

- A user selects an activity from a list of predefined room types
- The expected number of people is entered in a text field
- The app reads the corresponding flow-rate value from `activities.txt`
- The value is multiplied by the occupancy count to estimate the required airflow
- The result is sent to the connected device through the serial port

## Repository layout

- `engineering-project/src/projectui` — Java source and UI assets
- `engineering-project/src/projectui/activities.txt` — activity-to-flow-rate table
- `engineering-project/.vscode` — VS Code project configuration
- `engineering-project/lib` — project libraries

## Tech stack

- Java
- JavaFX
- RXTX serial library
- Arduino-compatible serial hardware

## Getting started

1. Install Java and JavaFX.
2. Add the required RXTX and JavaFX libraries to the project classpath.
3. Open `engineering-project` in VS Code.
4. Run the `App` configuration from `.vscode/launch.json`.

## Important note

This project currently contains Linux-specific settings, including the serial port `/dev/ttyACM0` and a hardcoded absolute file path. If you run it on a different machine, update those values in `Controller.java` to match your environment.
