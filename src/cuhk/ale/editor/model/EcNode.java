package cuhk.ale.editor.model;

import java.util.Vector;

import org.apache.log4j.Logger;

public class EcNode {
	
	static Logger logger = Logger.getLogger(EcNode.class);
	
	String name;
	EcNode parent;
	Vector<EcNode> siblings;
	String content;
	
	String type;
	String status;

	public EcNode(String name, EcNode parent, String type ) {
		super();
		this.name = name;
		this.parent = parent;		
		this.siblings = null;
		this.type = type;
		this.status = " - " ;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EcNode getParent() {
		return parent;
	}

	public void setParent(EcNode parent) {
		this.parent = parent;
	}

	public EcNode[] getSibling() {
		// logger.info("getSibling");
		
		if (this.siblings == null )
			return null;
		if (this.siblings.isEmpty())
			return null;
		EcNode[] tmp = new EcNode[1];
		return (EcNode[]) siblings.toArray(tmp);
	}

	public void addSibling(EcNode sibling) {
		logger.info("addSibling");
		
		if (this.siblings == null )
			this.siblings = new Vector<EcNode>();
		
		this.siblings.add(sibling);
		sibling.setParent(this);
	}
	
	public void removeSibling(EcNode sibling) {
		this.siblings.remove(sibling);
	}

	public void setContent(String content){
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Vector getSiblings() {
		return siblings;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}
