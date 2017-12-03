package com.gumbot.app.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import com.gumbot.app.core.Tile;
import com.gumbot.app.core.World;
import com.gumbot.app.gumbot.GumBot;

//Crea el espacio jugable
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
		
		//Dibuja el fondo
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawImage(World.CURRENT_BACKGROUND,0,-Tile.TILE_SIZE,GameFrame.WIDTH,PLAY_PANEL_HEIGHT, null);
		
		//Dibuja los Tiles en el mapa
		for(int i=0; i<World.ROWS; i++){
			for(int j=0; j<World.COLS; j++){
				if(World.tiledMap[i][j]!=null){
					g2.drawImage(World.tiledMap[i][j].getImage(), j*Tile.TILE_SIZE, i*Tile.TILE_SIZE, null);
				}
			}
		}
		
		
		if(!gumbot.getRestoring()){ //Si no esta en estado de reustauracion, dibuja a gumbot
			g2.drawImage(gumbot.getCurrentFrame(),gumbot.getCurrentX(),gumbot.getCurrentY(),null);
		}
	}
	
	
	public void addGumBot(GumBot gumbot) {
		this.gumbot = gumbot;
	}
	
	

}
