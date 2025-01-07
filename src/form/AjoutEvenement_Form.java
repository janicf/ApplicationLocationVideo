package form;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import _class.Evenement;
import _enum.TypeEvent;
import dao.EvenementDAO;

public class AjoutEvenement_Form extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_Titre;
	private JTextField textField_Prix;
	private JTextField textField_Lieu;
	private JTextField textField_DateDebut;
	private JTextField textField_DateFin;
	private JComboBox<TypeEvent> comboBox_Type;
	private JTextArea txtDescription;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					AjoutEvenement_Form frame = new AjoutEvenement_Form();
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
	public AjoutEvenement_Form() {
		initialize();
		createCombo();
	}

	private void initialize() {
		setTitle("Ajout evenement");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		setBounds(100, 100, 339, 545);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitre = new JLabel("Titre");
		lblTitre.setBounds(28, 24, 61, 16);
		contentPane.add(lblTitre);

		textField_Titre = new JTextField();
		textField_Titre.setBounds(28, 43, 199, 26);
		contentPane.add(textField_Titre);
		textField_Titre.setColumns(10);

		JLabel lblPrix = new JLabel("Prix");
		lblPrix.setBounds(28, 81, 61, 16);
		contentPane.add(lblPrix);

		textField_Prix = new JTextField();
		textField_Prix.setBounds(28, 100, 130, 26);
		contentPane.add(textField_Prix);
		textField_Prix.setColumns(10);

		JLabel lblType = new JLabel("Type");
		lblType.setBounds(28, 138, 61, 16);
		contentPane.add(lblType);

		comboBox_Type = new JComboBox();
		comboBox_Type.setBounds(28, 159, 130, 27);
		contentPane.add(comboBox_Type);

		JLabel lblDescription = new JLabel("Description");
		lblDescription.setBounds(28, 198, 99, 16);
		contentPane.add(lblDescription);

		JLabel lblLieu = new JLabel("Lieu");
		lblLieu.setBounds(28, 290, 61, 16);
		contentPane.add(lblLieu);

		textField_Lieu = new JTextField();
		textField_Lieu.setBounds(28, 309, 199, 26);
		contentPane.add(textField_Lieu);
		textField_Lieu.setColumns(10);

		JLabel lblDateDebut = new JLabel("Date de début");
		lblDateDebut.setBounds(28, 347, 99, 16);
		contentPane.add(lblDateDebut);

		JLabel lblDateFin = new JLabel("Date de fin");
		lblDateFin.setBounds(28, 400, 99, 16);
		contentPane.add(lblDateFin);

		JButton btnCreer = new JButton("Créer");
		btnCreer.addActionListener(new BtnCreerActionListener());
		btnCreer.setBounds(87, 462, 117, 29);
		contentPane.add(btnCreer);

		textField_DateDebut = new JTextField();
		textField_DateDebut.setBounds(28, 362, 130, 26);
		contentPane.add(textField_DateDebut);
		textField_DateDebut.setColumns(10);

		textField_DateFin = new JTextField();
		textField_DateFin.setBounds(28, 416, 130, 26);
		contentPane.add(textField_DateFin);
		textField_DateFin.setColumns(10);

		JLabel lblDateDebut_1 = new JLabel("yyyy-mm-dd hh:mm");
		lblDateDebut_1.setBounds(168, 366, 150, 16);
		contentPane.add(lblDateDebut_1);

		JLabel lblDateDebut_1_1 = new JLabel("yyyy-mm-dd hh:mm");
		lblDateDebut_1_1.setBounds(168, 420, 150, 16);
		contentPane.add(lblDateDebut_1_1);

		JLabel ChiffreSeulement = new JLabel("Chiffre seulement");
		ChiffreSeulement.setBounds(77, 83, 150, 13);
		contentPane.add(ChiffreSeulement);

		txtDescription = new JTextArea();
		txtDescription.setBounds(28, 224, 199, 54);
		contentPane.add(txtDescription);
	}

	private void createCombo() {
		for (TypeEvent type : TypeEvent.values()) {

			comboBox_Type.addItem(type);
		}

	}

	private class BtnCreerActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String titre = textField_Titre.getText();
				Double prix = Double.parseDouble(textField_Prix.getText());
				TypeEvent type_event = (TypeEvent) comboBox_Type.getSelectedItem();
				String descri = txtDescription.getText();
				String lieu = textField_Lieu.getText();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
				LocalDateTime dateDebut = LocalDateTime.parse(textField_DateDebut.getText(), formatter);
				LocalDateTime dateFim = LocalDateTime.parse(textField_DateFin.getText(), formatter);

				Evenement event = new Evenement(prix, titre, type_event, descri, lieu, dateDebut, dateFim);
				EvenementDAO.AjouterEvenement(event);
				dispose();
			} catch (NumberFormatException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
