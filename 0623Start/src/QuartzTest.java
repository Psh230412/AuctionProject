import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzTest {
    public static void main(String[] args) {
        try {
            // Grab the Scheduler instance from the Factory
            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler sched = sf.getScheduler();
            
            // define the job and tie it to our MyJob class
            JobDetail job = JobBuilder.newJob(MyJob.class)
                .withIdentity("myJob", "group1") // name "myJob", group "group1"
                .build();

            // Trigger the job to run now, and then repeat every 40 seconds
            Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(10)
                    .repeatForever())            
                .build();

            // Tell quartz to schedule the job using our trigger
            sched.scheduleJob(job, trigger);
            
            // Start the scheduler
            sched.start();
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }
}