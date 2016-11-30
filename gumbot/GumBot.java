package gumbot;

import gui.GameFrame;
import gui.PlayPanel;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import core.Block;
import core.Tile;
import core.World;

public abstract class GumBot {
	
	private static final int GUMBOT_HEIGHT = 32;
	private static final int GUMBOT_WIDTH  = 32;
	private static final int GUMBOT_STARTX = 128;
	
	private int currentX = GUMBOT_STARTX;
	private int currentY = GameFrame.HEIGHT-PlayPanel.TERRAIN_HEIGHT-GUMBOT_HEIGHT;
	private int currentCol;
	private int currentRow;
	
	private static final int DISPLACEMENT = 4;
	private static final int MOVE_COUNTER_THRESH = 5;
	private int moveCounter = 0;
	
	private Rectangle boundingBox;
	
	@SuppressWarnings("unused")
	private boolean idle = true;
	private int lastDirection = KeyEvent.VK_RIGHT;
	private static final int BUFFER_RUN_SIZE = 6;
	private int currentFrameNumber = 0;
	private BufferedImage currentFrame;
	protected BufferedImage idle_R;
	protected BufferedImage idle_L;
	protected BufferedImage [] run_R;
	protected BufferedImage [] run_L;
	
	private static final int JUMP_COUNTER_SPACES = 16;
	private int jumpCount;
	private boolean jumping;
	private boolean falling = false;
	
	private static final int RESTORING_THRESH = 84;
	private static final int RESTORING_MODULE = 12;
	private int restoringCount = 0;
	private boolean restoring = false;
	
	public static final int MAX_LIFE = 4;
	private int life = MAX_LIFE;
	
	public GumBot(){
		this.run_L = new BufferedImage[BUFFER_RUN_SIZE];
		this.run_R = new BufferedImage[BUFFER_RUN_SIZE];
		
		loadSprites();
		
		this.currentFrame = idle_R;
		this.boundingBox = new Rectangle(GUMBOT_STARTX, currentY, GUMBOT_WIDTH, GUMBOT_HEIGHT);
	}
	
	abstract protected void loadSprites();
	
	public void move(int direction){
		this.idle=false;
		switch (direction) {
			case KeyEvent.VK_LEFT:
				currentX = currentX-DISPLACEMENT;

				if(currentX <= 0){
					currentX = 0;
				}
				
				boundingBox.setLocation(currentX, currentY);
				
				if(!jumping && !falling){
					setFrameNumber();
					currentFrame=run_L[currentFrameNumber];
				} else {
					currentFrame=run_L[0];
				}
				
				lastDirection=KeyEvent.VK_LEFT;
				break;
			
			case KeyEvent.VK_RIGHT:
				currentX=currentX+DISPLACEMENT;
				boundingBox.setLocation(currentX, currentY);
				
				if(!jumping && !falling){
					setFrameNumber();
					currentFrame=run_R[currentFrameNumber];
				} else {
					currentFrame=run_R[0];
				}

				lastDirection=KeyEvent.VK_RIGHT;
				break;
				
			default:
				break;
		}
		currentRow = currentY/Tile.TILE_SIZE;
		currentCol = currentX/Tile.TILE_SIZE;
		moveCounter++;
	}
	
	private void setFrameNumber(){
		//currentFrameNumber = moveCounter / MOVE_COUNTER_THRESH;
		//currentFrameNumber = currentFrameNumber % 6;
		
		currentFrameNumber = (moveCounter / MOVE_COUNTER_THRESH) % 6;
		
		if(moveCounter > MOVE_COUNTER_THRESH * 6)
			moveCounter = 0;
	}
	
	public boolean outLimits(){
		if(currentX >= GameFrame.WIDTH){
			return true;
		}
		return false;
	}
	
	public void stop(){
		if(lastDirection == KeyEvent.VK_RIGHT)
			currentFrame = idle_R;
		else
			currentFrame = idle_L;
		
		this.idle = true;
	}
	
	public void checkCollision(){
		
		int footY=(int)(boundingBox.getMaxY());
		
		if(jumping){
			
			int upRow = (int)((boundingBox.getMinY()-1)/Tile.TILE_SIZE); //Posicion de arriba
			int upLeftCornerCol =  (int)((boundingBox.getMinX())/Tile.TILE_SIZE); //Esquina superior izquierda
			int upRightCornerCol = (int)((boundingBox.getMaxX())/Tile.TILE_SIZE); //Esquina superior derecha

			if(currentRow >= 0){
				if(World.tiledMap[upRow][upLeftCornerCol] instanceof Block){
					if(World.tiledMap[upRow][upLeftCornerCol].getBoundingBox().intersects(boundingBox)){
						jumping = false;
						jumpCount = 0;
						falling = true;
						return;
					}
				}
				if(World.tiledMap[upRow][upRightCornerCol] != null){
					if(World.tiledMap[upRow][upRightCornerCol].getBoundingBox().intersects(boundingBox)){
						jumping = false;
						jumpCount = 0;
						falling = true;
						return;
					}
				}
			}
		
		}
		
		
		if(lastDirection==KeyEvent.VK_RIGHT){
			int footX = (int)boundingBox.getMinX();   //Lado derecho
	
			int tileInFrontOfFootRow = ((footY-1)/Tile.TILE_SIZE); //Linea siguiente
			int tileInFrontOfFootCol = (footX/Tile.TILE_SIZE)+1;   //Columna siguiente
			
			if(tileInFrontOfFootCol<World.COLS){
				if(World.tiledMap[tileInFrontOfFootRow][tileInFrontOfFootCol] instanceof Block){ //Si choca con un bloque
					if(boundingBox.intersects(World.tiledMap[tileInFrontOfFootRow][tileInFrontOfFootCol].getBoundingBox())){
						currentX -= DISPLACEMENT;
						boundingBox.setLocation(currentX, currentY);
						currentCol = currentX/Tile.TILE_SIZE;
					}
				}
				if(World.tiledMap[currentRow][currentCol] instanceof Block){ //Si esta dentro de un bloque
					if(boundingBox.intersects(World.tiledMap[currentRow][currentCol].getBoundingBox())){
						currentX -= DISPLACEMENT;
						boundingBox.setLocation(currentX, currentY);
						currentCol = currentX/Tile.TILE_SIZE;
					}
				}
			}
		} else {
			int footX = (int) boundingBox.getMaxX();
			
			int tileInFrontOfFootRow = ((footY-1)/Tile.TILE_SIZE);
			int tileInFrontOfFootCol = (footX/Tile.TILE_SIZE)-1;
			
			if(tileInFrontOfFootCol >= 0){
				if(World.tiledMap[tileInFrontOfFootRow][tileInFrontOfFootCol] instanceof Block){
					if(boundingBox.intersects(World.tiledMap[tileInFrontOfFootRow][tileInFrontOfFootCol].getBoundingBox())){
						currentX += DISPLACEMENT;
						boundingBox.setLocation(currentX, currentY);
						currentCol = currentX/Tile.TILE_SIZE;
					}
				}
				if(World.tiledMap[currentRow][currentCol] instanceof Block){
					if(boundingBox.intersects(World.tiledMap[currentRow][currentCol].getBoundingBox())){
						currentX += DISPLACEMENT;
						boundingBox.setLocation(currentX, currentY);
						currentCol = currentX/Tile.TILE_SIZE;
					}
				}
			}	
		}
	}
	
	public void checkJumping(){
		if(jumping){
			if(jumpCount < JUMP_COUNTER_SPACES){
				currentY -= DISPLACEMENT;
				boundingBox.setLocation(currentX, currentY);
			}
			
			jumpCount++;
			
			if(jumpCount >= JUMP_COUNTER_SPACES){
				jumping = false;
				jumpCount = 0;
				falling = true;
			}
		}
	}
	
	public void checkFalling(){
		if(boundingBox.getMaxY()/Tile.TILE_SIZE>=World.ROWS){
			die();
		}
		
		if(jumping){
			return;
		}
		
		if(falling){
			currentY += DISPLACEMENT;
			currentRow = currentY/Tile.TILE_SIZE;
			boundingBox.setLocation(currentX, currentY);
		}
		
		int lowLeftX = (int)boundingBox.getMinX()+1;
		int lowRightX = (int) boundingBox.getMaxX()-1;
		
		int underlyingTileXR = lowRightX/Tile.TILE_SIZE;
		int underlyingTileXL = lowLeftX/Tile.TILE_SIZE;
		
		if(currentRow+1 >= World.ROWS || underlyingTileXR >= World.COLS){
			return;
		}
		if(!((World.tiledMap[currentRow+1][underlyingTileXR]) instanceof Block) && !((World.tiledMap[currentRow+1][underlyingTileXL]) instanceof Block)){
			falling = true;
			return;
		}
		
		falling = false;
	}
	
	public void jump(){
		this.jumping = true;
		this.jumpCount = 0;
		
		if(lastDirection == KeyEvent.VK_RIGHT)
			currentFrame = run_R[4];
		else
			currentFrame = run_L[4];
	}
	
	public void checkRestoring(){
		if(restoringCount > 0){
			restoringCount--;
			if((restoringCount % RESTORING_MODULE) == 0)
				restoring = !restoring;
		}
	}
	
	public void reinitialize(){
		currentX = 0;
		currentY = GameFrame.HEIGHT-PlayPanel.TERRAIN_HEIGHT-GUMBOT_HEIGHT;
		currentCol = 0;
		currentRow = currentY/Tile.TILE_SIZE;
		boundingBox = new Rectangle(GUMBOT_STARTX+DISPLACEMENT,currentY,GUMBOT_WIDTH,GUMBOT_HEIGHT);
		lastDirection = KeyEvent.VK_RIGHT;
		falling = false;
	}
	
	private void die(){
		currentX = GUMBOT_STARTX;
		currentY = GameFrame.HEIGHT-PlayPanel.TERRAIN_HEIGHT-GUMBOT_HEIGHT;
		currentCol = currentX/Tile.TILE_SIZE;
		currentRow = currentY/Tile.TILE_SIZE;
		boundingBox = new Rectangle(GUMBOT_STARTX+DISPLACEMENT,currentY,GUMBOT_WIDTH,GUMBOT_HEIGHT);
		lastDirection = KeyEvent.VK_RIGHT;
		falling = false;
		restoring = true;
		restoringCount = RESTORING_THRESH;
		life--;
	}
	
	public int getCurrentX(){
		return this.currentX;
	}
	public int getCurrentY(){
		return this.currentY;
	}
	public Rectangle getBoundingBox(){
		return this.boundingBox;
	}
	public BufferedImage getCurrentFrame(){
		return this.currentFrame;
	}
	public boolean getJumping(){
		return this.jumping;
	}
	public boolean getFalling(){
		return this.falling;
	}
	public boolean getRestoring(){
		return this.restoring;
	}
	public int getLife(){
		return this.life;
	}

}

