package cmput301w16t08.scaling_pancake.util;

/**
 * <code>PrePostActionWrapper</code> allows views to pass in their own specific methods
 * to be called during the <code>onPreExecute()</code> and <code>onPostExecute()</code> calls
 * of the <code>SearchInstrumentTask</code> in <code>ElasticSearchController</code>. This is
 * necessary for ensuring a smooth UI experience during interaction with the server, for example
 * it allows a progress bar to be displayed while the view is awaiting results.
 *
 * @author dan
 * @see cmput301w16t08.scaling_pancake.controllers.ElasticsearchController.SearchInstrumentsTask
 * @see cmput301w16t08.scaling_pancake.activities.DisplaySearchResultsActivity
 */
public abstract class PrePostActionWrapper
{
    public abstract void preAction();
    public abstract void postAction();
}
