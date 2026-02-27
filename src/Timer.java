public class Timer {
int totalTime = 60; // Countdown starting from 60 seconds
int startTime;

void setup() {
  size(400, 400);
  textAlign(CENTER, CENTER);
  textSize(64);
  startTime = millis(); // Record the exact time the program started
}

void draw() {
  background(50);
  
  // Calculate elapsed seconds
  int elapsedSeconds = (millis() - startTime) / 1000;
  int remainingTime = totalTime - elapsedSeconds;
  
  if (remainingTime >= 0) {
    fill(255);
    text(remainingTime, width/2, height/2);
  } else {
    fill(255, 0, 0);
    text("TIME'S UP!", width/2, height/2);
  }
}

}
