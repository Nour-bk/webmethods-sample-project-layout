package DEUtilCommon.pub;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.Arrays;
import com.wm.data.IData;
import com.wm.data.ValuesEmulator;
import com.wm.util.List;
import java.util.*;
// --- <<IS-END-IMPORTS>> ---

public final class list

{
	// ---( internal utility methods )---

	final static list _instance = new list();

	static list _newInstance() { return new list(); }

	static list _cast(Object o) { return (list)o; }

	// ---( server methods )---




	public static final void addObjectToList (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(addObjectToList)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] object:0:required list
		// [i] record:0:required document
		// [o] object:0:required list
		List l = (List) ValuesEmulator.get(pipeline, "list");
		Object o = ValuesEmulator.get(pipeline, "document");
		if (o!=null) {
			if (l==null) {
				l = new List(100); // default list size
			}
			l.addElement(o);
			ValuesEmulator.put(pipeline, "list", l);
		}			
		// --- <<IS-END>> ---

                
	}



	public static final void addToList (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(addToList)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] record:1:required documentList
		// [i] record:0:required document
		// [o] record:1:required documentList
		IDataCursor cursor = pipeline.getCursor();
		IData document = IDataUtil.getIData(cursor,"document");
		if (document!=null) {
			IData[] documentList = IDataUtil.getIDataArray(cursor,"documentList");
			int docListLength=0;
			if (documentList!=null) {
				docListLength = documentList.length;
			}
			IData[] docList = new IData[docListLength+1];
			for (int i=0;i<docListLength;i++) {
				docList[i]=documentList[i];
			}
			docList[docListLength]=document;
			documentList=docList;
			IDataUtil.put(cursor, "documentList", documentList);
		}
		cursor.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void createList (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(createList)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:optional initialListSize
		// [o] object:0:required list
		String initialListSize = (String) ValuesEmulator.get(pipeline, "initialListSize");
		List l = null;
		if (initialListSize == null) {
		  l = new List(100); // default list size
		}
		else {
		  l = new List(Integer.parseInt(initialListSize));
		}
		ValuesEmulator.put(pipeline, "list", l);
		// --- <<IS-END>> ---

                
	}



	public static final void documentListToList (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(documentListToList)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] record:1:required documentList
		// [o] object:0:required list
		IDataCursor cursor = pipeline.getCursor();
		IData[] documentList = IDataUtil.getIDataArray(cursor,"documentList");
		int size=0;
		List l=null;
		if (documentList!=null) {
			size = documentList.length;
			l = new List(size);
			for (int i=0; i<size; i++)
			{
			  l.addElement(documentList[i]);
			}
		}
		ValuesEmulator.put(pipeline, "list", l);
		// --- <<IS-END>> ---

                
	}



	public static final void getDocumentListSize (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getDocumentListSize)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] record:1:required documentList
		// [o] field:0:required documentListSize
		IDataCursor idcPipeline = pipeline.getCursor();
		IData[] documentList = IDataUtil.getIDataArray(idcPipeline,"documentList");
		int size = 0;
		if (documentList!=null) {
			size=documentList.length;
		}
		IDataUtil.put(idcPipeline, "documentListSize", Integer.toString(size));
		idcPipeline.destroy();
		//ValuesEmulator.put(pipeline, "documentListSize", Integer.toString(size));
		// --- <<IS-END>> ---

                
	}



	public static final void getListSize (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getListSize)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] object:0:required list
		// [o] field:0:required listSize
		List l = (List) ValuesEmulator.get(pipeline, "list");
		int size = l.size();
		ValuesEmulator.put(pipeline, "listSize", Integer.toString(size));
		// --- <<IS-END>> ---

                
	}



	public static final void isInStringList (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(isInStringList)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required item
		// [i] field:1:required list
		// [o] field:0:required result
		try{
			IDataCursor cursor = pipeline.getCursor();
			String[] list = IDataUtil.getStringArray(cursor,"list");
			String item = IDataUtil.getString(cursor,"item");
			boolean resultat = false;
			
			/*Parcours de la liste et v\u00E9rification de la pr\u00E9sence d'"item" dans "list"*/
			if(list != null){
				for(int i = 0; i < list.length && !resultat; i++){
					resultat = list[i].equals(item);
				}
			}
		
			/*Renseignement du r\u00E9sultat dans le pipeline*/
			if(resultat)
				cursor.insertAfter("result", "true");
			else
				cursor.insertAfter("result", "false");
			
			/*Destruction du curseur*/
			cursor.destroy();
		}
		catch(Exception e){
			throw new ServiceException("[AdminIS.utils:isInStringList] : " + e.toString());
		}
		// --- <<IS-END>> ---

                
	}



	public static final void listToDocumentList (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(listToDocumentList)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] object:0:required list
		// [o] record:1:required documentList
		List l = (List) ValuesEmulator.get(pipeline, "list");
		int size = l.size();
		IData[] output = new IData[size];
		for (int i = 0; i < size; i++) {
		  output[i] = (IData) l.elementAt(i);
		}
		ValuesEmulator.put(pipeline, "documentList", output);
		// --- <<IS-END>> ---

                
	}
}

