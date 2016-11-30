package core;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Tile {
	public static final int TILE_SIZE = 32;//64; //Tamaño de bloques
	protected int row;
	protected int col;
	protected BufferedImage image;
	protected Rectangle boundingBox;
	
	public Tile(int i, int j){
		this.row = i;
		this.col = j;
		initializeStuff();
	}
	
	protected abstract void initializeStuff();
	protected abstract void loadInformations();
	
	public BufferedImage getImage(){
		return image;
	}
	
	public Rectangle getBoundingBox() {
		return boundingBox;
	}
}
