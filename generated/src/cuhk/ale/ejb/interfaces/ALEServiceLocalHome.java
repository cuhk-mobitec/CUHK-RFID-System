/*
 * Generated by XDoclet - Do not edit!
 */
package cuhk.ale.ejb.interfaces;

/**
 * Local home interface for ALEService.
 * @xdoclet-generated at ${TODAY}
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */
public interface ALEServiceLocalHome
   extends javax.ejb.EJBLocalHome
{
   public static final String COMP_NAME="java:comp/env/ejb/ALEServiceLocal";
   public static final String JNDI_NAME="ALEServiceLocal";

   public cuhk.ale.ejb.interfaces.ALEServiceLocal create()
      throws javax.ejb.CreateException;

}
