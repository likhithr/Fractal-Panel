package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

import code.BurningShip;
import code.ColorModelFactory;
import code.JuliaSet;
import code.MandelbrotSet;
import code.Model;
import code.MultibrotSet;
import edu.buffalo.fractal.FractalPanel;

public class UI implements Runnable {

	private Model _model;
	private JFrame _frame;
	private JMenuBar _menub;
	private FractalPanel _fractalPanel;
	private ArrayList<JLabel> _hint;
	private JPanel _hints;

	public UI() {
		_model = new Model();
	}

	@Override
	public void run() {

		_frame = new JFrame();
		_menub = new JMenuBar();
		/**
		 * Creates a JMenu named "File" Creates 1 JMenu Items with names "Quit".
		 * Use it to exit
		 **/
		JMenu file = new JMenu("File");
		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		file.add(quit);

		/**
		 * Creates a JMenu on the Interface named "Fractal" Creates 4 JMenu
		 * Items with names "Mandelbrot Set", "Julia Set", "Burning Ship Set",
		 * and "Multibrot Set". It then gives all 4 JMenu Items their own
		 * actionListener. It then adds the each JMenu Item to the JMenu
		 * {@code fractal}
		 **/

		JMenu fractal = new JMenu("Fractal");
		JMenuItem mandelbrot = new JMenuItem("Mandelbrot Set");
		mandelbrot.addActionListener(new EventHandler(_model, new MandelbrotSet()));
		JMenuItem julia = new JMenuItem("Julia Set");
		julia.addActionListener(new EventHandler(_model, new JuliaSet(-1.7,1.7,-1.0,1.0)));
		JMenuItem burningShip = new JMenuItem("Burning Ship Set");
		burningShip.addActionListener(new EventHandler(_model, new BurningShip()));
		JMenuItem multibrot = new JMenuItem("Multibrot Set");
		multibrot.addActionListener(new EventHandler(_model, new MultibrotSet()));

		fractal.add(mandelbrot);
		fractal.add(julia);
		fractal.add(burningShip);
		fractal.add(multibrot);

		/**
		 * Creates a JMenu on the Interface named "Color" Creates 8 JMenu Items
		 * with names "Rainbow", "Blue", "Gray", "Red", "Green", "White",
		 * "Black", and "White Blue". It then gives all 8 JMenu Items their own
		 * actionListener. It then adds the each JMenu Item to the JMenu
		 * {@code color}
		 **/

		JMenu color = new JMenu("Color");
		JMenuItem rainbow = new JMenuItem("Rainbow");
		rainbow.addActionListener(new ColorHandler(_model, ColorModelFactory.createRainbowColorModel(255)));
		JMenuItem blue = new JMenuItem("Blue");
		blue.addActionListener(new ColorHandler(_model, ColorModelFactory.createBluesColorModel(255)));
		JMenuItem gray = new JMenuItem("Gray");
		gray.addActionListener(new ColorHandler(_model, ColorModelFactory.createGrayColorModel(255)));
		JMenuItem red = new JMenuItem("Red");
		red.addActionListener(new ColorHandler(_model, ColorModelFactory.createRedColorModel(255)));
		JMenuItem green = new JMenuItem("Green");
		green.addActionListener(new ColorHandler(_model, ColorModelFactory.createGreenColorModel(255)));
		JMenuItem white = new JMenuItem("White");
		white.addActionListener(new ColorHandler(_model, ColorModelFactory.createWhiteColorModel(255)));
		JMenuItem black = new JMenuItem("Black");
		black.addActionListener(new ColorHandler(_model, ColorModelFactory.createBlackColorModel(255)));
		JMenuItem wblue = new JMenuItem("White Blue");
		wblue.addActionListener(new ColorHandler(_model, ColorModelFactory.createWhiteBlueColorModel(255)));
		color.add(rainbow);
		color.add(blue);
		color.add(gray);
		color.add(red);
		color.add(green);
		color.add(white);
		color.add(black);
		color.add(wblue);

		/**
		 * Creates a JMenu on the Interface named "Other" Creates 1 JMenu Item
		 * with the name of "Escape Distance" Add an Action Listener to the
		 * JMenuItem {@code escapeDis} Then it adds JMenuItem {@code escapeDis}
		 * to JMenu {@code other}
		 **/

		JMenu other = new JMenu("Other");
		JMenuItem escapeDis = new JMenuItem("Escape Distance");
		escapeDis.addActionListener(new ActionListener() {
			/**
			 * Creates an Option to Enter a Distance that is desired by the user
			 * Checks to see if its null, and if it is not, call
			 * {@code checkInput(inputDistance)} to check what the user has
			 * input
			 **/
			@Override
			public void actionPerformed(ActionEvent e) {
				String inputDistance = JOptionPane.showInputDialog("Enter an escape distance:");
				if (inputDistance == null) {
				} else {
					checkInputDistance(inputDistance);
				}
			}
		});
		other.add(escapeDis);

		JMenuItem escapeTime = new JMenuItem("Escape Time");
		escapeTime.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String inputDistance = JOptionPane.showInputDialog("Enter an escape time:");
				if (inputDistance == null) {
				} else {
					checkInputTime(inputDistance);
				}
			}

		});
		other.add(escapeTime);

		JMenuItem reset = new JMenuItem("Reset Fractals");
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					String timeInput = Integer.toString(255);
					String disInput = Integer.toString(2);
					checkInputTime(timeInput);
					checkInputDistance(disInput);
				} catch (NullPointerException npe) {
					JOptionPane.showMessageDialog(null, "Can't reset because no fractal was selected.", "Reset Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		other.add(reset);
		/**
		 * Adds all 4 JMenus that were created to the JMenu Bar{@code _menub}
		 * Set JMenu layout to a Grid Layout with dimensions of 4, 1. Then add
		 * JMenuBar {@code _menub} to the JFrame {@code _frame}
		 */
		_menub.add(file);
		_menub.add(fractal);
		_menub.add(color);
		_menub.add(other);
		_menub.setLayout(new GridLayout(1, 4));
		_frame.add(_menub);

		_fractalPanel = new FractalPanel();
		_frame.add(_fractalPanel);

		/**
		 * Create 16 JLbale store into ArrayList<JLabel> {@code _hint} Add all
		 * the 16 JLabel to JPanel {@code _hints} Add JLabel {@code _hints} to
		 * JFrame {@code _frame}
		 */

		_hints = new JPanel();
		_hints.setLayout(new GridLayout(16, 1));
		_hint = new ArrayList<JLabel>();
		for (int x = 0; x < 16; x++) {
			JLabel hint = new JLabel();
			hint.setBackground(new Color(50, 50, 50));
			hint.setForeground(Color.WHITE);
			hint.setOpaque(true);
			hint.setFont(new Font("Consolas", Font.PLAIN, 18));
			hint.setHorizontalAlignment(JLabel.CENTER);
			_hints.add(hint);
			_hint.add(hint);
		}
		_frame.add(_hints);

		_frame.getContentPane().setLayout(new BoxLayout(_frame.getContentPane(), BoxLayout.Y_AXIS));
		_model.addObserver(this);
		update();
		
		_frame.addMouseListener(new MouseHandler(_model));
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.pack();
		_frame.setVisible(true);

	}

	/**
	 * Convert String (@code input) to Integer When it throw
	 * ({@code NumberFormatException}), Catch this exception and create a
	 * MessageDialog to tell user it's not a valid input When it throw
	 * ({@code NullPointerException}), Catch this exception and ignore it
	 * because It still pass the new escape distance to the model
	 * 
	 * @param input
	 */
	public void checkInputDistance(String input) {
		int distance;
		try {
			distance = Integer.parseInt(input);
			if (distance > 0) {
				_model.setEscapeDis(distance);
			} else {
				JOptionPane.showMessageDialog(null, "The integer has to be greater than 0.", "Distance Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null, "Invalid input, please enter an integer greater than 0.",
					"Distance Error", JOptionPane.ERROR_MESSAGE);
		} catch (NullPointerException npe) {
		}
	}

	public void checkInputTime(String input) {
		int time;
		try {
			time = Integer.parseInt(input);
			if (time > 0 && time < 256) {
				_model.setMaxEscTime(time);
			} else {
				JOptionPane.showMessageDialog(null, "The integer has to be greater than 0 and less than 256.",
						"Time Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null,
					"Invalid input, please enter an integer greater than 0 and less than 256.", "Time Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (NullPointerException npe) {
		}
	}

	/**
	 * Remove the FractalPanel{@code _fractalPanel} from frame {@code _frame}
	 * Make a new FractalPanel Call method
	 * {@code setIndexColorModel(IndexColorModel)} to set new IndexColorModel
	 * for {@code _fractalPanel} Call method {@code updateImage(EscapeTime)} to
	 * set new escape time for {@code _fractalPanel} Then add
	 * FractalPanel{@code _fractalPanel} to frame {@code _frame}
	 */
	public void newFractal() {
		_frame.remove(_fractalPanel);
		_fractalPanel = new FractalPanel();
		_fractalPanel.setIndexColorModel(_model.getSelectColor());
		_fractalPanel.updateImage(_model.getEscapeTime());
		_frame.add(_fractalPanel);
	}

	/**
	 * if newFractal() is false - when neither IndexColorModel nor type of
	 * fractal set are select by the user Remove the
	 * FractalPanel{@code _fractalPanel} from frame {@code _frame} Then set text
	 * of JLabel in ArrayList<JLabel> {@code _hint} to show the steps need to do
	 * for the user else remove the JPanel{@code _hint} from frame
	 * {@code _frame} and call {@code newFractal()} to make a new FractalPanel
	 */
	public void update() {
		if (!_model.newFractal()) {
			_frame.remove(_fractalPanel);
			_hint.get(1).setText("To set up your fractal:             ");
			_hint.get(4).setText("    Select a color from the Color menu    ");
			_hint.get(6).setText("  and  ");
			_hint.get(8).setText("  Select a fractal from the Factal menu   ");
			_hint.get(10).setText(" or ");
			_hint.get(12).setText("Select quit from File menu");
		} else {
			_frame.remove(_hints);
			newFractal();
		}
		_frame.pack();
	}
}
