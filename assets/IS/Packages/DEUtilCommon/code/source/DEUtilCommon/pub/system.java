package DEUtilCommon.pub;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import com.wm.data.IData;
import com.wm.data.IDataCursor;
import com.wm.data.IDataUtil;
import com.wm.lang.ns.NSService;
// --- <<IS-END-IMPORTS>> ---

public final class system

{
	// ---( internal utility methods )---

	final static system _instance = new system();

	static system _newInstance() { return new system(); }

	static system _cast(Object o) { return (system)o; }

	// ---( server methods )---




	public static final void getServiceName (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getServiceName)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [o] field:0:required serviceName
		IDataCursor cursor = pipeline.getCursor();
		NSService nsService = Service.getCallingService();
		if (nsService != null)
		{
		  IDataUtil.put(cursor, "serviceName", nsService.toString());
		}
		cursor.destroy();
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	static Reader getReader(IDataCursor cursor, String key)
	{
	  Reader reader = null;
	  if (cursor.first(key))
	  {
	    Object o = cursor.getValue();
	    if (o == null)
	    {
	      reader = new StringReader("");
	    }
	    else if (o instanceof BufferedReader)
	    {
	      reader = (BufferedReader) o;
	    }
	    else if (o instanceof Reader)
	    {
	      reader = new BufferedReader((Reader) o);
	    }
	    else if (o instanceof InputStream)
	    {
	      reader = new BufferedReader(new InputStreamReader((InputStream) o));
	    }
	    else if (o instanceof String)
	    {
	      reader = new BufferedReader(new StringReader((String) o));
	    }
	    else if (o instanceof byte[])
	    {
	      reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream((byte[]) o)));
	    }
	  }
	  return reader;
	
	}
	
	static Writer getWriter(IDataCursor cursor, String key)
	{
	  Writer writer = new StringWriter();
	  if (cursor.first(key))
	  {
	    Object o = cursor.getValue();
	    if (o == null)
	    {
	      return writer;
	    }
	    else if (o instanceof BufferedWriter)
	    {
	      writer = (BufferedWriter) o;
	    }
	    else if (o instanceof Writer)
	    {
	      writer = new BufferedWriter((Writer) o);
	    }
	    else if (o instanceof OutputStream)
	    {
	      writer = new BufferedWriter(new OutputStreamWriter((OutputStream) o));
	    }
	  }
	  return writer;
	
	}
		
	// --- <<IS-END-SHARED>> ---
}

