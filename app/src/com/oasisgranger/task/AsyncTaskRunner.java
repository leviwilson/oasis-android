package com.oasisgranger.task;

import android.os.AsyncTask;

public class AsyncTaskRunner implements TaskRunner {
	
	private class AsyncWorkItem<Parameter, Result> extends AsyncTask<Parameter, Integer, Result> {
		private final WorkItem<Parameter, Result> work;

		public AsyncWorkItem(WorkItem<Parameter, Result> work) {
			this.work = work;
		}
		
		@Override
		protected void onPreExecute() {
			work.onPreExecute();
		}

		@Override
		protected Result doInBackground(Parameter... parameters) {
			return (Result) work.doInBackground(parameters);
		}
		
		protected void onPostExecute(Result result) {
			work.onPostExecute(result);
		}
		
	}

	public <Parameter, Result> void run(WorkItem<Parameter, Result> work, Parameter... result) {
		new AsyncWorkItem<Parameter, Result>(work).execute(result);
	}

}
