package com.wdl.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

public class ExceptionToStringUtil {
	private static final String CAUSE_CAPTION = "Caused by: ";
    private static final String SUPPRESSED_CAPTION = "Suppressed: ";
    public static String toStringStackTrace(Throwable t){
		StringBuilder sb=new StringBuilder();
		String ip=null;
		String name=null;
		try {
			name = InetAddress.getLocalHost().getHostName();          
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			ip="unknowIP";
			name="unknowHostName";
		} 
		sb.append("ip:"+ip+"\n");
		sb.append("hostName:"+name+"\n");
		Set<Throwable> dejaVu =
	            Collections.newSetFromMap(new IdentityHashMap<Throwable, Boolean>());
        dejaVu.add(t);
        sb.append(t+"\n");
        StackTraceElement[] trace = t.getStackTrace();
        for (StackTraceElement traceElement : trace)
            sb.append("\tat " + traceElement+"\n");
        for (Throwable se : t.getSuppressed())
            appendEncolosedStackTrace(se, sb, trace, SUPPRESSED_CAPTION, "\t", dejaVu);
        Throwable ourCause = t.getCause();
        if (ourCause != null)
        	appendEncolosedStackTrace(ourCause, sb, trace, CAUSE_CAPTION, "", dejaVu);
		return sb.toString();
	}
	private static void appendEncolosedStackTrace(Throwable e,StringBuilder sb,
			StackTraceElement[] enclosingTrace,
            String caption,
            String prefix,
            Set<Throwable> dejaVu){
		if (dejaVu.contains(e)) {
            sb.append("\t[CIRCULAR REFERENCE:" + e + "]"+"\n");
		}else{
            dejaVu.add(e);
            // Compute number of frames in common between this and enclosing trace
            StackTraceElement[] trace = e.getStackTrace();
            int m = trace.length - 1;
            int n = enclosingTrace.length - 1;
            while (m >= 0 && n >=0 && trace[m].equals(enclosingTrace[n])) {
                m--; n--;
            }
            int framesInCommon = trace.length - 1 - m;

            // Print our stack trace
            sb.append(prefix + caption + e);
            for (int i = 0; i <= m; i++)
            	sb.append(prefix + "\tat " + trace[i]+"\n");
            if (framesInCommon != 0)
            	sb.append(prefix + "\t... " + framesInCommon + " more"+"\n");

            // Print suppressed exceptions, if any
            for (Throwable se : e.getSuppressed())
                appendEncolosedStackTrace(se, sb, trace, SUPPRESSED_CAPTION,
                                           prefix +"\t", dejaVu);

            // Print cause, if any
            Throwable ourCause = e.getCause();
            if (ourCause != null)
            	appendEncolosedStackTrace(ourCause, sb , trace, CAUSE_CAPTION, prefix, dejaVu);
        }
	}
}
