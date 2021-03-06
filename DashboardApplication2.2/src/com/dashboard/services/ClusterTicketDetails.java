package com.dashboard.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Util.ConnectToDatabase;
import Util.ConstantString;

import com.csvreader.CsvReader;
import com.dashboard.model.Dashboard;
import com.dashboard.service.impl.ClusterTicketDetailsImpl;
import com.dashboard.service.impl.DashFileReaderInterface;
import com.dashboard.service.impl.OpenTicketCountInterface;

public class ClusterTicketDetails implements ClusterTicketDetailsImpl {
	ConnectToDatabase condb = new ConnectToDatabase();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dashboard.services.PriorityOneDetailsImpl#showP1TicketDetail(java
	 * .lang.String, java.lang.String)
	 */
	public ArrayList<Dashboard> showPriorityTicketDetail(String prio,
			String callerName) throws SQLException {
		Connection conn = condb.DBConnnect();
		ArrayList<Dashboard> lst = null;
		
		
		lst = new ArrayList<Dashboard>();
		int priority =Integer.parseInt(prio);

	
		Statement statement,statement2;
		ResultSet set = null,set2=null;;
		List<String> clusterList= new ArrayList<String>();
		clusterList.add(ConstantString.DELIVER);
		clusterList.add(ConstantString.SALES);
		clusterList.add(ConstantString.CFOSTRING);
		clusterList.add(ConstantString.SERVICE);
		clusterList.add(ConstantString.NSNWIDE);
		clusterList.add(ConstantString.PEOPLEMGMT);
		clusterList.add(ConstantString.ITINFRA);

		try {
			statement = conn.createStatement();
/*
			if (callerName.equals("Incident")) {
				statement
						.execute("select * from  Ticket_Details2 where Priority ="
								+ priority
								+ "and Request_Type='Incident'");

			} else if (callerName.equals("Service_Request")) {
				statement
						.execute("select * from  Ticket_Details2 where Priority="
								+ priority
								+ "and Request_Type='Service Request' ");
			} else {*/
			statement = conn.createStatement();
			statement2 = conn.createStatement();
			for(String clusterName: clusterList)
			{
				
			statement2.execute("select Business_Service_Name from  BS_Details where Cluster_Name='"+clusterName+"' ");
			
			set2 = statement2.getResultSet();
			while (set2.next()) {
				Dashboard db1 = new Dashboard();
				db1.setBusinessServiceName(set2.getString("Business_Service_Name"));
			statement.execute("select * from  Ticket_Details2 where Priority="
								+ priority
								+ " and  Business_Service_Name='"+db1.getBusinessServiceName().trim()+"'");
			

			set = statement.getResultSet();
				/*statement
						.execute("select * from  Ticket_Details2 where Priority="
								+ priority
								+ "");
			//}

			set = statement.getResultSet();*/
			while (set.next()) {
				Dashboard db = new Dashboard();

				db.setTicketNumber(set.getString("Ticket_Number"));
				db.setArrivalDate(set.getString("Arrival_Date"));
				//db.setRequestType(set.getString("Request_Type"));
				//db.setBreached(set.getString("Breached_Tickets"));
				db.setBusinessServiceName(set.getString("Business_Service_Name"));
				db.setCaller(set.getString("Caller"));
				db.setCallState(set.getString("Call_State"));
				db.setIncidentDesc(set.getString("Incident_Desc"));
				//db.setCluster(set.getString("Cluster"));
				db.setItServiceName(set.getString("IT_Service_Name"));
				db.setItslaTargetDate(set.getString("SLA_Target_Date"));
				db.setAssignmentGroup(set.getString("Assignment_Group"));
			//	db.setResolvingService(set.getString("Resolving_Service_Provider"));
				//db.setAutomaticFlow(set.getString("Automatic_Flow"));
				db.setIncidentNumber(set.getString("Incident_Number"));
				db.setPriority(Integer.parseInt(prio));
				lst.add(db);

			}
			}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			set.close();
			conn.close();
		}

		return lst;
	}

	@Override
	public ArrayList<Dashboard> showStatusTicketDetail(String status,
			String callerName) throws SQLException {
		Connection conn = condb.DBConnnect();
		ArrayList<Dashboard> lst = null;

		lst = new ArrayList<Dashboard>();

		Statement statement;
		ResultSet set;

		try {
			statement = conn.createStatement();

			if (callerName.equals("Incident")) {
				statement
						.execute("select * from  Ticket_Details2 where Status='"
								+ status + "' and Request_Type='Incident'  ");

			} else if (callerName.equals("Service_Request")) {
				statement
						.execute("select * from  Ticket_Details2 where Status='"
								+ status
								+ "'and Request_Type='Service Request'");
			} else {
				statement
						.execute("select * from  Ticket_Details2 where Status='"
								+ status + "'");
			}

			set = statement.getResultSet();
			while (set.next()) {

				Dashboard db = new Dashboard();

				db.setTicketNumber(set.getString("Ticket_Number"));
				db.setArrivalDate(set.getString("Arrival_Date"));
				db.setRequestType(set.getString("Request_Type"));
				db.setBreached(set.getString("Breached_Tickets"));
				db.setBusinessServiceName(set.getString("Business_Service_Name"));
				db.setCaller(set.getString("Caller"));
				db.setCallState(set.getString("Call_State"));
				db.setIncidentDesc(set.getString("Incident_Desc"));
				db.setCluster(set.getString("Cluster"));
				db.setItServiceName(set.getString("IT_Service_Name"));
				db.setItslaTargetDate(set.getString("SLA_Target_Date"));
				db.setAssignmentGroup(set.getString("Assignment_Group"));
				db.setResolvingService(set.getString("Resolving_Service_Provider"));
				db.setAutomaticFlow(set.getString("Automatic_Flow"));
				db.setIncidentNumber(set.getString("Incident_Number"));
				db.setMadeSla(set.getString("Made_SLA"));
				db.setResolveDate(set.getString("Resolved_Date"));
				db.setOverdue(set.getString("Overdue"));
				db.setOpenSinceDays(set.getString("Open_Since"));
				db.setReferenceTicket(set.getString("Reference"));
				db.setSlaVi0lation(set.getString("SLA_Violation"));
				db.setMonthOfCreation(set.getString("MonthOfResolution"));
				db.setMonthOfResolution(set.getString("MonthOfCreation"));
				db.setPriority(set.getInt("Priority"));

				lst.add(db);
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return lst;

	}

	
	
	@Override
	public ArrayList<Dashboard> getClusterWizeList(String whichList,
			String callerName) throws Exception {
		Dashboard deliveryCluster = null;
		Dashboard salesCluster = null;
		Dashboard cfoCluster = null;
		Dashboard servicesCluster = null;
		Dashboard wideCluster = null;
		Dashboard peopleCluster = null;
		Dashboard infraCluster = null;
		DashFileReaderInterface reader = new DashFileReader();
		ArrayList<Dashboard> resultlst = new ArrayList<Dashboard>();

		OpenTicketCountInterface ticketCount = new OpenTicketCount();

		try {// get all priority count..
			if (whichList.equals("priority")) {
				deliveryCluster = new Dashboard();
				salesCluster = new Dashboard();

				cfoCluster = new Dashboard();

				servicesCluster = new Dashboard();

				wideCluster = new Dashboard();

				peopleCluster = new Dashboard();

				infraCluster = new Dashboard();

				deliveryCluster = ticketCount.ticketCounter(ConstantString.DELIVER, deliveryCluster,ConstantString.callerName);
				salesCluster = ticketCount.ticketCounter(ConstantString.SALES,
						salesCluster, ConstantString.callerName);
				//System.out.println(salesCluster.getP1Count()+"sales");
				cfoCluster = ticketCount.ticketCounter(
						ConstantString.CFOSTRING, cfoCluster,
						ConstantString.callerName);
				servicesCluster = ticketCount.ticketCounter(
						ConstantString.SERVICE, servicesCluster,
						ConstantString.callerName);
			//	System.out.println(salesCluster.getP4Count()+"sales");
				wideCluster = ticketCount.ticketCounter(ConstantString.NSNWIDE,
						wideCluster, ConstantString.callerName);
				peopleCluster = ticketCount.ticketCounter(
						ConstantString.PEOPLEMGMT, peopleCluster,
						ConstantString.callerName);
				infraCluster = ticketCount.ticketCounter(
						ConstantString.ITINFRA, infraCluster,
						ConstantString.callerName);

				resultlst.add(deliveryCluster);
				resultlst.add(salesCluster);
				resultlst.add(cfoCluster);
				resultlst.add(servicesCluster);
				resultlst.add(wideCluster);
				resultlst.add(peopleCluster);
				resultlst.add(infraCluster);
			} else if (whichList.equals("status")) {
				deliveryCluster = new Dashboard();
				salesCluster = new Dashboard();
				;
				cfoCluster = new Dashboard();
				;
				servicesCluster = new Dashboard();
				;
				wideCluster = new Dashboard();
				;
				peopleCluster = new Dashboard();
				;
				infraCluster = new Dashboard();
				;
				// get all status count
				deliveryCluster = ticketCount.statusCounter(
						ConstantString.DELIVER, deliveryCluster,
						ConstantString.callerName);
				salesCluster = ticketCount.statusCounter(ConstantString.SALES,
						salesCluster, ConstantString.callerName);
				cfoCluster = ticketCount.statusCounter(
						ConstantString.CFOSTRING, cfoCluster,
						ConstantString.callerName);
				servicesCluster = ticketCount.statusCounter(
						ConstantString.SERVICE, servicesCluster,
						ConstantString.callerName);
				wideCluster = ticketCount.statusCounter(ConstantString.NSNWIDE,
						wideCluster, ConstantString.callerName);
				peopleCluster = ticketCount.statusCounter(
						ConstantString.PEOPLEMGMT, peopleCluster,
						ConstantString.callerName);
				infraCluster = ticketCount.statusCounter(
						ConstantString.ITINFRA, infraCluster,
						ConstantString.callerName);

				resultlst.add(deliveryCluster);
				resultlst.add(salesCluster);
				resultlst.add(cfoCluster);
				resultlst.add(servicesCluster);
				resultlst.add(wideCluster);
				resultlst.add(peopleCluster);
				resultlst.add(infraCluster);

			} else if (whichList.equals("reopen"))

			{
				deliveryCluster = new Dashboard();
				salesCluster = new Dashboard();
				;
				cfoCluster = new Dashboard();
				;
				servicesCluster = new Dashboard();
				;
				wideCluster = new Dashboard();
				;
				peopleCluster = new Dashboard();
				;
				infraCluster = new Dashboard();
				;
				deliveryCluster = ticketCount.getReopenCountlst(
						ConstantString.DELIVER, ConstantString.callerName);
				salesCluster = ticketCount.getReopenCountlst(
						ConstantString.SALES, ConstantString.callerName);
				cfoCluster = ticketCount.getReopenCountlst(
						ConstantString.CFOSTRING, ConstantString.callerName);
				servicesCluster = ticketCount.getReopenCountlst(
						ConstantString.SERVICE, ConstantString.callerName);
				wideCluster = ticketCount.getReopenCountlst(
						ConstantString.NSNWIDE, ConstantString.callerName);
				peopleCluster = ticketCount.getReopenCountlst(
						ConstantString.PEOPLEMGMT, ConstantString.callerName);
				infraCluster = ticketCount.getReopenCountlst(
						ConstantString.ITINFRA, ConstantString.callerName);

				resultlst.add(deliveryCluster);
				resultlst.add(salesCluster);
				resultlst.add(cfoCluster);
				resultlst.add(servicesCluster);
				resultlst.add(wideCluster);
				resultlst.add(peopleCluster);
				resultlst.add(infraCluster);

			} else if (whichList.equals("backlog"))

			{
				deliveryCluster = new Dashboard();
				salesCluster = new Dashboard();
				;
				cfoCluster = new Dashboard();
				;
				servicesCluster = new Dashboard();
				;
				wideCluster = new Dashboard();
				;
				peopleCluster = new Dashboard();
				;
				infraCluster = new Dashboard();
				;
				deliveryCluster = reader.getBreachDetail(
						ConstantString.DELIVER, ConstantString.callerName);
				salesCluster = reader.getBreachDetail(ConstantString.SALES,
						ConstantString.callerName);
				cfoCluster = reader.getBreachDetail(ConstantString.CFOSTRING,
						ConstantString.callerName);
				servicesCluster = reader.getBreachDetail(
						ConstantString.SERVICE, ConstantString.callerName);
				wideCluster = reader.getBreachDetail(ConstantString.NSNWIDE,
						ConstantString.callerName);
				peopleCluster = reader.getBreachDetail(
						ConstantString.PEOPLEMGMT, ConstantString.callerName);
				infraCluster = reader.getBreachDetail(ConstantString.ITINFRA,
						ConstantString.callerName);

				resultlst.add(deliveryCluster);
				resultlst.add(salesCluster);
				resultlst.add(cfoCluster);
				resultlst.add(servicesCluster);
				resultlst.add(wideCluster);
				resultlst.add(peopleCluster);
				resultlst.add(infraCluster);

			}
			else if (whichList.equals("overdue"))

			{
				deliveryCluster = new Dashboard();
				salesCluster = new Dashboard();
				;
				cfoCluster = new Dashboard();
				;
				servicesCluster = new Dashboard();
				;
				wideCluster = new Dashboard();
				;
				peopleCluster = new Dashboard();
				;
				infraCluster = new Dashboard();
				;
				deliveryCluster = reader.getOverdueTicketDetail(
						ConstantString.DELIVER, ConstantString.callerName);
				salesCluster = reader.getOverdueTicketDetail(ConstantString.SALES,
						ConstantString.callerName);
				cfoCluster = reader.getOverdueTicketDetail(ConstantString.CFOSTRING,
						ConstantString.callerName);
				servicesCluster = reader.getOverdueTicketDetail(
						ConstantString.SERVICE, ConstantString.callerName);
				wideCluster = reader.getOverdueTicketDetail(ConstantString.NSNWIDE,
						ConstantString.callerName);
				peopleCluster = reader.getOverdueTicketDetail(
						ConstantString.PEOPLEMGMT, ConstantString.callerName);
				infraCluster = reader.getOverdueTicketDetail(ConstantString.ITINFRA,
						ConstantString.callerName);

				resultlst.add(deliveryCluster);
				resultlst.add(salesCluster);
				resultlst.add(cfoCluster);
				resultlst.add(servicesCluster);
				resultlst.add(wideCluster);
				resultlst.add(peopleCluster);
				resultlst.add(infraCluster);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultlst;
	}

	@Override
	public ArrayList<Dashboard> showOverdueTicketDetail(String callerName)
			throws SQLException {
		Connection conn = condb.DBConnnect();
		ArrayList<Dashboard> lst = null;
		
		
		lst = new ArrayList<Dashboard>();
		

		Statement statement;
		ResultSet set = null;

		try {
			statement = conn.createStatement();

			if (callerName.equals("Incident")) {
				statement
						.execute("select *    from  Ticket_Details2 where Overdue= -1 and Status <> 'Closed'   and   Request_Type='Incident'");

			} else if (callerName.equals("Service_Request")) {
				statement
						.execute("select * from  Ticket_Details2 where Overdue=-1 and Status <> 'Closed' and Request_Type='Service Request' ");
			} else {
				statement
						.execute("select * from  Ticket_Details2 where Overdue=-1 and Status <> 'Closed'");
			}

			set = statement.getResultSet();
			while (set.next()) {
				Dashboard db = new Dashboard();

				db.setTicketNumber(set.getString("Ticket_Number"));
				db.setArrivalDate(set.getString("Arrival_Date"));
				db.setRequestType(set.getString("Request_Type"));
				db.setBreached(set.getString("Breached_Tickets"));
				db.setBusinessServiceName(set.getString("Business_Service_Name"));
				db.setCaller(set.getString("Caller"));
				db.setCallState(set.getString("Call_State"));
				db.setIncidentDesc(set.getString("Incident_Desc"));
				db.setCluster(set.getString("Cluster"));
				db.setItServiceName(set.getString("IT_Service_Name"));
				db.setItslaTargetDate(set.getString("SLA_Target_Date"));
				db.setAssignmentGroup(set.getString("Assignment_Group"));
				db.setResolvingService(set.getString("Resolving_Service_Provider"));
				db.setAutomaticFlow(set.getString("Automatic_Flow"));
				db.setIncidentNumber(set.getString("Incident_Number"));
				db.setMadeSla(set.getString("Made_SLA"));
				db.setResolveDate(set.getString("Resolved_Date"));
				db.setOverdue(set.getString("Overdue"));
				db.setOpenSinceDays(set.getString("Open_Since"));
				db.setReferenceTicket(set.getString("Reference"));
				db.setSlaVi0lation(set.getString("SLA_Violation"));
				db.setMonthOfCreation(set.getString("MonthOfResolution"));
				db.setMonthOfResolution(set.getString("MonthOfCreation"));
				db.setPriority(set.getInt("Priority"));

				lst.add(db);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			set.close();
			conn.close();
		}

		return lst;

	}

	@Override
	public boolean importData(String fileName) throws Exception {
		System.out.println("in import");
		ConnectToDatabase condb=new ConnectToDatabase();
		Connection conn=condb.DBConnnect();
		Statement statement,statement1,statement2;
		 PreparedStatement pstmt = null;
		CsvReader reader= null;
		java.util.Date itsladate = null;
		final int batchSize = 1000;
		int count = 0;
		List<Dashboard> lst=new ArrayList<Dashboard>();
		String str="";
		try{	
			statement1=conn.createStatement();
			statement2=conn.createStatement();
			SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		    java.util.Date currDate=new java.util.Date();
		    String strDate = sdfDate.format(currDate);
		 // statement1.execute("insert into Upload_Time values('"+strDate.trim()+"')");
		  statement1.execute("update Upload_Time set Upload_Date='"+strDate.trim()+"'");
			reader = new CsvReader(fileName);
			reader.readHeaders();
			str="insert into Ticket_Details2(Ticket_Number,Arrival_Date,Call_State,Incident_Desc,Business_Service_Name,IT_Service_Name,Priority,"
					+ "Assignment_Group,Caller,Incident_Number,SLA_Target_Date,"
					+ "Status) values(?,?,?,?,?,?,?,?,?,?,?,?)";
			 pstmt=conn.prepareStatement(str);
			statement=conn.createStatement();
			//statement.execute("INSERT INTO Old_Tickets SELECT * FROM Ticket_Details2");
			statement2.execute("delete * FROM Ticket_Details2");
			/*statement.execute("insert into Old_Tickets(Ticket_Number,Arrival_Date,Request_Type,Call_State,Incident_Desc,Cluster,Business_Service_Name,IT_Service_Name,Priority,"
							+ "Assignment_Group,Resolving_Service_Provider,Caller,Incident_Number,Automatic_Flow,Made_SLA,Resolved_Date,SLA_Target_Date,Breached_Tickets,"
							+ "Overdue,Open_Since,Reference,SLA_Violation,Status,MonthOfCreation,MonthOfResolution) SELECT * FROM Ticket_Details2");
			statement.execute("delete * FROM Ticket_Details2");*/
			
			while (reader.readRecord()){
				Dashboard db=new Dashboard();
			   
			//    String strDate = sdfDate.format(currDate);
			db.setTicketNumber(reader.get("Task").trim());
				db.setArrivalDate(reader.get("Opened").trim());
				//db.setRequestType(reader.get("Request_Type").trim());
				str=reader.get("Call state");
				if(str!="")
				{
					db.setCallState(reader.get("Call state"));
				}else
				{
					db.setCallState("NA");
				}
				str=reader.get("Short description");
				if(str!="")
				{
					db.setIncidentDesc(reader.get("Short description").replace("=-", " "));
				}else
				{
					db.setIncidentDesc("NA");
				}
				//db.setCluster(reader.get("Cluster"));
				str=reader.get("Service offering");
				if(str!="")
				{
					db.setBusinessServiceName(reader.get("Service offering"));
				}
				else
				{
					db.setBusinessServiceName("NA");
				}
				str=reader.get("Support offering");
				if(str!="")
				{
					db.setItServiceName(reader.get("Support offering"));
				}
				else
				{
					db.setItServiceName("NA");
				}
				str=reader.get("Priority");
				if(str!="")
				{
					if(str.contains(" ")){
						   str= str.substring(0, str.indexOf(" ")); 
						  
						}
				db.setPriority(Integer.parseInt(str.trim()));
				 
				}else
				{
					db.setPriority(0);
					
					//System.out.println(str);
				}
				
				str=reader.get("Assignment group");
				if(str!="")
				{
					db.setAssignmentGroup(str);
				}
				else
				{
					db.setAssignmentGroup("NA");				
					
				}
				//db.setResolvingService(reader.get("Resolving_Service_Provider"));
				str=reader.get("Caller");
				if(str!="")
				{
					db.setCaller(reader.get("Caller"));
				}else
				{
					db.setCaller("NA");
				}
				
				str=reader.get("Incident");
				if(str!="")
				{
					db.setIncidentNumber(reader.get("Incident"));
				}else
				{
					db.setIncidentNumber("NA");
				}
				/*db.setAutomaticFlow(reader.get("Automatic_Flow").trim());
				db.setMadeSla(reader.get("Made_SLA").trim());
				*/
				/*str=reader.get("Resolved_Date");
				if(str!="")
				{
					db.setResolveDate(str);
				}
				else
				{
					db.setResolveDate(null);					
					
				}*/
				 
								
				str=reader.get("Planned end time");
				if(str!="")
				{
					db.setItslaTargetDate(str);
				}
				else
				{
					db.setItslaTargetDate("NA");					
					
				}
				
			
				if(str!="")
				{
					db.setStatus(reader.get("Stage"));
				}else
				{
					db.setStatus("NA");
				}
				lst.add(db);
					
			}
			
			try {
				for(Dashboard dab:lst)
				{
	
				
				
				 pstmt.setString(1, dab.getTicketNumber());
				 pstmt.setString(2, dab.getArrivalDate());
				
				 pstmt.setString(3, dab.getCallState());
				 pstmt.setString(4, dab.getIncidentDesc().replace("'", "''") );
				
				 pstmt.setString(5, dab.getBusinessServiceName());
				 pstmt.setString(6, dab.getItServiceName());
				 pstmt.setInt(7, dab.getPriority());
				 pstmt.setString(8, dab.getAssignmentGroup());
				
				 pstmt.setString(9, dab.getCaller().replace("'", "''"));
				 pstmt.setString(10, dab.getIncidentNumber());
				
			
				 pstmt.setString(11, dab.getItslaTargetDate());
			
				
				 pstmt.setString(12, dab.getStatus());
				
			
				pstmt.executeUpdate();
			/*str="insert into Ticket_Details2(Ticket_Number,Arrival_Date,Call_State,Incident_Desc,Business_Service_Name,IT_Service_Name,Priority,"
							+ "Assignment_Group,Caller,Incident_Number,SLA_Target_Date,"
							+ "Status) values ('"
					+ dab.getTicketNumber()
					+ "','"
					+ dab.getArrivalDate()
					+ "','"
					+ dab.getCallState()
					+ "','"
					+ dab.getIncidentDesc().replace("'", "''")
					+ "','"
					+ dab.getBusinessServiceName()
					+ "','"
					+ dab.getItServiceName()
					+ "',"
					+ dab.getPriority()
					+ ",'"
					+ dab.getAssignmentGroup()
					+ "','"
					+ dab.getCaller().replace("'", "''")
					+ "','"
					+ dab.getIncidentNumber()
					+ "','"
					+ dab.getItslaTargetDate()
					+ "','"
					+ dab.getStatus()
					+ "')";
					
					statement.execute(str);*/
				
				} //pstmt.executeBatch();
				//System.out.println(str);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(str);
			}
			
			updateBreachBacklog();
	
		
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(str);
			} finally{
				reader.close();
				conn.close();
			
			}
		return false;
		
	}

	@Override
	public ArrayList<String> getTicketNo(String term) throws Exception {

		ConnectToDatabase condb=new ConnectToDatabase();
		Connection conn=condb.DBConnnect();
		ArrayList<String> list = new ArrayList<String>();
		 PreparedStatement ps = null;
         String data;
         ResultSet rs=null;
         try {
                 ps = conn.prepareStatement("SELECT * FROM Ticket_Details2  WHERE Ticket_Number  LIKE ?");
                 ps.setString(1, term + "%");
                 rs = ps.executeQuery();
                 while (rs.next()) {
                         data = rs.getString("Ticket_Number");
                         list.add(data);
                 }
         } catch(Exception e)
		{
			e.printStackTrace();
		} finally{
			rs.close();
			conn.close();
		
		}
		 
		return list;
	}

	@Override
	public ArrayList<Dashboard> getSerachDataList(String tno, String fdate,String tdate) throws Exception {
		ConnectToDatabase condb=new ConnectToDatabase();
		Connection conn=condb.DBConnnect();
		ArrayList<Dashboard> list = new ArrayList<Dashboard>();
		Statement statement=null;
		ResultSet set = null;
		
	//	System.out.println("from date:......................"+fdate);
		 PreparedStatement ps = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			
			statement = conn.createStatement();
		//	statement.execute("select * from  Ticket_Details2 where Ticket_Number='"+tno+"'");
			if(!tno.isEmpty() && fdate.isEmpty() && tdate.isEmpty())
			{
			
				 ps = conn.prepareStatement("select * from  Ticket_Details2 where Ticket_Number=?");
				 ps.setString(1,tno);
		}		
			else if(tno.isEmpty() && !fdate.isEmpty() && tdate.isEmpty())
			{
				java.util.Date startDate = formatter.parse(fdate);
				
				ps = conn.prepareStatement("select * from  Ticket_Details2 where  Arrival_Date>=?");
				 ps.setDate(1, new java.sql.Date(startDate.getTime()));
				
			}else if(tno.isEmpty() && !fdate.isEmpty() && !tdate.isEmpty())
			{
				java.util.Date startDate = formatter.parse(fdate);
				java.util.Date endDate = formatter.parse(tdate);
				 ps = conn.prepareStatement("select * from  Ticket_Details2 where  Arrival_Date>=? and Arrival_Date<=?");
				 ps.setDate(1, new java.sql.Date(startDate.getTime()));
				 ps.setDate(2, new java.sql.Date(endDate.getTime()));
			}
			
			else if(tno.isEmpty() && fdate.isEmpty() && !tdate.isEmpty())
			{
				
				java.util.Date endDate = formatter.parse(tdate);
				ps = conn.prepareStatement("select * from  Ticket_Details2 where  Arrival_Date<=?");
				 ps.setDate(1, new java.sql.Date(endDate.getTime()));
			}
			else if(!tno.isEmpty() && !fdate.isEmpty() && tdate.isEmpty())
			{
				java.util.Date startDate = formatter.parse(fdate);
				//java.util.Date endDate = formatter.parse(tdate);
				 ps = conn.prepareStatement("select * from  Ticket_Details2 where  Ticket_Number=? and Arrival_Date>=?");
				 ps.setString(1,tno);
				 ps.setDate(2, new java.sql.Date(startDate.getTime()));
				// ps.setDate(3, new java.sql.Date(endDate.getTime()));
			}
			else if(!tno.isEmpty() && fdate.isEmpty() && !tdate.isEmpty())
			{
				//java.util.Date startDate = formatter.parse(fdate);
				java.util.Date endDate = formatter.parse(tdate);
				 ps = conn.prepareStatement("select * from  Ticket_Details2 where  Ticket_Number=?  and Arrival_Date<=?");
				 ps.setString(1,tno);
				// ps.setDate(2, new java.sql.Date(startDate.getTime()));
				 ps.setDate(2, new java.sql.Date(endDate.getTime()));
			}
			else if(!tno.isEmpty() && !fdate.isEmpty() && !tdate.isEmpty())
			{
				java.util.Date startDate = formatter.parse(fdate);
				java.util.Date endDate = formatter.parse(tdate);
				 ps = conn.prepareStatement("select * from  Ticket_Details2 where  Ticket_Number=? and Arrival_Date>=? and Arrival_Date<=?");
				 ps.setString(1,tno);
				 ps.setDate(2, new java.sql.Date(startDate.getTime()));
				 ps.setDate(3, new java.sql.Date(endDate.getTime()));
			}
			/*else
			{
				statement.execute("select * from  Ticket_Details2 where Ticket_Number='"+tno+"'");
			}
		*/
		
		set = ps.executeQuery();
	//	set = statement.getResultSet();
		while (set.next()) {
			Dashboard db = new Dashboard();

			db.setTicketNumber(set.getString("Ticket_Number"));
			db.setArrivalDate(set.getString("Arrival_Date"));
			//db.setRequestType(set.getString("Request_Type"));
			//db.setBreached(set.getString("Breached_Tickets"));
			db.setBusinessServiceName(set.getString("Business_Service_Name"));
			db.setCaller(set.getString("Caller"));
			db.setCallState(set.getString("Call_State"));
			db.setIncidentDesc(set.getString("Incident_Desc"));
			//db.setCluster(set.getString("Cluster"));
			db.setItServiceName(set.getString("IT_Service_Name"));
			db.setItslaTargetDate(set.getString("SLA_Target_Date"));
			db.setAssignmentGroup(set.getString("Assignment_Group"));
		//	db.setResolvingService(set.getString("Resolving_Service_Provider"));
			//db.setAutomaticFlow(set.getString("Automatic_Flow"));
			db.setIncidentNumber(set.getString("Incident_Number"));

			list.add(db);

		}
		
	}catch(SQLException e)
	{
		e.printStackTrace();
	}catch (ParseException e) {
        e.printStackTrace();
    }finally{
		set.close();
		conn.close();
	
	}
	return list;
	}
	
public String getUpdateTime()throws Exception {
		
		ConnectToDatabase condb=new ConnectToDatabase();
		Connection conn=condb.DBConnnect();
		 Statement statement;
		 String dte ="";
		 ResultSet set = null;
		 try {
			 statement = conn.createStatement();
			 
			 
			 
				statement.execute("select Upload_Date from Upload_Time");
			
				set = statement.getResultSet();
			
	         set.next();
	         dte=set.getString(1);	
			
				 //conn.close();
			 } catch(SQLException e)
				{
					e.printStackTrace();
				}finally{
					set.close();
					conn.close();
				
				}
		return dte;
				
				 
				
		}

@Override
public void updateBreachBacklog()throws Exception {

	ConnectToDatabase condb=new ConnectToDatabase();
	Connection conn=condb.DBConnnect();
	 Statement statement,statement2,statement3,statement4,statement5;
	 String dte ="";
	 ResultSet set,set2,set3;
	 ArrayList<Dashboard> overduelst =  new ArrayList<Dashboard>();
	 ArrayList<Dashboard> backloglst =  new ArrayList<Dashboard>();
	 try {
		 SimpleDateFormat sdfDate = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");// dd/MM/yyyy
			Date now = new Date();
			String strDate = sdfDate.format(now);
			statement = conn.createStatement();
			statement2 = conn.createStatement();
			statement3 = conn.createStatement();
			statement4 = conn.createStatement();
			statement5 = conn.createStatement();
	//System.out.println("select * from  Ticket_Details2 where Breached_Tickets=-1 and Cluster='"+clusterName+"' and Status <> 'Closed' ");
	statement2.execute("select Business_Service_Name from  BS_Details ");

	set2 = statement2.getResultSet();
	while (set2.next()) {
		Dashboard db1 = new Dashboard();
		db1.setBusinessServiceName(set2.getString("Business_Service_Name"));
		
		statement.execute("select count(*)as count from  Ticket_Details2 where SLA_Target_Date < #"
					+ strDate+ "# and  Business_Service_Name='"+db1.getBusinessServiceName().trim()+"'");
		statement3.execute("select count(*)as count from  Ticket_Details2 where SLA_Target_Date > #"
				+ strDate+ "# and  Business_Service_Name='"+db1.getBusinessServiceName().trim()+"'");
		set = statement.getResultSet();
		set3 = statement3.getResultSet();
		while (set.next()) {
			
			Dashboard db = new Dashboard();

			db.setOverdueCount(set.getInt("count"));
			overduelst.add(db);
			
		}
	while (set3.next()) {
			
			Dashboard db = new Dashboard();

			db.setBklogCount(set3.getInt("count"));
			backloglst.add(db);
			
		}
	for(Dashboard db : overduelst)
	{
		statement4.execute("update BS_Details set Overdue="+db.getOverdueCount()+" where Business_Service_Name='"+db1.getBusinessServiceName().trim()+"' ");
	}
	for(Dashboard db : backloglst)
	{
		statement5.execute("update BS_Details set Backlog="+db.getBklogCount()+" where Business_Service_Name='"+db1.getBusinessServiceName().trim()+"' ");
	}
			
	 }
	
	 } catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			conn.close();
		}
}

@Override
public ArrayList<Dashboard> showClusterDump(String callerName,String clusterName)
		throws SQLException {
	ConnectToDatabase condb = new ConnectToDatabase();
	Connection conn = condb.DBConnnect();
	ArrayList<Dashboard> lst = null;
	lst = new ArrayList<Dashboard>();
	Statement statement,statement2;
	ResultSet set,set2;
	//System.out.println(callerName + " showBreachTicket im method");

	try {
		SimpleDateFormat sdfDate = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");// dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);
		statement = conn.createStatement();
		statement2 = conn.createStatement();

		
			//System.out.println("select * from  Ticket_Details2 where Breached_Tickets=-1 and Cluster='"+clusterName+"' and Status <> 'Closed' ");
			statement2.execute("select Business_Service_Name from  BS_Details where Cluster_Name='"+clusterName+"' ");
		
			set2 = statement2.getResultSet();
			while (set2.next()) {
				Dashboard db1 = new Dashboard();
				db1.setBusinessServiceName(set2.getString("Business_Service_Name"));
				//System.out.println(db1.getBusinessServiceName());
				statement.execute("select * from  Ticket_Details2 where  Business_Service_Name='"+db1.getBusinessServiceName().trim()+"'");
				set = statement.getResultSet();
				while (set.next()) {
					
					Dashboard db = new Dashboard();

					db.setTicketNumber(set.getString("Ticket_Number"));
					db.setArrivalDate(set.getString("Arrival_Date"));
					//db.setRequestType(set.getString("Request_Type"));
					//db.setBreached(set.getString("Breached_Tickets"));
					db.setBusinessServiceName(set.getString("Business_Service_Name"));
					db.setCaller(set.getString("Caller"));
					db.setCallState(set.getString("Call_State"));
					db.setIncidentDesc(set.getString("Incident_Desc"));
					//db.setCluster(set.getString("Cluster"));
					db.setItServiceName(set.getString("IT_Service_Name"));
					db.setItslaTargetDate(set.getString("SLA_Target_Date"));
					db.setAssignmentGroup(set.getString("Assignment_Group"));
				//	db.setResolvingService(set.getString("Resolving_Service_Provider"));
					//db.setAutomaticFlow(set.getString("Automatic_Flow"));
					db.setIncidentNumber(set.getString("Incident_Number"));
					/*db.setMadeSla(set.getString("Made_SLA"));
					db.setResolveDate(set.getString("Resolved_Date"));
					db.setOverdue(set.getString("Overdue"));
					db.setOpenSinceDays(set.getString("Open_Since"));
					db.setReferenceTicket(set.getString("Reference"));
					db.setSlaVi0lation(set.getString("SLA_Violation"));
					db.setMonthOfCreation(set.getString("MonthOfResolution"));
					db.setMonthOfResolution(set.getString("MonthOfCreation"));*/
					db.setPriority(set.getInt("Priority"));
					//	System.out.println(db.getBusinessServiceName());
					lst.add(db);
					
				}
					
			
		}
		
	} catch (SQLException e) {
	
		e.printStackTrace();
	} finally {
		conn.close();
	}
	return lst;
}
	
	
}
