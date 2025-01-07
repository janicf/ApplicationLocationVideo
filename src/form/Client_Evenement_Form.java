package form;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDateTime;
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

import _class.Evenement;
import dao.EvenementDAO;

public class Client_Evenement_Form extends JFrame {

	private int idClient;
	protected Evenement eventSelectionne;

	// affichage different model
	protected DefaultListModel<Evenement> modelListMesEvents = new DefaultListModel<Evenement>();
	protected JList<Evenement> list_mesEvents = new JList<Evenement>(modelListMesEvents);

	protected DefaultListModel<Evenement> modelListTousLesEvents = new DefaultListModel<Evenement>();
	protected JList<Evenement> list_tousLesEvents = new JList<Evenement>(modelListTousLesEvents);

	// affichage selon abonnement
	protected JLabel lblPrix;
	protected JButton btn_participer;
	protected JLabel lbl_DejaInscrit;
	protected JLabel lbl_eventTermine;

	// affichage infos films
	protected JLabel lblTitreEvenement;
	protected JLabel lblDateDeDebut;
	protected JLabel lblDateDeFin;
	protected JLabel lblTypeEvent;
	protected JLabel lblLieu;
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
					int idClientTest = 1;
					Client_Evenement_Form frame = new Client_Evenement_Form(idClientTest);
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
	public Client_Evenement_Form(int idClient) {
		this.idClient = idClient;
		initialize();
	}

	private void initialize() {
		setTitle("Evenement");
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

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(20, 39, 448, 514);
		contentPane.add(tabbedPane);

		JPanel tab_mesEvenements = new JPanel();
		tabbedPane.addTab("Mes Evenements", null, tab_mesEvenements, null);
		tab_mesEvenements.setLayout(null);

		list_mesEvents.addMouseListener(new List_mesEventsMouseListener());
		list_mesEvents.setBounds(10, 10, 423, 467);
		tab_mesEvenements.add(list_mesEvents);

		JPanel tab_TousLesEvenements = new JPanel();
		tabbedPane.addTab("Tous les évènements", null, tab_TousLesEvenements, null);
		tab_TousLesEvenements.setLayout(null);

		list_tousLesEvents.addMouseListener(new List_tousLesEventsMouseListener());
		list_tousLesEvents.setBounds(10, 9, 423, 467);
		tab_TousLesEvenements.add(list_tousLesEvents);

		// ======================================
		// Panel infos film sélectionné
		// ======================================

		JPanel panel_InformationFilm = new JPanel();
		panel_InformationFilm.setBounds(478, 39, 408, 514);
		contentPane.add(panel_InformationFilm);
		panel_InformationFilm.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_InformationFilm.setLayout(null);

		JLabel lblInfoEventSlectionne = new JLabel("Informations évènement sélectionné");
		lblInfoEventSlectionne.setBounds(10, 10, 235, 13);
		panel_InformationFilm.add(lblInfoEventSlectionne);

		JPanel panel_contenuEvent = new JPanel();
		panel_contenuEvent.setBounds(10, 33, 388, 428);
		panel_InformationFilm.add(panel_contenuEvent);
		panel_contenuEvent.setLayout(null);

		lblTitreEvenement = new JLabel("Titre de l'évènement");
		lblTitreEvenement.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTitreEvenement.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitreEvenement.setBounds(10, 10, 368, 20);
		panel_contenuEvent.add(lblTitreEvenement);

		lblDateDeDebut = new JLabel("Date de debut : ");
		lblDateDeDebut.setHorizontalAlignment(SwingConstants.LEFT);
		lblDateDeDebut.setBounds(10, 58, 368, 13);
		panel_contenuEvent.add(lblDateDeDebut);

		lblDateDeFin = new JLabel("Date de fin : ");
		lblDateDeFin.setHorizontalAlignment(SwingConstants.LEFT);
		lblDateDeFin.setBounds(10, 85, 368, 13);
		panel_contenuEvent.add(lblDateDeFin);

		lblTypeEvent = new JLabel("Type Event :");
		lblTypeEvent.setBounds(10, 131, 368, 13);
		panel_contenuEvent.add(lblTypeEvent);

		lblLieu = new JLabel("Lieu :");
		lblLieu.setBounds(10, 108, 368, 13);
		panel_contenuEvent.add(lblLieu);

		txtrDescription = new JTextArea();
		txtrDescription.setWrapStyleWord(true);
		txtrDescription.setFont(new Font("Tahoma", Font.PLAIN, 10));
		txtrDescription.setBorder(null);
		txtrDescription.setBackground(new Color(240, 240, 240));
		txtrDescription.setText("Description :");
		txtrDescription.setEditable(false);
		txtrDescription.setLineWrap(true);
		txtrDescription.setBounds(10, 154, 368, 74);
		panel_contenuEvent.add(txtrDescription);

		JPanel panel_evenement = new JPanel();
		panel_evenement.setBounds(0, 262, 388, 166);
		panel_contenuEvent.add(panel_evenement);
		panel_evenement.setLayout(null);

		lbl_DejaInscrit = new JLabel("Vous êtes déjà inscrit à cette évènement");
		lbl_DejaInscrit.setForeground(new Color(0, 128, 128));
		lbl_DejaInscrit.setVisible(false);
		lbl_DejaInscrit.setBounds(10, 43, 357, 21);
		panel_evenement.add(lbl_DejaInscrit);

		lbl_eventTermine = new JLabel("Cet évènement est terminé");
		lbl_eventTermine.setVisible(false);
		lbl_eventTermine.setForeground(new Color(255, 0, 128));
		lbl_eventTermine.setBounds(10, 47, 357, 13);
		panel_evenement.add(lbl_eventTermine);

		lblPrix = new JLabel("Prix : ");
		lblPrix.setBounds(10, 43, 357, 21);
		panel_evenement.add(lblPrix);

		btn_participer = new JButton("Participer");
		btn_participer.setVisible(false);
		btn_participer.addMouseListener(new Btn_participerMouseListener());
		btn_participer.setBounds(10, 78, 368, 21);
		panel_evenement.add(btn_participer);

		// ======================================
		// Contenue des jList des pages de tab
		// ======================================

		// ajouter les items dans le jlist_mesEvents et tous les events
		ArrayList<Evenement> rsListEventClient = new ArrayList<Evenement>();
		ArrayList<Evenement> rsListTousLesEvents = new ArrayList<Evenement>();

		try {
			rsListEventClient = EvenementDAO.EventClient(idClient);
			for (Evenement e : rsListEventClient) {
				modelListMesEvents.addElement(e);
			}

			rsListTousLesEvents = EvenementDAO.TousLesEvents();
			for (Evenement e : rsListTousLesEvents) {
				modelListTousLesEvents.addElement(e);
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	private void affichageInfoEvent(Evenement event) {
		// set titre, date de sortie, duree, description et prix
		lblTitreEvenement.setText(event.getTitre());
		lblDateDeDebut.setText("Date de debut: " + event.getDateDebut().toString());
		lblDateDeFin.setText("Date de fin: " + event.getDateFin().toString());
		lblTypeEvent.setText("Genres : " + event.getType_event().toString());
		lblLieu.setText("Sujets : " + event.getLieu().toString());
		txtrDescription.setText("Description : " + event.getDescription());
		lblPrix.setText("Prix : " + event.getPrix() + "$");

		// affichage selon une participation existante
		Boolean isParticipationValide = false;
		try {
			isParticipationValide = EvenementDAO.VerificationParticipationClient(idClient, event.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (isEventTermine(event)) {
			lblPrix.setVisible(false);
			btn_participer.setVisible(false);
			lbl_DejaInscrit.setVisible(false);
			lbl_eventTermine.setVisible(true);

		} else if (isParticipationValide) {
			lblPrix.setVisible(false);
			btn_participer.setVisible(false);
			lbl_DejaInscrit.setVisible(true);
			lbl_eventTermine.setVisible(false);

		} else {
			lblPrix.setVisible(true);
			btn_participer.setVisible(true);
			lbl_DejaInscrit.setVisible(false);
			lbl_eventTermine.setVisible(false);
		}

	}

	private boolean isEventTermine(Evenement event) {
		if (event.getDateFin().compareTo(LocalDateTime.now()) < 0) {
			return true;
		}
		return false;
	}

	private class Btn_backToClientPageMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			Client_page_Form appClient = new Client_page_Form(idClient);
			appClient.setVisible(true);
			dispose();
		}
	}

	private class Btn_participerMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			Client_Confirme_Event_Form appClient = new Client_Confirme_Event_Form(idClient, eventSelectionne);
			appClient.setVisible(true);
		}
	}

	// liste clické nécessite le changement de l'affichage du film
	private class List_mesEventsMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			eventSelectionne = list_mesEvents.getSelectedValue();
			affichageInfoEvent(eventSelectionne);
		}
	}

	private class List_tousLesEventsMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			eventSelectionne = list_tousLesEvents.getSelectedValue();
			affichageInfoEvent(eventSelectionne);
		}
	}
}
