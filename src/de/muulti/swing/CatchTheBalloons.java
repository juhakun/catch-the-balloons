package de.muulti.swing;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class CatchTheBalloons extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private BalloonPanel panelBalloon1;
	private BalloonPanel panelBalloon2;
	private CloudPanel panelCloud;
	private CloudPanel panelCloud2;
	private JLabel lblOutputHits;
	private JPanel panelForButtons;
	private JButton btnStart;
	private JButton btnStartPro;
	private JButton btnAgain;
	private Thread t1;
	private Thread t2;
	private Thread t3;
	private Thread t4;
	private JPanel panelStart;
	public static boolean gameOver = false;

	static CatchTheBalloons frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new CatchTheBalloons();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CatchTheBalloons() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 660);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ImageIcon icon = new ImageIcon(this.getClass().getResource("BackgroundBallons.png"));

		panelStart = new JPanel();
		panelStart.setOpaque(true);
		panelStart.setBounds(0, 0, 960, 660);
		contentPane.add(panelStart);
		panelStart.setBackground(new Color(240, 248, 255));
		panelStart.setLayout(null);

		btnStart = new JButton("Spiel für Anfänger");
		btnStart.setBounds(399, 187, 157, 50);
		panelStart.add(btnStart);
		btnStart.setFocusPainted(false);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startBallonsAndClouds(3000, 2800);
			}
		});

		btnStartPro = new JButton("Spiel für Profis");
		btnStartPro.setBounds(399, 352, 157, 50);
		panelStart.add(btnStartPro);
		btnStartPro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startBallonsAndClouds(1400, 1200);
			}
		});

		JLabel lblBackground = new JLabel("");
		lblBackground.setBounds(6, 74, 931, 449);
		panelStart.add(lblBackground);
		lblBackground.setIcon(icon);

		JLayeredPane layeredPaneForClouds = new JLayeredPane();
		layeredPaneForClouds.setBounds(6, 6, 948, 506);
		contentPane.add(layeredPaneForClouds);

		panelCloud = new CloudPanel();
		panelCloud.setBounds(6, 6, 948, 275);
		layeredPaneForClouds.add(panelCloud);
		panelCloud.setOpaque(false);
		panelCloud.setBackground(new Color(240, 248, 255));

		panelCloud2 = new CloudPanel();
		panelCloud2.setBounds(6, 275, 950, 550);
		layeredPaneForClouds.add(panelCloud2);
		panelCloud2.setOpaque(false);
		panelCloud2.setBackground(new Color(240, 248, 255));

		JPanel panelGame = new JPanel();
		panelGame.setOpaque(false);
		panelGame.setBackground(new Color(240, 248, 255));
		panelGame.setBounds(6, 6, 948, 626);
		contentPane.add(panelGame);
		panelGame.setLayout(null);

		panelForButtons = new JPanel();
		panelForButtons.setBounds(6, 428, 942, 182);
		panelForButtons.setOpaque(false);
		panelGame.add(panelForButtons);
		panelForButtons.setBackground(new Color(240, 248, 255));
		panelForButtons.setLayout(null);

		lblOutputHits = new JLabel("");
		lblOutputHits.setOpaque(false);
		panelForButtons.setVisible(false);
		lblOutputHits.setHorizontalTextPosition(SwingConstants.CENTER);
		lblOutputHits.setHorizontalAlignment(SwingConstants.CENTER);
		lblOutputHits.setBounds(265, 61, 377, 58);
		panelForButtons.add(lblOutputHits);

		btnAgain = new JButton("Nochmal spielen");
		btnAgain.setLocation(309, 131);
		btnAgain.setSize(290, 50);
		panelForButtons.add(btnAgain);
		btnAgain.setFocusPainted(false);
		btnAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelStart.setVisible(true);
				panelForButtons.setVisible(false);
			}

		});

		JLayeredPane layeredPaneForBothBallons = new JLayeredPane();
		layeredPaneForBothBallons.setBounds(0, 0, 948, 626);
		panelGame.add(layeredPaneForBothBallons);
		layeredPaneForBothBallons.setLayout(new MigLayout("", "[grow][grow]", "[532.00,grow]"));

		panelBalloon1 = new BalloonPanel();
		layeredPaneForBothBallons.add(panelBalloon1, "cell 1 0,grow");

		panelBalloon2 = new BalloonPanel();
		layeredPaneForBothBallons.add(panelBalloon2, "cell 0 0,grow");
	}

	public void startBallons(BalloonPanel p, Thread t) {
		if (p.countKaboom > 0 || p.countBallons > 0 || p.countStars > 0) {
			gameOver = false;
			p.countKaboom = 0;
			p.countBallons = 0;
			p.countStars = 0;
			lblOutputHits.setText("");
		}

		if (t == null) {
			t = new Thread(p);
		}

		if (!t.isAlive()) {
			t.start();
		}

	}

	public void startBallonsAndClouds(int speed1, int speed2) {
		t1 = new Thread(panelBalloon1);
		t2 = new Thread(panelBalloon2);
		startBallons(panelBalloon1, t1);
		startBallons(panelBalloon2, t2);
		t3 = new Thread(panelCloud);
		t4 = new Thread(panelCloud2);
		panelBalloon1.setVisible(true);
		panelBalloon1.clickable = true;
		panelBalloon1.speed = speed1;
		panelBalloon2.clickable = true;
		panelBalloon2.setVisible(true);
		panelBalloon2.speed = speed2;
		panelCloud.setVisible(true);
		panelCloud.x = 0;
		panelCloud.y = 25;
		t3.start();
		panelCloud2.setVisible(true);
		panelCloud2.x = -500;
		panelCloud2.y = 25;
		t4.start();
		btnStart.setEnabled(false);
		btnStartPro.setEnabled(false);
		panelStart.setVisible(false);
		panelForButtons.setVisible(false);

	}

	public static void stopGame() {
		
		if (!frame.t2.isAlive()) {
//			gameOver = true;
			System.out.println(gameOver);
			frame.t3.interrupt();
			frame.t4.interrupt();
			frame.showResults();
			frame.btnAgain.setEnabled(true);
		}

	}

	public void showResults() {
		int result = ( panelBalloon1.countStars + panelBalloon2.countStars) ;
//				- (panelBallon1.countKaboom + panelBallon2.countKaboom);
		lblOutputHits.setText("Du hast " + String.valueOf(result) + " Sterne gesammelt!");
		btnStart.setEnabled(true);
		btnStartPro.setEnabled(true);
		panelBalloon1.clickable = false;
		panelBalloon2.clickable = false;
		panelForButtons.setVisible(true);

	}

}
