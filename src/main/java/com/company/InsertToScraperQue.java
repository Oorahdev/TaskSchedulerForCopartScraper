package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import static com.company.Publisher.publishToQueue;

import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by slan on 11/7/2017.
 */

public class InsertToScraperQue implements Job {



        //private consumer list = new consumer();
        ArrayList<String> list = consumer.getCurrentLotNumbers();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
        String db_connect_string = "jdbc:jtds:sqlserver://oorah-admire03:1433/AdmireTempData/";
        //String insert = "INSERT INTO dbo.ArrayListBackupForCopartNotes (Lotnumber) Values(?)";

        Connection conn = null;
        try {

            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conn = DriverManager.getConnection(db_connect_string, "yunion", "421kirby#");

        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        // Connection con = DriverManager.getConnection(host, username, password);
            Statement statement = conn.createStatement();

            statement.executeUpdate("Truncate table ArrayListBackupForCopartNotes");
            System.out.println("truncated the table");

            Iterator i = list.iterator();
            while(i.hasNext()){
                Object element = i.next();
                statement.executeUpdate("INSERT INTO ArrayListBackupForCopartNotes  Values('" + element +"')");
            }

            //for (Iterator<String> i = list.iterator(); i.hasNext(); ) {
             //   System.out.println(i.next().toString());
              //  statement.executeUpdate("INSERT INTO ArrayListBackupForCopartNotes  Values('" + i.next().toString() +"')");

            //}

            conn.close();
        }
        catch(SQLException err){
            System.out.println(err.getMessage());
        }
        // publish the lotnumbers in the array to que
        //truncate db table add lot numbers
        //String timeStampfinal = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        LocalDateTime timestamp = LocalDateTime.now().minusMinutes(30);
        //System.out.println(timestamp);
        String finaltimestamp = LocalDateTime.now().minusMinutes(30).format(DateTimeFormatter.ofPattern("MM/dd/yy HH:mm:ss"));

        //DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        //String finaltimestamp = dateFormat.format(timestamp);

        if (list != null) {
            System.out.println(list);

            Iterator i = list.iterator();
            while(i.hasNext()){
                Object element = i.next();
            //for (Iterator<String> i = list.iterator(); i.hasNext(); ) {
                publishToQueue("celery", element  + "," + finaltimestamp);
                System.out.println(element + " "  + finaltimestamp);
                System.out.println("you made it here");
            }
        }
    }
}
