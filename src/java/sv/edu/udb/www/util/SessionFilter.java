/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.util;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sv.edu.udb.www.entities.Usuario;

/**
 *
 * @author alejo
 */
public class SessionFilter implements Filter {
    
    private static final boolean debug = true;

    private FilterConfig filterConfig = null;
    
    public SessionFilter() {
    }    
    
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(request, response);
        return;
        /*
        HttpServletRequest peticion = (HttpServletRequest) request;
        HttpServletResponse respuesta = (HttpServletResponse) response;
        HttpSession session = peticion.getSession(false);
        
        String loginURI = peticion.getContextPath() + "/";
        
       
        String debug = peticion.getRequestURI().substring(peticion.getContextPath().length());
        boolean loggedIn = (session != null && session.getAttribute("Session") != null);
        boolean IsLoginRequest = (peticion.getRequestURI().equals(loginURI) || peticion.getRequestURI().equals(loginURI + "index.xhtml") );
        
        if(peticion.getRequestURI().contains("javax.faces.resource")){
           chain.doFilter(request, response);
           return;
        }
        if(!loggedIn && IsLoginRequest){
            chain.doFilter(request, response);
            return;
        }
        if(!loggedIn && !IsLoginRequest){
            //JSFUtil.addErrorMessage("Debe iniciar sesion");
            respuesta.sendRedirect(loginURI);
            return;
        }

        if(loggedIn){
            Usuario u = (Usuario) session.getAttribute("Session");
            String path = peticion.getRequestURI().substring(peticion.getContextPath().length());
            
            if(path.startsWith("/Administrador/") && !u.getTipo().getTipo().equals("Administrador")){
                //JSFUtil.addErrorMessage("Debe iniciar sesion");
                respuesta.sendRedirect(loginURI);
            }else{chain.doFilter(request, response);return;}
            
            if(path.startsWith("/Encargado/") && !u.getTipo().getTipo().equals("Encargado")){
                //JSFUtil.addErrorMessage("Debe iniciar sesion");
                respuesta.sendRedirect(loginURI);
            }else{ chain.doFilter(request, response); return;}
            
            if(path.startsWith("/Dependiente/") && !u.getTipo().getTipo().equals("Dependiente")){
                //JSFUtil.addErrorMessage("Debe iniciar sesion");
                respuesta.sendRedirect(loginURI);
            }else{ chain.doFilter(request, response); return;}
            
            if(path.startsWith("/Cliente/") && !u.getTipo().getTipo().equals("Cliente")){
                //JSFUtil.addErrorMessage("Debe iniciar sesion");
                respuesta.sendRedirect(loginURI);
            }else{ chain.doFilter(request, response); return;}
        }
        */
    }

    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void destroy() {        
    }

    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {                
                log("SessionFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("SessionFilter()");
        }
        StringBuffer sb = new StringBuffer("SessionFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);        
        
        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);                
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");                
                pw.print(stackTrace);                
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }
    
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }
    
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);        
    }
    
}
