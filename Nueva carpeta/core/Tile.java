package core;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

//Tile para mapa
public abstract class Tile {
	public static final int TILE_SIZE = 32; //Tamaño de bloques
	protected int row;
	protected int col;
	protected BufferedImage image;
	protected Rectangle boundingBox;
	
	public Tile(int i, int j){
		this.row = i;
		this.col = j;
		initializeStuff();
	}
	
	protected abstract void initializeStuff(); //Inicializa las dimenciones y hitbox
	protected abstract void loadInformations(); //Carga imagenes
	
	
	//Metodos Get
	public BufferedImage getImage(){
		return image;
	}
	
	public Rectangle getBoundingBox() {
		return boundingBox;
	}
}
