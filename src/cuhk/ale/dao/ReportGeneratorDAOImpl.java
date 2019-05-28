package cuhk.ale.dao;

import java.sql.*;
import java.util.*;
import javax.naming.InitialContext;
import javax.sql.*;

import org.apache.log4j.Logger;

import cuhk.ale.ECTag;
import cuhk.ale.PhysicalReader;
import cuhk.ale.ejb.ReportGeneratorBean;

public class ReportGeneratorDAOImpl implements ReportGeneratorDAO {

	static Logger logger = Logger.getLogger(ReportGeneratorDAOImpl.class.getName( ));
	
	private DataSource jdbcFactory;
	
	public void init() {
		InitialContext c = null;
		if (this.jdbcFactory == null) {
			try {
				c = new InitialContext();
				this.jdbcFactory = (DataSource) c.lookup("java:comp/env/mySQLDS");
			}
			catch (Exception e) {
				logger.error("Error in ReportGeneratorDAOImpl.init()");
				e.printStackTrace();
				
			}
		}
	}

	public List<PhysicalReader> getPhysicalReaders(List logicalReaders, String suppressFlag) {
		
		logger.info("*** ReportGeneratorDAOImpl:getPhysicalReaders() is called. ***");
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Vector<PhysicalReader> result = new Vector<PhysicalReader> ();
		
		try {
			conn = jdbcFactory.getConnection();
			
			String queryString = "select distinct r.* from logicalreader as l, readermapping as m, reader as r where " +
				"(l.logicalreader_id=m.logicalreader_id) AND " + 
				"(m.reader_id=r.reader_id) AND (";
			
			for (Object logicalReader : logicalReaders) {
				queryString += "l.logicalreader_id='" + logicalReader + "' OR ";
			}

			queryString = queryString.substring(0,queryString.length()-4);
			queryString += ")";
			
			if (suppressFlag!=null) {
				queryString += "AND l.suppress=" + suppressFlag;	
			}

			logger.debug(queryString);
			
			ps = conn.prepareStatement(queryString);
		
			rs = ps.executeQuery();
			while (rs.next()) {
				String reader_id = rs.getString("reader_id");
				boolean suppress = rs.getBoolean("suppress");
				String manufacturer = rs.getString("manufacturer");
				String model = rs.getString("model");
				String ipaddress = rs.getString("ipaddress");
				PhysicalReader physicalReader = new PhysicalReader();
				physicalReader.setReaderID(reader_id);
				physicalReader.setSuppress(suppress);
				physicalReader.setManufacturer(manufacturer);
				physicalReader.setModel(model);
				physicalReader.setIPAddress(ipaddress);
				result.add(physicalReader);
				logger.info(reader_id);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				ps.close();
				conn.close();
			}
			catch (Exception e) {
			}
		}
		logger.info("*** ReportGeneratorDAOImpl:getPhysicalReaders() end. ***");
		return result;
	}	
	
	public List getTags(List logicalReaders,java.util.Date start,java.util.Date end) {
		
		logger.info("*** ReportGeneratorDAOImpl:getTags() is called. ***");
		
		List <PhysicalReader> physicalReaders = getPhysicalReaders(logicalReaders,"0");
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Vector<ECTag> result = new Vector<ECTag> ();

		boolean skip=true;

		// check for no physicalReaders case
		for (PhysicalReader physicalReader : physicalReaders) {
			if (!physicalReader.isSuppress()) {
				skip=false;
				break;
			}
		}

		if (!skip) {
			try {
				conn = jdbcFactory.getConnection();
				String queryString = "select distinct tag_id from read_event as a, read_tag as b where " +
						"(a.event_id=b.event_id) AND " +
						" (timestamp >= ? AND timestamp < ?) AND (";
				
				for (PhysicalReader physicalReader : physicalReaders) {
					if (!physicalReader.isSuppress())
						queryString += "reader_id='" + physicalReader.getReaderID() + "' OR ";
				}
	
				queryString = queryString.substring(0,queryString.length()-4);
				queryString += ")";
	
				logger.info(queryString);
				logger.info("start:"+start);
				logger.info("end:"+end);
				
				ps = conn.prepareStatement(queryString);
				ps.setTimestamp(1,new java.sql.Timestamp(start.getTime()));
				ps.setTimestamp(2,new java.sql.Timestamp(end.getTime()));
				
				rs = ps.executeQuery();
				while (rs.next()) {
					String tag_id = rs.getString("tag_id");
					ECTag tag = new ECTag();
					tag.setTagId(tag_id);
					result.add(tag);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				try {
					rs.close();
					ps.close();
					conn.close();
				}
				catch (Exception e) {
				}
			}
		}
	
		logger.info("*** ReportGeneratorDAOImpl:getTags() end. ***");
		return result;
	}
}
