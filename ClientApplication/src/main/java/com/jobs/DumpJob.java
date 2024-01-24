package com.jobs;

import com.controllers.MainController;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Map;

public class DumpJob implements Job{

    private final static Logger log = LoggerFactory.getLogger(MainController.class);


    @Value("${quartz.dumphost}")
    private  String host;

    @Value("${quartz.dumpport}")
    private  String port;

    @Value("${spring.datasource.username}")
    private  String username;

    @Value("${quartz.exportfilepath}")
    private String exportFile;

    @Value("${quartz.importfilepath}")
    private String inportFile;

    @Value("${spring.datasource.password}")
    private  String password;
    
    @Value("${quartz.dbName}")
    private String dbName;

    public  void exportDumpFile() throws IOException, InterruptedException {
        BufferedReader r=null;
        Runtime rt = Runtime.getRuntime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Process p=null;
        ProcessBuilder pb;
        rt = Runtime.getRuntime();
        pb = new ProcessBuilder(
                inportFile,
                "--host", host,
                "--port", port,
                "--username", username,
                "--no-password",
                "--format", "custom",
                "--blobs",
                "--verbose", "--file", exportFile+"pbpost_backup_"+dateFormat.format(timestamp)+".sql",dbName);
        try {
            final Map<String, String> env = pb.environment();
            env.put("PGPASSWORD", password);
            p = pb.start();
             r = new BufferedReader(
                    new InputStreamReader(p.getErrorStream()));
            String line = r.readLine();
            while (line != null) {
                log.info(line);
                line = r.readLine();
            }

            log.info(String.valueOf(p.exitValue()));
            log.info("Hooray! Database Successfully Backup");

        } catch (IOException e) {
            log.info(e.getMessage());
        }
        finally {
            r.close();
            p.waitFor();
        }
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            exportDumpFile();
        } catch (IOException e) {
            log.info(e.toString());
        } catch (InterruptedException e) {
            log.info(e.toString());
        }
    }
}
