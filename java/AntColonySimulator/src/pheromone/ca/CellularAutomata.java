package pheromone.ca;

import processing.core.PApplet;
import processing.core.PVector;
import utils.gui.SubPlot;

public class CellularAutomata {
	protected int nrows;
	protected int ncols;
	protected int nStates;
	private int radiusNeigh;
	protected Cell[][] cells;
	private int[] colors;
	protected float cellWidth, cellHeight; //pixels
	public float xmin;
	public float ymin;
	private final SubPlot plt;
	
	public CellularAutomata(PApplet p, SubPlot plt, int nrows, int ncols, int nStates, int radiusNeigh) {
		this.nrows = nrows;
		this.ncols = ncols;
		this.nStates = nStates;
		this.radiusNeigh = radiusNeigh;
		cells = new Cell[nrows][ncols];
		colors = new int[nStates];
		float[] bb = plt.getBoundingBox();
		xmin = bb[0];
		ymin = bb[1];
		cellWidth = bb[2]/ncols;
		cellHeight = bb[3]/nrows;
		this.plt = plt;
		createCells();
		setStateColors(p);
	}
	
	public float getCellWidth() {
		return cellWidth;
	}
	
	public float getCellHeight() {
		return cellHeight;
	}
	
	public void setStateColors(PApplet p) {
		for(int i=0; i<nStates; i++) {
			colors[i] = p.color(p.random(255), p.random(255), p.random(255));
		}
	}
	
	public void setStateColors(int[] colors) {
		this.colors = colors;
	}
	
	public int[] getStateColors() {
		return colors;
	}
	
	protected void createCells() {
		for (int i=0;i<nrows;i++) {
			for (int j=0;j<ncols;j++) {
				cells[i][j] = new Cell(this, i,j);
			}
		}
		setMooreNeighbours();
	}
	
	public PVector getCenterCell(int row, int col) {
		float x = (col + 0.5f) * cellWidth;
		float y = (row + 0.5f) * cellHeight;
		double[] w = plt.getWorldCoord(x, y);
		return new PVector((float)w[0], (float)w[1]);
	}
	
	public Cell world2Cell(double x, double y) {
		float[] xy = plt.getPixelCoord(x,y);
		return pixel2Cell(xy[0], xy[1]);
	}
	
	public Cell pixel2Cell(float x, float y) {
		int row = (int)((y-ymin)/cellHeight);
		int col = (int)((x-xmin)/cellWidth);
		if(row>= nrows) row = nrows - 1;
		if(col>= ncols) col = ncols - 1;
		return cells[row][col];
	}

	protected void setMooreNeighbours() {
		int NN = (int)Math.pow(2*radiusNeigh+1,2);
		for (int i=0;i<nrows;i++) {
			for (int j=0;j<ncols;j++) {
				Cell[] neigh = new Cell[NN];
				int n=0;
				for (int ii=-radiusNeigh;ii<=radiusNeigh;ii++) {
					int row = (i + ii + nrows)%nrows;
					for (int jj=-radiusNeigh;jj<=radiusNeigh;jj++) {
						int col = (j + jj + ncols)%ncols;
						neigh[n++] = cells[row][col];
					}
				}
				cells[i][j].setNeighbours(neigh);
			}
		}
	}
	
	public void display(PApplet p) {
		for (int i=0;i<nrows;i++) {
			for (int j=0;j<ncols;j++) {
				cells[i][j].display(p);
			}
		}
	}
}
