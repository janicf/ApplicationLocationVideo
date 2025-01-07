package form;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.ClientDAO;

public class Client_page_Form extends JFrame {

	private int idClient;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					int idClient = 1;
					Client_page_Form frame = new Client_page_Form(idClient);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Client_page_Form(int idClient) {
		this.idClient = idClient;

		initialize();
	}

	private void initialize() {
		setTitle("Accueil");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		String nomClient = "";
		try {
			nomClient = ClientDAO.getNomClient(idClient);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JLabel lblBienvenue = new JLabel("Bienvenue " + nomClient);
		lblBienvenue.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblBienvenue.setBounds(10, 10, 266, 25);
		contentPane.add(lblBienvenue);

		JButton btn_RechercheVideo = new JButton("Recherche vidéos");
		btn_RechercheVideo.addMouseListener(new Btn_RechercheVideoMouseListener());
		btn_RechercheVideo.setBounds(10, 50, 266, 21);
		contentPane.add(btn_RechercheVideo);

		JButton btn_Location = new JButton("Location et promotions");
		btn_Location.addMouseListener(new Btn_LocationMouseListener());
		btn_Location.setBounds(10, 81, 266, 21);
		contentPane.add(btn_Location);

		JButton btn_Evenement = new JButton("Evenements");
		btn_Evenement.addMouseListener(new Btn_EvenementMouseListener());
		btn_Evenement.setBounds(10, 112, 266, 21);
		contentPane.add(btn_Evenement);

		JButton btn_Profil = new JButton("Profil");
		btn_Profil.addMouseListener(new Btn_ProfilMouseListener());
		btn_Profil.setVisible(false);
		btn_Profil.setBounds(10, 143, 266, 21);
		contentPane.add(btn_Profil);

		JButton btnDeconnexion = new JButton("Déconnexion");
		btnDeconnexion.addMouseListener(new BtnDeconnexionMouseListener());
		btnDeconnexion.setBackground(new Color(192, 192, 192));
		btnDeconnexion.setBounds(10, 182, 266, 21);
		contentPane.add(btnDeconnexion);
	}

	private class Btn_RechercheVideoMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			Client_recherche_Form appClient = new Client_recherche_Form(idClient);
			appClient.setVisible(true);
			dispose();
		}
	}

	private class Btn_LocationMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			Client_Location_Form appClient = new Client_Location_Form(idClient);
			appClient.setVisible(true);
			dispose();
		}
	}

	private class Btn_EvenementMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			Client_Evenement_Form appClient = new Client_Evenement_Form(idClient);
			appClient.setVisible(true);
			dispose();
		}
	}

	private class Btn_ProfilMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO liaison une fois que la page existera
		}
	}

	private class BtnDeconnexionMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			Connection_Form appClient = new Connection_Form();
			appClient.frmConnection.setVisible(true);
			dispose();
		}
	}
}
