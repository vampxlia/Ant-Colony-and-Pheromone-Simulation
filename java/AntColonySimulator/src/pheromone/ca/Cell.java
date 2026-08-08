package pheromone.ca;

import processing.core.PApplet;

public class Cell {
	protected int row;
	protected int col;
	protected int state;
	private Cell[] neighbours;
	protected CellularAutomata ca;
	
	public Cell(CellularAutomata ca, int row, int col) {
		this.ca = ca;
		this.row = row;
		this.col = col;
		this.state = 0;
		this.neighbours = null;
	}
	
	public void setNeighbours(Cell[] vizinhos) {
		this.neighbours = vizinhos;
	}
	
	public Cell[] getNeighbours(){
		return neighbours;
	}
	
	public void setState(int estado) {
		this.state = estado;
	}
	
	public int getState(){
		return state;
	}
	
	public void display(PApplet p) {
		p.pushStyle();
		p.noStroke();
		p.fill(ca.getStateColors()[state]);
		p.rect(ca.xmin+col*ca.cellWidth, ca.ymin +row*ca.cellHeight, ca.cellWidth, ca.cellHeight);
		p.popStyle();
	}
}
