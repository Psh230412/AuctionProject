import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

public class test {
	private static JLabel label1;
	private static JLabel label2;
	private static JLabel label3;
	
    private Scheduler scheduler;
    
    private static TimerTest timerTest;

    public test() {
        JFrame frame = new JFrame();
        timerTest = new TimerTest();
        frame.setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(200, 100));

		JButton button1 = new JButton("Button 1");
		JButton button2 = new JButton("Button 2");
		JButton button3 = new JButton("Button 3");
		
		button1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				timerTest.update(1);
			}
		});

		panel.add(button1, "SOUTH");
		panel.add(button2, "SOUTH");
		panel.add(button3, "SOUTH");

		label1 = new JLabel("00:00:00");
		label2 = new JLabel("00:00:00");
		label3 = new JLabel("00:00:00");
		
		panel.add(label1, "NORTH");
		panel.add(label2, "NORTH");
		panel.add(label3, "NORTH");

		frame.add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();

            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("label", label1);

            JobDetail job = JobBuilder.newJob(LabelUpdateJob.class)
                    .withIdentity("labelUpdateJob", "group1")
                    .usingJobData(jobDataMap)
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("labelUpdateTrigger", "group1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(1)
                            .repeatForever())
                    .build();

            scheduler.scheduleJob(job, trigger);

            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    
    public static String duration(LocalDateTime targetDateTime, LocalDateTime now) {
		Duration duration = Duration.between(now, targetDateTime);
		long days = duration.toDays();
		long hours = duration.toHours() % 24;
		long minutes = duration.toMinutes() % 60;
		long seconds = duration.getSeconds() % 60;

		return String.format("%d일, %d시간, %d분, %d초", days, hours, minutes, seconds);
	}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new test();
            }
        });
    }

    public static class LabelUpdateJob implements Job {
        public void execute(JobExecutionContext context) throws JobExecutionException {
            JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
            JLabel label = (JLabel) jobDataMap.get("label");

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String currentTime = sdf.format(new Date());

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                	LocalDateTime now = LocalDateTime.now();
                	LocalDateTime time1 = timerTest.select(1);
        			LocalDateTime time2 = timerTest.select(2);
        			LocalDateTime time3 = timerTest.select(3);

        			String result1 = duration(time1, now);
        			String result2 = duration(time2, now);
        			String result3 = duration(time3, now);
        			
        			label1.setText(result1);
        			label2.setText(result2);
        			label3.setText(result3);
                }
            });
        }
    }
}
