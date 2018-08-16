package org.felix.netty.ftp.state;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.felix.netty.ftp.exceptions.StateChangeNotAllowedException;

import java.util.Objects;

/**
 * Session state machine.The order of state should be useful for judge login priority.
 */
public class SessionStateMachine {

    public static final int INIT = 0;

    public static final int CONNECTED = 1;

    public static final int USER_NAME_READY = 2;

    public static final int USER_LOGGED = 10;

    public static final int READY_TRANSFORM = 11;

    private static final Table<Integer, Integer, Boolean> STATE_RULE_TABLE = HashBasedTable.create();

    static {
        STATE_RULE_TABLE.put(INIT, INIT, true);
        STATE_RULE_TABLE.put(CONNECTED, CONNECTED, true);
        STATE_RULE_TABLE.put(USER_NAME_READY, USER_NAME_READY, true);
        STATE_RULE_TABLE.put(USER_LOGGED, USER_LOGGED, true);

        STATE_RULE_TABLE.put(INIT, CONNECTED, true);

        STATE_RULE_TABLE.put(CONNECTED, USER_NAME_READY, true);
        STATE_RULE_TABLE.put(USER_NAME_READY, USER_LOGGED, true);
        STATE_RULE_TABLE.put(USER_LOGGED, READY_TRANSFORM, true);

        //logout
        STATE_RULE_TABLE.put(USER_LOGGED, CONNECTED, true);
    }

    private int state;

    private Object extData;

    public SessionStateMachine() {
        this.state = INIT;
    }

    public int getState() {
        return state;
    }

    public void setState(int futureState) {
        Boolean rule = STATE_RULE_TABLE.get(state, futureState);
        if (Objects.isNull(rule) || !rule.booleanValue()) {
            throw new StateChangeNotAllowedException("state change not allowed", state, futureState);
        }
        this.state = futureState;
    }

    public Object getExtData() {
        return extData;
    }

    public void setExtData(Object extData) {
        this.extData = extData;
    }
}
