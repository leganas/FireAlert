package by.legan.android.firealert.utils;

public abstract class ProgressCheck<Progress, Result> {
    public void onProgressUpdate(Progress... values){};
    public void onPostExecute(Result... values) {};
}
