package com.lxh.test.job;

import com.lxh.test.api.IfindSomething;
import com.lxh.test.impl.FindSomethingImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class testjob implements Job {
    FindSomethingImpl findSomething=new FindSomethingImpl();
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        String returnvalue=findSomething.getSomething();
        String returnvalue1=findSomething.getSomething();
        System.out.println(new Date()+"1============" + returnvalue);
        System.out.println(new Date()+"2============" + returnvalue1);
    }

}
