@echo off
java --module-path "C:\Users\ryan.doriguetto\IdeaProjects\PI3\lib\javafx-sdk-17.0.15\lib" --add-modules javafx.controls,javafx.fxml -Dprism.order=sw -Dprism.forceGPU=false -jar PI-2025-1.0-SNAPSHOT-jar-with-dependencies.jar
pause
