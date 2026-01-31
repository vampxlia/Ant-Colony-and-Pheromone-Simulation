package pheromone;

import ant.AntState;
import food.Food;
import nest.Nest;
import pheromone.ca.CellularAutomata;
import processing.core.PApplet;
import processing.core.PVector;
import utils.SceneObject;
import utils.gui.SubPlot;

import java.util.ArrayList;

public class Pheromones extends CellularAutomata{
    public Pheromones(PApplet p, SubPlot plt, int nrows, int ncols, int nStates, int radiusNeigh) {
        super(p, plt, nrows, ncols, nStates, radiusNeigh);
    }

    public void update(){
        for (int i=0;i<nrows;i++) {
            for (int j=0;j<ncols;j++) {
                Pheromone pheromone = (Pheromone) cells[i][j];
                pheromone.decay(0.001f);
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

    @Override
    public Pheromone pixel2Cell(float x, float y) {
        int row = (int)((y-ymin)/cellHeight);
        int col = (int)((x-xmin)/cellWidth);
        if(row>= nrows) row = nrows - 1;
        if(col>= ncols) col = ncols - 1;
        return (Pheromone) cells[row][col];
    }

    public PVector getSteeringForce(PVector pos, AntState state){

        // 1. Converter posição da formiga → célula atual
        Pheromone center = (Pheromone) world2Cell(pos.x, pos.y);
        int row = center.getRow();
        int col = center.getCol();

        PVector force = new PVector();
        int vizinhanca = 10;
        // 2. Percorrer vizinhança Moore (3x3)
        for (int dr = -vizinhanca; dr <= vizinhanca; dr++) {
            for (int dc = -vizinhanca; dc <= vizinhanca; dc++) {

                // ignora a célula central
                if (dr == 0 && dc == 0) continue;

                int r = row + dr;
                int c = col + dc;

                // 3. Verificar limites
                if (r < 0 || r >= nrows || c < 0 || c >= ncols) continue;

                Pheromone ph = (Pheromone) cells[r][c];
                float intensity = ph.getIntensity(state);

                if (intensity <= 0) continue;

                // 4. Direção REAL no espaço do mundo
                PVector cellCenter = getCenterCell(r, c);
                PVector dir = PVector.sub(cellCenter, pos);

                if (dir.magSq() == 0) continue;

                dir.normalize();
                dir.mult(intensity);

                force.add(dir);
            }
        }

        return force;
    }


}
