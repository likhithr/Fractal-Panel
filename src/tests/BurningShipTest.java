package tests;

import static org.junit.Assert.*;

import javax.swing.SwingWorker;

import org.junit.Test;

import code.BurningShip;
import code.FractalThread;
import edu.buffalo.fractal.WorkerResult;

public class BurningShipTest {

	/**
	 * Burning Ship Set Translate a pixel's row to the associated x-coordinate
	 * in the fractal test for the first, second and last x coordinate
	 */
	@Test
	public void BurningShipRowToXCoordinate() {
		BurningShip ship = new BurningShip();
		ship.setCoordinateX(-1.8, -1.7);
		double[][] x = ship.getCoordinateX();
		assertEquals(-1.8, x[0][0], 0.0001);
		assertEquals(-1.7, x[2047][0], 0.0001);
	}

	/**
	 * Burning Ship Set Translate a pixel's column to the associated
	 * y-coordinate in the fractal test for the first, second and last y
	 * coordinate
	 */
	@Test
	public void BurningShipColToYCoordinate() {
		BurningShip ship = new BurningShip();
		ship.setCoordinateY(-0.08, 0.025);
		double[][] y = ship.getCoordinateY();
		assertEquals(-0.08, y[0][0], 0.0001);
		assertEquals(0.025, y[0][2047], 0.0001);
	}

	/**
	 * Burning Ship Set test for escape time for the given coordinate
	 * (-1.7443359374999874, -0.017451171875000338) never exceed escape distance
	 * 2, so escape time == 255
	 */
	@Test
	public void BurningShipNeverExceedEscDis() {
		BurningShip ship = new BurningShip();
		assertEquals(255, ship.escapeTime(255, 2, -1.7443359374999874, -0.017451171875000338));
	}

	/**
	 * Burning Ship Set test for escape time for the given coordinate
	 * (-1.7443359374999874, -0.017451171875000338) never exceed escape distance
	 * 2, so escape time == 135
	 */
	@Test
	public void BurningShipNeverExceedEscapeDistance() {
		BurningShip ship = new BurningShip();
		assertEquals(135, ship.escapeTime(135, 2, -1.7443359374999874, -0.017451171875000338));
	}

	/**
	 * Burning Ship Set test none of the pixels in the Burning Ship set have an
	 * escape time of 0 or 1
	 */
	@Test
	public void BurningShipEscapeTime() {
		BurningShip ship = new BurningShip();
		ship.setEscapeDis(2);
		ship.setMaxEscapeTime(255);
		FractalThread f = new FractalThread();
		f.setWorkers(4);
		SwingWorker<WorkerResult, Void>[] sw = f.getWorkers(ship);
		for(SwingWorker<WorkerResult, Void> w: sw) {
			w.execute();
		}
		for(SwingWorker<WorkerResult, Void> w: sw) {
			while(!w.isDone()) {}
		}
		int[][] result = ship.getEscapeTime();
		for (int row = 0; row < result.length; row = row + 1) {
			for (int col = 0; col < result[row].length; col = col + 1) {
				assertFalse(1 == result[row][col]);
				assertFalse(0 == result[row][col]);
			}
		}
	}

	/**
	 * Burning Ship Set test for escape time for the given coordinate
	 * (-1.6999999999999802, 0.0030136986301371603) exceeds the escape distance
	 * 3 after a ten loop pass, so escape time == 10
	 */
	@Test
	public void BurningShipTenLoop() {
		BurningShip ship = new BurningShip();
		assertEquals(10, ship.escapeTime(255, 3, -1.6999999999999802, 0.0030136986301371603));
	}

	/**
	 * Burning Ship Set The method called to calculate the fractal returns a 2-d
	 * array with 512 rows and 512 columns
	 */
	@Test
	public void BurningShipFractalSize() {
		BurningShip ship = new BurningShip();
		ship.setEscapeDis(2);
		ship.setMaxEscapeTime(255);
		FractalThread f = new FractalThread();
		f.setWorkers(4);
		SwingWorker<WorkerResult, Void>[] sw = f.getWorkers(ship);
		for(SwingWorker<WorkerResult, Void> w: sw) {
			w.execute();
		}
		for(SwingWorker<WorkerResult, Void> w: sw) {
			while(!w.isDone()) {}
		}
		int[][] result = ship.getEscapeTime();
		assertEquals(2048, result.length);
		assertEquals(2048, result[0].length);

	}

}
