package com.company;

import java.util.Date;

public class CLine extends Line
{
    private Date date;
    private int waitingTime;

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public int getWaitingTime()
    {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime)
    {
        this.waitingTime = waitingTime;
    }
}
