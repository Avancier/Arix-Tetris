package demo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Tetris extends JPanel{
	
	public static final int ROWS=20;
	public static final int COLS=10;
	public static final int CELL_SIZE=26;
	public static BufferedImage backGround;
	public static BufferedImage gameOver;
	public static BufferedImage I;
	public static BufferedImage T;
	public static BufferedImage O;
	public static BufferedImage S;
	public static BufferedImage Z;
	public static BufferedImage L;
	public static BufferedImage J;
    int line;
    int score;
	int hangshu;
	int sum=0;
	private Cell[][] wall=new Cell[ROWS][COLS];
	private Tetromino tetromino;
	private Tetromino nextOne;
	private boolean isPaused;
	private boolean isGameOver;
	private Timer timer;
	static{
		try {
			backGround=ImageIO.read(Tetris.class.getResource("tetris.png"));
		    gameOver=ImageIO.read(Tetris.class.getResource("game-over.png"));
		    I=ImageIO.read(Tetris.class.getResource("I.png"));
		    T=ImageIO.read(Tetris.class.getResource("T.png"));
		    O=ImageIO.read(Tetris.class.getResource("O.png"));
		    S=ImageIO.read(Tetris.class.getResource("S.png"));
		    Z=ImageIO.read(Tetris.class.getResource("Z.png"));
		    L=ImageIO.read(Tetris.class.getResource("L.png"));
		    J=ImageIO.read(Tetris.class.getResource("J.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void paint(Graphics g){
		g.drawImage(backGround, 0, 0, null);
		//“∆π˝»•
		g.translate(15, 15);
		paintWall(g);
		paintTetromino(g);
		paintNextOne(g);
		paintScore(g);
		paintLines(g);
		//“∆ªÿ¿¥
		g.translate(-15, -15);
		if(isGameOver){
			g.drawImage(gameOver, 0, 0, null);
		}
	}
	private void paintWall(Graphics g) {
		for(int row=0;row<ROWS;row++){
			for(int col=0;col<COLS;col++){
				Cell c= wall[row][col];
				int x=CELL_SIZE*col;
				int y=CELL_SIZE*row;
				if(c==null){
					g.setColor(new Color(0));
					g.drawRect(x,y,CELL_SIZE,CELL_SIZE);
				}else{
					g.drawImage(c.getImage(), x-1, y-1,null);
				}
			}
		}
	}
	private void paintTetromino(Graphics g) {
		if(tetromino==null){
			return ;
		}
		Cell[] cells=tetromino.cells;
		for(Cell c:cells){
			int x=c.getCol()*CELL_SIZE;
			int y=c.getRow()*CELL_SIZE;
			g.drawImage(c.getImage(), x-1, y-1, null);
		}
	}
	private void paintNextOne(Graphics g) {
		if(nextOne==null){
			return ;
		}
		Cell[] cells=nextOne.cells;
		for(Cell c:cells){
			int x=(c.getCol()+10)*CELL_SIZE;
			int y=(c.getRow()+1)*CELL_SIZE;
			g.drawImage(c.getImage(), x-1, y-1, null);
		}
	}
	private void paintScore(Graphics g) {
		g.drawString("∑÷ ˝£∫ "+score, 320,160);
	}
	private void paintLines(Graphics g) {
		g.drawString("µ±«∞À˘œ˚–– ˝£∫"+hangshu, 320, 220);
		g.drawString("À˘œ˚–– ˝£∫"+sum, 320+100, 220);
		g.drawString("¥ÛÃÏ πÕı’  ‘›Õ£(p)  ø™ º(c)", 320, 220+50);
	}
	private void moveLeftAction(){
		tetromino.moveLeft();
		if(outOfBound()||coinside()){
			tetromino.moveRight();
		}
	}
	private void moveRightAction(){
		tetromino.moveRight();
		if(outOfBound()||coinside()){
			tetromino.moveLeft();
		}
	}
	private void rotateAction(){
		tetromino.rotateRight();
		if(outOfBound()||coinside()){
			tetromino.rotateLeft();
		}
	}
	private void softDropAction(){
		if(canDrop()){
			tetromino.softDrop();
		}
		else{
			landToWall();
			destroyLine();
			checkGameOver();
			tetromino=nextOne;
			nextOne=Tetromino.randomOne();
		}
	}
	private void hardDropAction(){
		while(canDrop()){
			tetromino.softDrop();
		}
		landToWall();
		destroyLine();
		checkGameOver();
		tetromino=nextOne;
		nextOne=Tetromino.randomOne();
	}
	private void startGameAction(){
		clearWall();
	    score=0;
		hangshu=0;
		isPaused=false;
		isGameOver=false;
		tetromino=Tetromino.randomOne();
		nextOne=Tetromino.randomOne();
		timer=new Timer();
		timer.schedule(new TimerTask(){
			public void run(){
				softDropAction();
				repaint();
				}
			}, 700, 700);
	}
	private void pausedAction(){
		isPaused=true;
		timer.cancel();
	}
	private void continueAction(){
		isPaused=false;
		timer=new Timer();
		timer.schedule(new TimerTask(){
			public void run(){
				softDropAction();
				repaint();
				}
		},700,700);
	}
	
	//≈–∂œ «∑Òœ¬¬‰
    private boolean canDrop()
    {
    	for(Cell c:tetromino.cells)
    	{
    		if(c.getRow()==ROWS-1)
    		{
    			return false;	
    		}
    		if(wall[c.getRow()+1][c.getCol()]!=null)
    		{
    			return false;
    		}
    	}
    	return true;
    }
    //≈–∂œ «∑Ò¬˙––
    private boolean fullOfLine(int row){
    	Cell[]line=wall[row];
    	for(Cell c:line){
    		if(c==null){
    			return false;
    		}
    	}
    	return true;
    }
    //≈–∂œ «∑Ò≥ˆΩÁ
    private boolean outOfBound()
    {
    	Cell[] cells=tetromino.cells;
    	for(Cell c:cells)
    	{
    		int row=c.getRow();
    		int col=c.getCol();
    		if(row<0||row>=ROWS||col<0||col>=COLS){
    		     return true;
    		}
    	}
    	return false;
    }
    //≈–∂œ «∑Ò÷ÿ∫œ
    public boolean coinside(){
    	Cell[] cells=tetromino.cells;
    	for(Cell c:cells)
    	{
    		int row=c.getRow();
    		int col=c.getCol();
    		if(wall[row][col]!=null)
    		{
    			return true;
    		}
    	}
    	return false;
    }
    //◊≈«Ω
    private void landToWall(){
    	Cell[] cells=tetromino.cells;
    	for(Cell c:cells){
    		int row=c.getRow();
    		int col=c.getCol();
    		wall[row][col]=c;
    	}
    }
    //œ˚––
    private void destroyLine(){
    	line=0;
    	for(int row=0;row<ROWS;row++){
    		if(fullOfLine(row)){
    			line++;
    			removeLine(row);
    		}
    	}
      hangshu=line;
      score+=(line*line)*10;
      sum+=hangshu;
    }
    //…æ≥˝“ª––
    private void removeLine(int row){
    	for(int i=row;i>0;i--){
    		System.arraycopy(wall[i-1],0,wall[i],0,COLS);
    	}
    	Arrays.fill(wall[0], null);
    }
    //«Â«Ω
    private void clearWall(){
    	for(Cell[] line:wall){
    		Arrays.fill(line, null);
    	}
    }
	private void checkGameOver() {
		if(wall[0][4]!=null){
			isGameOver=true;
			timer.cancel();
			repaint();
		}
	}
	//◊‹øÿ
	private void action(){
		startGameAction();
		KeyListener l=new KeyAdapter(){
			public void keyPressed(java.awt.event.KeyEvent e) {
				int key=e.getKeyCode();
				if(isGameOver==true){
					if(key==KeyEvent.VK_S){
						startGameAction();
						repaint();
					}
					return;
				}
				if(isPaused){
					if(key==KeyEvent.VK_C){
						continueAction();
						repaint();
					}
					return;
				}
				switch(key){
				case KeyEvent.VK_P: pausedAction();break;
				case KeyEvent.VK_DOWN: softDropAction();break;
				case KeyEvent.VK_LEFT: moveLeftAction();break;
				case KeyEvent.VK_RIGHT: moveRightAction();break;
				case KeyEvent.VK_SPACE: hardDropAction();break;
				case KeyEvent.VK_UP: rotateAction();break;
				}
				repaint();
			}
		};
		this.addKeyListener(l);
		this.requestFocus();
	}
	
	public static void main(String[] args) 
	{
       Tetris tetris=new Tetris();
       JFrame frame=new JFrame("俄罗斯方块");
       frame.setSize(525,580);
       frame.setBackground(new Color(0xffff00));
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   frame.add(tetris);
	   frame.setVisible(true);
	   tetris.action();
	}
	}

