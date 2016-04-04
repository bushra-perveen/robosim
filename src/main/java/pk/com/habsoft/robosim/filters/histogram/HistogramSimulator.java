package pk.com.habsoft.robosim.filters.histogram;

public class HistogramSimulator implements Runnable {

	public final static int SENSE = 0, MOVE = 1;
	int timeDelay = 1000;
	boolean running = false;
	Thread currentThread = null;
	int[][] commands = new int[0][0];
	int currentMove = 0;
	int count = 0;// How many moves you want to run

	HistogramFilter filter;
	HistogramFilterView output;

	public HistogramSimulator(HistogramFilterView output, HistogramFilter filter) {
		this.output = output;
		this.filter = filter;
	}

	public synchronized boolean isRunning() {
		return running;
	}

	public void nextStep() {
		count = 1;
		start();
	}

	public void reset() {
		filter.reset();
		count = 0;
		currentMove = 0;
	}

	@Override
	public void run() {
		setRunning(true);
		int i = 0;
		while (isRunning() && currentMove < commands.length && i < count) {
			i++;
			// if command is SENSE then perform sense
			if (commands[currentMove][1] == SENSE) {
				output.showOutPut(currentMove + "  Sense >>  " + HistogramFilterView.sensorNames[commands[currentMove][0]]);
				filter.sense(commands[currentMove][0]);
			} else if (commands[currentMove][1] == MOVE) {
				output.showOutPut(currentMove + "  Move   >>  " + HistogramFilterView.btnNames[commands[currentMove][0]]);
				filter.move(commands[currentMove][0]);
			}
			currentMove++;
			output.repaint();

			try {
				Thread.sleep(timeDelay);
			} catch (InterruptedException e) {
			}
		}
		if (currentMove == commands.length) {
			currentMove = 0;
			count = 0;
			output.showOutPut("----------------------------------------------");
			output.rbStart.setSelected(false);
			output.rbStop.setSelected(true);
			output.btnNext.setEnabled(true);
			output.btnBuildSimulation.setEnabled(true);
			output.btnResetSimulation.setEnabled(true);

		}

		currentThread = null;
	}

	public void setCommands(int[][] commands) {
		this.count = commands.length;
		this.commands = commands;
	}

	public synchronized void setRunning(boolean b) {
		running = b;
	}

	public void simulate() {
		count = commands.length - currentMove;
		start();
	}

	private boolean start() {
		if (currentThread == null) {
			currentThread = new Thread(this);
			currentThread.start();
			return true;
		}
		return false;
	}

}
