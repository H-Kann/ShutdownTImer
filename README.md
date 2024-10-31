![Banner2](https://github.com/user-attachments/assets/b84b2f55-8d56-4ac0-8d71-cad90a96edf8)
The Shutdown Timer is a Java-based application designed to schedule and manage system shutdowns on Windows machines.
 

Using Maven for dependency management and build automation, this application provides a simple user interface that allows users to set a timer for shutting down their system after a specified duration.

# Screenshots
<p align="center"><img src="https://github.com/user-attachments/assets/9f5ce800-5bac-4491-b695-a7dcc81429e6"></p>


<p align="center"><img src="https://github.com/user-attachments/assets/b47a383f-7064-4192-86a0-0b2105ee3fd4"></p>



# Key Features:
<li><b>User-Friendly Interface:</b> A simple graphical user interface (GUI) that allows users to input the desired shutdown time.</li>
<li><b>Timer Functionality:</b> Users can specify a countdown duration (in hours, minutes and seconds) after which the system will automatically shut down.</li>
<li><b>Cancel Shutdown:</b> The application provides an option to cancel the scheduled shutdown, giving users flexibility if their plans change.</li>
<li><b>Notifications:</b> Users receive notifications when the shutdown timer starts, ensuring they are aware of the upcoming shutdown.</li>

# Tools Used:
<li><b>Java:</b> The core programming language for building the application.</li>
<li><b>Maven:</b> Used for managing project dependencies, building the project, and packaging the application.</li>
<li><b>JavaFX:</b> For creating the GUI.</li>

# How It Works:
<li><b>Input Timer:</b> Users enter the desired time duration using the spinners.</li>

<li><b>Start Countdown:</b> Upon pressing the start button, the application starts a countdown timer.</li>

<li><b>Shutdown Command:</b> Before the timer starts, the application executes the Windows shutdown command using </li>

  ```java
ProcessBuilder("shutdown", "-s", "-t", String.valueOf(time))
  ```
<li><b>Cancel Option:</b> Users can click a "Cancel" button to stop the countdown and prevent shutdown.
