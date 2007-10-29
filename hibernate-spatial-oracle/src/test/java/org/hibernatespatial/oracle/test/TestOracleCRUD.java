/**
 * $Id$
 *
 * This file is part of Spatial Hibernate, an extension to the 
 * hibernate ORM solution for geographic data. 
 *  
 * Copyright © 2007 K.U. Leuven LRD, Spatial Applications Division, Belgium
 *
 * This work was partially supported by the European Commission, 
 * under the 6th Framework Programme, contract IST-2-004688-STP.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * For more information, visit: http://www.cadrie.com/
 */
 
package org.hibernatespatial.oracle.test;

import junit.framework.JUnit4TestAdapter;

import org.hibernatespatial.test.TestCRUD;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class TestOracleCRUD {

	private final static TestCRUD delegate;

	static {
		delegate = new TestCRUD();
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		delegate.setUpBeforeClass();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		delegate.tearDownAfterClass();
	}

	@Test
	public void testSaveLineStringEntity() throws Exception {
		delegate.testSaveLineStringEntity();
	}

	@Test
	public void testSaveNullLineStringEntity() throws Exception {
		delegate.testSaveNullLineStringEntity();
	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestOracleCRUD.class);
	}
}