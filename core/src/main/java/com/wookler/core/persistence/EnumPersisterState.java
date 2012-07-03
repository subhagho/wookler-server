/**
 * 
 */
package com.wookler.core.persistence;

/**
 * Enum defines the states of a Persistance handler.
 * 
 * @author subhagho
 * 
 */
public enum EnumPersisterState {
	/**
	 * Persister is open and functioning normally.
	 */
	Open,
	/**
	 * Persister has been closed and disposed.
	 */
	Closed,
	/**
	 * Persister threw an unrecoverable exception.
	 */
	Error,
	/**
	 * Persister state is unknown, not initialized and opened yet.
	 */
	Unknown
}
