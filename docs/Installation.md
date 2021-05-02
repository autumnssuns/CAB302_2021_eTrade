## Installations
The project were developed using Amazon Corretto Java Development Toolkit (jdk15.0.2_7). To install, follow the instructions at https://docs.aws.amazon.com/corretto/latest/corretto-15-ug/downloads-list.html
### A. Download This Project
Please skip this step if you have access to the project via a .zip file.

1. In IntelliJ IDEA, go to File -> New -> Project from Version Control...
2. In this project's GitHub, click "Code" and copy the HTTPS URL (https://github.com/autumnssuns/Sem1_2021_CAB302_Group024_eTrade.git)
3. Paste this URL into the URL field in "Get from Version Control" window in IntelliJ.
4. Edit the "Directory" field as needed and click "Clone".

_Note: Set the project SDK to SDK 15, if it has not been done so:_
1. In the project, go to File -> Project Structure...
2. In the project SDK field, select "15 Amazon Corretto 15.0.2".
3. Click "OK".
### B. Download Required External Libraries
#### JavaFX for GUI
1. Download the appropriate version of [JavaFX SDK](https://gluonhq.com/products/javafx/) for your operating system and unzip to a desired location.
2. In the project, go to File -> Project Structure...
3. Select "Libraries" tab.
4. Click "New Project Library" ("+" button) -> Java.
5. Point to the "lib" directory of the JavaFX SDK and add the module.
6. From the project, go to Run -> Edit Configuration... and add a VM option:
(Windows) --module-path "\path\to\javafx-sdk\lib" --add-modules javafx.controls,javafx.fxml
(Linux/Mac) --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
Replace the path\to\javafx-sdk\lib with the appropriate location of the JavaSDK "lib" directory.

Video Tutorial: https://www.youtube.com/watch?v=Ope4icw6bVk&ab_channel=BroCode

For more information, access https://openjfx.io/openjfx-docs/
 
#### SQLite3 for DB (Guide on how to download & add jdbc-driver library)

Information source: https://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver/

Install driver for SQLite (video tutorial): https://www.youtube.com/watch?v=293M9-QRZ0c&t=741s&ab_channel=CodeJava

For intellij: https://www.jetbrains.com/help/idea/connecting-to-a-database.html

### C. Run this project
1. Open the project in IntelliJ.
2. Navigate to `src` -> `client` -> `Main`
3. Right click on Main and select `Run 'Main'`
