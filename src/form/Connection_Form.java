package form;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import bd.DbConnection;
import dao.ClientDAO;

public class Connection_Form extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JFrame frmConnection;
	private JTextField emailtxt;
	private JTextField passtxt;
	private JLabel lblErrConnection;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Connection_Form window = new Connection_Form();
					window.frmConnection.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Connection_Form() {
		initialize();

		try {
			DbConnection connection = DbConnection.getInstance();
			System.out.println("Connexion à la BD ouverte avec Succés");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmConnection = new JFrame();
		frmConnection.setTitle("Connection");
		frmConnection.setBounds(100, 100, 750, 510);
		frmConnection.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmConnection.getContentPane().setLayout(null);

		emailtxt = new JTextField();
		emailtxt.setBounds(55, 166, 634, 30);
		frmConnection.getContentPane().add(emailtxt);
		emailtxt.setColumns(10);

		passtxt = new JTextField();
		passtxt.setBounds(55, 242, 634, 30);
		frmConnection.getContentPane().add(passtxt);
		passtxt.setColumns(10);

		JLabel lblEmail = new JLabel("Courriel :");
		lblEmail.setBounds(55, 126, 165, 30);
		frmConnection.getContentPane().add(lblEmail);

		JLabel lblPass = new JLabel("Mot de passe :");
		lblPass.setBounds(55, 202, 165, 30);
		frmConnection.getContentPane().add(lblPass);

		lblErrConnection = new JLabel("erreur :");
		lblErrConnection.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrConnection.setVisible(false);
		lblErrConnection.setForeground(Color.RED);
		lblErrConnection.setBounds(55, 292, 634, 32);
		frmConnection.getContentPane().add(lblErrConnection);

		JButton btnConnection = new JButton("Connecter");
		btnConnection.addActionListener(new BtnNewButtonActionListener());
		btnConnection.setFocusTraversalKeysEnabled(false);
		btnConnection.setFocusPainted(false);
		btnConnection.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnConnection.setBounds(300, 373, 147, 21);
		frmConnection.getContentPane().add(btnConnection);

		JButton btnCreateUser = new JButton("Créer un compte");
		btnCreateUser.addMouseListener(new BtnCreateUserMouseListener());
		btnCreateUser.setFocusPainted(false);
		btnCreateUser.setFocusTraversalKeysEnabled(false);
		btnCreateUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCreateUser.setContentAreaFilled(false);
		btnCreateUser.setForeground(new Color(30, 144, 255));
		btnCreateUser.setBounds(300, 342, 147, 21);
		frmConnection.getContentPane().add(btnCreateUser);
	}

	public JTextField getEmailtxt() {
		return emailtxt;
	}

	public void setEmailtxt(JTextField emailtxt) {
		this.emailtxt = emailtxt;
	}

	public JTextField getPasstxt() {
		return passtxt;
	}

	public void setPasstxt(JTextField passtxt) {
		this.passtxt = passtxt;
	}

	public JLabel getLblErrConnection() {
		return lblErrConnection;
	}

	public void setLblErrConnection(JLabel lblErrConnection) {
		this.lblErrConnection = lblErrConnection;
	}

	private class BtnNewButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String txtemail = getEmailtxt().getText();
			String password = getPasstxt().getText();

			try {
				Object[] resultat1 = ClientDAO.VerifierConnexion(txtemail, password);
				int id = (int) resultat1[0];
				String typeUtilisateur = (String) resultat1[1];
				afficherResultat(id, typeUtilisateur, frmConnection);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private class BtnCreateUserMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			CreationCompte_Form appClient = new CreationCompte_Form();
			appClient.setVisible(true);
			frmConnection.dispose();
		}
	}

	public void afficherResultat(int id, String typeUtilisateur, JFrame frmConnection) {
		switch (typeUtilisateur) {
		case "A":
			Admin_page_Form appAdmin = new Admin_page_Form();
			appAdmin.setVisible(true);
			frmConnection.dispose();
			break;
		case "C":
			Client_page_Form appClient = new Client_page_Form(id);
			appClient.setVisible(true);
			frmConnection.dispose();
			break;
		case "E":
			getLblErrConnection().setText("Erreur : Courriel ou mot de passe incorrect.");
			getLblErrConnection().setVisible(true);
			break;
		default:

			break;
		}

	}
}
