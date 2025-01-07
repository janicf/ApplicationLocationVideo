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

import _class.Evenement;
import dao.EvenementDAO;

public class Client_Confirme_Event_Form extends JFrame {

	private int idClient;
	private Evenement event;

	protected JLabel lbl_TitreDuEvent_Variable;
	protected JLabel lbl_prixVariable;

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
					Evenement event = new Evenement();
					event.setId(10);
					event.setPrix(9.99);
					event.setTitre("Evenement Test");
					Client_Confirme_Event_Form frame = new Client_Confirme_Event_Form(idClient, event);
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
	public Client_Confirme_Event_Form(int idClient, Evenement event) {
		this.idClient = idClient;
		this.event = event;
		initialize();
	}

	private void initialize() {
		setTitle("Confirmation participation evenement");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbl_confirmation = new JLabel("Confirmez votre participation à l'évènement suivant : ");
		lbl_confirmation.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_confirmation.setBounds(10, 10, 376, 13);
		contentPane.add(lbl_confirmation);

		lbl_TitreDuEvent_Variable = new JLabel("Titre de l'évènement");
		lbl_TitreDuEvent_Variable.setForeground(new Color(0, 0, 0));
		lbl_TitreDuEvent_Variable.setBounds(0, 22, 386, 34);
		contentPane.add(lbl_TitreDuEvent_Variable);
		lbl_TitreDuEvent_Variable.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		lbl_TitreDuEvent_Variable.setHorizontalAlignment(SwingConstants.CENTER);

		JTextArea txtrEnCliquantConfirmer = new JTextArea();
		txtrEnCliquantConfirmer.setWrapStyleWord(true);
		txtrEnCliquantConfirmer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtrEnCliquantConfirmer.setText("En cliquant confirmer, vous acceptez que le prix suivant vous soit facturé.");
		txtrEnCliquantConfirmer.setEditable(false);
		txtrEnCliquantConfirmer.setBackground(new Color(240, 240, 240));
		txtrEnCliquantConfirmer.setLineWrap(true);
		txtrEnCliquantConfirmer.setBounds(10, 66, 366, 34);
		contentPane.add(txtrEnCliquantConfirmer);

		lbl_prixVariable = new JLabel("Prix : ");
		lbl_prixVariable.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_prixVariable.setBounds(10, 98, 366, 13);
		contentPane.add(lbl_prixVariable);

		JButton btnConfirmer = new JButton("Confirmer");
		btnConfirmer.addMouseListener(new BtnConfirmerMouseListener());
		btnConfirmer.setBounds(63, 132, 126, 21);
		contentPane.add(btnConfirmer);

		JButton btnCanceler = new JButton("Cancel");
		btnCanceler.addMouseListener(new BtnCancelerMouseListener());
		btnCanceler.setBounds(199, 132, 121, 21);
		contentPane.add(btnCanceler);

		affichageInfoEvent(event);
	}

	private void affichageInfoEvent(Evenement event) {
		// set titre, date de sortie, duree, description et prix
		lbl_TitreDuEvent_Variable.setText(event.getTitre());
		lbl_prixVariable.setText("Prix : " + event.getPrix() + "$");
	}

	private class BtnConfirmerMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			try {
				EvenementDAO.nouvelleParticipation(idClient, event.getId());
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
