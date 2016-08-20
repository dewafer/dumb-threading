package com.dewafer.threading.demo.support;

public class RunResult {
    String name;
    int runId;
    long diff;
    int resultCount;

    public RunResult(String name, int runId, long diff, int resultCount) {
        this.name = name;
        this.runId = runId;
        this.diff = diff;
        this.resultCount = resultCount;
    }

    @Override
    public String toString() {
        return String.format("name:%s, Run:%d, diff:%d(ms), resultCount:%d", name, runId, diff, resultCount);
    }
}
