package DEUtilCommon.pub;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.app.b2b.server.InvokeState;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import com.wm.lang.ns.NSNode;
import java.lang.Object;
import java.util.List;
// --- <<IS-END-IMPORTS>> ---

public final class utils

{
	// ---( internal utility methods )---

	final static utils _instance = new utils();

	static utils _newInstance() { return new utils(); }

	static utils _cast(Object o) { return (utils)o; }

	// ---( server methods )---




	public static final void getCorrelationID (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getCorrelationID)>> ---
		// @sigtype java 3.5
		// [o] field:0:required rootContextID
		String[] contextStack;
		String currentRootID = "";
		try {
			contextStack = InvokeState.getCurrentState().getAuditRuntime().getContextStack();
			currentRootID = contextStack[0];
			
		} catch (Exception ex) {
			ServiceException sx = new ServiceException(ex);
			throw sx;
		}
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put(pipelineCursor_1, "rootContextID", currentRootID);
		
		pipelineCursor_1.destroy(); 
			
		// --- <<IS-END>> ---

                
	}



	public static final void nodeToIData (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(nodeToIData)>> ---
		// @sigtype java 3.5
		// [i] object:0:required node
		// [o] record:0:required idata
		// pipeline in
		IDataCursor pipelineCursor = pipeline.getCursor();
		Object node = IDataUtil.get( pipelineCursor, "node" );
		pipelineCursor.destroy();
		
		NSNode nsnode = (NSNode) node;
		IData idata = nsnode.getAsData();
		
		// pipeline out
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "idata", idata );
		pipelineCursor_1.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void removeDuplicates (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(removeDuplicates)>> ---
		// @sigtype java 3.5
		// [i] field:1:required inputList
		// [o] field:1:required outputList
		IDataCursor pipelineCursor = pipeline.getCursor();
		String[]	inputList = IDataUtil.getStringArray( pipelineCursor, "inputList" );
		pipelineCursor.destroy();
		List<String> list = Arrays.asList(inputList);
		
		Set<String> set = new HashSet<>(list);
		String[] outputList = set.toArray(new String[set.size()]);		
		
		
		
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "outputList", outputList );
		pipelineCursor_1.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void threadWait (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(threadWait)>> ---
		// @sigtype java 3.5
		// [i] field:0:required delayValue
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	delayValue = IDataUtil.getString( pipelineCursor, "delayValue" );
			long time = Long.parseLong(delayValue);
			try {
			    Thread.sleep(time * 1000);
			} catch (InterruptedException ie) {
			    Thread.currentThread().interrupt();
			}
			
		pipelineCursor.destroy();
		
		// pipeline
			
		// --- <<IS-END>> ---

                
	}
}

