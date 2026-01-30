package pheromone;

import pheromone.ca.Cell;
import pheromone.ca.CellularAutomata;
import processing.core.PApplet;
import utils.gui.SubPlot;

public class Pheromones extends CellularAutomata{
    public Pheromones(PApplet p, SubPlot plt, int nrows, int ncols, int nStates, int radiusNeigh) {
        super(p, plt, nrows, ncols, nStates, radiusNeigh);
    }

    public void update(){
        for (int i=0;i<nrows;i++) {
            for (int j=0;j<ncols;j++) {
                Pheromone pheromone = (Pheromone) cells[i][j];
                pheromone.decay(0.01f);
            }
        }
    }

    @Override
    protected void createCells(){
        for (int i=0;i<nrows;i++) {
            for (int j=0;j<ncols;j++) {
                cells[i][j] = new Pheromone(this, i,j);
            }
        }
        setMooreNeighbours();
    }

}
