package DEUtilCommon.pub;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.data.IData;
import com.wm.data.IDataCursor;
import com.wm.data.IDataUtil;
// --- <<IS-END-IMPORTS>> ---

public final class string

{
	// ---( internal utility methods )---

	final static string _instance = new string();

	static string _newInstance() { return new string(); }

	static string _cast(Object o) { return (string)o; }

	// ---( server methods )---




	public static final void concatWithSep (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(concatWithSep)>> ---
		// @sigtype java 3.5
		// [i] field:0:required inString_1
		// [i] field:0:required inString_2
		// [i] field:0:required inString_3
		// [i] field:0:required inString_4
		// [i] field:0:required inString_5
		// [i] field:1:required inStringList
		// [i] field:0:required separator
		// [i] field:0:required forceEmptyString {"false","true"}
		// [o] field:0:required value
		IDataCursor idcPipeline = pipeline.getCursor();
		
		String strSinglesOuput="",strLstOutput = "",strGlobalOutput="";
		String sep="";
		boolean forceEmptyString = false;
		
		if(idcPipeline.first("separator")){
			Object objValue = idcPipeline.getValue();
			if(objValue != null){
				sep = (String)objValue;
			}
		}
		
		if(idcPipeline.first("forceEmptyString")){
			Object objValue = idcPipeline.getValue();
			if(objValue != null){
				forceEmptyString = Boolean.getBoolean((String)objValue);
				
			}
		}
		
		
		for(int i=0;i<5;i++){
			if(idcPipeline.first("inString_"+(i+1))){
				Object objValue = idcPipeline.getValue();
				if(objValue != null){
					String locValue = (String)objValue;
					if(!locValue.equalsIgnoreCase("")){
						strSinglesOuput = strSinglesOuput + sep + locValue ;
					}
				}
			}
		}
		
		if(idcPipeline.first("inStringList")){
			Object objValue = idcPipeline.getValue();
		
			if(objValue != null){
				String[] locValue = (String[])objValue;
				if(locValue != null){
					for(int i=0;i<locValue.length;i++){
						if(locValue[i] != null && !locValue[i].equalsIgnoreCase("")){
							strLstOutput = strLstOutput + sep + locValue[i] ;
						}
					}
				}
			}
		}
		
		strGlobalOutput = strSinglesOuput + strLstOutput;
		strSinglesOuput = null;
		strLstOutput = null;
		if(!strGlobalOutput.equalsIgnoreCase("")){
			strGlobalOutput = strGlobalOutput.replaceAll("^"+Pattern.quote(sep), "");
			strGlobalOutput = strGlobalOutput.replaceAll(Pattern.quote(sep)+"$", "");
			strGlobalOutput = strGlobalOutput.replaceAll(Pattern.quote(sep+sep),sep);
		}
		
		if(!strGlobalOutput.equalsIgnoreCase("")){
			idcPipeline.insertAfter("value", strGlobalOutput);
		}
		strGlobalOutput = null;
		sep = null;
		// --- <<IS-END>> ---

                
	}
}

