# CUHK RFID Middleware 1.0 

--- 

A flexible and cost-effective solution for RFID network deployment and configuration. 
A product from Mobitec of IE department, CUHK. 

### Overview of CUHK RFID System 1.0 
CUHK RFID System 1.0 is a flexible and cost-effective software complying with EPCglobal middelware specifications. It follows the architecture framework specification of EPCglobal and the Application Level Events (ALE) Specification, Version 1.0. CUHK RFID System 1.0 provide a standard ALE interface for user applications to access the RFID network. The ALE interface is extended to support reading and writing of the tag memory. RFID readers can be connected to the server running this middleware through IP network and RS-232 adaptors. Through management console of CUHK RFID System 1.0, all readers in the RFID network can be configured, controlled, managed and monitored. User applications can be easily developed and integrated with the middleware system. 

### Key features of CUHK RFID System 1.0 

Standard ALE interface 

The CUHK RFID system 1.0 follows the architecture framework specification of EPCglobal and implemented an ALE engine and ALE interface as shown in Fig. 1. It provide a standard ALE interface that fully complies with the EPCglobal ALE 1.0 specification. The ALE engine in the middleware performs tag event collection, management and filtering. The ALE engine also route the tag events to subscribed user applications and generate tag reports according to the requirements and specifications of user applications. The ALE API provides a standard interface for the user application to use the tag data. It provides a means for clients to specify, in a high-level, what EPC data they are interested in, and in what format the report is wanted. For the current implementation, the ALE API is bonded to a WS-i compliant SOAP protocol. In the future, more bindings will be provided. 

![The EPCglobal Architecture Framework](http://mobitec.ie.cuhk.edu.hk/rfid/middleware/img/epc_arch.jpg)
Fig. 1 The EPCglobal Architecture Framework 

Enhancements to support accessing of tag memory and sensing functions on tag 

The ALE interface is extended to support reading and writing to tag memory. The extension follows the extension rule of EPCglobal ALE 1.0 specification. 2 functions are added in the interface: ReadData and WriteData. For requirement of sensor data, user application can call ReadData function, and to control the sensor functions in the tag, user application can call WriteData function. 

Powerful Application Level Events (ALE) processing engine 

The key functions of ALE engine include collecting, filtering, aggregating and delivering of tag events. Filters in the ALE engine help to remove duplicate tag events and events that not of interest. Filters may also contain small pieces of process or business logic. Standard filters are provided for event smoothing, event batching, event changes (tags in and out), and event pass/blocking. The ALE engine assures reliable data transmission from readers to subscriber applications. The components in the ALE engine can be shared by multiple user applications (such as Warehouse Management System (WMS), Enterprise Resources Planning System (ERP)). 

Support multiple standards of RFID tags and devices 

The CUHK RFID System 1.0 support EPC and ISO tag standards. The supported tag types include EPCglobal UHF Class 1 Gen 2 (ISO 18000-6C) as well as all earlier EPC tag types and ISO tags. This middleware also support CUHK CuTag reader and CuBadge reader. CuTag and CuBadge tags are active tags developed by CUHK EE department, aiming at supporting various kinds of sensing function. The middleware server provides an interface that enables RFID readers and other network devices or sensors to be connected to the RFID network. To integrate new devices and tags into the network, device adaptors can be easily developed to interface with the middleware. 

Device management and Centralized administration 

All devices connected to the middleware server can be managed by the management console. The console is web-based easy-to-use graphical interface for the administrator to configure the system and monitor work status of all system components. It gives a diagram view of the RFID reader network and user applications. Administrator of the RFID network can setup the RFID reader network by simply clicking the elements in the diagram. The reader can be configured and controlled, and work status can be monitored through the management console. Administrator and user applications use logical reader as a router of tag events from reader to user applications. Logical readers defined in the ALE specification can be created and revised. Mapping between readers and logical readers can be setup and revised. 

Easy Installation and Configuration 

CUHK RFID system 1.0 is designed for easy deployment and configuration. Common filters, adaptors and processing modules are already available. Management console as well as simple test modules such as Reader Emulator and Tag Viewer is also provided. A quick start guide is provided for the user to quickly understand the system configuration and management. 

System architecture and major components 

The system architecture of CUHK RFID System 1.0 is shown in Fig. 2. 

![Architecture](http://mobitec.ie.cuhk.edu.hk/rfid/middleware/img/block_diag.jpg)
Fig. 2 CUHK RFID System 1.0 Architecture 


In the middle, there is the ALE Engine Core. On the right, readers are connected to the ALE engine through adaptors. For test purpose, a Reader Emulator is provided. A SOAP ALE API is available now. User applications works with ALE engine through ALE API. For reference of development of user applications, a Tag Viewer which can perform general ECspec definition and result presentation is provided. With ALE engine, management console, Tag Viewer and Reader Rmulator, user can setup a virtual RFID network immediately and try the management of the RFID network in a quick start. All system components, readers, user applications, and ALE engine can be managed through the management console. 

Some key components of the CUHK RFID System 1.0 are: 

ALE Engine 
ALE Engine is the core component of CUHK RFID System. It performs tag event collection, filtering, aggregation and delivering according to user application requirement. It collects tag events from multiple readers and put them into a buffer for temporal storage. According to user requirements, it filters the tag event and generates reports in every event cycle. 

ALE interface module 
This interface is a standard API for user application to access tag events through middleware. User can specify what event is of interest and in what form the report is wanted. The user application can also specify a notification channel for the middleware to report in every event cycle. 

Management console 
This management console is a web-based graphical interface for system administrator to manage the RFID network. Administrator can use it to deploy the RFID network, configure reader devices and logical readers, and manage user applications, monitor the work status of the whole system. 

Device adaptors 
Device adaptors enable devices from many different manufacturers, such as RFID or bar code readers, to communicate and interact with the ALE engine core. 

Reader emulator 
For quick test of the RFID network without hardware reader devices, a reader emulator is provided. The reader cycle of the reader emulator can be specified by user. Then it generates tag events randomly or according to user’s specification. 

Tag Viewer 
Tag Viewer is a simple program that uses ALE API to view tag events of all readers in the RFID network. User can specify the interested tag events, such as addition, deletion and current event of tags, and the scope of tag ID that is of interest. The source code of tag viewer is available and can serve as a reference for user program development. 

Software and Documents 

The CUHK RFID System 1.0 is developed based on Java and J2EE technology. 

The CUHK RFID System 1.0 comes with an installation CD, in which an installation guide, quick start guide and User guide as well as Javadoc of the system is provided. 

![Management Console](http://mobitec.ie.cuhk.edu.hk/rfid/middleware/img/mgmt_con.jpg)
Fig. 3 Management Console 

![Tag Capturer](http://mobitec.ie.cuhk.edu.hk/rfid/middleware/img/tag_cap.png)
Fig. 4 Tag Capturer 

![Reader Emulator](http://mobitec.ie.cuhk.edu.hk/rfid/middleware/img/rdr_emu.jpg)
Fig. 5 Reader Emulator 

For more information, please visit [http://mobitec.ie.cuhk.edu.hk/rfid/middleware/index.htm](http://mobitec.ie.cuhk.edu.hk/rfid/middleware/index.htm)
