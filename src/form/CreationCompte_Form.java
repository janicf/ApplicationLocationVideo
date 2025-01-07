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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import dao.ClientDAO;

public class CreationCompte_Form extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lbl_Err_RemplirToutesLesCases;
	private JTextField textField_Prenom;
	private JTextField textField_Nom;
	private JTextField textField_Courriel;
	private JLabel lbl_Err_CourrielDejaExistant;
	private JTextField textField_MotDePasse;
	private JTextField textField_DateNaissance;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					CreationCompte_Form frame = new CreationCompte_Form();
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
	public CreationCompte_Form() {
		initialize();
	}

	private void initialize() {
		setTitle("Création compte");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 661, 511);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCreationCompte = new JLabel("Création de compte");
		lblCreationCompte.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblCreationCompte.setBounds(247, 29, 166, 16);
		contentPane.add(lblCreationCompte);

		lbl_Err_RemplirToutesLesCases = new JLabel("Veuiller remplir toutes les cases avant de continuer");
		lbl_Err_RemplirToutesLesCases.setVisible(false);
		lbl_Err_RemplirToutesLesCases.setForeground(new Color(255, 0, 128));
		lbl_Err_RemplirToutesLesCases.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Err_RemplirToutesLesCases.setBounds(129, 407, 365, 13);
		contentPane.add(lbl_Err_RemplirToutesLesCases);

		JLabel lblNom = new JLabel("Nom");
		lblNom.setBounds(217, 261, 61, 16);
		contentPane.add(lblNom);

		textField_Prenom = new JTextField();
		textField_Prenom.setBounds(217, 225, 186, 26);
		contentPane.add(textField_Prenom);
		textField_Prenom.setColumns(10);

		JLabel lblPrnom = new JLabel("Prénom");
		lblPrnom.setBounds(217, 199, 61, 16);
		contentPane.add(lblPrnom);

		textField_Nom = new JTextField();
		textField_Nom.setBounds(217, 284, 186, 26);
		contentPane.add(textField_Nom);
		textField_Nom.setColumns(10);

		JLabel lblDateDeNaissance = new JLabel("Date de naissance (YYYY-MM-JJ)");
		lblDateDeNaissance.setBounds(217, 334, 246, 16);
		contentPane.add(lblDateDeNaissance);

		JLabel lblCourriel = new JLabel("Courriel");
		lblCourriel.setBounds(217, 79, 91, 16);
		contentPane.add(lblCourriel);

		textField_Courriel = new JTextField();
		textField_Courriel.setBounds(217, 97, 186, 26);
		contentPane.add(textField_Courriel);
		textField_Courriel.setColumns(10);

		lbl_Err_CourrielDejaExistant = new JLabel("Erreur un compte existe déjà avec ce couriel");
		lbl_Err_CourrielDejaExistant.setVisible(false);
		lbl_Err_CourrielDejaExistant.setForeground(new Color(255, 0, 128));
		lbl_Err_CourrielDejaExistant.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Err_CourrielDejaExistant.setBounds(129, 122, 365, 13);
		contentPane.add(lbl_Err_CourrielDejaExistant);

		JLabel lblMotDePasse = new JLabel("Mot de passe");
		lblMotDePasse.setBounds(217, 145, 114, 16);
		contentPane.add(lblMotDePasse);

		textField_MotDePasse = new JTextField();
		textField_MotDePasse.setBounds(217, 163, 186, 26);
		contentPane.add(textField_MotDePasse);
		textField_MotDePasse.setColumns(10);

		textField_DateNaissance = new JTextField();
		textField_DateNaissance.setBounds(217, 353, 186, 26);
		contentPane.add(textField_DateNaissance);
		textField_DateNaissance.setColumns(10);

		JButton btn_Creation = new JButton("Créer compte");
		btn_Creation.addMouseListener(new Btn_CreationMouseListener());
		btn_Creation.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		btn_Creation.setBounds(252, 430, 117, 29);
		contentPane.add(btn_Creation);

	}

	private class Btn_CreationMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			lbl_Err_CourrielDejaExistant.setVisible(false);
			lbl_Err_RemplirToutesLesCases.setVisible(false);

			String courriel = textField_Courriel.getText();
			String prenom = textField_Prenom.getText();
			String nom = textField_Nom.getText();
			String mdp = textField_MotDePasse.getText();
			String dateNaissance = textField_DateNaissance.getText();

			Boolean isCourrielUnique = false;
			try {
				isCourrielUnique = ClientDAO.courrielUnique(courriel);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (emptyFields(courriel, prenom, nom, mdp, dateNaissance)) {
				lbl_Err_RemplirToutesLesCases.setVisible(true); // afficher message erreur
			} else if (isCourrielUnique) {
				try {
					ClientDAO.nouveauClient(prenom, nom, dateNaissance, mdp, courriel);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				Connection_Form appClient1 = new Connection_Form();
				appClient1.frmConnection.setVisible(true);

				Succes_Creation_Compte_Form appClient2 = new Succes_Creation_Compte_Form();
				appClient2.setVisible(true);

				dispose();
			} else {
				lbl_Err_CourrielDejaExistant.setVisible(true); // afficher message erreur
			}
		}

		private boolean emptyFields(String courriel, String prenom, String nom, String mdp, String dateNaissance) {
			if (courriel.compareTo("") == 0) {
				return true;
			}
			if (prenom.compareTo("") == 0) {
				return true;
			}
			if (nom.compareTo("") == 0) {
				return true;
			}
			if (mdp.compareTo("") == 0) {
				return true;
			}
			if (dateNaissance.compareTo("") == 0) {
				return true;
			}
			return false;
		}
	}
}
