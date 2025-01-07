package form;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import _class.Client;
import _class.Evenement;
import _class.Genre;
import _class.Utile;
import _class.Video;
import _enum.EtatVideo;
import dao.ClientDAO;
import dao.EvenementDAO;
import dao.VideoDAO;

public class Admin_page_Form extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	protected JTextField textPrenom;
	protected JTextField textNom;
	protected JTextField textCourriel;
	private JTextField textTitre;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	protected JComboBox cmbxAbonnement;
	private JTextField textFilm;
	private JTextField txtSujet;
	private JTextField txtActeurPrenom;
	private JTextField txtPrenomRea;
	protected JComboBox cmbxType;
	protected JComboBox<String> cmbxGenre;

	private final DefaultListModel<Client> modelClient = new DefaultListModel<>();
	private JList<Client> listClient;

	private final DefaultListModel<Evenement> modelEvent = new DefaultListModel<>();
	private JList<Evenement> list_events;

	private final DefaultListModel<Video> modelVideo = new DefaultListModel<>();
	private JList<Video> listFilm;
	private JTextField txtActeurNom;
	private JTextField txtNomRea;

	private Video video = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Admin_page_Form frame = new Admin_page_Form();
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
	public Admin_page_Form() {
		initialize();
		setGenre();
		try {
			updateListCLient(ClientDAO.TousLesClients());
			updateListEvenement(EvenementDAO.TousLesEvenements());
			updateListFilm(VideoDAO.TousLesVideos(false));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initialize() {
		setTitle("Admin");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 856, 849);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		JPanel panel_film = new JPanel();
		panel_film.setBorder(null);
		panel_film.setToolTipText("Film");
		contentPane.add(panel_film);
		panel_film.setLayout(null);

		JPanel panel_recher_film = new JPanel();
		panel_recher_film.setBorder(null);
		panel_recher_film.setBounds(10, 10, 264, 247);
		panel_film.add(panel_recher_film);
		panel_recher_film.setLayout(null);

		JLabel lblFiltre = new JLabel("Filtre");
		lblFiltre.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblFiltre.setBounds(67, 12, 45, 13);
		panel_recher_film.add(lblFiltre);

		JButton btnRechercherFilm = new JButton("Rechercher");
		btnRechercherFilm.addActionListener(new BtnRechercherActionListener());
		btnRechercherFilm.setBounds(122, 10, 110, 21);
		panel_recher_film.add(btnRechercherFilm);

		JLabel lblTitreFilm = new JLabel("Titre du Film");
		lblTitreFilm.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTitreFilm.setBounds(10, 35, 102, 13);
		panel_recher_film.add(lblTitreFilm);

		JLabel lblGenre = new JLabel("Genre");
		lblGenre.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblGenre.setBounds(10, 89, 102, 13);
		panel_recher_film.add(lblGenre);

		cmbxGenre = new JComboBox();
		cmbxGenre.setBounds(10, 112, 120, 21);
		panel_recher_film.add(cmbxGenre);

		JLabel lblSujet = new JLabel("Sujet");
		lblSujet.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSujet.setBounds(140, 89, 102, 13);
		panel_recher_film.add(lblSujet);

		JLabel lblActeur = new JLabel("Prenom acteur");
		lblActeur.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblActeur.setBounds(10, 143, 120, 13);
		panel_recher_film.add(lblActeur);

		JLabel lblRealisateur = new JLabel("Prenom Réalis");
		lblRealisateur.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblRealisateur.setBounds(10, 189, 120, 13);
		panel_recher_film.add(lblRealisateur);

		textFilm = new JTextField();
		textFilm.setBounds(10, 60, 244, 19);
		panel_recher_film.add(textFilm);
		textFilm.setColumns(10);

		txtSujet = new JTextField();
		txtSujet.setBounds(140, 113, 114, 19);
		panel_recher_film.add(txtSujet);
		txtSujet.setColumns(10);

		txtActeurPrenom = new JTextField();
		txtActeurPrenom.setBounds(10, 160, 120, 19);
		panel_recher_film.add(txtActeurPrenom);
		txtActeurPrenom.setColumns(10);

		txtPrenomRea = new JTextField();
		txtPrenomRea.setBounds(10, 212, 120, 19);
		panel_recher_film.add(txtPrenomRea);
		txtPrenomRea.setColumns(10);

		txtActeurNom = new JTextField();
		txtActeurNom.setBounds(146, 160, 108, 19);
		panel_recher_film.add(txtActeurNom);
		txtActeurNom.setColumns(10);

		JLabel lblNomActeur = new JLabel("nom acteur");
		lblNomActeur.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNomActeur.setBounds(150, 143, 92, 13);
		panel_recher_film.add(lblNomActeur);

		txtNomRea = new JTextField();
		txtNomRea.setColumns(10);
		txtNomRea.setBounds(140, 212, 120, 19);
		panel_recher_film.add(txtNomRea);

		JLabel lblNomRalisateur = new JLabel("nom Réalis");
		lblNomRalisateur.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNomRalisateur.setBounds(140, 189, 114, 13);
		panel_recher_film.add(lblNomRalisateur);

		JPanel panel_list_film = new JPanel();
		panel_list_film.setBorder(null);
		panel_list_film.setBounds(284, 30, 384, 227);
		panel_film.add(panel_list_film);
		panel_list_film.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 364, 213);
		panel_list_film.add(scrollPane);

		listFilm = new JList();
		scrollPane.setViewportView(listFilm);

		JPanel panel_cmd_film = new JPanel();
		panel_cmd_film.setBorder(null);
		panel_cmd_film.setBounds(678, 30, 146, 227);
		panel_film.add(panel_cmd_film);
		panel_cmd_film.setLayout(null);

		JButton btnArchiver = new JButton("Archiver/Déarchiver");
		btnArchiver.addActionListener(new BtnArchiverActionListener());
		btnArchiver.setBounds(0, 124, 146, 21);
		panel_cmd_film.add(btnArchiver);

		JButton btnAjouterFilm = new JButton("Ajouter");
		btnAjouterFilm.addActionListener(new BtnAjouterFilmActionListener());
		btnAjouterFilm.setBounds(24, 82, 85, 21);
		panel_cmd_film.add(btnAjouterFilm);

		JLabel lblNewLabel = new JLabel("Contenu video");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(285, 10, 137, 13);
		panel_film.add(lblNewLabel);

		JPanel panel_client = new JPanel();
		panel_client.setAutoscrolls(true);
		contentPane.add(panel_client);
		panel_client.setLayout(null);

		JPanel panel_filtre_clients = new JPanel();
		panel_filtre_clients.setBounds(0, 10, 274, 247);
		panel_client.add(panel_filtre_clients);
		panel_filtre_clients.setLayout(null);

		JLabel lblFiltre_1 = new JLabel("Filtre");
		lblFiltre_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblFiltre_1.setBounds(78, 12, 45, 13);
		panel_filtre_clients.add(lblFiltre_1);

		JButton btnRechercheClient = new JButton("Rechercher");
		btnRechercheClient.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRechercheClient.addActionListener(new BtnRechercher_1ActionListener());
		btnRechercheClient.setBounds(133, 10, 110, 21);
		panel_filtre_clients.add(btnRechercheClient);

		JLabel lblprenom = new JLabel("Prenom");
		lblprenom.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblprenom.setBounds(10, 35, 102, 13);
		panel_filtre_clients.add(lblprenom);

		textPrenom = new JTextField();
		textPrenom.setBounds(10, 59, 254, 19);
		panel_filtre_clients.add(textPrenom);
		textPrenom.setColumns(10);

		JLabel lblNom = new JLabel("Nom");
		lblNom.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNom.setBounds(10, 88, 102, 13);
		panel_filtre_clients.add(lblNom);

		textNom = new JTextField();
		textNom.setColumns(10);
		textNom.setBounds(10, 112, 254, 19);
		panel_filtre_clients.add(textNom);

		JLabel lblCourriel = new JLabel("Courriel");
		lblCourriel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCourriel.setBounds(10, 141, 102, 13);
		panel_filtre_clients.add(lblCourriel);

		textCourriel = new JTextField();
		textCourriel.setColumns(10);
		textCourriel.setBounds(10, 165, 254, 19);
		panel_filtre_clients.add(textCourriel);

		JLabel lblNewLabel_1 = new JLabel("Abonner");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(10, 194, 102, 13);
		panel_filtre_clients.add(lblNewLabel_1);

		cmbxAbonnement = new JComboBox();
		cmbxAbonnement.setModel(new DefaultComboBoxModel(new String[] { "<None>", "Oui" }));
		cmbxAbonnement.setSelectedIndex(0);
		cmbxAbonnement.setToolTipText("");
		cmbxAbonnement.setBounds(10, 216, 254, 21);
		panel_filtre_clients.add(cmbxAbonnement);

		JPanel panel_5 = new JPanel();
		panel_5.setBounds(284, 32, 390, 225);
		panel_client.add(panel_5);
		panel_5.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 10, 370, 205);
		panel_5.add(scrollPane_1);

		listClient = new JList<Client>();
		scrollPane_1.setViewportView(listClient);

		JPanel panel_7 = new JPanel();
		panel_7.setBounds(684, 10, 138, 247);
		panel_client.add(panel_7);
		panel_7.setLayout(null);

		JLabel lblClient = new JLabel("Clients");
		lblClient.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblClient.setBounds(285, 9, 137, 13);
		panel_client.add(lblClient);

		JPanel panel_Evenement = new JPanel();
		contentPane.add(panel_Evenement);
		panel_Evenement.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 263, 247);
		panel_Evenement.add(panel);
		panel.setLayout(null);

		JLabel lblFiltre_1_1 = new JLabel("Filtre");
		lblFiltre_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblFiltre_1_1.setBounds(58, 12, 45, 13);
		panel.add(lblFiltre_1_1);

		JButton btnRechercherEvent = new JButton("Rechercher");
		btnRechercherEvent.addActionListener(new BtnRechercher_1_1ActionListener());
		btnRechercherEvent.setBounds(113, 10, 110, 21);
		panel.add(btnRechercherEvent);

		JLabel lblTitre = new JLabel("Titre");
		lblTitre.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTitre.setBounds(10, 35, 243, 13);
		panel.add(lblTitre);

		textTitre = new JTextField();
		textTitre.setColumns(10);
		textTitre.setBounds(10, 59, 243, 19);
		panel.add(textTitre);

		cmbxType = new JComboBox();
		cmbxType.setModel(
				new DefaultComboBoxModel(new String[] { "<None>", "projection", "exposition", "activité", "autre" }));
		cmbxType.setBounds(9, 111, 244, 21);
		panel.add(cmbxType);

		JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblType.setBounds(9, 88, 102, 13);
		panel.add(lblType);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(283, 33, 390, 224);
		panel_Evenement.add(panel_1);
		panel_1.setLayout(null);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 10, 370, 204);
		panel_1.add(scrollPane_2);

		list_events = new JList();
		scrollPane_2.setViewportView(list_events);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(683, 10, 139, 247);
		panel_Evenement.add(panel_2);
		panel_2.setLayout(null);

		JButton btnAjouterEvent = new JButton("Ajouter");
		btnAjouterEvent.addActionListener(new BtnAjouterEventActionListener());
		btnAjouterEvent.setBounds(28, 27, 85, 21);
		panel_2.add(btnAjouterEvent);

		JLabel lblEvenement = new JLabel("Évenement");
		lblEvenement.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblEvenement.setBounds(283, 10, 137, 20);
		panel_Evenement.add(lblEvenement);
	}

	private void updateListCLient(ArrayList<Client> clients) throws SQLException {
		modelClient.removeAllElements();

		for (Client c : clients) {

			modelClient.addElement(c);
		}

		listClient.setModel(modelClient);
	}

	private void updateListEvenement(ArrayList<Evenement> events) throws SQLException {
		modelEvent.removeAllElements();

		for (Evenement e : events) {
			modelEvent.addElement(e);
		}
		list_events.setModel(modelEvent);
	}

	private void updateListFilm(ArrayList<Video> videos) throws SQLException {
		modelVideo.removeAllElements();

		for (Video v : videos) {
			modelVideo.addElement(v);
		}
		listFilm.setModel(modelVideo);
	}

	private void setGenre() {
		cmbxGenre.addItem("<none>");
		try {
			ArrayList<Genre> genres = VideoDAO.TousLesGenres();
			for (Genre g : genres) {
				cmbxGenre.addItem(g.getNom());
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private class BtnRechercher_1ActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Enlever espace
			String prenom = textPrenom.getText().replace(" ", "");
			String nom = textNom.getText().replace(" ", "");
			String courriel = textCourriel.getText().replace(" ", "");

			ArrayList<String[]> mesFiltres = new ArrayList<String[]>();
			if (!prenom.isEmpty()) {
				String[] filtrePrenom = { "prenom", prenom };
				mesFiltres.add(filtrePrenom);
			}
			if (!nom.isEmpty()) {
				String[] filtreNom = { "nom", nom };
				mesFiltres.add(filtreNom);
			}
			if (!courriel.isEmpty()) {
				String[] filtreCourriel = { "courriel", courriel };
				mesFiltres.add(filtreCourriel);
			}

			int index = cmbxAbonnement.getSelectedIndex();
			String[] filtreAbonnement = new String[2];
			// String[] filtreAbonnement_2 = new String[2];
			switch (index) {
			case 0:
				break;
			case 1:
				filtreAbonnement[0] = "actif";
				filtreAbonnement[1] = "1";
				mesFiltres.add(filtreAbonnement);
				break;
			default:
				break;
			}

			try {
				if (mesFiltres.size() == 0) {
					updateListCLient(ClientDAO.TousLesClients());
				} else {
					ArrayList<Client> listClientFiltre = ClientDAO
							.RecherherClientParObj(Utile.CreationConditionWhereDuFiltre(mesFiltres));
					updateListCLient(listClientFiltre);
				}

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private class BtnRechercher_1_1ActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String titre = textTitre.getText().replace(" ", "");
			ArrayList<String[]> mesFiltres = new ArrayList<String[]>();
			if (!titre.isEmpty()) {
				String[] filtreTitre = { "titre", titre };
				mesFiltres.add(filtreTitre);
			}
			int index = cmbxType.getSelectedIndex();
			String[] filtreTypeEvent = new String[2];
			if (index != 0) {
				filtreTypeEvent[0] = "type_event";
				filtreTypeEvent[1] = cmbxType.getSelectedItem().toString();
				mesFiltres.add(filtreTypeEvent);
			}

			try {
				if (mesFiltres.size() == 0) {
					updateListEvenement(EvenementDAO.TousLesEvenements());
				} else {
					ArrayList<Evenement> listEventFiltre = EvenementDAO
							.RecherherEvenementParObj(Utile.CreationConditionWhereDuFiltre(mesFiltres));
					updateListEvenement(listEventFiltre);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

	private class BtnRechercherActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String titre = textFilm.getText().replace(" ", "");
			String genre = (String) cmbxGenre.getSelectedItem();
			String sujet = txtSujet.getText().replace(" ", "");
			String prenomActeur = txtActeurPrenom.getText().replace(" ", "");
			String nomActeur = txtActeurNom.getText().replace(" ", "");
			String prenomRea = txtPrenomRea.getText().replace(" ", "");
			String nomRea = txtNomRea.getText().replace(" ", "");

			ArrayList<String[]> filtre = new ArrayList<String[]>();
			String LeftJoinRequete = ""; // STRING CONTENANT TOUS LES JOINTURES NECESSAIRE POUR LES FILTRES CHOISIT

			// AJOUT DES FILTRES
			if (!titre.isEmpty()) {
				String[] ajoutTitre = { "V.titre", titre };
				filtre.add(ajoutTitre);
			}
			if (genre.compareTo("<none>") != 0) {
				String[] ajoutGenre = { "G.nom", genre }; // WHERE G.NOM = valeur_textGenre
				filtre.add(ajoutGenre);
				LeftJoinRequete += "LEFT JOIN VideoGenre VG on V.id = VG.idVideo " // AJOUT DES JOINTURES QU'ON A BESOIN
						+ "LEFT JOIN Genre G on VG.idGenre = G.id ";
			}
			if (!sujet.isEmpty()) {
				String[] ajoutSujet = { "S.nom", sujet };
				filtre.add(ajoutSujet);
				LeftJoinRequete += "LEFT JOIN VideoSujet VS on V.id = VS.idVideo "
						+ "LEFT JOIN Sujet S on VS.idSujet = S.id ";
			}
			if (!prenomActeur.isEmpty() || !nomActeur.isEmpty()) { // LES INPUTS VIENT DU MEME TABLE DONC UN SEULE JOIN
				if (!prenomActeur.isEmpty()) {
					String[] ajoutActeurPrenom = { "A.prenom", prenomActeur };
					filtre.add(ajoutActeurPrenom);
				}
				if (!nomActeur.isEmpty()) {
					String[] ajoutActeurNom = { "A.nom", nomActeur };
					filtre.add(ajoutActeurNom);
				}
				LeftJoinRequete += "LEFT JOIN VideoActeur VA on V.id = VA.idVideo "
						+ "LEFT JOIN Acteur A on VA.idActeur = A.id ";
			}
			if (!prenomRea.isEmpty() || !nomRea.isEmpty()) { // LES INPUTS VIENT DU MEME TABLE DONC UN SEULE JOIN
				if (!prenomRea.isEmpty()) {
					String[] ajoutReaPrenom = { "R.prenom", prenomRea };
					filtre.add(ajoutReaPrenom);
				}
				if (!nomRea.isEmpty()) {
					String[] ajoutReaNom = { "R.nom", nomRea };
					filtre.add(ajoutReaNom);
				}
				LeftJoinRequete += "LEFT JOIN VideoRealisateur VR on V.id = VR.idVideo "
						+ "LEFT JOIN Realisateur R on VR.idRealisateur = R.id ";
			}

			// BUIL REQUETE
			try {
				if (filtre.size() == 0) {
					updateListFilm(VideoDAO.TousLesVideos(false));
				} else {
					String requeteFiltre = VideoDAO.CreationConditionWhereDuFiltre(filtre);
					ArrayList<Video> listVideoFiltre = VideoDAO.VideosSelonFiltre(LeftJoinRequete, requeteFiltre,
							false);
					updateListFilm(listVideoFiltre);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private class BtnAjouterFilmActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			AjoutContenu_Form ajoutVideo = new AjoutContenu_Form();
			ajoutVideo.setModal(true);
			ajoutVideo.setVisible(true);
			try {
				updateListFilm(VideoDAO.TousLesVideos(false));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private class BtnArchiverActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			video = listFilm.getSelectedValue();
			if (video != null) {
				try {
					if (video.getEtat() == EtatVideo.archivé) {

						VideoDAO.desarchiverFilm(video.getId());
					} else {
						VideoDAO.archiverFilm(video.getId());
					}
					updateListFilm(VideoDAO.TousLesVideos(false));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	private class BtnAjouterEventActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			AjoutEvenement_Form event = new AjoutEvenement_Form();
			event.setModal(true);
			event.setVisible(true);
			try {
				updateListEvenement(EvenementDAO.TousLesEvenements());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

}
