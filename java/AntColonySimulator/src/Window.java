import processing.core.PApplet;
import processing.core.PImage;

public class Window extends PApplet {
    Configs configs = new Configs();
    PImage ant;
    PImage antFood;
    PImage food;
    PImage nest;
    public void settings() {
        size(configs.getxWindow(), configs.getyWindow());
        ant = loadImage("assets/ant.png");
        antFood = loadImage("assets/ant.png");
        food = loadImage("assets/ant.png");
        nest = loadImage("assets/ant.png");

    }

    public void setup() {
        background(0);
    }

    public void draw() {
        background(0);
        image(img, 0, 0);
    }

    public static void main(String[] args) {
        Window window = new Window();
        PApplet.runSketch(new String[]{"AntColony"}, window);
    }
}
