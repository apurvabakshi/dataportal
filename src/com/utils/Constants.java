package com.utils;


/**
 * All constants are declared in this class
 * @author user
 *
 */
public class Constants {
	public static final String CASSANDRA_DRIVER 	= "org.apache.cassandra.cql.jdbc.CassandraDriver";
	public static final String CASSANDRA_LOCATION 	= "jdbc:cassandra://localhost:9160/gslabportal";
	public static final String CASSANDRA_USERNAME 	= "admin"; 
	public static final String CASSANDRA_PASSWORD 	= "password"; 
	public static final String LDAP_BASE 			= "ou=gslab,dc=maxcrc,dc=com";
	public static final String LDAP_FACTORY 		= "com.sun.jndi.ldap.LdapCtxFactory";
	public static final String LDAP_URL				= "ldap://172.18.18.237";
	public static final String SERVER_IP			= "192.168.0.4";

}
