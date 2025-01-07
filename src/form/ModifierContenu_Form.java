package form;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ModifierContenu_Form extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_Titre;
	private JTextField textField_Prix;
	private JTextField textField_Duree;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField txtSujet;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ModifierContenu_Form frame = new ModifierContenu_Form();
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
	public ModifierContenu_Form() {
		initialize();
	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 625);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitre = new JLabel("Titre");
		lblTitre.setBounds(52, 27, 61, 16);
		contentPane.add(lblTitre);

		textField_Titre = new JTextField();
		textField_Titre.setBounds(52, 45, 188, 16);
		contentPane.add(textField_Titre);
		textField_Titre.setColumns(10);

		JLabel lblPrix = new JLabel("Prix");
		lblPrix.setBounds(52, 71, 61, 16);
		contentPane.add(lblPrix);

		textField_Prix = new JTextField();
		textField_Prix.setBounds(52, 89, 188, 16);
		contentPane.add(textField_Prix);
		textField_Prix.setColumns(10);

		JLabel lblTypeVideo = new JLabel("Type vidéo");
		lblTypeVideo.setBounds(52, 115, 89, 16);
		contentPane.add(lblTypeVideo);

		JComboBox comboBox_TypeVideo = new JComboBox();
		comboBox_TypeVideo.setBounds(52, 134, 188, 16);
		contentPane.add(comboBox_TypeVideo);

		JLabel lblDescriptionVideo = new JLabel("Description vidéo");
		lblDescriptionVideo.setBounds(52, 160, 130, 16);
		contentPane.add(lblDescriptionVideo);

		JLabel lblDuree = new JLabel("Durée");
		lblDuree.setBounds(52, 268, 61, 16);
		contentPane.add(lblDuree);

		textField_Duree = new JTextField();
		textField_Duree.setBounds(52, 288, 188, 16);
		contentPane.add(textField_Duree);
		textField_Duree.setColumns(10);

		JLabel lblDateDeSortie = new JLabel("Date de sortie");
		lblDateDeSortie.setBounds(273, 344, 72, 16);
		contentPane.add(lblDateDeSortie);

		JLabel lblDateDajout = new JLabel("Date d'ajout");
		lblDateDajout.setBounds(406, 344, 72, 16);
		contentPane.add(lblDateDajout);

		JLabel lbletat = new JLabel("État");
		lbletat.setBounds(52, 315, 61, 16);
		contentPane.add(lbletat);

		JComboBox comboBox_Etat = new JComboBox();
		comboBox_Etat.setBounds(52, 344, 130, 16);
		contentPane.add(comboBox_Etat);

		JLabel lblGratuitAbonne = new JLabel("Gratuit abonné");
		lblGratuitAbonne.setBounds(52, 374, 109, 16);
		contentPane.add(lblGratuitAbonne);

		JRadioButton rdbtnOui = new JRadioButton("Oui");
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

		JComboBox comboBox_Genre = new JComboBox();
		comboBox_Genre.setBounds(317, 27, 96, 16);
		contentPane.add(comboBox_Genre);

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
		btnCrer.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		btnCrer.setBounds(232, 551, 117, 29);
		contentPane.add(btnCrer);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(273, 59, 242, 46);
		contentPane.add(scrollPane_1);

		JList listGenre = new JList();
		scrollPane_1.setViewportView(listGenre);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(273, 212, 242, 46);
		contentPane.add(scrollPane_2);

		JList listActeur = new JList();
		scrollPane_2.setViewportView(listActeur);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(52, 186, 188, 72);
		contentPane.add(scrollPane);

		JTextArea txtDescription = new JTextArea();
		scrollPane.setViewportView(txtDescription);

		JButton btnNewButton = new JButton("Ajouter");
		btnNewButton.setBounds(525, 58, 101, 21);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Supprimer");
		btnNewButton_1.setBounds(525, 84, 101, 21);
		contentPane.add(btnNewButton_1);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(273, 134, 242, 46);
		contentPane.add(scrollPane_3);

		JList listSujet = new JList();
		scrollPane_3.setViewportView(listSujet);

		JButton btnNewButton_2 = new JButton("Ajouter");
		btnNewButton_2.setBounds(525, 129, 101, 21);
		contentPane.add(btnNewButton_2);

		JButton btnNewButton_1_1 = new JButton("Supprimer");
		btnNewButton_1_1.setBounds(525, 155, 101, 21);
		contentPane.add(btnNewButton_1_1);

		JButton btnNewButton_3 = new JButton("Ajouter");
		btnNewButton_3.setBounds(525, 212, 101, 21);
		contentPane.add(btnNewButton_3);

		JButton btnNewButton_1_2 = new JButton("Supprimer");
		btnNewButton_1_2.setBounds(525, 238, 101, 21);
		contentPane.add(btnNewButton_1_2);

		JButton btnNewButton_3_1 = new JButton("Ajouter");
		btnNewButton_3_1.setBounds(525, 288, 101, 21);
		contentPane.add(btnNewButton_3_1);

		JButton btnNewButton_1_2_1 = new JButton("Supprimer");
		btnNewButton_1_2_1.setBounds(525, 314, 101, 21);
		contentPane.add(btnNewButton_1_2_1);

		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(273, 288, 242, 45);
		contentPane.add(scrollPane_4);

		JList listRea = new JList();
		scrollPane_4.setViewportView(listRea);

		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setBounds(52, 492, 574, 29);
		contentPane.add(scrollPane_5);

		JTextArea textArea_1 = new JTextArea();
		scrollPane_5.setViewportView(textArea_1);

		textField = new JTextField();
		textField.setBounds(273, 370, 96, 19);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(406, 370, 96, 19);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		JLabel lblforma = new JLabel("Format : yyyy-mm-dd");
		lblforma.setBounds(323, 402, 140, 13);
		contentPane.add(lblforma);
	}
}
