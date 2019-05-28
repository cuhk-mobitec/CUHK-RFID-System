package cuhk.junit;
 
import junit.framework.TestCase;
import cuhk.ale.ejb.interfaces.TagDataService;
import cuhk.ale.ejb.interfaces.TagDataServiceHome;
import cuhk.ale.ejb.interfaces.TagDataServiceUtil;
import cuhk.ale.reader.DataSpec;

public class TagDataServiceTestCase extends TestCase {

	public TagDataServiceTestCase() throws Exception {
		super();
	} 

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {  
		super.tearDown();   
	}

	/*
		 * Test method for 'cuhk.ale.ejb.TagDataServiceBean.testReadData(data)'
		 */
//		public final void testWriteData() throws Exception {
//			byte[] b = {0,1,2,3,4,5,6,7,8,9};		    
//	
//			DataSpec dspec = new DataSpec("urn:epc:tag:gid-96:10001.0.2", 1, 0, 10, b);
//			TagDataServiceHome TagDataServiceHome = (TagDataServiceHome) TagDataServiceUtil
//					.getHome();
//			TagDataService TagDataService = TagDataServiceHome.create();
//			int i = TagDataService.writeData(dspec);
//			System.out.println(i); 
//	
//			assert (true);
//		}

		/*  
		 * Test method for 'cuhk.ale.ejb.TagDataServiceBean.testReadData(data)'
		 */
		public final void testReadData() throws Exception {   
		
			DataSpec dspec = new DataSpec("urn:epc:tag:gid-96:10001.0.2", 1, 2, 3);
			TagDataServiceHome TagDataServiceHome = (TagDataServiceHome) TagDataServiceUtil
					.getHome();
			TagDataService TagDataService = TagDataServiceHome.create();
			byte[] b = TagDataService.readData(dspec);
			System.out.println(b[0]);

			assert (true);
		}

	// public void test() {
	// System.out.println(Grammar.RANGE_COMPONENT);
	//	
	//		
	// }
}
