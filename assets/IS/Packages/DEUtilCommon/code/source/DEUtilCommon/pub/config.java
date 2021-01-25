package DEUtilCommon.pub;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.lang.ns.NSService;
import java.io.File;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
// --- <<IS-END-IMPORTS>> ---

public final class config

{
	// ---( internal utility methods )---

	final static config _instance = new config();

	static config _newInstance() { return new config(); }

	static config _cast(Object o) { return (config)o; }

	// ---( server methods )---




	public static final void getConfigFilename (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getConfigFilename)>> ---
		// @sigtype java 3.5
		// [i] field:0:required packageName
		// [i] field:0:optional configFilename
		// [o] field:0:required configFile
		IDataCursor c = pipeline.getCursor();
		
		try {
		
			String	packageName = IDataUtil.getString( c, "packageName" );
			String	configFilename = IDataUtil.getString( c, "configFilename" );
		
		    String configFile = "packages" + File.separator + packageName + File.separator + "config" 
								+ File.separator + configFilename + ".properties";
		
			IDataUtil.put(c, "configFile", configFile);
		
		} catch (Exception e) {
		
			c.destroy();
			throw new ServiceException(e.getMessage());
		
		}
		
		c.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void getPackageName (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getPackageName)>> ---
		// @sigtype java 3.5
		// [o] field:0:required packageName
		//pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		
		NSService myService = (NSService) Service.getCallingService();
		String serviceName =  myService.getNSName().getFullName();
		
		
		String dirName = (serviceName.split("\\."))[0]; 
		
		// pipeline out
		IDataUtil.put(pipelineCursor, "packageName", dirName);
		
		pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void parseConfigFilename (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(parseConfigFilename)>> ---
		// @sigtype java 3.5
		// [i] field:0:required configFile
		// [o] record:1:required properties
		// [o] - field:0:required key
		// [o] - field:0:required value
		try {
		
		IDataCursor pipelineCursor = pipeline.getCursor();
		
			String	configFile = IDataUtil.getString( pipelineCursor, "configFile" );
			
		if ( (configFile==null) || (configFile.equals("")) ) configFile = "Constants.properties";
		
			java.util.Vector<String> paramKey = new java.util.Vector<String>();
			java.util.Vector<String> paramValue = new java.util.Vector<String>();
		
			String line = "";
			String lineComment = "";
			java.io.FileReader fileR = new java.io.FileReader(configFile);
			java.io.LineNumberReader lineR = new java.io.LineNumberReader(fileR);
			boolean b = true;
		
			while ( b ) {
		
				line = lineR.readLine();
				
		
				if ( line == null ) {
					b = false;
				}
		
				if ( line != null && !line.equals("") && !line.equals("\n") && !line.equals("\r")) {
					if ( line.startsWith("#")  ) {
						if ( line.startsWith("#1") || line.startsWith("#2") || line.startsWith("#3") ) {
							paramKey.add("");
							String value = "" + line.substring(3,line.length()-1);
							value = replace(value, "'", "&acute;");
							paramValue.add(value);
		
						} else {
							if (line.startsWith("#C")) {
								lineComment = line.substring(3, line.length()-1);
								line = lineR.readLine();
								java.util.StringTokenizer stLine = new java.util.StringTokenizer(line, "=");
								if (stLine.hasMoreTokens()) {
									paramKey.add("" + stLine.nextToken());
								} else {
									paramKey.add("");
								}
								if (stLine.hasMoreTokens()) {
									paramValue.add(stLine.nextToken());
								} else {
									paramValue.add("");
								}
							}
						}
					} else {
						int t = line.indexOf("=");
						
						paramKey.add(line.substring(0,t));
						String value = line.substring(t+1,line.length());
						value = replace(value, "\"", "&quot;");
						paramValue.add(value);
		
					}
				}
		    }
		
			lineR.close();
			fileR.close();
		
			int N = paramKey.size();
		
			IData[]	parameters = new IData[N];
			for (int k=0; k<N; k++) {
		
				parameters[k] = IDataFactory.create();
				IDataCursor parametersCursor = parameters[k].getCursor();
					IDataUtil.put( parametersCursor, "key", paramKey.get(k) );
					IDataUtil.put( parametersCursor, "value", paramValue.get(k) );
		
				parametersCursor.destroy();
		
			}
			IDataUtil.put( pipelineCursor, "properties", parameters );
		
		pipelineCursor.destroy();
		
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
			
		// --- <<IS-END>> ---

                
	}



	public static final void readPropertyFile (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(readPropertyFile)>> ---
		// @sigtype java 3.5
		// [i] field:0:required filename
		// [i] field:0:required packageName
		// [o] record:1:required properties
		// [o] - field:0:required key
		// [o] - field:0:required value
		IDataCursor idcPipeline = pipeline.getCursor();
		String strFilename = null;
		String packageName=IDataUtil.getString(idcPipeline,"packageName");
		
		if (idcPipeline.first("filename"))
		{
			strFilename = (String)idcPipeline.getValue();
		}
		else
		{
			throw new ServiceException("Could not read Properties file.  filename is null!");
		}
		
		try
		{
			File file = new File("packages/"+packageName+"/config/"+strFilename+".properties");
			if (file.canRead())
			{
				Properties property = new Properties();
				InputStream stream = new java.io.BufferedInputStream(new java.io.FileInputStream(file));
		
				try
				{
					property.load(stream);
				}
				finally
				{
					stream.close();
				}
		
				Enumeration<?> enumProp = property.propertyNames();
				IData[]	parameters = new IData[property.size()];
				int i=0;
				while (enumProp.hasMoreElements())
				{
					String strPropertyName = (String)enumProp.nextElement();
					String strPropertyValue = property.getProperty(strPropertyName);
		
					parameters[i] = IDataFactory.create();
					IDataCursor parametersCursor = parameters[i].getCursor();
						IDataUtil.put( parametersCursor, "key", strPropertyName );
						IDataUtil.put( parametersCursor, "value", strPropertyValue );
			
					parametersCursor.destroy();
					
					i++;
				}
		
				// Put entire property object into the output pipeline
				IDataUtil.put( idcPipeline,"properties", parameters);
			}
			else
			{
				throw new ServiceException("Could not read properties file: " +packageName+"/config/"+ strFilename);
			}
		}
		catch( Exception e )
		{
			throw new ServiceException("Error reading properties file: " + e);
		}
		finally
		{
			idcPipeline.destroy();
		}
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	public static String replace(String chaine, String oldChar, String newChar) throws Exception {
		
		String rtnString = chaine;
		java.util.StringTokenizer stChaine = new java.util.StringTokenizer(chaine, oldChar);
		if (stChaine.hasMoreTokens()) {
			rtnString = stChaine.nextToken();
		}
		while (stChaine.hasMoreTokens()) {
			rtnString += newChar + stChaine.nextToken();
		}
	
		return rtnString;
	}	
		
	// --- <<IS-END-SHARED>> ---
}

