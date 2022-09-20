package de.muulti.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class CloudPanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	public int x;
	public int y;
	private Image img;
	private boolean moveForward = true;


	public CloudPanel() {
		setBackground(Color.white);

		try {
			img = ImageIO.read(this.getClass().getResource("Cloud.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		
		// set movement of the cloud
		while (true) {
			synchronized (this) {
				if (x > getWidth()+ 100) {
					moveForward = false;
				} else if (x <= 0)
					moveForward = true;
				if (moveForward) {
					x += 1;
				} else {
					x -= 1;
				}

			}
			repaint();
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				break;
			}
		}

	}

	public synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, x-img.getWidth(this), y, img.getWidth(this), img.getHeight(this), this);

	}

}
