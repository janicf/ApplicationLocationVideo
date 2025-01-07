package form;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import _class.Video;

public class Client_Ecoute_Film_Form extends JFrame {

	private int idClient;
	private Video video;

	protected JLabel lbl_TitreDuFilm_Variable;

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
					int idClientTest = 5;
					Video videoTest = new Video();
					videoTest.setTitre("Film Test");

					Client_Ecoute_Film_Form frame = new Client_Ecoute_Film_Form(idClientTest, videoTest);

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
	public Client_Ecoute_Film_Form(int idClient, Video video) {
		this.idClient = idClient;
		this.video = video;
		initialize();
	}

	private void initialize() {
		setTitle("Téléchargement");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		// ======================================
		// Bouton retour
		// ======================================

		JButton btn_backToClientPage = new JButton("Retour à la page client");
		btn_backToClientPage.addMouseListener(new Btn_backToClientPageMouseListener());
		btn_backToClientPage.setBounds(10, 10, 168, 21);
		contentPane.add(btn_backToClientPage);

		JLabel lbl_entete = new JLabel("Vous êtes en train de télécharger ...");
		lbl_entete.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_entete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbl_entete.setBounds(98, 35, 303, 21);
		contentPane.add(lbl_entete);

		// ======================================
		// Panel infos video sélectionné
		// ======================================

		JPanel panel_InformationFilm = new JPanel();
		panel_InformationFilm.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_InformationFilm.setBounds(10, 66, 466, 287);
		contentPane.add(panel_InformationFilm);
		panel_InformationFilm.setLayout(null);

		JLabel lbl_TitreDuFilmFix = new JLabel("Titre du video :");
		lbl_TitreDuFilmFix.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_TitreDuFilmFix.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_TitreDuFilmFix.setBounds(10, 97, 446, 34);
		panel_InformationFilm.add(lbl_TitreDuFilmFix);

		lbl_TitreDuFilm_Variable = new JLabel("Titre du video");
		lbl_TitreDuFilm_Variable.setForeground(new Color(0, 0, 0));
		lbl_TitreDuFilm_Variable.setBounds(10, 134, 446, 34);
		panel_InformationFilm.add(lbl_TitreDuFilm_Variable);
		lbl_TitreDuFilm_Variable.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		lbl_TitreDuFilm_Variable.setHorizontalAlignment(SwingConstants.CENTER);

		affichageInfoVideo(video);

	}

	private void affichageInfoVideo(Video video) {
		// set titre, date de sortie, duree, description et prix
		lbl_TitreDuFilm_Variable.setText(video.getTitre());
	}

	private class Btn_backToClientPageMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			Client_page_Form appClient = new Client_page_Form(idClient);
			appClient.setVisible(true);
			dispose();
		}
	}
}
