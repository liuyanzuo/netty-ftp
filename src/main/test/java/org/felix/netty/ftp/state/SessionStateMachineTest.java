package org.felix.netty.ftp.state;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.Inet4Address;

import static org.felix.netty.ftp.state.SessionStateMachine.CONNECTED;
import static org.felix.netty.ftp.state.SessionStateMachine.USER_NAME_READY;

/**
 * SessionStateMachine Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Aug 17, 2018</pre>
 */
public class SessionStateMachineTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getState()
     */
    @Test
    public void testGetState() throws Exception {
    }

    /**
     * Method: setState(int futureState)
     */
    @Test
    public void testSetState() throws Exception {
        SessionStateMachine stateMachine = new SessionStateMachine();
        stateMachine.setState(USER_NAME_READY);
    }

    /**
     * Method: getExtData()
     */
    @Test
    public void testGetExtData() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: setExtData(Object extData)
     */
    @Test
    public void testSetExtData() throws Exception {
//TODO: Test goes here... 
    }


} 
