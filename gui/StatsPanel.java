package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import gumbot.GumBot;


public class StatsPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	public static final int STATS_HEIGHT = 40;
	private static final int HEARTS_X_DISTANCE = 60;
	private static final int HEARTS_START_X = 84;
	private static final int HEARTS_START_Y = 4;
	private static final int HEARTS_SIZE = 32;
	
	private BufferedImage livingHeart;
	private BufferedImage deadHeart;
	private BufferedImage statsPanel;
	private GumBot gumbot;
	

	public StatsPanel(){
		this.setSize(GameFrame.WIDTH, STATS_HEIGHT);
		this.setBackground(Color.BLACK);
		this.setLayout(null);
		loadImages();
	}
	
	private void loadImages() {
		try {
			statsPanel=ImageIO.read(getClass().getResource("/items/statsBar.png"));
			livingHeart=ImageIO.read(getClass().getResource("/items/livingHeart.png"));
			deadHeart=ImageIO.read(getClass().getResource("/items/deadHeart.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		
		g2.drawImage(statsPanel,0,0,GameFrame.WIDTH-5,STATS_HEIGHT,null);
		
		for(int i=0; i<GumBot.MAX_LIFE; i++){
			if(gumbot.getLife()>i){
				g2.drawImage(livingHeart,HEARTS_START_X+HEARTS_X_DISTANCE*i,HEARTS_START_Y,HEARTS_SIZE,HEARTS_SIZE,null);
			} else {
				g2.drawImage(deadHeart,HEARTS_START_X+HEARTS_X_DISTANCE*i,HEARTS_START_Y,HEARTS_SIZE,HEARTS_SIZE,null);
			}
		}
	}
	

	public void addGumBot(GumBot gumbot) {
		this.gumbot = gumbot;
	}
	
	
}
