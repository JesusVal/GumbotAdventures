package com.gumbot.app.gui;

import com.gumbot.app.gumbot.GumBot;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


//Crea las estadisticas de juego para mostrar
public class StatsPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	public static final int STATS_HEIGHT = 40;
	private static final int BATERY_START_X = 32;
	private static final int BATERY_START_Y = 4;
	private static final int COINS_START_X = 128;
	private static final int COINS_START_Y = 30;
	
	private BufferedImage [] batery;
	private BufferedImage coin;
	private BufferedImage statsPanel;
	private Font pixelFont;
	private int score = 100010; //Puntaje inicial
	private GumBot gumbot;
	

	public StatsPanel(){
		this.setSize(GameFrame.WIDTH, STATS_HEIGHT);
		this.setBackground(Color.BLACK);
		this.setLayout(null);
		loadImages(); //Carga las imagenes de los componentes
	}
	
	private void loadImages() {
		this.batery = new BufferedImage[GumBot.MAX_LIFE+1];
		try {
			statsPanel = ImageIO.read(getClass().getResource("com/gumbot/app/items/statsBar.png"));
			batery[0] = ImageIO.read(getClass().getResource("com/gumbot/app/items/batery_0.png"));
			batery[1] = ImageIO.read(getClass().getResource("com/gumbot/app/items/batery_1.png"));
			batery[2] = ImageIO.read(getClass().getResource("com/gumbot/app/items/batery_2.png"));
			batery[3] = ImageIO.read(getClass().getResource("com/gumbot/app/items/batery_3.png"));
			coin = ImageIO.read(getClass().getResource("com/gumbot/app/items/coin_1.png"));
			//Carga la fuente de las letras
			pixelFont=Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/items/pixel.ttf")).deriveFont(35.0f);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e){
			e.printStackTrace();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		
		//Color de las letras
		g2.setColor(Color.WHITE);
		g2.setFont(pixelFont);
		
		g2.drawImage(statsPanel,0,0,GameFrame.WIDTH-5,STATS_HEIGHT,null);
		
		//Dependiendo la vida de Gumbot, cambia la imagen de la vida
		if(gumbot.getLife() == 3){
			g2.drawImage(batery[3],BATERY_START_X,BATERY_START_Y,null);
		}else if(gumbot.getLife() == 2){
			g2.drawImage(batery[2],BATERY_START_X,BATERY_START_Y,null);
		}else if(gumbot.getLife() == 1){
			g2.drawImage(batery[1],BATERY_START_X,BATERY_START_Y,null);
		}else{
			g2.drawImage(batery[0],BATERY_START_X,BATERY_START_Y,null);
		}
		
		//Dibuja las monedas en monedero
		g2.drawImage(coin, COINS_START_X,COINS_START_Y-24,null);
		g2.drawString("x"+gumbot.getCoins(), COINS_START_X+32, COINS_START_Y);
		//Dibuja el puntaje
		g2.drawString("Score "+ score, COINS_START_X+96, COINS_START_Y);
	}
	
	public void reduceScore(int i){
		this.score = (this.score>0) ? this.score-i : 0;
	}
	
	public void addGumBot(GumBot gumbot) {
		this.gumbot = gumbot;
	}
	
	
}
