package DEUtilCommon.pub;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.text.DecimalFormat;
import java.math.*;
// --- <<IS-END-IMPORTS>> ---

public final class math

{
	// ---( internal utility methods )---

	final static math _instance = new math();

	static math _newInstance() { return new math(); }

	static math _cast(Object o) { return (math)o; }

	// ---( server methods )---




	public static final void roundNumber (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(roundNumber)>> ---
		// @sigtype java 3.5
		// [i] field:0:required number
		// [i] field:0:required precision
		// [o] field:0:required roundedNumber
		
		IDataCursor idcPipeline = pipeline.getCursor();
		if (!idcPipeline.first("number"))
		{
			throw new ServiceException("input number was null!");
		}
		
		String strNumber = (String)idcPipeline.getValue();
		double dblNumber = Double.parseDouble(strNumber);
		
		
		if (idcPipeline.first("precision")){
			String strPrecision = (String)idcPipeline.getValue();
			double dblPrecision = Double.parseDouble(strPrecision);
		
			double dblMulDivFactor = java.lang.Math.pow(10, dblPrecision);
			double dblRoundedNumber = java.lang.Math.round(dblNumber * dblMulDivFactor) / dblMulDivFactor;
			
			IDataUtil.put( idcPipeline,"roundedNumber", Double.toString(dblRoundedNumber));
		}
		else{	
			IDataUtil.put( idcPipeline,"roundedNumber", strNumber);
		
		}
		
		idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}
}

