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
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import _class.Video;
import dao.VideoDAO;

public class Client_Confirme_Location_Form extends JFrame {

	private int idClient;
	private Video video;

	protected JLabel lbl_TitreDuFilm_Variable;
	protected JLabel lbl_prixVariable;

	private JTextArea txtrEnCliquantConfirmer;
	private JLabel lbl_locationAbonnement;

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
					Video video = new Video();
					video.setId(10);
					video.setPrix(9.99);
					video.setTitre("Film Test");
					Client_Confirme_Location_Form frame = new Client_Confirme_Location_Form(idClient, video);
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
	public Client_Confirme_Location_Form(int idClient, Video video) {
		this.idClient = idClient;
		this.video = video;
		initialize();
	}

	private void initialize() {
		setTitle("Confirmation Location");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbl_confirmation = new JLabel("Confirmez votre location du video suivant : ");
		lbl_confirmation.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_confirmation.setBounds(10, 10, 376, 13);
		contentPane.add(lbl_confirmation);

		lbl_TitreDuFilm_Variable = new JLabel("Titre du video");
		lbl_TitreDuFilm_Variable.setForeground(new Color(0, 0, 0));
		lbl_TitreDuFilm_Variable.setBounds(0, 22, 386, 34);
		contentPane.add(lbl_TitreDuFilm_Variable);
		lbl_TitreDuFilm_Variable.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		lbl_TitreDuFilm_Variable.setHorizontalAlignment(SwingConstants.CENTER);

		// si non abonnée
		txtrEnCliquantConfirmer = new JTextArea();
		txtrEnCliquantConfirmer.setVisible(false);
		txtrEnCliquantConfirmer.setWrapStyleWord(true);
		txtrEnCliquantConfirmer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtrEnCliquantConfirmer.setText("En cliquant confirmer, vous acceptez que le prix suivant vous soit facturé.");
		txtrEnCliquantConfirmer.setEditable(false);
		txtrEnCliquantConfirmer.setBackground(new Color(240, 240, 240));
		txtrEnCliquantConfirmer.setLineWrap(true);
		txtrEnCliquantConfirmer.setBounds(10, 66, 366, 34);
		contentPane.add(txtrEnCliquantConfirmer);

		lbl_prixVariable = new JLabel("Prix : ");
		lbl_prixVariable.setVisible(false);
		lbl_prixVariable.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_prixVariable.setBounds(10, 98, 366, 13);
		contentPane.add(lbl_prixVariable);

		// si abonné
		lbl_locationAbonnement = new JLabel("Cette location est gratuite avec votre abonnement.");
		lbl_locationAbonnement.setVisible(false);
		lbl_locationAbonnement.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_locationAbonnement.setBounds(10, 83, 366, 13);
		contentPane.add(lbl_locationAbonnement);

		// boutons
		JButton btnConfirmer = new JButton("Confirmer");
		btnConfirmer.addMouseListener(new BtnConfirmerMouseListener());
		btnConfirmer.setBounds(63, 132, 126, 21);
		contentPane.add(btnConfirmer);

		JButton btnCanceler = new JButton("Cancel");
		btnCanceler.addMouseListener(new BtnCancelerMouseListener());
		btnCanceler.setBounds(199, 132, 121, 21);
		contentPane.add(btnCanceler);

		affichageInfoVideo(video);

	}

	private void affichageInfoVideo(Video video) {
		// set titre et prix

		lbl_TitreDuFilm_Variable.setText(video.getTitre());

		boolean isAbonnementValide = false;
		try {
			isAbonnementValide = VideoDAO.VerificationValiditeAbonnement(idClient);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (isAbonnementValide && video.isGratuit_abonne()) {
			// inclus avec l'abonnement
			lbl_locationAbonnement.setVisible(true);

		} else {
			// sans abonnement
			txtrEnCliquantConfirmer.setVisible(true);
			lbl_prixVariable.setVisible(true);
			lbl_prixVariable.setText("Prix : " + video.getPrix() + "$");
		}

	}

	private class BtnConfirmerMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {

			try {
				VideoDAO.nouvelleLocation(idClient, video.getId());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dispose();
		}
	}

	private class BtnCancelerMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			dispose();
		}
	}
}
