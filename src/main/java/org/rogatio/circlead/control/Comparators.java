/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.control;

import java.util.Comparator;

import org.rogatio.circlead.model.work.DefaultWorkitem;
import org.rogatio.circlead.model.work.Role;

/**
 * Comparators for workitems.
 *
 * @author Matthias Wegner
 */
public class Comparators {

	/**  Sorts workitems by title. */
	public final static Comparator<DefaultWorkitem> TITLE = new Comparator<DefaultWorkitem>() {
		@Override
		public int compare(DefaultWorkitem o1, DefaultWorkitem o2) {
			return o1.getTitle().compareTo(o2.getTitle());
		}
	};

	/**  Sorts workitems by status. */
	public final static Comparator<DefaultWorkitem> STATUS = new Comparator<DefaultWorkitem>() {
		@Override
		public int compare(DefaultWorkitem o1, DefaultWorkitem o2) {
			return o1.getStatus().compareTo(o2.getStatus());
		}
	};
	
	/**  Sorts roles by redundance. */
	public final static Comparator<Role> REDUNDANCE = new Comparator<Role>() {
		@Override
		public int compare(Role o1, Role o2) {
			return ((Double)o2.getRedundance()).compareTo(((Double)o1.getRedundance()));
		}
	};

}
