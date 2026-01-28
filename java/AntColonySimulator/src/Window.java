import processing.core.PApplet;

public class Window extends PApplet {
    Configs configs = new Configs();

    public void settings() {
        size(configs.getxWindow(), configs.getyWindow());
    }

    public void setup() {
        background(0);
    }

    public void draw() {
        background(0);
    }

    public static void main(String[] args) {
        Window window = new Window();
        PApplet.runSketch(new String[]{"AntColony"}, window);
    }
}
