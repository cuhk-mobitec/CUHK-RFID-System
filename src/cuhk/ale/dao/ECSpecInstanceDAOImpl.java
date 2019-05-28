/** 
 * $Id: ECSpecInstanceDAOImpl.java,v 1.4 2006/09/18 14:31:13 kyyu Exp $
 */
package cuhk.ale.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import cuhk.ale.ejb.ECSpecInstanceBean;
import cuhk.ale.ejb.interfaces.ECSpecInstancePK;
import cuhk.ale.exceptions.ECSpecOptimisticLockException;
import epcglobal.ale.ECSpec;
import epcglobal.ale.ObjectFactory;

/**
 * @version $Id: ECSpecInstanceDAOImpl.java,v 1.4 2006/09/18 14:31:13 kyyu Exp $
 * @author kyyu
 */
public class ECSpecInstanceDAOImpl extends ReportGeneratorDAOImpl implements ECSpecInstanceDAO {

	static final String MAIN_TABLE_NAME = "ecspecInstance";

	static final String URL_TABLE_NAME = "specUrls";

	static final Logger logger = Logger.getLogger(ECSpecInstanceDAOImpl.class.getName());

	private DataSource jdbcFactory;

	/**
	 * 
	 */
	public ECSpecInstanceDAOImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cuhk.ale.dao.ECSpecInstanceDAO#init()
	 */
	public void init() {
		InitialContext c = null;
		if (this.jdbcFactory == null) {
			try {
				c = new InitialContext();
				this.jdbcFactory = (DataSource) c.lookup("java:comp/env/mySQLDS");
			} catch (NamingException e) {
				ECSpecInstanceDAOImpl.logger.debug("in init, ", e);
				throw new EJBException(e);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cuhk.ale.dao.ECSpecInstanceDAO#load(cuhk.ale.ejb.interfaces.ECSpecInstancePK,
	 *      cuhk.ale.ejb.ECSpecInstanceBean)
	 */
	public void load(ECSpecInstancePK pk, ECSpecInstanceBean ejb) throws EJBException {
		ECSpecInstanceDAOImpl.logger.debug("load [" + pk.getSpecName() + "]");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = jdbcFactory.getConnection();
			String queryString = "select specName, specXML, state, stateVersion, startTime, previousStartTime, previousEndTime from "
					+ MAIN_TABLE_NAME + " where specName = ?";
			ps = conn.prepareStatement(queryString);
			ps.setString(1, pk.specName);
			rs = ps.executeQuery();
			if (rs.next()) {
				ejb.setSpecName(rs.getString("specName").trim());
				// FIXME: have an xml parser to do it.

				// Deserialize from a byte array

				JAXBContext jc = JAXBContext.newInstance("epcglobal.ale");
				Unmarshaller unmarshaller = jc.createUnmarshaller();
				JAXBElement element = (JAXBElement) unmarshaller.unmarshal( new ByteArrayInputStream(rs.getBytes("specXML")));
				ejb.setECSpec((ECSpec) element.getValue());
				
				// ejb.setECSpec((ECSpec) unmarshaller.unmarshal( new ByteArrayInputStream(rs.getBytes("specXML"))));


				ejb.setState(rs.getInt("state"));
				ejb.setStateVersion(rs.getInt("stateVersion"));
				ejb.setStartTime(rs.getLong("startTime"));
				ejb.setPreviousStartTime(rs.getLong("previousStartTime"));
				ejb.setPreviousEndTime(rs.getLong("previousEndTime"));
			}
		} catch (SQLException e) {
			throw new EJBException(e);
		} catch (JAXBException e) {
			throw new EJBException(e);
		} finally {
			try {
				rs.close();
				ps.close();
				conn.close();
			} catch (Exception e) {
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cuhk.ale.dao.ECSpecInstanceDAO#store(cuhk.ale.ejb.ECSpecInstanceBean)
	 */
	public void store(ECSpecInstanceBean ejb) {

		ECSpecInstanceDAOImpl.logger.debug("store [" + ejb.getSpecName() + "]");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = jdbcFactory.getConnection();

			try {
				// do the optimistic query :
				String queryString = "select stateVersion from " + MAIN_TABLE_NAME + " where specName = ? ";
				ps = conn.prepareStatement(queryString);
				ps.setString(1, ejb.getSpecName().trim());
				rs = ps.executeQuery();
				if ((!rs.next()) || (rs.getInt("stateVersion") != ejb.getStateVersion())) {
					ECSpecInstanceDAOImpl.logger.debug("OPTIMISTIC LOCK DETECTED!");
					throw new ECSpecOptimisticLockException("optimistic locking! " + ejb.getStateVersion());
				} else {
					// ECSpecInstanceDAOImpl.logger.debug("store passed
					// optimistic lock with StateVersion " +
					// ejb.getStateVersion());
					ejb.setStateVersion(ejb.getStateVersion() + 1);
				}
			} finally {
				try {
					rs.close();
					ps.close();
				} catch (Exception e) {
				}
			}

//			String updateString = "update "
//					+ MAIN_TABLE_NAME
//					+ " set specXML = ?, state = ?, stateVersion = ? , startTime = ? , previousStartTime = ? , previousEndTime = ? where specName = ?";
			String updateString = "update "
					+ MAIN_TABLE_NAME
					+ " set state = ?, stateVersion = ? , startTime = ? , previousStartTime = ? , previousEndTime = ? where specName = ?";
			ps = conn.prepareStatement(updateString);

			// TODO there is a way to make it more generic and efficient... do
			// it later.
			// FIXME use XML writer to do it.

			// Serialize to a byte array
//			JAXBContext jc = JAXBContext.newInstance("epcglobal.ale");
//			Marshaller marshaller = jc.createMarshaller();
//			ByteArrayOutputStream bos = new ByteArrayOutputStream();
//			marshaller.marshal(
//					new ObjectFactory().createECSpec(ejb.getECSpec()), bos);
//			byte[] buf = bos.toByteArray();
int i=1;
//  		ps.setBytes(i++, buf);
			ps.setInt(i++, ejb.getState());
			ps.setInt(i++, ejb.getStateVersion());
			ps.setLong(i++, ejb.getStartTime());
			ps.setLong(i++, ejb.getPreviousStartTime());
			ps.setLong(i++, ejb.getPreviousEndTime());

			ps.setString(i++, ejb.getSpecName().trim());

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new EJBException(e);
//		} catch (JAXBException e) {
//			throw new EJBException(e);
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (Exception e) {
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cuhk.ale.dao.ECSpecInstanceDAO#remove(cuhk.ale.ejb.interfaces.ECSpecInstancePK)
	 */
	public void remove(ECSpecInstancePK pk) throws RemoveException, EJBException {
		ECSpecInstanceDAOImpl.logger.debug("remove [" + pk.getSpecName() + "]");
		Connection conn = null;
		PreparedStatement ps = null;
		try {

			conn = jdbcFactory.getConnection();
			String createString = "delete from " + MAIN_TABLE_NAME + " where specName = ? ";
			ps = conn.prepareStatement(createString);
			ps.setString(1, pk.getSpecName().trim());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RemoveException(e.getMessage());
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (Exception e) {
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cuhk.ale.dao.ECSpecInstanceDAO#create(cuhk.ale.ejb.ECSpecInstanceBean)
	 */
	public ECSpecInstancePK create(ECSpecInstanceBean ejb) {

		ECSpecInstanceDAOImpl.logger.debug("create [" + ejb.getSpecName() + "]");
		// should use key in argument !

		ECSpecInstancePK key = new ECSpecInstancePK(ejb.getSpecName());
		Connection conn = null;
		PreparedStatement ps = null;
		try {

			conn = jdbcFactory.getConnection();
			String createString = "insert into "
					+ MAIN_TABLE_NAME
					+ " (specName, specXML, state, stateVersion, startTime, previousStartTime, previousEndTime) VALUES (?,?,?,?,?,?,?) ";
			ps = conn.prepareStatement(createString);

			ps.setString(1, ejb.getSpecName().trim());

			JAXBContext jc = JAXBContext.newInstance("epcglobal.ale", this.getClass().getClassLoader());
			Marshaller marshaller = jc.createMarshaller();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			marshaller.marshal(
					new ObjectFactory().createECSpec(ejb.getECSpec()), bos);
			byte[] buf = bos.toByteArray();
			ps.setBytes(2, buf);

			ps.setInt(3, ejb.getState());
			ps.setInt(4, ejb.getStateVersion());
			ps.setLong(5, ejb.getStartTime());
			ps.setLong(6, ejb.getPreviousStartTime());
			ps.setLong(7, ejb.getPreviousEndTime());

			ECSpecInstanceDAOImpl.logger.debug(ejb.getState() + " " + ejb.getStateVersion());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new EJBException(e);
		} catch (JAXBException e) {
			throw new EJBException(e);
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (Exception e) {
			}
		}

		return key;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cuhk.ale.dao.ECSpecInstanceDAO#findByPrimaryKey(cuhk.ale.ejb.interfaces.ECSpecInstancePK)
	 */
	public ECSpecInstancePK findByPrimaryKey(ECSpecInstancePK pk) throws FinderException {

		ECSpecInstanceDAOImpl.logger.debug("findByPrimaryKey [" + pk.getSpecName() + "]");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = jdbcFactory.getConnection();
			String queryString = "select specName from " + MAIN_TABLE_NAME + " where specName = ? ";
			ps = conn.prepareStatement(queryString);
			ps.setString(1, pk.specName);
			rs = ps.executeQuery();
			if (rs.next()) {
				return new ECSpecInstancePK(rs.getString(1).trim());
			}
		} catch (SQLException e) {
			throw new FinderException(e.getMessage());
		} finally {
			try {
				rs.close();
				ps.close();
				conn.close();
			} catch (Exception e) {
			}
		}
		throw new FinderException("Cannot find item for " + pk.specName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cuhk.ale.dao.ECSpecInstanceDAO#getNotificationURLs(java.lang.String)
	 */
	public List getNotificationURLs(String specName) {
		ECSpecInstanceDAOImpl.logger.debug("getNotificationURLs [" + specName + "]");
		List<String> list = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = jdbcFactory.getConnection();
			String queryString = "select notificationUrl from " + URL_TABLE_NAME + " where specName = ?";
			ps = conn.prepareStatement(queryString);
			ps.setString(1, specName);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("notificationUrl"));
			}
		} catch (SQLException e) {
			throw new EJBException(e);
		} finally {
			try {
				rs.close();
				ps.close();
				conn.close();
			} catch (Exception e) {
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cuhk.ale.dao.ECSpecInstanceDAO#addNotificationURL(java.lang.String,
	 *      java.lang.String)
	 */
	public void addNotificationURL(String specName, String url) {

		ECSpecInstanceDAOImpl.logger.debug("addNotificationURL [" + specName + " " + url + "]");

		Connection conn = null;
		PreparedStatement ps = null;
		try {

			conn = jdbcFactory.getConnection();
			String createString = "insert into " + URL_TABLE_NAME + " (specName, notificationUrl) VALUES (?,?) ";
			ps = conn.prepareStatement(createString);
			ps.setString(1, specName.trim());
			ps.setString(2, url.trim());
			ps.executeUpdate();
		} catch (SQLException e) {
			// FIXME handle duplicate subscription
			throw new EJBException(e);
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (Exception e) {
			}
		}

		return;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cuhk.ale.dao.ECSpecInstanceDAO#deleteNotificationURL(java.lang.String,
	 *      java.lang.String)
	 */
	public void deleteNotificationURL(String specName, String url) {

		ECSpecInstanceDAOImpl.logger.debug("deleteNotificationURL [" + specName + " " + url + "]");
		Connection conn = null;
		PreparedStatement ps = null;
		try {

			conn = jdbcFactory.getConnection();
			String createString = "delete from " + URL_TABLE_NAME + " where specName= ? and notificationUrl = ? ";
			ps = conn.prepareStatement(createString);
			ps.setString(1, specName.trim());
			ps.setString(2, url.trim());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new EJBException(e);
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (Exception e) {
			}
		}

		return;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cuhk.ale.dao.ECSpecInstanceDAO#ejbSelectSpecNames()
	 */
	public List getSpecNames() throws EJBException {
		ECSpecInstanceDAOImpl.logger.debug("getSpecNames ");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		try {
			conn = jdbcFactory.getConnection();
			String queryString = "select specName from " + MAIN_TABLE_NAME;
			ps = conn.prepareStatement(queryString);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1).trim());
			}
		} catch (SQLException e) {
			throw new EJBException(e.getMessage());
		} finally {
			try {
				rs.close();
				ps.close();
				conn.close();
			} catch (Exception e) {
			}
		}
		return list;
	}

	public List getTagRead(List readers, long start, long end) {
		return super.getTags(readers, new Date(start), new Date(end));
	}

}
