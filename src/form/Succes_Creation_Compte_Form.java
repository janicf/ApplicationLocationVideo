package form;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Succes_Creation_Compte_Form extends JFrame {

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
					Succes_Creation_Compte_Form frame = new Succes_Creation_Compte_Form();
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
	public Succes_Creation_Compte_Form() {
		initialize();
	}

	private void initialize() {
		setTitle("Création terminé avec succès");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 333, 110);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSucces = new JLabel("Creation confirmée");
		lblSucces.setHorizontalAlignment(SwingConstants.CENTER);
		lblSucces.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblSucces.setBounds(83, 10, 166, 16);
		contentPane.add(lblSucces);

		JButton btnOK = new JButton("OK");
		btnOK.addMouseListener(new BtnOKMouseListener());
		btnOK.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		btnOK.setBounds(83, 34, 166, 29);
		contentPane.add(btnOK);
	}

	private class BtnOKMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			dispose();
		}
	}
}
