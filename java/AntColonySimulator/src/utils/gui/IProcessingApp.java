package utils.gui;
import processing.core.PApplet;
import processing.core.PImage;

public interface IProcessingApp {
	public void settings(PApplet p);
	public void setup(PApplet p);
	public void draw(PApplet p, float dt);
	public void mousePressed(PApplet p);
	public void keyPressed(PApplet p);
}
