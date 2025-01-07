package form;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import _class.Video;
import dao.VideoDAO;

public class Client_Location_Form extends JFrame {

	private int idClient;
	protected Video videoSelectionne;

	// affichage different model
	protected DefaultListModel<Video> modelListMesLocations = new DefaultListModel<Video>();
	protected JList<Video> list_mesLocations = new JList<Video>(modelListMesLocations);

	protected DefaultListModel<Video> modelListInclusAbbonnement = new DefaultListModel<Video>();
	protected JList<Video> list_inclusAbonnement = new JList<Video>(modelListInclusAbbonnement);
	protected JLabel lbl_Err_AucunAbonnenementValide;

	protected DefaultListModel<Video> modelListNouveaute = new DefaultListModel<Video>();
	protected JList<Video> list_nouveaute = new JList<Video>(modelListNouveaute);

	// affichage selon abonnement
	protected JTabbedPane tabbedPane;
	protected JPanel tab_inclusAbonnement;
	protected JLabel lblPrix;
	protected JButton btn_locationFilm;
	protected JLabel lbl_InclusAbonnement;
	protected JButton btn_ecouterFilm;
	protected JLabel lbl_LocationValide;

	// affichage infos films
	protected JLabel lblTitreDuFilm;
	protected JLabel lblDateDeSortie;
	protected JLabel lblGenres;
	protected JLabel lblSujets;
	protected JLabel lblActeurs;
	protected JLabel lblRealisateur;
	protected JLabel lblDuree;
	protected JTextArea txtrDescription;

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
					Client_Location_Form frame = new Client_Location_Form(idClientTest);
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
	public Client_Location_Form(int idClient) {
		this.idClient = idClient;
		initialize();
	}

	private void initialize() {
		setTitle("Location");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		JButton btn_backToClientPage = new JButton("Retour à la page client");
		btn_backToClientPage.setBounds(10, 8, 168, 21);
		btn_backToClientPage.addMouseListener(new Btn_backToClientPageMouseListener());
		contentPane.setLayout(null);
		contentPane.add(btn_backToClientPage);

		// ======================================
		// Panel tabbed pages
		// ======================================

		lbl_Err_AucunAbonnenementValide = new JLabel("Aucun abonnement actif");
		lbl_Err_AucunAbonnenementValide.setBounds(188, 8, 229, 21);
		contentPane.add(lbl_Err_AucunAbonnenementValide);
		lbl_Err_AucunAbonnenementValide.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Err_AucunAbonnenementValide.setVisible(false);
		lbl_Err_AucunAbonnenementValide.setForeground(new Color(255, 0, 128));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(20, 39, 448, 514);
		contentPane.add(tabbedPane);

		JPanel tab_mesLocations = new JPanel();
		tabbedPane.addTab("Mes Locations", null, tab_mesLocations, null);
		tab_mesLocations.setLayout(null);

		list_mesLocations.addMouseListener(new List_mesLocationsMouseListener());
		list_mesLocations.setBounds(10, 10, 423, 467);
		tab_mesLocations.add(list_mesLocations);

		tab_inclusAbonnement = new JPanel();
		tabbedPane.addTab("Videos Gratuits", null, tab_inclusAbonnement, null);
		tab_inclusAbonnement.setLayout(null);

		list_inclusAbonnement.addMouseListener(new List_inclusAbonnementMouseListener());
		list_inclusAbonnement.setBounds(10, 9, 423, 467);
		tab_inclusAbonnement.add(list_inclusAbonnement);

		JPanel tab_nouveaute = new JPanel();
		tabbedPane.addTab("Nouveauté", null, tab_nouveaute, null);
		tab_nouveaute.setLayout(null);

		list_nouveaute.addMouseListener(new List_nouveauteMouseListener());
		list_nouveaute.setBounds(10, 10, 423, 467);
		tab_nouveaute.add(list_nouveaute);

		// ======================================
		// Panel infos film sélectionné
		// ======================================

		JPanel panel_InformationFilm = new JPanel();
		panel_InformationFilm.setBounds(478, 39, 408, 514);
		contentPane.add(panel_InformationFilm);
		panel_InformationFilm.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_InformationFilm.setLayout(null);

		JLabel lblInformationsFilmSlectionn = new JLabel("Informations video sélectionné");
		lblInformationsFilmSlectionn.setBounds(10, 10, 235, 13);
		panel_InformationFilm.add(lblInformationsFilmSlectionn);

		JPanel panel_contenuFilm = new JPanel();
		panel_contenuFilm.setBounds(10, 33, 388, 428);
		panel_InformationFilm.add(panel_contenuFilm);
		panel_contenuFilm.setLayout(null);

		lblTitreDuFilm = new JLabel("Titre du Film");
		lblTitreDuFilm.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTitreDuFilm.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitreDuFilm.setBounds(10, 10, 368, 20);
		panel_contenuFilm.add(lblTitreDuFilm);

		lblDateDeSortie = new JLabel("Date de sortie");
		lblDateDeSortie.setHorizontalAlignment(SwingConstants.CENTER);
		lblDateDeSortie.setBounds(10, 33, 368, 13);
		panel_contenuFilm.add(lblDateDeSortie);

		lblGenres = new JLabel("Genre :");
		lblGenres.setBounds(10, 59, 368, 13);
		panel_contenuFilm.add(lblGenres);

		lblSujets = new JLabel("Sujet :");
		lblSujets.setBounds(10, 82, 368, 13);
		panel_contenuFilm.add(lblSujets);

		lblActeurs = new JLabel("Acteurs : ");
		lblActeurs.setVerticalAlignment(SwingConstants.TOP);
		lblActeurs.setBounds(10, 105, 368, 20);
		panel_contenuFilm.add(lblActeurs);

		lblRealisateur = new JLabel("Réalisateurs : ");
		lblRealisateur.setVerticalAlignment(SwingConstants.TOP);
		lblRealisateur.setBounds(10, 129, 368, 20);
		panel_contenuFilm.add(lblRealisateur);

		lblDuree = new JLabel("Durée (minutes) : ");
		lblDuree.setBounds(10, 152, 368, 13);
		panel_contenuFilm.add(lblDuree);

		txtrDescription = new JTextArea();
		txtrDescription.setFont(new Font("Tahoma", Font.PLAIN, 10));
		txtrDescription.setBorder(null);
		txtrDescription.setBackground(new Color(240, 240, 240));
		txtrDescription.setText("Description :");
		txtrDescription.setEditable(false);
		txtrDescription.setLineWrap(true);
		txtrDescription.setBounds(10, 175, 368, 74);
		panel_contenuFilm.add(txtrDescription);

		JPanel panel_location = new JPanel();
		panel_location.setBounds(0, 262, 388, 166);
		panel_contenuFilm.add(panel_location);
		panel_location.setLayout(null);

		lblPrix = new JLabel("Prix : ");
		lblPrix.setBounds(10, 43, 215, 21);
		panel_location.add(lblPrix);

		btn_locationFilm = new JButton("Louer");
		btn_locationFilm.setVisible(false);
		btn_locationFilm.addMouseListener(new Btn_locationFilmMouseListener());
		btn_locationFilm.setBounds(10, 78, 368, 21);
		panel_location.add(btn_locationFilm);

		lbl_InclusAbonnement = new JLabel("Inclus dans l'abonnement");
		lbl_InclusAbonnement.setVisible(false);
		lbl_InclusAbonnement.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_InclusAbonnement.setBounds(10, 43, 215, 21);
		panel_location.add(lbl_InclusAbonnement);

		btn_ecouterFilm = new JButton("Télécharger");
		btn_ecouterFilm.setVisible(false);
		btn_ecouterFilm.addMouseListener(new BtnEcouterMouseListener());
		btn_ecouterFilm.setBounds(10, 78, 368, 21);
		panel_location.add(btn_ecouterFilm);

		lbl_LocationValide = new JLabel("Location Valide ");
		lbl_LocationValide.setVisible(false);
		lbl_LocationValide.setBounds(10, 47, 368, 13);
		panel_location.add(lbl_LocationValide);

		// ======================================
		// Contenue des jList des pages de tab
		// ======================================

		// ajouter les items dans le jlist_mesLocations
		ArrayList<Video> rsListVideoActif = new ArrayList<Video>();
		ArrayList<Video> rsListVideoInclusAbonnement = new ArrayList<Video>();
		ArrayList<Video> rsListVideoNouveaute = new ArrayList<Video>();
		Boolean isVueClient = true;
		try {
			rsListVideoActif = VideoDAO.VideosLocationsActifsClient(idClient);
			for (Video v : rsListVideoActif) {
				modelListMesLocations.addElement(v);
			}

			if (VideoDAO.VerificationValiditeAbonnement(idClient)) {
				lbl_Err_AucunAbonnenementValide.setVisible(false);
				rsListVideoInclusAbonnement = VideoDAO.VideosInclusAbonnementClient(idClient, isVueClient);
				for (Video v : rsListVideoInclusAbonnement) {
					modelListInclusAbbonnement.addElement(v);
				}
			} else {
				tabbedPane.remove(tab_inclusAbonnement);
				lbl_Err_AucunAbonnenementValide.setVisible(true);
			}

			rsListVideoNouveaute = VideoDAO.VideosNouveaute();
			for (Video v : rsListVideoNouveaute) {
				modelListNouveaute.addElement(v);
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	private void affichageInfoVideo(Video video) {
		// set titre, date de sortie, duree, description et prix
		lblTitreDuFilm.setText(video.getTitre());
		lblDateDeSortie.setText(video.getDate_sortie().toString());
		lblDuree.setText("Durée : " + video.getDurre() + " minutes");
		txtrDescription.setText("Description : " + video.getDescription());
		lblPrix.setText("Prix : " + video.getPrix() + "$");

		// set genres, sujets, acteurs et realisateurs
		lblGenres.setText("Genres : " + video.getGenres().toString());
		lblSujets.setText("Sujets : " + video.getSujets().toString());
		lblActeurs.setText("Acteurs : " + video.getActeurs().toString());
		lblRealisateur.setText("Realisateurs : " + video.getRealisateurs().toString());

		// affichage selon l'abonnement
		// TODO ajouté text si location déjà valide
		Boolean isLocationValide = false;
		try {
			isLocationValide = VideoDAO.VerificationValiditeLocationCientVideo(idClient, video.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (isLocationValide) {
			lblPrix.setVisible(false);
			btn_locationFilm.setVisible(false);
			lbl_InclusAbonnement.setVisible(false);
			btn_ecouterFilm.setVisible(true);
			lbl_LocationValide.setVisible(true);

		} else if (video.isGratuit_abonne()) {
			try {
				if (VideoDAO.VerificationValiditeAbonnement(idClient)) {
					lblPrix.setVisible(false);
					btn_locationFilm.setVisible(true);
					lbl_InclusAbonnement.setVisible(true);
					btn_ecouterFilm.setVisible(false);
					lbl_LocationValide.setVisible(false);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			lblPrix.setVisible(true);
			btn_locationFilm.setVisible(true);
			lbl_InclusAbonnement.setVisible(false);
			btn_ecouterFilm.setVisible(false);
			lbl_LocationValide.setVisible(false);
		}

	}

	private class Btn_backToClientPageMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			Client_page_Form appClient = new Client_page_Form(idClient);
			appClient.setVisible(true);
			dispose();
		}
	}

	private class Btn_locationFilmMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			Client_Confirme_Location_Form appClient = new Client_Confirme_Location_Form(idClient, videoSelectionne);
			appClient.setVisible(true);
		}
	}

	private class BtnEcouterMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			Client_Ecoute_Film_Form appClient = new Client_Ecoute_Film_Form(idClient, videoSelectionne);
			appClient.setVisible(true);
			dispose();
		}
	}

	// liste clické nécessite le changement de l'affichage du film
	private class List_mesLocationsMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			videoSelectionne = list_mesLocations.getSelectedValue();
			videoSelectionne = VideoDAO.remplirInfosVideo(videoSelectionne);
			affichageInfoVideo(videoSelectionne);
		}
	}

	private class List_inclusAbonnementMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			videoSelectionne = list_inclusAbonnement.getSelectedValue();
			videoSelectionne = VideoDAO.remplirInfosVideo(videoSelectionne);
			affichageInfoVideo(videoSelectionne);
		}
	}

	private class List_nouveauteMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			videoSelectionne = list_nouveaute.getSelectedValue();
			videoSelectionne = VideoDAO.remplirInfosVideo(videoSelectionne);
			affichageInfoVideo(videoSelectionne);
		}
	}

}
