package com.baidu.acache;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.baidu.ascheduler.entry.Sched;

@Service
public class BeanMock {

    @Sched
    public Integer testGet(Integer x) {
        return ++x;
    }

    @Sched(param = "T(java.lang.Math).PI", result = "#this.getKey()", nameSpace = "test")
    public Map<Integer, String> testGetList(ConcurrentHashMap<Integer, String> param) {
        Map<Integer, String> ret = new HashMap<Integer, String>();
        for (Entry<Integer, String> pi : param.entrySet()) {
            ret.put(pi.getKey(), pi.getKey() + ":" + pi.getValue());
        }
        return ret;
    }

    boolean throwEx = true;

    @Sched(retry = 2)
    public Integer get(Integer x) {
        if (throwEx) {
            throwEx = false;
            throw new RuntimeException("error");
        }
        return x;
    }

    @Sched
    public Integer getInt() {
        return 5;
    }

    @Sched(ignList = 0)
    public Integer getInt2(Integer f) {
        return 6;
    }

    @Sched(cache = "NopDriver")
    public String getStr() {
        return "OK";
    }

    @Sched(cache = "NopDriver", sequential = true)
    public List<String> getStrs(String[] args) {
        return Arrays.asList(args);
    }
}
