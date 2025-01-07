package form;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import _class.Acteur;
import _class.Genre;
import _class.Realisateur;
import _class.Sujet;
import _class.Utile;
import _class.Video;
import _enum.EtatVideo;
import _enum.TypeVideo;
import dao.VideoDAO;

public class AjoutContenu_Form extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_Titre;
	private JTextField textField_Prix;
	private JTextField textField_Duree;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField txtSujet;
	private JTextField txtDateSortie;
	protected JComboBox<TypeVideo> comboBox_TypeVideo;
	protected JComboBox<EtatVideo> comboBox_Etat;
	private JTextField textGenre;
	protected JRadioButton rdbtnOui;

	// GENRE
	protected JList<Genre> listGenre;
	private final DefaultListModel<Genre> modelGenre = new DefaultListModel<>();
	protected ArrayList<Genre> genreVideos;
	private ArrayList<Genre> tousLesGenres;

	// SUJET
	protected JList<Sujet> listSujet;
	private final DefaultListModel<Sujet> modelSujet = new DefaultListModel<>();
	protected ArrayList<Sujet> sujetVideos;
	private ArrayList<Sujet> tousLesSujets;

	// ACTEUR
	protected JList<Acteur> listActeur;
	private final DefaultListModel<Acteur> modelActeur = new DefaultListModel<>();
	protected ArrayList<Acteur> acteurVideos = new ArrayList<Acteur>();

	// REALISATEUR
	protected JList<Realisateur> listRea;
	private final DefaultListModel<Realisateur> modelReali = new DefaultListModel<>();
	protected ArrayList<Realisateur> realisVideos = new ArrayList<Realisateur>();

	protected JTextArea txtDescription;
	protected JLabel lblNewErr;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					AjoutContenu_Form frame = new AjoutContenu_Form();
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
	public AjoutContenu_Form() {
		setTitle("Ajout contenu");
		initialize();
		comboBox_TypeVideo.setModel(new DefaultComboBoxModel<>(TypeVideo.values()));
		comboBox_Etat.setModel(new DefaultComboBoxModel<>(EtatVideo.values()));

		JLabel lblNewLabel = new JLabel("format: nombre entier");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		lblNewLabel.setBounds(111, 270, 150, 13);
		contentPane.add(lblNewLabel);

		JLabel lblFormatChiffreSeulement = new JLabel("format: chiffre seulement");
		lblFormatChiffreSeulement.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		lblFormatChiffreSeulement.setBounds(100, 74, 150, 13);
		contentPane.add(lblFormatChiffreSeulement);
		genreVideos = new ArrayList<>();
		sujetVideos = new ArrayList<>();
	}

	private void initialize() {
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 650, 625);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitre = new JLabel("Titre *");
		lblTitre.setBounds(52, 27, 61, 16);
		contentPane.add(lblTitre);

		textField_Titre = new JTextField();
		textField_Titre.setBounds(52, 45, 188, 16);
		contentPane.add(textField_Titre);
		textField_Titre.setColumns(10);

		JLabel lblPrix = new JLabel("Prix *");
		lblPrix.setBounds(52, 71, 61, 16);
		contentPane.add(lblPrix);

		textField_Prix = new JTextField();
		textField_Prix.setBounds(52, 89, 188, 16);
		contentPane.add(textField_Prix);
		textField_Prix.setColumns(10);

		JLabel lblTypeVideo = new JLabel("Type vidéo *");
		lblTypeVideo.setBounds(52, 115, 89, 16);
		contentPane.add(lblTypeVideo);

		comboBox_TypeVideo = new JComboBox<TypeVideo>();
		comboBox_TypeVideo.setBounds(52, 134, 188, 16);
		contentPane.add(comboBox_TypeVideo);

		JLabel lblDescriptionVideo = new JLabel("Description vidéo");
		lblDescriptionVideo.setBounds(52, 160, 130, 16);
		contentPane.add(lblDescriptionVideo);

		JLabel lblDuree = new JLabel("Durée *");
		lblDuree.setBounds(52, 268, 61, 16);
		contentPane.add(lblDuree);

		textField_Duree = new JTextField();
		textField_Duree.setBounds(52, 288, 188, 16);
		contentPane.add(textField_Duree);
		textField_Duree.setColumns(10);

		JLabel lblDateDeSortie = new JLabel("Date de sortie *");
		lblDateDeSortie.setBounds(273, 344, 109, 16);
		contentPane.add(lblDateDeSortie);

		JLabel lbletat = new JLabel("État *");
		lbletat.setBounds(52, 315, 61, 16);
		contentPane.add(lbletat);

		comboBox_Etat = new JComboBox<EtatVideo>();
		comboBox_Etat.setBounds(52, 344, 130, 20);
		contentPane.add(comboBox_Etat);

		JLabel lblGratuitAbonne = new JLabel("Gratuit abonné *");
		lblGratuitAbonne.setBounds(52, 374, 109, 16);
		contentPane.add(lblGratuitAbonne);

		rdbtnOui = new JRadioButton("Oui");
		buttonGroup.add(rdbtnOui);
		rdbtnOui.setBounds(52, 397, 73, 23);
		contentPane.add(rdbtnOui);

		JRadioButton rdbtnNon = new JRadioButton("Non");
		rdbtnNon.setSelected(true);
		buttonGroup.add(rdbtnNon);
		rdbtnNon.setBounds(123, 397, 67, 23);
		contentPane.add(rdbtnNon);

		JLabel lblGenre = new JLabel("Genre");
		lblGenre.setBounds(273, 27, 61, 16);
		contentPane.add(lblGenre);

		JLabel lblSujet = new JLabel("Sujet");
		lblSujet.setBounds(273, 115, 32, 16);
		contentPane.add(lblSujet);

		txtSujet = new JTextField();
		txtSujet.setBounds(317, 115, 103, 16);
		contentPane.add(txtSujet);
		txtSujet.setColumns(10);

		JLabel lblListeActeur = new JLabel("Liste acteur");
		lblListeActeur.setBounds(273, 186, 109, 16);
		contentPane.add(lblListeActeur);

		JLabel lblListeRalisateur = new JLabel("Liste réalisateur");
		lblListeRalisateur.setBounds(273, 268, 130, 16);
		contentPane.add(lblListeRalisateur);

		JButton btnCrer = new JButton("Créer");
		btnCrer.addActionListener(new BtnCrerActionListener());
		btnCrer.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		btnCrer.setBounds(232, 551, 117, 29);
		contentPane.add(btnCrer);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(273, 59, 242, 46);
		contentPane.add(scrollPane_1);

		listGenre = new JList<Genre>();
		scrollPane_1.setViewportView(listGenre);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(273, 212, 242, 46);
		contentPane.add(scrollPane_2);

		listActeur = new JList<Acteur>();
		scrollPane_2.setViewportView(listActeur);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(52, 186, 188, 72);
		contentPane.add(scrollPane);

		txtDescription = new JTextArea();
		scrollPane.setViewportView(txtDescription);

		JButton btnAjouterGenre = new JButton("Ajouter");
		btnAjouterGenre.addActionListener(new BtnAjouterGenreActionListener());
		btnAjouterGenre.setBounds(525, 58, 101, 21);
		contentPane.add(btnAjouterGenre);

		JButton btnNewButton_1 = new JButton("Supprimer");
		btnNewButton_1.setBounds(525, 84, 101, 21);
		contentPane.add(btnNewButton_1);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(273, 134, 242, 46);
		contentPane.add(scrollPane_3);

		listSujet = new JList<Sujet>();
		scrollPane_3.setViewportView(listSujet);

		JButton btnNewButton_2 = new JButton("Ajouter");
		btnNewButton_2.addActionListener(new BtnNewButton_2ActionListener());
		btnNewButton_2.setBounds(525, 129, 101, 21);
		contentPane.add(btnNewButton_2);

		JButton btnNewButton_1_1 = new JButton("Supprimer");
		btnNewButton_1_1.setBounds(525, 155, 101, 21);
		contentPane.add(btnNewButton_1_1);

		JButton btnNewButton_3 = new JButton("Ajouter");
		btnNewButton_3.addActionListener(new BtnNewButton_3ActionListener());
		btnNewButton_3.setBounds(525, 212, 101, 21);
		contentPane.add(btnNewButton_3);

		JButton btnNewButton_1_2 = new JButton("Supprimer");
		btnNewButton_1_2.setBounds(525, 238, 101, 21);
		contentPane.add(btnNewButton_1_2);

		JButton btnNewButton_3_1 = new JButton("Ajouter");
		btnNewButton_3_1.addActionListener(new BtnNewButton_3_1ActionListener());
		btnNewButton_3_1.setBounds(525, 288, 101, 21);
		contentPane.add(btnNewButton_3_1);

		JButton btnNewButton_1_2_1 = new JButton("Supprimer");
		btnNewButton_1_2_1.setBounds(525, 314, 101, 21);
		contentPane.add(btnNewButton_1_2_1);

		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(273, 288, 242, 45);
		contentPane.add(scrollPane_4);

		listRea = new JList<Realisateur>();
		scrollPane_4.setViewportView(listRea);

		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setBounds(52, 492, 574, 29);
		contentPane.add(scrollPane_5);

		txtDateSortie = new JTextField();
		txtDateSortie.setBounds(273, 370, 96, 19);
		contentPane.add(txtDateSortie);
		txtDateSortie.setColumns(10);

		JLabel lblforma = new JLabel("Format : yyyy-mm-dd");
		lblforma.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		lblforma.setBounds(379, 346, 140, 13);
		contentPane.add(lblforma);

		textGenre = new JTextField();
		textGenre.setColumns(10);
		textGenre.setBounds(317, 27, 103, 16);
		contentPane.add(textGenre);

		lblNewErr = new JLabel("");
		lblNewErr.setBounds(52, 449, 574, 33);
		contentPane.add(lblNewErr);
	}

	private void updateListGenre(ArrayList<Genre> genre) {
		modelGenre.removeAllElements();

		for (Genre g : genre) {

			modelGenre.addElement(g);
		}

		listGenre.setModel(modelGenre);
	}

	private ArrayList<Genre> updateTouslesGenres() throws SQLException {
		return tousLesGenres = VideoDAO.TousLesGenres();
	}

	private void updateListSujet(ArrayList<Sujet> sujet) {
		modelSujet.removeAllElements();

		for (Sujet s : sujet) {

			modelSujet.addElement(s);
		}

		listSujet.setModel(modelSujet);
	}

	private ArrayList<Sujet> updateTouslesSujets() throws SQLException {
		return tousLesSujets = VideoDAO.TousLesSujets();
	}

	private void updateListActeur(ArrayList<Acteur> acteur) {
		modelActeur.removeAllElements();

		for (Acteur a : acteur) {

			modelActeur.addElement(a);
		}

		listActeur.setModel(modelActeur);
	}

	private void updateListReali(ArrayList<Realisateur> realisateur) {
		modelReali.removeAllElements();

		for (Realisateur r : realisateur) {

			modelReali.addElement(r);
		}

		listRea.setModel(modelReali);
	}

	private class BtnAjouterGenreActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Boolean
			Boolean genreExiste = false;

			// INPUT
			String nomGenre = textGenre.getText().replace(" ", "");

			// Verifie Si le GENRE existe
			try {
				for (Genre g : updateTouslesGenres()) {
					if (g.getNom().equalsIgnoreCase(nomGenre)) {// EXIST DEJA
						genreExiste = true;
						if (!genreVideos.contains(g)) {// EMPECHER DOUBLON
							genreVideos.add(g);
							updateListGenre(genreVideos);
						}

					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (!genreExiste && !nomGenre.isEmpty()) { // S'il n'existe pas
				// CREER UPDATE
				try {
					VideoDAO.CreateGenre(nomGenre);
					Genre genre = VideoDAO.RechercheGenreParSonNom(nomGenre);
					if (genre != null) {
						genreVideos.add(genre);
						updateListGenre(genreVideos);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

				}

			}

		}
	}

	private class BtnNewButton_2ActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Boolean sujetExiste = false;
			String nomSujet = txtSujet.getText().replace(" ", "");

			// Verifie Si le SUJET existe
			try {
				for (Sujet s : updateTouslesSujets()) {
					if (s.getNom().equalsIgnoreCase(nomSujet)) {// EXIST DEJA
						sujetExiste = true;
						if (!sujetVideos.contains(s)) {// EMPECHER DOUBLON
							sujetVideos.add(s);
							updateListSujet(sujetVideos);
						}

					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (!sujetExiste && !nomSujet.isEmpty()) { // S'il n'existe pas
				try {
					VideoDAO.CreateSujet(nomSujet);
					Sujet sujet = VideoDAO.RechercheSujetParSonNom(nomSujet);
					if (sujet != null) {
						sujetVideos.add(sujet);
						updateListSujet(sujetVideos);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	private class BtnNewButton_3ActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Ajouter_Acteur_Form acteurApp = new Ajouter_Acteur_Form();
			acteurApp.setModal(true);
			acteurApp.setVisible(true);
			if (!acteurVideos.contains(acteurApp.getA())) {
				acteurVideos.add(acteurApp.getA());
				updateListActeur(acteurVideos);
			}

		}
	}

	private class BtnNewButton_3_1ActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Ajouter_Reali_Form realiApp = new Ajouter_Reali_Form();
			realiApp.setModal(true);
			realiApp.setVisible(true);
			if (!realisVideos.contains(realiApp.getR())) {
				realisVideos.add(realiApp.getR());
				updateListReali(realisVideos);
			}
		}
	}

	private class BtnCrerActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String titre = textField_Titre.getText();
				Double prix = Double.parseDouble(textField_Prix.getText());
				TypeVideo type = (TypeVideo) comboBox_TypeVideo.getSelectedItem();
				String descrip = txtDescription.getText();
				int durre = Integer.parseInt(textField_Duree.getText());
				EtatVideo etat = (EtatVideo) comboBox_Etat.getSelectedItem();
				Boolean gratuit = rdbtnOui.isSelected();
				Date dateSortie = Utile.parseDate(txtDateSortie.getText());
				Date dateAjout = java.sql.Date.valueOf(LocalDate.now());

				Video v = new Video(prix, titre, type, descrip, durre, dateSortie, dateAjout, etat, gratuit);
				System.out.println(dateAjout);
				VideoDAO.AjouterVideo(v); // AJOUT FILM
				int id = VideoDAO.TrouverIDVideo(v);
				VideoDAO.AjouterGenresVideo(id, genreVideos);
				VideoDAO.AjouterSujetVideo(id, sujetVideos); //
				VideoDAO.AjouterActeurVideo(id, acteurVideos);
				VideoDAO.AjouterReaVideo(id, realisVideos);
				dispose();
			} catch (NumberFormatException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				lblNewErr.setText("Veuiller remplir les champs obligatoires dans son format mentionner");
			}
		}
	}
}
