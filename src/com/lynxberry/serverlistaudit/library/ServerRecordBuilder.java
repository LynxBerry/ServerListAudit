package com.lynxberry.serverlistaudit.library;

/**
 * Created by stevenshao on 28/10/2016.
 */
public class ServerRecordBuilder extends RecordBuilder {
    @Override
    protected Record innerBuild() {
        return new ServerRecord();
    }
}
