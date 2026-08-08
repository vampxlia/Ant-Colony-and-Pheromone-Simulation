package pheromone;

import pheromone.ca.Cell;
import pheromone.ca.CellularAutomata;
import processing.core.PApplet;
import processing.core.PVector;
import utils.SceneObject;


public class Pheromone extends Cell implements SceneObject {
    protected float searchIntensity;
    protected float returnIntensity;

    public Pheromone(CellularAutomata ca, int row, int col) {
        super(ca, row, col);
        this.searchIntensity = 0;
        this.returnIntensity = 0;
    }

    public float getIntensity(){
        if (searchIntensity>0){
            return searchIntensity;
        }
        else if (returnIntensity>0){
            return returnIntensity;
        }
        return 0f;
    }

    public float getRadius(){
        return 0.5f;
    }

    public void decay(float decayRate){
        //this.searchIntensity = (int) (this.searchIntensity - (this.searchIntensity * decayRate));
        //this.returnIntensity = (int) (this.returnIntensity  - (this.returnIntensity * decayRate));
        this.searchIntensity = Math.max(this.searchIntensity - decayRate, 0);
        this.returnIntensity = Math.max(this.returnIntensity - decayRate, 0);
    }

    public void newReturnPheromone(float intensity){
        this.returnIntensity = intensity;
    }

    public void newSearchPheromone(float intensity){
        this.searchIntensity = intensity;
    }

    @Override
    public void display(PApplet p){
        int searchAlpha = (int) (160 * searchIntensity);
        int returnAlpha = (int) (160 * returnIntensity);

        if(this.searchIntensity > 100) System.out.println(searchIntensity);


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
        return new PVector((ca.xmin + col * ca.getCellWidth()) + (ca.getCellWidth() / 2),
                           (ca.ymin + row * ca.getCellHeight()) + (ca.getCellHeight() / 2));
                              //inicio plot + nCelulas*tamanho + tamanho / 2
    }

    public void remove() {
        searchIntensity = 0f;
        returnIntensity = 0f;
    }
}
