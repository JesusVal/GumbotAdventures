package gumbot;

import gui.GameFrame;
import gui.PlayPanel;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import core.Block;
import core.Coins;
import core.Tile;
import core.World;
import core.spikes;

public abstract class GumBot {
	
	//Dimensiones de Gumbot
	private static final int GUMBOT_HEIGHT = 32;
	private static final int GUMBOT_WIDTH  = 32;
	private static final int GUMBOT_STARTX = 32;
	
	//Posicion de Gumbot
	private int currentX = GUMBOT_STARTX;
	private int currentY = GameFrame.HEIGHT-PlayPanel.TERRAIN_HEIGHT;//-GUMBOT_HEIGHT-200;
	private int currentCol;
	private int currentRow;
	
	//Desplasamiento de Gumbot
	private static final int DISPLACEMENT = 4;
	private static final int MOVE_COUNTER_THRESH = 5;
	private int moveCounter = 0;
	
	//HitBox
	private Rectangle boundingBox;
	
	//Variables para sprites
	private int lastDirection = KeyEvent.VK_RIGHT;
	private static final int BUFFER_RUN_SIZE = 6;
	private int currentFrameNumber = 0;
	private BufferedImage currentFrame;
	protected BufferedImage idle_R;
	protected BufferedImage idle_L;
	protected BufferedImage [] run_R;
	protected BufferedImage [] run_L;
	
	//Estado de salto
	private static final int JUMP_COUNTER_SPACES = 20;
	private int jumpCount;
	private boolean jumping;
	private boolean falling = false;
	
	//Estados de recuperacion
	private static final int RESTORING_THRESH = 84;
	private static final int RESTORING_MODULE = 12;
	private int restoringCount = 0;
	private boolean restoring = false;
	
	//Vida
	public static final int MAX_LIFE = 3;
	private int life = MAX_LIFE;
	private int coins = 0;
	
	public GumBot(){
		//Inicializa los espacios para los Sprites
		this.run_L = new BufferedImage[BUFFER_RUN_SIZE];
		this.run_R = new BufferedImage[BUFFER_RUN_SIZE];
		
		loadSprites(); //Carga llos spites en los arreglos
		
		this.currentFrame = idle_R; //Da un sprite inicial
		this.boundingBox = new Rectangle(GUMBOT_STARTX, currentY, GUMBOT_WIDTH, GUMBOT_HEIGHT); //Inicializa la HitBox
	}
	
	abstract protected void loadSprites(); //Dependiendo del Gumbot se cargan las imagenes
	
	public void move(int direction){
		switch (direction) {
			case KeyEvent.VK_LEFT: //Cuando se mueve a la izquierda
				currentX = currentX-DISPLACEMENT; //Mueve a Gumbot n-Desplazamientos

				if(currentX <= 0){ //No deja regresar por la izquierda
					currentX = 0;
				}
				
				boundingBox.setLocation(currentX, currentY); //Mueve tambien la Hitox
				
				if(!jumping && !falling){ //Cambia el sprite dependiendo si estaba saltando o cayendo
					setFrameNumber();
					currentFrame=run_L[currentFrameNumber];
				} else {
					currentFrame=run_L[0];
				}
				
				lastDirection=KeyEvent.VK_LEFT;//Guarda la dirreccion hacia donde apunta
				break;
			
			case KeyEvent.VK_RIGHT: //Lo mismo pero hacia la derecha y en esta si puedes salir de la pantalla
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
		//Cambia la columna y fila actuales de Gumbot
		currentRow = currentY/Tile.TILE_SIZE;
		currentCol = currentX/Tile.TILE_SIZE;
		moveCounter++; 
	}
	
	//Calcula el frame de movimiento en el que se encuentra, hay 6
	private void setFrameNumber(){
		currentFrameNumber = (moveCounter / MOVE_COUNTER_THRESH) % 6;
		
		if(moveCounter > MOVE_COUNTER_THRESH * 6)
			moveCounter = 0;
	}
	
	//Determina si esta fuera de los limites de X en el mapa
	public boolean outLimits(){
		if(currentX >= GameFrame.WIDTH){
			return true;
		}
		return false;
	}
	
	//Reinicia a un sprite de reposo cuando no se mueve, este apunta 
	//a la ultima direccion que se camino
	public void stop(){
		if(lastDirection == KeyEvent.VK_RIGHT)
			currentFrame = idle_R;
		else
			currentFrame = idle_L;
	}
	
	//Revisa las colisiones
	public void checkCollision(){
		
		int footY=(int)(boundingBox.getMaxY());
		
		if(jumping){ //En caso que este brincando
			
			int upRow = (int)((boundingBox.getMinY()-1)/Tile.TILE_SIZE); //Posicion de arriba
			int upLeftCornerCol =  (int)((boundingBox.getMinX())/Tile.TILE_SIZE); //Esquina superior izquierda
			int upRightCornerCol = (int)((boundingBox.getMaxX())/Tile.TILE_SIZE); //Esquina superior derecha

			if(currentRow >= 0){
				if(World.tiledMap[upRow][upLeftCornerCol] instanceof Block){ //Revisa si hay un bloque en la esquina superior izquierda
					if(World.tiledMap[upRow][upLeftCornerCol].getBoundingBox().intersects(boundingBox)){
						jumping = false; //Si choca con un bloque deja de saltar
						jumpCount = 0;
						falling = true; //Y empieza a caer
						return;
					}
				}
				if(World.tiledMap[upRow][upRightCornerCol] instanceof Block){ //Revisa si hay un bloque en la esquina superior derecha
					if(World.tiledMap[upRow][upRightCornerCol].getBoundingBox().intersects(boundingBox)){
						jumping = false;
						jumpCount = 0;
						falling = true;
						return;
					}
				}
			}
		
		}
		
		//Por separado revisa las colisiones de los lados, para poder saltar y moverse a la vez
		if(lastDirection==KeyEvent.VK_RIGHT){ //Revisa las collisiones de la derecha
			int footX = (int)boundingBox.getMinX();   //Lado derecho
	
			int tileInFrontOfFootRow = ((footY-1)/Tile.TILE_SIZE); //Linea siguiente
			int tileInFrontOfFootCol = (footX/Tile.TILE_SIZE)+1;   //Columna siguiente
			
			if(tileInFrontOfFootCol<World.COLS){
				if(World.tiledMap[tileInFrontOfFootRow][tileInFrontOfFootCol] instanceof Block){ //Si choca con un bloque
					if(boundingBox.intersects(World.tiledMap[tileInFrontOfFootRow][tileInFrontOfFootCol].getBoundingBox())){
						currentX -= DISPLACEMENT; //Resta el mismo desplazamiento de avance
						boundingBox.setLocation(currentX, currentY);
						currentCol = currentX/Tile.TILE_SIZE;
					}
				}
					if(World.tiledMap[tileInFrontOfFootRow][tileInFrontOfFootCol] instanceof spikes){ //Si choca con unos pinchos
						if(boundingBox.intersects(World.tiledMap[tileInFrontOfFootRow][tileInFrontOfFootCol].getBoundingBox()))
							die();  //Se muere
					}
					
					//Por seguridad no se permite que Gumbot este dentro de los bloques
				if(World.tiledMap[currentRow][currentCol] instanceof Block){ //Si esta dentro de un bloque
					if(boundingBox.intersects(World.tiledMap[currentRow][currentCol].getBoundingBox())){
						currentX -= DISPLACEMENT;
						boundingBox.setLocation(currentX, currentY);
						currentCol = currentX/Tile.TILE_SIZE;
					}
				}
			}
		} else { //Lo mismo pero a la izquierda
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
					if(World.tiledMap[tileInFrontOfFootRow][tileInFrontOfFootCol] instanceof spikes){
						if(boundingBox.intersects(World.tiledMap[tileInFrontOfFootRow][tileInFrontOfFootCol].getBoundingBox()))
							die();
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
	
	//Revisa las colisiones de bloques especiales
	public void checkSpecialBlocks(){
		
		if((boundingBox.getMaxX())/Tile.TILE_SIZE < World.COLS ){
			if(World.tiledMap[currentRow][currentCol] instanceof spikes){
				if(World.tiledMap[currentRow][currentCol].getBoundingBox().intersects(boundingBox)){
					die();
				}
			}
		}
		//Revisa i choca con una moneda 
		if((boundingBox.getMaxX()/Tile.TILE_SIZE)<World.COLS){
			if(World.tiledMap[currentRow][currentCol] instanceof Coins){ //Si choca con una moneda
				if(World.tiledMap[currentRow][currentCol].getBoundingBox().intersects(boundingBox)){
						coins++; //Aumenta el monedero
					World.tiledMap[currentRow][currentCol] = null; //Borra la moneda del mapa
				}
			}
		}
	}
	
	//Estado de salto
	public void checkJumping(){
		if(jumping){ //Si esta saltando
			if(jumpCount < JUMP_COUNTER_SPACES){ //Si aun le falta por saltar
				currentY -= DISPLACEMENT; //Se mueve hacia arriba
				boundingBox.setLocation(currentX, currentY);
			}
			
			jumpCount++;//cuenta los espacios saltados
			
			if(jumpCount >= JUMP_COUNTER_SPACES){ //Si salto lo que debia
				jumping = false; //Deja de saltar
				jumpCount = 0;   //Reinicia el contador de salto
				falling = true;  //Y comienza a caer
			}
		}
	}
	
	//Estado de caidas
	public void checkFalling(){
		if(boundingBox.getMaxY()/Tile.TILE_SIZE>=World.ROWS){ //Si se sale del mapa por y
			die();  //Se muere
		}
		
		if(jumping){ //Si se esta saltando pues no se hace nada
			return;
		}
		
		if(falling){ //Si esta cayendo
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
		//Si no hay bloque abajo, se cae
		if(!((World.tiledMap[currentRow+1][underlyingTileXR]) instanceof Block) && !((World.tiledMap[currentRow+1][underlyingTileXL]) instanceof Block)){
			falling = true;
			return;
		}
		//Si hay pinchos abajo, se muere
		if(World.tiledMap[currentRow +1][underlyingTileXR] instanceof spikes && (World.tiledMap[currentRow +1][underlyingTileXL] instanceof spikes)){
			die();
		}
		
		falling = false;
	}
	
	//empieza el salto
	public void jump(){
		this.jumping = true;
		this.jumpCount = 0;
		
		//Cambia los sprites al saltar
		if(lastDirection == KeyEvent.VK_RIGHT)
			currentFrame = run_R[4];
		else
			currentFrame = run_L[4];
	}
	
	//Estado de recuperacion
	public void checkRestoring(){
		if(restoringCount > 0){ //Si se esta en tiempo de recuperacion
			restoringCount--;
			if((restoringCount % RESTORING_MODULE) == 0) //Sale delestado de recuperacion
				restoring = !restoring;
		}
	}
	
	//Inicializa a gumbot 
	public void reinitialize(){
		currentX = 0;
		currentY = GameFrame.HEIGHT-PlayPanel.TERRAIN_HEIGHT+GUMBOT_HEIGHT;
		currentCol = 0;
		currentRow = currentY/Tile.TILE_SIZE;
		boundingBox = new Rectangle(GUMBOT_STARTX+DISPLACEMENT,currentY,GUMBOT_WIDTH,GUMBOT_HEIGHT);
		lastDirection = KeyEvent.VK_RIGHT;
		falling = false;
	}
	
	//Mata a Gumbot e inicia el ciclo de recuperacion
	private void die(){
		currentX = GUMBOT_STARTX;
		currentY = GameFrame.HEIGHT-PlayPanel.TERRAIN_HEIGHT+GUMBOT_HEIGHT;
		currentCol = currentX/Tile.TILE_SIZE;
		currentRow = currentY/Tile.TILE_SIZE;
		boundingBox = new Rectangle(GUMBOT_STARTX+DISPLACEMENT,currentY,GUMBOT_WIDTH,GUMBOT_HEIGHT);
		lastDirection = KeyEvent.VK_RIGHT;
		falling = false;
		restoring = true;
		restoringCount = RESTORING_THRESH;
		life--;
	}
	
	
	//Metodos Get
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
	public int getCoins(){
		return this.coins;
	}

}

