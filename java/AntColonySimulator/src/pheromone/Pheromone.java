package pheromone;

import pheromone.ca.Cell;
import pheromone.ca.CellularAutomata;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import utils.SceneObject;



public class Pheromone extends Cell implements SceneObject {
    private int searchIntensity;
    private int returnIntensity;

    public Pheromone(CellularAutomata ca, int row, int col) {
        super(ca, row, col);
        this.searchIntensity = 0;
        this.returnIntensity = 0;
    }

    public void decay(float decayRate){
        this.searchIntensity = (int) (this.searchIntensity * decayRate);
        this.returnIntensity = (int) (this.returnIntensity * decayRate);
    }

    public void newReturnPheromone(){
        this.returnIntensity = 1;
    }

    public void newSearchPheromone(){
        this.searchIntensity = 1;
    }

    @Override
    public void display(PApplet p){
        int searchAlpha = 255 * searchIntensity;
        int returnAlpha = 255 * returnIntensity;

        p.pushStyle();
        p.noStroke();
        p.fill(255,0,0,searchAlpha);
        p.rect(ca.xmin+ super.col * ca.getCellWidth(), ca.ymin + super.row * ca.getCellHeight(), ca.getCellWidth(), ca.getCellHeight());
        p.popStyle();

        p.pushStyle();
        p.noStroke();
        p.fill(0,0,255,returnAlpha);
        p.rect(ca.xmin+ super.col * ca.getCellWidth(), ca.ymin + super.row * ca.getCellHeight(), ca.getCellWidth(), ca.getCellHeight());
        p.popStyle();
    }

    @Override
    public PVector getPos() {
        return null;
    }
}
