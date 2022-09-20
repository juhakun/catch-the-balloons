package de.muulti.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

public class BalloonPanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	private Random xPosBallon;
	private Random yPosBallon;
	private double x;
	private double y;
	private Image balloon;
	private Image kaboom;
	private Image confetti;
	private Image star;
	private Image img;
	private int countClicks = 0;
	int countBallons;
	int countKaboom;
	int countStars;
	int speed;
	private Clip audioClip;
	boolean clickable;

	public BalloonPanel() {
		setOpaque(false);
		setBackground(new Color(240, 248, 255));

		try {
			balloon = ImageIO.read(this.getClass().getResource("Balloon.png"));
			kaboom = ImageIO.read(this.getClass().getResource("Kaboom.png"));
			confetti = ImageIO.read(this.getClass().getResource("Confetti.png"));
			star = ImageIO.read(this.getClass().getResource("Star.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (clickable) {
					// Count number of click events
					countClicks++;
					int kaboomNo1 = new Random().nextInt(10 - 0 + 0) + 0;

					// get Position of the mouse
					int xPosMouse = e.getX();
					int yPosMouse = e.getY();

					// check if click event is hitting the ballon
					if (countClicks > 1 && xPosMouse >= x && xPosMouse <= x + (img.getWidth(null)) && yPosMouse >= y
							&& yPosMouse <= y + (img.getHeight(null))) {
						if (countBallons == kaboomNo1) {
							img = kaboom;
							countKaboom++;
						} else {
							img = star;
							countStars++;
						}
						try {
							String filepath = "BallonPlatzen.wav";
							audioClip = AudioSystem.getClip();
							audioClip.open(AudioSystem.getAudioInputStream(this.getClass().getResource(filepath)));
							audioClip.start();
						} catch (LineUnavailableException e1) {
							e1.printStackTrace();
						} catch (UnsupportedAudioFileException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						repaint();
					}
				}
			}
		});
	}

	@Override
	public void run() {
		// set random position of ballon
		xPosBallon = new Random();
		yPosBallon = new Random();

		while (countBallons < 10) {
			synchronized (this) {
				img = balloon;
				x = xPosBallon.nextDouble() * (getWidth() - confetti.getWidth(this) / 6);
				countBallons++;
				y = yPosBallon.nextDouble() * (getHeight() - confetti.getHeight(this) / 6);
			}
			repaint();

			// set speed of movement depending on chosen game level
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				break;
			}

		}

		CatchTheBalloons.stopGame();

	}

	public synchronized void paintComponent(Graphics g) {
		if (countClicks == 0) {
			img = balloon;
		}
		try {
			super.paintComponent(g);
			if (img == confetti) {
				g.drawImage(img, (int) x - 60, (int) y - 60, img.getWidth(this) / 5, img.getHeight(this) / 5, null);

			} else {
				g.drawImage(img, (int) x, (int) y, img.getWidth(this), img.getHeight(this), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
