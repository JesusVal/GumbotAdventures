package com.gumbot.app.gumbot;

import java.io.IOException;

import javax.imageio.ImageIO;

public class GreenGumBot extends GumBot{

	protected void loadSprites() {
		try {
			//Carga imagenes para GumBot verde
			idle_R=ImageIO.read(getClass().getResource("com/gumbot/app/sprites/gumbot_green_rest_r.png"));
			idle_L=ImageIO.read(getClass().getResource("com/gumbot/app/sprites/gumbot_green_rest_l.png"));
			
			
			
			run_R[0]=ImageIO.read(getClass().getResource("com/gumbot/app/sprites/gumbot_green_r1.png"));
			run_R[1]=ImageIO.read(getClass().getResource("com/gumbot/app/sprites/gumbot_green_r2.png"));
			run_R[2]=ImageIO.read(getClass().getResource("com/gumbot/app/sprites/gumbot_green_r3.png"));
			run_R[3]=ImageIO.read(getClass().getResource("com/gumbot/app/sprites/gumbot_green_r4.png"));
			run_R[4]=ImageIO.read(getClass().getResource("com/gumbot/app/sprites/gumbot_green_r5.png"));
			run_R[5]=ImageIO.read(getClass().getResource("com/gumbot/app/sprites/gumbot_green_r6.png"));
			
			
			run_L[0]=ImageIO.read(getClass().getResource("com/gumbot/app/sprites/gumbot_green_l1.png"));
			run_L[1]=ImageIO.read(getClass().getResource("com/gumbot/app/sprites/gumbot_green_l2.png"));
			run_L[2]=ImageIO.read(getClass().getResource("com/gumbot/app/sprites/gumbot_green_l3.png"));
			run_L[3]=ImageIO.read(getClass().getResource("com/gumbot/app/sprites/gumbot_green_l4.png"));
			run_L[4]=ImageIO.read(getClass().getResource("com/gumbot/app/sprites/gumbot_green_l5.png"));
			run_L[5]=ImageIO.read(getClass().getResource("com/gumbot/app/sprites/gumbot_green_l6.png"));
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
