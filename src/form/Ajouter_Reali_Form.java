package form;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import _class.Realisateur;
import _class.Utile;
import dao.VideoDAO;

public class Ajouter_Reali_Form extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textPrenom;
	private JTextField textNom;
	private JTextField txtDateNaissance;
	protected JRadioButton rdbtnExistant;
	protected JPanel panel_Ajouter;
	protected JPanel panel_Creer;
	protected JLabel lblErreur;

	private final DefaultListModel<Realisateur> modelReali = new DefaultListModel<>();
	private JList<Realisateur> listReali;

	protected String err = "";

	private Realisateur r;

	public Realisateur getR() {
		return r;
	}

	public void setR(Realisateur r) {
		this.r = r;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Ajouter_Reali_Form frame = new Ajouter_Reali_Form();
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
	public Ajouter_Reali_Form() {
		setTitle("Ajout realisateur");
		initialize();
		try {
			updateListRealisateur(VideoDAO.TousLesRealisateurs());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initialize() {
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 839, 514);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblAjouter = new JLabel("Ajouter réalisateur");
		lblAjouter.setBounds(10, 10, 134, 13);
		panel.add(lblAjouter);

		rdbtnExistant = new JRadioButton("Existant");
		rdbtnExistant.addActionListener(new RdbtnNewRadioButtonActionListener());
		rdbtnExistant.setSelected(true);
		buttonGroup.add(rdbtnExistant);
		rdbtnExistant.setBounds(10, 39, 103, 21);
		panel.add(rdbtnExistant);

		JRadioButton rdbtnCreer = new JRadioButton("Creer réalisateur");
		rdbtnCreer.addActionListener(new RdbtnCreerActionListener());
		buttonGroup.add(rdbtnCreer);
		rdbtnCreer.setBounds(10, 71, 164, 21);
		panel.add(rdbtnCreer);

		lblErreur = new JLabel("");
		lblErreur.setForeground(Color.RED);
		lblErreur.setBounds(161, 57, 393, 13);
		panel.add(lblErreur);

		panel_Ajouter = new JPanel();
		contentPane.add(panel_Ajouter);
		panel_Ajouter.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 532, 135);
		panel_Ajouter.add(scrollPane);

		listReali = new JList<Realisateur>();
		listReali.addMouseListener(new ListActeurMouseListener());
		scrollPane.setViewportView(listReali);

		JButton btnNewButton = new JButton("Ajouter");
		btnNewButton.addActionListener(new BtnNewButtonActionListener());
		btnNewButton.setBounds(636, 68, 85, 21);
		panel_Ajouter.add(btnNewButton);

		panel_Creer = new JPanel();
		panel_Creer.setVisible(false);
		contentPane.add(panel_Creer);
		panel_Creer.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Nom");
		lblNewLabel_1.setBounds(146, 22, 111, 13);
		panel_Creer.add(lblNewLabel_1);

		textPrenom = new JTextField();
		textPrenom.setBounds(10, 45, 96, 19);
		panel_Creer.add(textPrenom);
		textPrenom.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Prenom");
		lblNewLabel_2.setBounds(10, 22, 85, 13);
		panel_Creer.add(lblNewLabel_2);

		textNom = new JTextField();
		textNom.setColumns(10);
		textNom.setBounds(146, 45, 96, 19);
		panel_Creer.add(textNom);

		JLabel lblNewLabel = new JLabel("Date de naissance (yyyy-mm-jj)");
		lblNewLabel.setBounds(299, 22, 225, 13);
		panel_Creer.add(lblNewLabel);

		txtDateNaissance = new JTextField();
		txtDateNaissance.setColumns(10);
		txtDateNaissance.setBounds(299, 45, 96, 19);
		panel_Creer.add(txtDateNaissance);

		JButton btnNewButton_1 = new JButton("Creer");
		btnNewButton_1.addActionListener(new BtnNewButton_1ActionListener());
		btnNewButton_1.setBounds(635, 44, 85, 21);
		panel_Creer.add(btnNewButton_1);
	}

	private class RdbtnNewRadioButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (rdbtnExistant.isSelected()) {
				panel_Ajouter.setVisible(true);
				panel_Creer.setVisible(false);
				System.out.println("test");
			} else {
				panel_Ajouter.setVisible(false);
				panel_Creer.setVisible(true);
			}
		}
	}

	private class RdbtnCreerActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (rdbtnExistant.isSelected()) {
				panel_Ajouter.setVisible(true);
				panel_Creer.setVisible(false);
				System.out.println("test");
			} else {
				panel_Ajouter.setVisible(false);
				panel_Creer.setVisible(true);
			}
		}
	}

	private class BtnNewButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			r = listReali.getSelectedValue();
			dispose();
		}
	}

	private class ListActeurMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			r = listReali.getSelectedValue();
		}
	}

	private class BtnNewButton_1ActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String prenom;
			String nom;
			Date dateNaissance;
			try {
				prenom = textPrenom.getText().replace(" ", "");
				nom = textNom.getText().replace(" ", "");
				dateNaissance = Utile.parseDate(txtDateNaissance.getText());
				r = new Realisateur(prenom, nom, dateNaissance);
				VideoDAO.CreateRea(r);
				dispose();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				err += "Erreur dans vos donners, veiller bien remplir tous les champs au format indiquer";
				lblErreur.setText(err);
			}

		}
	}

	private void updateListRealisateur(ArrayList<Realisateur> realisateur) {
		modelReali.removeAllElements();

		for (Realisateur r : realisateur) {

			modelReali.addElement(r);
		}

		listReali.setModel(modelReali);
		listReali.setSelectedIndex(0);
	}
}
