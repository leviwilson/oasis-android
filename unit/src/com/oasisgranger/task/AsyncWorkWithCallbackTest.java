package com.oasisgranger.task; 
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.oasisgranger.task.AsyncWorkWithCallback.Callback;

public class AsyncWorkWithCallbackTest {
    
    @Mock Callback<Integer> callback;
    @Mock Callback<Integer> otherCallback;
    
    private AsyncWorkWithCallback<Void, Integer> asyncWork;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        asyncWork = new AsyncWorkWithCallback<Void, Integer>(callback);
    }
    
    @Test
    public void itNotifiesTheCallerBeforeExecuting() {
        asyncWork.onPreExecute();
        
        verify(callback).onPreExecute();
    }
    
    @Test
    public void itNotifiesTheCallerAfterExecuting() {
        asyncWork.onPostExecute(42);
        
        verify(callback).onPostExecute(42);
    }
    
    @Test
    public void isConsideredExecutingWhenWorkIsStarted() {
        asyncWork.onPreExecute();
        
        assertThat(asyncWork.isWorking(), is(true));
    }
    
    @Test
    public void isConsideredNotBeExcecutingIfWorkIsStopped() {
        asyncWork.onPreExecute();
        
        asyncWork.onPostExecute(0);
        
        assertThat(asyncWork.isWorking(), is(false));
    }
    
    @Test
    public void itDoesNotNotifyTheCallerAfterExecutingIfItIsDetached() {
        asyncWork.detach();
        
        asyncWork.onPostExecute(42);
        
        verify(callback, times(0)).onPostExecute(any(Integer.class));
    }
    
    @Test
    public void itNotifiesNewCallersOfTheLastResultWhenTheyAttach() {
        asyncWork.detach();
        asyncWork.onPostExecute(42);
        
        asyncWork.attach(callback);
        
        verify(callback).onPostExecute(42);
    }
    
    @Test
    public void itDoesNotNotifyTheCallerOfTheLastResultIfNonExists() {
        asyncWork.attach(callback);
        
        verify(callback, times(0)).onPostExecute(any(Integer.class));
    }
    
    @Test
    public void itIsAwareOfNewStreamsOfWork() {
        finishWorkItemAndStartAnother();
        
        asyncWork.attach(callback);
        
        verify(callback, times(0)).onPostExecute(any(Integer.class));
    }

    private void finishWorkItemAndStartAnother() {
        asyncWork.detach();
        asyncWork.onPostExecute(42);
        asyncWork.onPreExecute();
    }

}
