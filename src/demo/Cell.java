package demo;

import java.awt.image.BufferedImage;

public class Cell {
	private int row;
	private int col;
	private BufferedImage image;
	//*Œﬁ≤Œππ‘Ï∑Ω∑®
	public Cell(){
		this(0,0,null);
	}
	//*”–≤Œππ‘Ï∑Ω∑®
	public Cell(int row,int col)
	{
		this(row,col,null);
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}

	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public Cell(int row,int col,BufferedImage image)
	{
		//TODO this
		this.row=row;
		this.col=col;
		this.image=image;
	}
	void left()
	{
		col--;
	}
	void right()
	{
		col++;
	}
 	void drop()
 	{
 		row++;
 	}
}

