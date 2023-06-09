package org.briarproject.android.dontkillmelib.wakelock;

import org.briarproject.nullsafety.NotNullByDefault;

@NotNullByDefault
interface SharedWakeLock {

	/**
	 * Acquires the wake lock. This increments the wake lock's reference count,
	 * so unlike {@link AndroidWakeLock#acquire()} every call to this method
	 * must be followed by a balancing call to {@link #release()}.
	 */
	void acquire();

	/**
	 * Releases the wake lock. This decrements the wake lock's reference count,
	 * so unlike {@link AndroidWakeLock#release()} every call to this method
	 * must follow a balancing call to {@link #acquire()}.
	 */
	void release();
}
