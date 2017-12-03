package core;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Block extends Tile {
	
	private String imgName;
	private int x;
	private int y;

	public Block(String imgName,int i, int j) {
		super(i,j);
		this.imgName = imgName;
		loadInformations(); //Carga imagen de bloques
	}

	@Override
	protected void initializeStuff() {
		x = col*TILE_SIZE;
		y = row*TILE_SIZE;
		boundingBox = new Rectangle(x,y,TILE_SIZE,TILE_SIZE);
	}
	
	protected void loadInformations() {
		try {
			//Dependiendo del nombre del bloque carga la imagen
			image=ImageIO.read(getClass().getResource("/items/"+imgName+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
