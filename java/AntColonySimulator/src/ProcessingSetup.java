import processing.core.PApplet;
import utils.gui.IProcessingApp;

public class ProcessingSetup extends PApplet {
	public static IProcessingApp app;
	private int lastUpdate; 
	
	public void settings() {
		size(500,500);
		app.settings(this);
	}
	
	public void setup() {
		app.setup(this);
		lastUpdate = millis();
	}
	
	public void draw() {
		int now = millis();
		float dt = (now-lastUpdate)/1000f;
		lastUpdate = now;
		app.draw(this, dt);
	}

	public void mousePressed(){
		app.mousePressed(this);
	}

	public void keyPressed(){
		app.keyPressed(this);
	}
	
	
	public static void main(String[] args) {
		app = new AntColonySimulator();
		PApplet.main(ProcessingSetup.class);
	}
}
