import java.sql.Connection;
import java.sql.SQLException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class MyJob implements Job {
	Connection conn=null;
//	AuctionDetabase adb = new AuctionDetabase();
	
	public MyJob() {
		
	}
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
//		try {
//			adb.timeFlies(conn);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		
		
//		Aution.editLable(AuctionDetabase.Auctionlist,Aution.remainingTimeArr);
		
	}
	
	

}