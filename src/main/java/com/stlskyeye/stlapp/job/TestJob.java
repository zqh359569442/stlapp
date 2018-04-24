package com.stlskyeye.stlapp.job;

import com.stlskyeye.stlapp.core.quarzt.Quarzt;

@Quarzt(name="1",groupName = "sto",cron = "* 0/5 * * * ?",threadNum = 3)
public class TestJob {
}
