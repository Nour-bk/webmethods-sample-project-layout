package DEUtilCommon.pub;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.lang.xml.XmlUtil;
import java.io.InputStream;
import java.io.StringWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
// --- <<IS-END-IMPORTS>> ---

public final class xml

{
	// ---( internal utility methods )---

	final static xml _instance = new xml();

	static xml _newInstance() { return new xml(); }

	static xml _cast(Object o) { return (xml)o; }

	// ---( server methods )---




	public static final void removeAllNamespaces (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(removeAllNamespaces)>> ---
		// @sigtype java 3.5
		// [i] field:0:required inString
		// [o] field:0:required outString
		// pipeline
		try {
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	inString = IDataUtil.getString( pipelineCursor, "inString" );
		pipelineCursor.destroy();
		Source xmlSource = new StreamSource(inString);
		StringWriter outWriter = new StringWriter();
		Result result = new StreamResult( outWriter );
		
		TransformerFactory factory = TransformerFactory.newInstance();
		InputStream xslt = XmlUtil.class.getResourceAsStream("removeNs.xslt");
		Transformer transformer = factory.newTransformer(new StreamSource(xslt));
		transformer.transform(xmlSource, result);
		StringBuffer sb = outWriter.getBuffer(); 
		String finalstring = sb.toString();
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "outString", finalstring);
		pipelineCursor_1.destroy();
		
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}			
		// --- <<IS-END>> ---

                
	}
}

