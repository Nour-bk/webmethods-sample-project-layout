package DEUtilCommon.pub;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.DatatypeConverter;
import com.wm.data.IData;
import com.wm.data.IDataCursor;
import com.wm.data.IDataFactory;
import com.wm.data.IDataUtil;
import com.wm.data.ValuesEmulator;
// --- <<IS-END-IMPORTS>> ---

public final class date

{
	// ---( internal utility methods )---

	final static date _instance = new date();

	static date _newInstance() { return new date(); }

	static date _cast(Object o) { return (date)o; }

	// ---( server methods )---




	public static final void calculDateTimeDifference (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(calculDateTimeDifference)>> ---
		// @sigtype java 3.5
		// [i] field:0:required firstDatetime
		// [i] field:0:required secondDatetime
		// [o] field:0:required result
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	firstDatetime = IDataUtil.getString( pipelineCursor, "firstDatetime" );
		String	secondDatetime = IDataUtil.getString( pipelineCursor, "secondDatetime" );
		pipelineCursor.destroy();
		
		String result = ""; 
		long sub = 0 ;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		try {
			Date date1 = format.parse(firstDatetime);
			Date date2 = format.parse(secondDatetime);
			 sub = date2.getTime() - date1.getTime();
			  result = Long.toString(sub);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// pipeline
		pipelineCursor = pipeline.getCursor();
		IDataUtil.put( pipelineCursor, "result", result );
		pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void compareDateTime (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(compareDateTime)>> ---
		// @sigtype java 3.5
		// [i] field:0:required firstDatetime
		// [i] field:0:required secondDatetime
		// [o] field:0:required result
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	firstDatetime = IDataUtil.getString( pipelineCursor, "firstDatetime" );
			String	secondDatetime = IDataUtil.getString( pipelineCursor, "secondDatetime" );
		pipelineCursor.destroy();
		
		String result = "";
		
		if (firstDatetime == null && secondDatetime == null) {
			// startDatetime = endDatetime
			result = "0";
			
		} else if (firstDatetime == null && secondDatetime != null) {
			// startDatetime < endDatetime
			result = "-1";
			
		} else if (firstDatetime != null && secondDatetime == null) {
			// startDatetime > endDatetime
			result = "1";
			
		} else {
		
			try {
				Calendar calendarFirst = DatatypeConverter.parseDateTime(firstDatetime);
				Calendar calendarSecond = DatatypeConverter.parseDateTime(secondDatetime);
				result = String.valueOf(calendarFirst.compareTo(calendarSecond));
				
			} catch (Exception e) {
				throw new ServiceException(e);
			}
			
		}
		// pipeline
		pipelineCursor = pipeline.getCursor();
		IDataUtil.put( pipelineCursor, "result", result );
		pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void getCurrentDateString (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getCurrentDateString)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required pattern
		// [i] field:0:optional timezone
		// [i] field:0:optional locale
		// [o] field:0:required value
		IDataCursor idcPipeline = pipeline.getCursor();
		try
		{
		  String inPattern = "";
		  if (idcPipeline.first("pattern"))
		  {
		    inPattern = (String) idcPipeline.getValue();
		  }
		  else
		  {
		    throw new ServiceException("Input parameter 'pattern' was not found.");
		  }
		
		  IData output = Service.doInvoke("pub.date", "getCurrentDateString", pipeline);
		  IDataCursor outCur = output.getCursor();
		
		  String stDate = IDataUtil.getString(outCur, "value");
		  IDataUtil.remove(outCur, "value");
		
		  outCur.destroy();
		
		  if (inPattern.endsWith("Z"))
		  {
		    Matcher matcher = specialTimezonePattern.matcher(stDate);
		    if (matcher.find())
		    {
		      stDate = matcher.replaceAll(":" + matcher.group(1));
		    }
		  }
		
		  idcPipeline.insertAfter("value", stDate);
		}
		catch (Exception e)
		{
		  throw new ServiceException(e.getMessage());
		}
		
		idcPipeline.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void incrementDate (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(incrementDate)>> ---
		// @sigtype java 3.5
		// [i] field:0:required startingDate
		// [i] field:0:required startingDateFormat
		// [i] field:0:required endingDateFormat
		// [i] field:0:optional addYears
		// [i] field:0:optional addMonths
		// [i] field:0:optional addDays
		// [i] field:0:optional addHours
		// [i] field:0:optional addMinutes
		// [i] field:0:optional addSeconds
		// [o] field:0:required endingDate
		IDataCursor idcPipeline = pipeline.getCursor();
		
		String strStartingDate = null;
		if (idcPipeline.first("startingDate"))
		{
			strStartingDate = (String)idcPipeline.getValue();
		}
		else
		{
			throw new ServiceException("startingDate must be supplied!");
		}
		String strStartingDateFormat = null;
		if (idcPipeline.first("startingDateFormat"))
		{
			strStartingDateFormat = (String)idcPipeline.getValue();
		}
		else
		{
			throw new ServiceException("startingDateFormat must be supplied!");
		}
		String strEndingDateFormat = null;
		if (idcPipeline.first("endingDateFormat"))
		{
			strEndingDateFormat = (String)idcPipeline.getValue();
		}
		else
		{
			throw new ServiceException("endingDateFormat must be supplied!");
		}
		
		String strAddYears = null;
		String strAddMonths = null;
		String strAddDays = null;
		String strAddHours = null;
		String strAddMinutes = null;
		String strAddSeconds = null;
		
		if (idcPipeline.first("addYears"))
		{
			strAddYears = (String)idcPipeline.getValue();
		}
		if (idcPipeline.first("addMonths"))
		{
			strAddMonths = (String)idcPipeline.getValue();
		}
		if (idcPipeline.first("addDays"))
		{
			strAddDays = (String)idcPipeline.getValue();
		}
		if (idcPipeline.first("addHours"))
		{
			strAddHours = (String)idcPipeline.getValue();
		}
		if (idcPipeline.first("addMinutes"))
		{
			strAddMinutes = (String)idcPipeline.getValue();
		}
		if (idcPipeline.first("addSeconds"))
		{
			strAddSeconds = (String)idcPipeline.getValue();
		}
		
		SimpleDateFormat ssdf = new SimpleDateFormat(strStartingDateFormat);
		
		Date startingDate = null;
		try
		{
			startingDate = ssdf.parse(strStartingDate);
		}
		catch (Exception e)
		{
			throw new ServiceException(e.toString());
		}
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(startingDate);
		
		if (strAddYears != null)
		{
			gc.add(Calendar.YEAR, Integer.parseInt(strAddYears));
		}
		if (strAddMonths != null)
		{
			gc.add(Calendar.MONTH, Integer.parseInt(strAddMonths));
		}
		if (strAddDays != null)
		{
			gc.add(Calendar.DAY_OF_MONTH, Integer.parseInt(strAddDays));
		}
		if (strAddHours != null)
		{
			gc.add(Calendar.HOUR_OF_DAY, Integer.parseInt(strAddHours));
		}
		if (strAddMinutes != null)
		{
			gc.add(Calendar.MINUTE, Integer.parseInt(strAddMinutes));
		}
		if (strAddSeconds != null)
		{
			gc.add(Calendar.SECOND, Integer.parseInt(strAddSeconds));
		}
		
		Date endingDate = gc.getTime();
		SimpleDateFormat esdf = new SimpleDateFormat(strEndingDateFormat);
		String strEndingDate = esdf.format(endingDate);
		
		IDataUtil.put( idcPipeline,"endingDate", strEndingDate);
		idcPipeline.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void isDate (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(isDate)>> ---
		// @sigtype java 3.5
		// [i] field:0:required input
		// [i] field:0:required dateFormat
		// [o] field:0:required isDate

	IDataCursor pipelineCursor = pipeline.getCursor();
	pipelineCursor.first( "input" );
	String input = (String)pipelineCursor.getValue();
	pipelineCursor.first( "dateFormat" );
	String dateFormat = (String)pipelineCursor.getValue();

	String isDate = "true";

	try
	{
		// Format the current time.
		SimpleDateFormat sdf;
		if (dateFormat == null || dateFormat.equals(""))
		{
			sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		}
		else
		{
			sdf = new SimpleDateFormat (dateFormat);
		}		
		sdf.setLenient(false);

		Date parsedDate = sdf.parse(input);
	//PRA 04/04/2011
		String testDate = sdf.format(parsedDate);
		//on compare la date test avec celle de la source
 		if(testDate.compareTo(input) != 0)
          		isDate = "false";
	//FIN PRA 04/04/2011
	}
	catch (Exception e)
	{
		isDate = "false";
	}

	IDataUtil.put( pipelineCursor,"isDate", isDate);
	pipelineCursor.destroy();

		// --- <<IS-END>> ---

                
	}



	public static final void validateDateFormat (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(validateDateFormat)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:optional date
		// [i] field:0:required pattern
		// [i] field:0:optional locale
		// [i] field:0:optional isRequired {"true","false"}
		// [o] field:0:required isValid
		IDataCursor idc = pipeline.getCursor();
		
		String date	= IDataUtil.getString(idc,"date");
		String pattern	= IDataUtil.getString(idc,"pattern");
		String locale	= IDataUtil.getString(idc,"locale");
		String required	= IDataUtil.getString(idc,"isRequired");
		
		Locale javaLocale = null;
		
		if (date == null) date = "";
		if (required == null) required = "true";
		if(locale==null){ javaLocale = new Locale("fr");
		}else{	javaLocale = new Locale(locale);  }
		
		date = date.trim();
		if(date.length()==0){
			if (required.equals("true")) {
				IDataUtil.put(idc,"isValid","false");	
				idc.destroy();
				return;
		//		throw new ServiceException ("[CommonServices.date:validateDateFormat] Input date must be specified");
			} else {
				IDataUtil.put(idc,"isValid","true");	
				idc.destroy();
				return;
			}
		}
		if(pattern==null)
			throw new ServiceException ("[CommonServices.date:validateDateFormat] Input pattern must be specified");
		
		String validate = "true";
		Date javaDate 	= null;
		
		DateFormat dateFormat 	= new java.text.SimpleDateFormat(pattern,javaLocale);
		
		try{
			javaDate = dateFormat.parse(date);
		}catch(Throwable th){
			validate = "false"; 
		}
		
		if(validate == "true" && ! date.equals(dateFormat.format(javaDate)) )
			validate = "false";
		
		IDataUtil.put(idc,"isValid",validate);
		idc.destroy();
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	private static Pattern specialTimezonePattern = Pattern.compile(":?(\\d{2})$");
	
	public static final String DEFAULT_PATTERN = "yyyy/MM/dd HH:mm:ss.S";
	
	public static Date stringToDate(String sDate, String pattern) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
	 	return formatter.parse(sDate);
	}
	
	public static Timestamp dateTosqlTimeStamp(Date date){
		return new Timestamp(date.getTime());
	}
	
	private static boolean compareDateStrings(String value1, String value2, String operator, String pattern)
	{
	  if (value1 == null || value2 == null)
	  {
	    return false;
	  }
	
	  String datePattern = pattern;
	
	  if (pattern == null)
	  {
	    datePattern = "yyyy-MM-dd HH:mm:ss.S";
	  }
	  long time1;
	  long time2;
	
	  DateFormat dateFormat = new SimpleDateFormat(datePattern);
	  try
	  {
	    Date date1 = dateFormat.parse(value1);
	    Date date2 = dateFormat.parse(value2);
	    time1 = date1.getTime();
	    time2 = date2.getTime();
	  }
	  catch (ParseException pe)
	  {
	    return false;
	  }
	
	  if ("=".equals(operator))
	  {
	    return time1 == time2;
	  }
	  else if (">".equals(operator))
	  {
	    return time1 > time2;
	  }
	  else if ("<".equals(operator))
	  {
	    return time1 < time2;
	  }
	  else if (">=".equals(operator))
	  {
	    return time1 >= time2;
	  }
	  else if ("<=".equals(operator))
	  {
	    return time1 <= time2;
	  }
	  return false;
	}
		
	// --- <<IS-END-SHARED>> ---
}

