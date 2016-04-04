package pk.com.habsoft.robosim.utils;

import java.util.Observable;

public class CObservable extends Observable {

	public CObservable() {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Observable#clearChanged()
	 */
	@Override
	public synchronized void clearChanged() {
		super.clearChanged();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Observable#setChanged()
	 */
	@Override
	public synchronized void setChanged() {
		super.setChanged();
	}
}
