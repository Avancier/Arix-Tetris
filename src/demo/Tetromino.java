package demo;

import java.util.Random;

public class Tetromino {
	protected Cell[] cells;
	protected Status[] status;
	private int index=10000;
	private Tetromino()
	{
		cells=new Cell[4];
	}
	public void moveLeft()
	{
		for(Cell c:cells)
		{
			c.left();
		}
	}
	public void moveRight()
	{
		//–¬for—≠ª∑ foreach—≠ª∑
		for(Cell c:cells)
		{
			c.right();
		} 
	}
	public void rotateRight(){
		index++;
		Status s=status[index%this.status.length];
		int row=cells[0].getRow();
		int col=cells[0].getCol();
		for(int i=0;i<4;i++)
		{
			cells[i].setRow(row+s.sArr[i].getRow());
			cells[i].setCol(col+s.sArr[i].getCol());
		}
	}
	public void rotateLeft(){
		index--;
		Status s=status[index%this.status.length];
		int row=cells[0].getRow();
		int col=cells[0].getCol();
		for(int i=0;i<4;i++)
		{
			cells[i].setRow(row+s.sArr[i].getRow());
			cells[i].setCol(col+s.sArr[i].getCol());
		}
	}
	public void softDrop()
	{
		for(Cell c:cells)
		{
			c.drop();
		}
	}
	public static Tetromino randomOne()
	{
		Random rdn=new Random();
		int k=rdn.nextInt(7);
		switch(k)
		{
		case 0:return new I();
		case 1:return new T();
		case 2:return new O();
		case 3:return new S();
		case 4:return new Z();
		case 5:return new L();
		case 6:return new J();
		default:
			return null;
		}
	}
	private class Status
	{
		Cell []sArr=new Cell[4];
		
 		Status(Cell f0t0,Cell f1t0,Cell f2t0,Cell f3t0)
 		{
 			this.sArr[0]=f0t0;
 			this.sArr[1]=f1t0;
 			this.sArr[2]=f2t0;
 			this.sArr[3]=f3t0;
 		}
	}
	private static class I extends Tetromino
	{
		I(){
			cells[0]=new Cell(0,4,Tetris.I);
			cells[1]=new Cell(0,3,Tetris.I);
			cells[2]=new Cell(0,5,Tetris.I);
			cells[3]=new Cell(0,6,Tetris.I);
			status=new Status[2];
			status[0]=new Status(
					new Cell(0,0),
					new Cell(0,-1),
					new Cell(0,1),
					new Cell(0,2));
			status[1]=new Status(
					new Cell(0,0),
					new Cell(-1,0),
					new Cell(1,0),
					new Cell(2,0));
			
		}
	}
	private static class O extends Tetromino
	{
		O(){
			cells[0]=new Cell(0,4,Tetris.O);
			cells[1]=new Cell(0,5,Tetris.O);
			cells[2]=new Cell(1,4,Tetris.O);
			cells[3]=new Cell(1,5,Tetris.O);
			status=new Status[1];
			status[0]=new Status(
					new Cell(0,0),
					new Cell(0,1),
					new Cell(1,0),
					new Cell(1,1));
		}
		
	}
	private static class T extends Tetromino
	{
		T(){
			cells[0]=new Cell(0,4,Tetris.T);
			cells[1]=new Cell(0,3,Tetris.T);
			cells[2]=new Cell(0,5,Tetris.T);
			cells[3]=new Cell(1,4,Tetris.T);
			status=new Status[4];
			status[0]=new Status(
					new Cell(0,0),
					new Cell(0,-1),
					new Cell(0,1),
					new Cell(1,0));
			status[1]=new Status(
					new Cell(0,0),
					new Cell(-1,0),
					new Cell(1,0),
					new Cell(0,-1));
			status[2]=new Status(
					new Cell(0,0),
					new Cell(0,1),
					new Cell(0,-1),
					new Cell(-1,0));
			status[3]=new Status(
					new Cell(0,0),
					new Cell(1,0),
					new Cell(-1,0),
					new Cell(0,1));
			
		}
		
	}
	private static class J extends Tetromino
	{
		J(){
			cells[0]=new Cell(0,4,Tetris.J);
			cells[1]=new Cell(0,3,Tetris.J);
			cells[2]=new Cell(0,5,Tetris.J);
			cells[3]=new Cell(1,5,Tetris.J);
			status=new Status[4];
			status[0]=new Status(
					new Cell(0,0),
					new Cell(0,-1),
					new Cell(0,1),
					new Cell(1,1));
			status[1]=new Status(
					new Cell(0,0),
					new Cell(-1,0),
					new Cell(1,0),
					new Cell(1,-1));
			status[2]=new Status(
					new Cell(0,0),
					new Cell(0,1),
					new Cell(0,-1),
					new Cell(-1,-1));
			status[3]=new Status(
					new Cell(0,0),
					new Cell(1,0),
					new Cell(-1,0),
					new Cell(-1,1));
		}
	}
	private static class L extends Tetromino
	{
		L(){
			cells[0]=new Cell(0,4,Tetris.L);
			cells[1]=new Cell(0,3,Tetris.L);
			cells[2]=new Cell(0,5,Tetris.L);
			cells[3]=new Cell(1,3,Tetris.L);
			status=new Status[4];
			status[0]=new Status(
					new Cell(0,0),
					new Cell(0,-1),
					new Cell(0,1),
					new Cell(1,1));
			status[1]=new Status(
					new Cell(0,0),
					new Cell(-1,0),
					new Cell(1,0),
					new Cell(-1,-1));
			status[2]=new Status(
					new Cell(0,0),
					new Cell(0,1),
					new Cell(0,-1),
					new Cell(-1,1));
			status[3]=new Status(
					new Cell(0,0),
					new Cell(1,0),
					new Cell(-1,0),
					new Cell(1,1));
		}
	}
	private static class Z extends Tetromino
	{
		Z(){
			cells[0]=new Cell(0,4,Tetris.Z);
			cells[1]=new Cell(0,3,Tetris.Z);
			cells[2]=new Cell(1,4,Tetris.Z);
			cells[3]=new Cell(1,5,Tetris.Z);
			status=new Status[2];
			status[0]=new Status(
					new Cell(0,0),
					new Cell(0,-1),
					new Cell(1,0),
					new Cell(1,1));
			status[1]=new Status(
					new Cell(0,0),
					new Cell(-1,0),
					new Cell(0,-1),
					new Cell(1,-1));
		}
	}
	private static class S extends Tetromino
	{
		S()
		{
			cells[0]=new Cell(1,4,Tetris.S);
			cells[1]=new Cell(1,3,Tetris.S);
			cells[2]=new Cell(0,4,Tetris.S);
			cells[3]=new Cell(0,5,Tetris.S);
			status=new Status[2];
			status[0]=new Status(
					new Cell(0,0),
					new Cell(0,-1),
					new Cell(-1,0),
					new Cell(-1,1));
			status[1]=new Status(
					new Cell(0,0),
					new Cell(-1,0),
					new Cell(0,1),
					new Cell(1,1));
		}
	}
}