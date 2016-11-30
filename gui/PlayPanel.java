package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;

import javax.swing.JPanel;

import core.Tile;
import core.World;
import gumbot.GumBot;


public class PlayPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	public static final int TERRAIN_HEIGHT = 192; 
	public static final int PLAY_PANEL_HEIGHT = 640;
		
	private GumBot gumbot;

	
	public PlayPanel(){
		this.setSize(GameFrame.WIDTH, PLAY_PANEL_HEIGHT);
		this.setBackground(Color.GREEN);
		this.setLayout(null);
		this.setDoubleBuffered(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2=(Graphics2D) g;
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawImage(World.CURRENT_BACKGROUND,0,-Tile.TILE_SIZE,GameFrame.WIDTH,PLAY_PANEL_HEIGHT, null);
		
		//g2.drawImage(World.tiledMap[2][2].getImage(), 2*Tile.TILE_SIZE, 2*Tile.TILE_SIZE, null);
		
		
		for(int i=0; i<World.ROWS; i++){
			for(int j=0; j<World.COLS; j++){
				if(World.tiledMap[i][j]!=null){
					g2.drawImage(World.tiledMap[i][j].getImage(), j*Tile.TILE_SIZE, i*Tile.TILE_SIZE, null);
				}
			}
		}
		
		
		if(!gumbot.getRestoring()){
			g2.drawImage(gumbot.getCurrentFrame(),gumbot.getCurrentX(),gumbot.getCurrentY(),null);
			//g2.draw(gumbot.getBoundingBox()); //Hitbox
		}
	}
	
	
	public void addGumBot(GumBot gumbot) {
		this.gumbot = gumbot;
	}
	
	

}
