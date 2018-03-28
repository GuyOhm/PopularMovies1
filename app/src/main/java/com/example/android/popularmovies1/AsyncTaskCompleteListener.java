package com.example.android.popularmovies1;

/**
 * Callback interface for {@link android.os.AsyncTask} as per:
 * http://www.jameselsey.co.uk/blogs/techblog/extracting-out-your-asynctasks-into-separate-classes-makes-your-code-cleaner/
 */

public interface AsyncTaskCompleteListener<T> {
    public void onTaskComplete(T result);
}
