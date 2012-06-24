package com.oasisgranger.task;

public class AsyncWorkWithCallback<Parameter, Result> implements WorkItem<Parameter, Result> {

    private Callback<Result> callback;
    private Result lastResult;
    private boolean workCompletedInDetachedState;
    private boolean isWorking;

    public AsyncWorkWithCallback(final Callback<Result> callback) {
        this.callback = callback;
    }

    @Override
    public void onPreExecute() {
        workCompletedInDetachedState = false;
        callback.onPreExecute();
        isWorking = true;
    }

    @Override
    public Result doInBackground(Parameter... parameters) {
        throw new UnsupportedOperationException("doInBackground is required to be overridden in " + AsyncWorkWithCallback.class.getName());
    }

    public void attach(Callback<Result> callback) {
        this.callback = callback;
        ensureUnawareCallersAreNotified();
    }

    public void detach() {
        this.callback = new NullCallback();
    }

    @Override
    public void onPostExecute(Result result) {
        callback.onPostExecute(result);
        isWorking = false;
    }

    private void ensureUnawareCallersAreNotified() {
        if (workCompletedInDetachedState) {
            this.callback.onPostExecute(lastResult);
        }
    }

    private void saveResultForDetachedCallers(Result result) {
        workCompletedInDetachedState = true;
        lastResult = result;
    }
    
    private final class NullCallback implements Callback<Result>
    {
        @Override
        public void onPreExecute() {
        }

        @Override
        public void onPostExecute(Result result) {
            saveResultForDetachedCallers(result);
        }
        
    }

    public interface Callback<Result> {
        void onPreExecute();

        void onPostExecute(Result result);
    }

    public boolean isWorking() {
        return isWorking;
    }
}
