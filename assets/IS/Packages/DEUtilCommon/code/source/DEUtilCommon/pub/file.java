package DEUtilCommon.pub;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.io.*;
import java.lang.SecurityException;
import java.math.BigInteger;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import com.wm.lang.ns.*;
// --- <<IS-END-IMPORTS>> ---

public final class file

{
	// ---( internal utility methods )---

	final static file _instance = new file();

	static file _newInstance() { return new file(); }

	static file _cast(Object o) { return (file)o; }

	// ---( server methods )---




	public static final void listFiles (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(listFiles)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required directory
		// [i] field:0:optional extension
		// [o] field:1:required fileList
		// [o] field:1:required fileList_fullpathnames
		// [o] field:0:required numFiles
	IDataCursor idcPipeline = pipeline.getCursor();

	String directory = null;
	String extension=IDataUtil.getString(idcPipeline, "extension");
	if (idcPipeline.first("directory"))
	{
		directory = (String)idcPipeline.getValue();
	}
	else
	{
		throw new ServiceException("Directory is null!");
	}

	String strFileSeparator = System.getProperty("file.separator");
	int numFiles = 0;
	
	// *** Check if path is on the allowed list ***
	/*
	try
	{
		if (!checkPathValidity(directory, "read"))
		{
			throw new ServiceException("Specified path is not on the read allowed list in the PSUtilities configuration file!");
		}
	}
	catch (Exception e)
	{
		throw new ServiceException(e.getMessage());
	}
	*/
	// *** End check ***

	File dir = new File(directory);

	if (!dir.exists() || !dir.isDirectory())
	{
		throw new ServiceException("Error reading inbox directory!");
	}

	String dir_list[];
	
	if (extension==null || extension=="")
		dir_list = dir.list();
	else dir_list= dir.list((d, name) -> name.endsWith(extension));
	int num_filesanddir = dir_list.length;

	if (num_filesanddir < 1)		//No files to send
	{
		IDataUtil.put( idcPipeline,"numFiles", Integer.toString(numFiles));
		return;
	}

	//Determine number of actual files
	File curr_file;
	for (int i = 0; i < num_filesanddir; i++)
	{
		curr_file = new File(directory + strFileSeparator + dir_list[i]);
		if (curr_file.isFile())
		{
			numFiles++;
		}
	}
	curr_file = null;
 
	//Create array with only names of files (excluding directories)
	String [] fileList = new String[numFiles]; 
	String [] fileList_fullpathnames = new String[numFiles]; 
	numFiles = 0;
	for (int i = 0; i < num_filesanddir; i++)
	{
		curr_file = new File(directory + strFileSeparator + dir_list[i]);
		if (curr_file.isFile())
		{
			fileList[numFiles] = dir_list[i];

			// Assemble fully qualified filenames (check UNIX & NT)
			char lastChar = directory.charAt(directory.length() - 1);
			if ((lastChar == '\\') || (lastChar == '/'))
			{
				fileList_fullpathnames[numFiles] = directory + dir_list[i];
			}
			else
			{
				fileList_fullpathnames[numFiles] = directory + strFileSeparator + dir_list[i];
			}
			numFiles++;
		}
	}
	curr_file = null;

	IDataUtil.put( idcPipeline,"numFiles", Integer.toString(numFiles));
	IDataUtil.put( idcPipeline,"fileList", fileList);
	IDataUtil.put( idcPipeline,"fileList_fullpathnames", fileList_fullpathnames);
	idcPipeline.destroy();
	
		// --- <<IS-END>> ---

                
	}



	public static final void writeFile (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(writeFile)>> ---
		// @sigtype java 3.5
		// [i] field:0:required content
		// [i] field:0:required path
		// [i] field:0:required fileName
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	content = IDataUtil.getString( pipelineCursor, "content" );
		String	path = IDataUtil.getString( pipelineCursor, "path" );
		String	fileName = IDataUtil.getString( pipelineCursor, "fileName" );
		
		
		File directory = new File(path);
		if (!directory.exists()) directory.mkdirs();
		
		File file = new File(directory.getAbsolutePath() + "/" + fileName);
		
		          try {
		              FileWriter fw = new FileWriter(file.getAbsoluteFile());
		              BufferedWriter bw = new BufferedWriter(fw);
		              bw.write(content);
		              bw.newLine();
		              bw.close();
		          }
		          catch (IOException e){
		              e.printStackTrace();
		              
		          }
		
		pipelineCursor.destroy();
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	/**
	 * The number of bytes in a kilobyte.
	 */
	public static final long ONE_KB = 1024;
	public static final BigInteger ONE_KB_BI = BigInteger.valueOf(ONE_KB);
	public static final long ONE_MB = ONE_KB * ONE_KB;
		
	// --- <<IS-END-SHARED>> ---
}

