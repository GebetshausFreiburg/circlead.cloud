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

/**
 * The Class Comparators.
 */
public class Comparators {

	 /** The title. */
 	public static Comparator<DefaultWorkitem> TITLE = new Comparator<DefaultWorkitem>() {
         @Override
         public int compare(DefaultWorkitem o1, DefaultWorkitem o2) {
             return o1.getTitle().compareTo(o2.getTitle());
         }
     };
	
}
