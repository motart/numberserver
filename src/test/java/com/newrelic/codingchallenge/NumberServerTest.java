package com.newrelic.codingchallenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.Socket;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

@SuppressWarnings("deprecation")
public class NumberServerTest {
    @Mock
    Socket socket = mock(Socket.class);
    NumberServer fixture = spy(NumberServer.class);
    ReportPrinter reportPrinter = spy(ReportPrinter.class);
    LogFileWriter logFileWriter = mock(LogFileWriter.class);

    @BeforeEach
    public void setup() {
        //if we don't call below, we will get NullPointerException
        MockitoAnnotations.initMocks(this);
        fixture.setLogFileWriter(logFileWriter);
    }

    @Test
    public void processInputTest() throws IOException, InterruptedException {
        String[] rawInput = "terminate,123456789,123456789,111111111,222222222,3333333333333,abcdefghi,terminate".split(",");

        doNothing().when(fixture).terminate();
        doNothing().when(logFileWriter).writeLine(any());
        doNothing().when(socket).close();
        for (String input : rawInput) {
            fixture.processInput(input, socket, new StringBuilder());
        }

        // verify the report output
        assertEquals(1, fixture.getReportPrinter().getDiffDupCount());
        assertEquals(3, fixture.getReportPrinter().getDiffUniqueCount());
        assertEquals(3, fixture.getReportPrinter().getTotalUniqueCount());

        // verify the cache content
        assertEquals(null, fixture.getCache().get("terminate"));
        assertEquals(1, (int) fixture.getCache().get("123456789"));
        assertEquals(1, (int) fixture.getCache().get("111111111"));
        assertEquals(1, (int) fixture.getCache().get("222222222"));
        assertEquals(null, fixture.getCache().get("3333333333333"));
        assertEquals(null, fixture.getCache().get("abcdefghi"));
        assertEquals(3, (int) fixture.getCache().size());
        verify(fixture, times(2)).terminate();
    }

}
