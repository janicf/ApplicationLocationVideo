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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import _class.Acteur;
import _class.Genre;
import _class.Realisateur;
import _class.Sujet;
import _class.Video;
import dao.VideoDAO;

public class Client_recherche_Form extends JFrame {

	private int idClient;
	private Video videoSelectionne;
	private Boolean isVueClient = true;

	// filtre
	protected JComboBox<String> comboBox_choixTitre;
	protected JComboBox<String> comboBox_choixGenre;
	protected JComboBox<String> comboBox_choixSujet;
	protected JComboBox<String> comboBox_choixActeur;
	protected JComboBox<String> comboBox_choixRealisateur;

	// affichage resultat
	protected JLabel lbl_Err_AucunResultat;
	protected DefaultListModel<Video> modelListResultatRecherche = new DefaultListModel<Video>();
	protected JList<Video> listResultatRecherche = new JList<Video>(modelListResultatRecherche);

	// affichage infos films
	protected JLabel lblTitreDuFilm;
	protected JLabel lblDateDeSortie;
	protected JLabel lblGenres;
	protected JLabel lblSujets;
	protected JLabel lblActeurs;
	protected JLabel lblRealisateur;
	protected JLabel lblDuree;
	protected JTextArea txtrDescription;

	// affichage selon abonnement
	protected JLabel lblPrix;
	protected JButton btn_locationFilm;
	protected JLabel lbl_InclusAbonnement;
	protected JButton btn_ecouterFilm;
	protected JLabel lbl_LocationValide;

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
					Client_recherche_Form frame = new Client_recherche_Form(idClientTest);
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
	public Client_recherche_Form(int idClient) {
		this.idClient = idClient;
		initialize();
	}

	private void initialize() {
		setTitle("Recherche de vidéos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		// ======================================
		// Panel Filtre de la recherche
		// ======================================

		JScrollPane scrollPane_filtre = new JScrollPane();
		scrollPane_filtre.setBorder(new LineBorder(new Color(130, 135, 144), 1, true));
		scrollPane_filtre.setBounds(10, 32, 180, 471);
		contentPane.add(scrollPane_filtre);

		JPanel panel_top_filtre = new JPanel();
		scrollPane_filtre.setColumnHeaderView(panel_top_filtre);

		JLabel Label_Filtre = new JLabel("Filtre");
		panel_top_filtre.add(Label_Filtre);

		JButton btn_Go = new JButton("Go");
		btn_Go.addMouseListener(new BtnGoMouseListener());
		btn_Go.setForeground(new Color(0, 0, 0));
		btn_Go.setBackground(new Color(0, 255, 128));
		panel_top_filtre.add(btn_Go);

		JPanel panel_bottom_filtre = new JPanel();
		scrollPane_filtre.setViewportView(panel_bottom_filtre);
		panel_bottom_filtre.setLayout(null);

		JLabel Label_ChoixTitre = new JLabel("Titre du film");
		Label_ChoixTitre.setBounds(10, 22, 158, 13);
		panel_bottom_filtre.add(Label_ChoixTitre);

		comboBox_choixTitre = new JComboBox<String>();
		comboBox_choixTitre.setBounds(10, 45, 158, 21);
		panel_bottom_filtre.add(comboBox_choixTitre);

		JLabel Label_ChoixGenre = new JLabel("Genre");
		Label_ChoixGenre.setHorizontalAlignment(SwingConstants.LEFT);
		Label_ChoixGenre.setBounds(10, 89, 158, 13);
		panel_bottom_filtre.add(Label_ChoixGenre);

		comboBox_choixGenre = new JComboBox<String>();
		comboBox_choixGenre.setBounds(10, 112, 158, 21);
		panel_bottom_filtre.add(comboBox_choixGenre);

		JLabel Label_ChoixSujet = new JLabel("Sujet");
		Label_ChoixSujet.setHorizontalAlignment(SwingConstants.LEFT);
		Label_ChoixSujet.setBounds(10, 156, 158, 13);
		panel_bottom_filtre.add(Label_ChoixSujet);

		comboBox_choixSujet = new JComboBox<String>();
		comboBox_choixSujet.setBounds(10, 179, 158, 21);
		panel_bottom_filtre.add(comboBox_choixSujet);

		JLabel Label_ChoixActeur = new JLabel("Acteur");
		Label_ChoixActeur.setHorizontalAlignment(SwingConstants.LEFT);
		Label_ChoixActeur.setBounds(10, 233, 158, 13);
		panel_bottom_filtre.add(Label_ChoixActeur);

		comboBox_choixActeur = new JComboBox<String>();
		comboBox_choixActeur.setBounds(10, 256, 158, 21);
		panel_bottom_filtre.add(comboBox_choixActeur);

		JLabel Label_ChoixRealisateur = new JLabel("Réalisateur");
		Label_ChoixRealisateur.setHorizontalAlignment(SwingConstants.LEFT);
		Label_ChoixRealisateur.setBounds(10, 307, 158, 13);
		panel_bottom_filtre.add(Label_ChoixRealisateur);

		comboBox_choixRealisateur = new JComboBox<String>();
		comboBox_choixRealisateur.setBounds(10, 327, 158, 21);
		panel_bottom_filtre.add(comboBox_choixRealisateur);

		// ======================================
		// Panel resultats recherche
		// ======================================

		JPanel panel_ResultatRecherche = new JPanel();
		panel_ResultatRecherche.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_ResultatRecherche.setBackground(new Color(240, 240, 240));
		panel_ResultatRecherche.setBounds(200, 32, 242, 471);
		contentPane.add(panel_ResultatRecherche);
		panel_ResultatRecherche.setLayout(null);

		JLabel lblResultatRecherche = new JLabel("Résultat de la recherche");
		lblResultatRecherche.setBounds(10, 10, 222, 13);
		panel_ResultatRecherche.add(lblResultatRecherche);

		lbl_Err_AucunResultat = new JLabel("Aucun resultat trouvé");
		lbl_Err_AucunResultat.setVisible(false);
		lbl_Err_AucunResultat.setForeground(new Color(255, 0, 128));
		lbl_Err_AucunResultat.setBounds(10, 26, 222, 13);
		panel_ResultatRecherche.add(lbl_Err_AucunResultat);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 49, 222, 412);
		panel_ResultatRecherche.add(scrollPane);

		listResultatRecherche.addMouseListener(new ListMouseListener());
		scrollPane.setViewportView(listResultatRecherche);

		// ======================================
		// Panel infos film sélectionné
		// ======================================

		JPanel panel_InformationFilm = new JPanel();
		panel_InformationFilm.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_InformationFilm.setBounds(453, 32, 408, 471);
		contentPane.add(panel_InformationFilm);
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
		txtrDescription.setBounds(10, 176, 368, 73);
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

		lbl_LocationValide = new JLabel("Location Valide");
		lbl_LocationValide.setVisible(false);
		lbl_LocationValide.setBounds(10, 47, 368, 13);
		panel_location.add(lbl_LocationValide);

		// ======================================
		// Contenue des combos box du filtre
		// ======================================
		comboBox_choixTitre.addItem("<none>");
		comboBox_choixGenre.addItem("<none>");
		comboBox_choixSujet.addItem("<none>");
		comboBox_choixActeur.addItem("<none>");
		comboBox_choixRealisateur.addItem("<none>");

		try {
			ArrayList<String> titres = VideoDAO.TousLesTitresVideos(isVueClient);
			for (String t : titres) {
				comboBox_choixTitre.addItem(t);
			}

			ArrayList<Genre> genres = VideoDAO.TousLesGenres();
			for (Genre g : genres) {
				comboBox_choixGenre.addItem(g.getNom());
			}

			ArrayList<Sujet> sujets = VideoDAO.TousLesSujets();
			for (Sujet s : sujets) {
				comboBox_choixSujet.addItem(s.getNom());
			}

			ArrayList<Acteur> acteurs = VideoDAO.TousLesActeurs();
			for (Acteur a : acteurs) {
				comboBox_choixActeur.addItem(a.getPrenom() + " " + a.getNom());
			}

			ArrayList<Realisateur> realisateurs = VideoDAO.TousLesRealisateurs();
			for (Realisateur r : realisateurs) {
				comboBox_choixRealisateur.addItem(r.getPrenom() + " " + r.getNom());
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JButton btn_backToClientPage = new JButton("Retour à la page client");
		btn_backToClientPage.addMouseListener(new Btn_backToClientPageMouseListener());
		btn_backToClientPage.setBounds(10, 1, 168, 21);
		contentPane.add(btn_backToClientPage);
	}

	private class BtnGoMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			String textTitre = (String) comboBox_choixTitre.getSelectedItem();
			String textGenre = (String) comboBox_choixGenre.getSelectedItem();
			String textSujet = (String) comboBox_choixSujet.getSelectedItem();
			String textActeur = (String) comboBox_choixActeur.getSelectedItem();
			String textRealisateur = (String) comboBox_choixRealisateur.getSelectedItem();

			ArrayList<String[]> filtre = new ArrayList<String[]>();
			String innerJoinRequete = "";

			if (textTitre.compareTo("<none>") != 0) {
				String[] ajoutTitre = { "V.titre", textTitre };
				filtre.add(ajoutTitre);
			}
			if (textGenre.compareTo("<none>") != 0) {
				String[] ajoutGenre = { "G.nom", textGenre };
				filtre.add(ajoutGenre);
				innerJoinRequete += "LEFT JOIN VideoGenre VG on V.id = VG.idVideo "
						+ "LEFT JOIN Genre G on VG.idGenre = G.id ";
			}
			if (textSujet.compareTo("<none>") != 0) {
				String[] ajoutSujet = { "S.nom", textSujet };
				filtre.add(ajoutSujet);
				innerJoinRequete += "LEFT JOIN VideoSujet VS on V.id = VS.idVideo "
						+ "LEFT JOIN Sujet S on VS.idSujet = S.id ";
			}
			if (textActeur.compareTo("<none>") != 0) {
				String[] acteur = textActeur.split(" ");
				String[] ajoutActeurPrenom = { "A.prenom", acteur[0] };
				String[] ajoutActeurNom = { "A.nom", acteur[1] };
				filtre.add(ajoutActeurPrenom);
				filtre.add(ajoutActeurNom);
				innerJoinRequete += "LEFT JOIN VideoActeur VA on V.id = VA.idVideo "
						+ "LEFT JOIN Acteur A on VA.idActeur = A.id ";
			}
			if (textRealisateur.compareTo("<none>") != 0) {
				String[] realisateur = textRealisateur.split(" ");
				String[] ajoutRealisateurPrenom = { "R.prenom", realisateur[0] };
				String[] ajoutRealisateurNom = { "R.nom", realisateur[1] };
				filtre.add(ajoutRealisateurPrenom);
				filtre.add(ajoutRealisateurNom);
				innerJoinRequete += "LEFT JOIN VideoRealisateur VR on V.id = VR.idVideo "
						+ "LEFT JOIN Realisateur R on VR.idRealisateur = R.id ";

			}

			ArrayList<Video> listVideo = new ArrayList<Video>();

			if (textTitre.compareTo("<none>") == 0 && textGenre.compareTo("<none>") == 0
					&& textSujet.compareTo("<none>") == 0 && textActeur.compareTo("<none>") == 0
					&& textRealisateur.compareTo("<none>") == 0) {
				// si tous les choix de filtre sont a "<none>"
				try {
					listVideo = VideoDAO.TousLesVideos(isVueClient);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} else {
				// lancer la requête avec le filtre dynamique
				String requeteFiltre = VideoDAO.CreationConditionWhereDuFiltre(filtre);
				if (requeteFiltre.compareTo("") != 0) {
					try {
						listVideo = VideoDAO.VideosSelonFiltre(innerJoinRequete, requeteFiltre, isVueClient);
						// System.out.println(listVideo);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

			// clear model à chaque recherche
			modelListResultatRecherche.removeAllElements();
			// ajouter les items dans le jlist
			if (listVideo.isEmpty()) {
				lbl_Err_AucunResultat.setVisible(true);
			} else {
				lbl_Err_AucunResultat.setVisible(false);
				for (Video v : listVideo) {
					modelListResultatRecherche.addElement(v);
				}
			}
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
					btn_locationFilm.setVisible(false);
					lbl_InclusAbonnement.setVisible(true);
					btn_ecouterFilm.setVisible(true);
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

	// changer le contenu du panel Info Film
	private class ListMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			videoSelectionne = listResultatRecherche.getSelectedValue();
			videoSelectionne = VideoDAO.remplirInfosVideo(videoSelectionne);
			affichageInfoVideo(videoSelectionne);
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
}
