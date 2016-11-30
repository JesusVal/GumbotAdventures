package core;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

public class World {
	
	public static BufferedImage CURRENT_BACKGROUND;
	public static Tile[][] tiledMap;
	public static final int ROWS = 18;//9;
	public static final int COLS = 20;//20;
	
	
	public World(){
		tiledMap = new Tile[ROWS][COLS];
	}
	
	public void initializeStage(int level){
		try {
			CURRENT_BACKGROUND = ImageIO.read(getClass().getResource("/items/background"+String.valueOf(level)+".png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		InputStream is = this.getClass().getResourceAsStream("/levels/level"+String.valueOf(level)+".txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line = null;
		String[] tilesInLine = new String[ROWS];
		
		try {
			int i=0;
			while( (line = reader.readLine())  != null){
				tilesInLine = line.split(" ");
				for(int j=0; j<COLS; j++){
					if(!tilesInLine[j].equalsIgnoreCase("empt")){
						tiledMap[i][j] = newTileInstance(tilesInLine[j],i,j);
					} else {
						tiledMap[i][j] = null;
					}
				}
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Tile newTileInstance(String name, int i, int j) {
		switch (name) {
			case "trg1":
				return new Block("trg1", i, j);
			case "trg2":
				return new Block("trg2", i, j);
			case "trg3":
				return new Block("trg3", i, j);
			case "trg4":
				return new Block("trg4", i, j);
			case "trg5":
				return new Block("trg5", i, j);
			case "trg6":
				return new Block("trg6", i, j);
			case "trg7":
				return new Block("trg7", i, j);
			case "trg8":
				return new Block("trg8", i, j);
			case "ter1":
				return new Block("ter1", i, j);
		}
		return null;
	}
	
	
}
