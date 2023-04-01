package es.deusto.spq.server.database;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public class PMF {
private static PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");

	public static PersistenceManagerFactory get() {
		return pmf;
	}
}
