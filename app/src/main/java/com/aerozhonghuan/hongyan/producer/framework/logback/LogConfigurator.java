package com.aerozhonghuan.hongyan.producer.framework.logback;

import com.aerozhonghuan.hongyan.producer.utils.SimpleSettings;
import com.aerozhonghuan.foundation.log.LogUtil;

import org.slf4j.LoggerFactory;

import java.io.File;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.android.LogcatAppender;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;

/**
 * 日志框架logback
 * Created by zhangyunfei on 15/9/21.
 */
public class LogConfigurator {

    public static final String TAG = "LogConfigurator";

    public static void confifure() {
//        String packageName = OBDApplication.getInstance().getPackageName();
        final String LOG_DIR = SimpleSettings.getLogDir().toString();
        LogUtil.d(TAG, "## 日志路径：" + LOG_DIR);
        File dir = new File(LOG_DIR);
        dir.mkdir();

        LogUtil.d(TAG, "## 日志文件夹是否存在" + dir.exists());
        if (dir.listFiles() != null)
            for (int i = 0; i < dir.listFiles().length; i++) {
                File f = dir.listFiles()[i];
                LogUtil.d(TAG, "## 日志文件:" + f.getPath() + ", size=" + f.length());
            }

        final String PREFIX = "log";
        configureLogbackDirectly(LOG_DIR, PREFIX);
    }

    private static void configureLogbackDirectly(String log_dir, String filePrefix) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.reset();

        RollingFileAppender<ILoggingEvent> rollingFileAppender = new RollingFileAppender<ILoggingEvent>();
        rollingFileAppender.setAppend(true);
        rollingFileAppender.setContext(context);
        rollingFileAppender.setFile(log_dir + "/" + filePrefix + ".0.log");

        FixedWindowRollingPolicy rollingPolicy = new FixedWindowRollingPolicy();
        rollingPolicy.setFileNamePattern(log_dir + "/" + filePrefix + ".%i.log");
        rollingPolicy.setMinIndex(1);
        rollingPolicy.setMaxIndex(2);
        rollingPolicy.setParent(rollingFileAppender);
        rollingPolicy.setContext(context);
        rollingPolicy.start();

        SizeBasedTriggeringPolicy<ILoggingEvent> sizeBasedTriggeringPolicy = new SizeBasedTriggeringPolicy<>();
        sizeBasedTriggeringPolicy.setMaxFileSize("1000KB");
        sizeBasedTriggeringPolicy.setContext(context);
        sizeBasedTriggeringPolicy.start();

        rollingFileAppender.setRollingPolicy(rollingPolicy);
        rollingFileAppender.setTriggeringPolicy(sizeBasedTriggeringPolicy);

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setPattern("%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %message%n");
        encoder.setContext(context);
        encoder.start();

        rollingFileAppender.setEncoder(encoder);
        rollingFileAppender.start();

        LogcatAppender logcatAppender = new LogcatAppender();
        logcatAppender.setContext(context);
        logcatAppender.setEncoder(encoder);
        logcatAppender.setName("logcat1");
        logcatAppender.start();

        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.ALL);
        root.addAppender(rollingFileAppender);
        root.addAppender(logcatAppender);
    }


}
