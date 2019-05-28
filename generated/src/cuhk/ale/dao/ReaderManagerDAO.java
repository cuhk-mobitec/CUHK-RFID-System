/*
 * Generated by XDoclet - Do not edit!
 */
package cuhk.ale.dao;

/**
 * Data Access Object interface for ReaderManager.
 * @xdoclet-generated at ${TODAY}
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */
public interface ReaderManagerDAO
{
    public void init();

    public void submitTags(java.lang.String readerID,java.util.List tags) throws java.lang.Exception;

    public boolean isPhysicalReaderExists(java.lang.String readerID) throws java.lang.Exception;

    public void insertPhysicalReaderInfo(java.lang.String readerID,cuhk.ale.PhysicalReaderInfo physicalReaderInfo) throws java.lang.Exception;

    public void updatePhysicalReaderInfo(java.lang.String readerID,cuhk.ale.PhysicalReaderInfo physicalReaderInfo) throws java.lang.Exception;

    public java.util.List retrieveLogicalReaderNames() throws java.lang.Exception;

    public java.util.List retrieveReaderNamesFromTag(java.lang.String tag) throws java.lang.Exception;

}