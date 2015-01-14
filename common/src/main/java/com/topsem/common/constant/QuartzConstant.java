package com.topsem.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chen on 14-6-16.
 */
public class QuartzConstant {

    public static final String TRIGGER_NAME = "trigger_name";
    public static final String TRIGGER_GROUP = "trigger_group";
    public static final String START_TIME = "start_time";
    public static final String END_TIME = "end_time";
    public static final String REPEAT_COUNT = "repeatCount";
    public static final String REPEAT_INTERVAL = "repeatInterval";
    public static final String NEXT_FIRE_TIME = "next_fire_time";
    public static final String PREV_FIRE_TIME = "prev_fire_time";
    public static final String TRIGGER_STATE = "trigger_state";
    public static final Map<String, String> status = new HashMap<String, String>();

    static {
        status.put("PAUSED", "暂停");
        status.put("ACQUIRED", "运行中");
        status.put("WAITING", "等待中");
    }
}

