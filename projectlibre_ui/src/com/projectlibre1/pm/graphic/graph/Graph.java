/*******************************************************************************
 * The contents of this file are subject to the Common Public Attribution License 
 * Version 1.0 (the "License"); you may not use this file except in compliance with 
 * the License. You may obtain a copy of the License at 
 * http://www.projectlibre.com/license . The License is based on the Mozilla Public 
 * License Version 1.1 but Sections 14 and 15 have been added to cover use of 
 * software over a computer network and provide for limited attribution for the 
 * Original Developer. In addition, Exhibit A has been modified to be consistent 
 * with Exhibit B. 
 *
 * Software distributed under the License is distributed on an "AS IS" basis, 
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for the 
 * specific language governing rights and limitations under the License. The 
 * Original Code is ProjectLibre. The Original Developer is the Initial Developer 
 * and is ProjectLibre Inc. All portions of the code written by ProjectLibre are 
 * Copyright (c) 2012-2019. All Rights Reserved. All portions of the code written by 
 * ProjectLibre are Copyright (c) 2012-2019. All Rights Reserved. Contributor 
 * ProjectLibre, Inc.
 *
 * Alternatively, the contents of this file may be used under the terms of the 
 * ProjectLibre End-User License Agreement (the ProjectLibre License) in which case 
 * the provisions of the ProjectLibre License are applicable instead of those above. 
 * If you wish to allow use of your version of this file only under the terms of the 
 * ProjectLibre License and not to allow others to use your version of this file 
 * under the CPAL, indicate your decision by deleting the provisions above and 
 * replace them with the notice and other provisions required by the ProjectLibre 
 * License. If you do not delete the provisions above, a recipient may use your 
 * version of this file under either the CPAL or the ProjectLibre Licenses. 
 *
 *
 * [NOTE: The text of this Exhibit A may differ slightly from the text of the notices 
 * in the Source Code files of the Original Code. You should use the text of this 
 * Exhibit A rather than the text found in the Original Code Source Code for Your 
 * Modifications.] 
 *
 * EXHIBIT B. Attribution Information for ProjectLibre required
 *
 * Attribution Copyright Notice: Copyright (c) 2012-2019, ProjectLibre, Inc.
 * Attribution Phrase (not exceeding 10 words): 
 * ProjectLibre, open source project management software.
 * Attribution URL: http://www.projectlibre.com
 * Graphic Image as provided in the Covered Code as file: projectlibre-logo.png with 
 * alternatives listed on http://www.projectlibre.com/logo 
 *
 * Display of Attribution Information is required in Larger Works which are defined 
 * in the CPAL as a work which combines Covered Code or portions thereof with code 
 * not governed by the terms of the CPAL. However, in addition to the other notice 
 * obligations, all copies of the Covered Code in Executable and Source Code form 
 * distributed must, as a form of attribution of the original author, include on 
 * each user interface screen the "ProjectLibre" logo visible to all users. 
 * The ProjectLibre logo should be located horizontally aligned with the menu bar 
 * and left justified on the top left of the screen adjacent to the File menu. The 
 * logo must be at least 144 x 31 pixels. When users click on the "ProjectLibre" 
 * logo it must direct them back to http://www.projectlibre.com. 
 *******************************************************************************/
package com.projectlibre1.pm.graphic.graph;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.ToolTipManager;

import com.projectlibre1.pm.graphic.graph.event.GraphEvent;
import com.projectlibre1.pm.graphic.graph.event.GraphListener;
import com.projectlibre1.pm.graphic.model.cache.NodeModelCache;
import com.projectlibre1.field.Field;
import com.projectlibre1.field.FieldConverter;
import com.projectlibre1.graphic.configuration.BarStyles;
import com.projectlibre1.graphic.configuration.GraphicConfiguration;
import com.projectlibre1.grouping.core.Node;
import com.projectlibre1.grouping.core.model.WalkersNodeModel;
import com.projectlibre1.pm.task.Project;

/**
 *
 */
public abstract class Graph extends JComponent implements GraphListener, GraphParams{
    protected BarStyles barStyles = null;
    protected Project project;
    protected GraphModel model;
    
	/**
	 * @param project
	 * 
	 */
	public Graph(Project project,String viewName) {
		this(new GraphModel(project,viewName),project);
	}
	protected Graph(GraphModel model, Project project) {
		super();
		this.project = project;
		setModel(model);
		ToolTipManager.sharedInstance().registerComponent(this);
		//renderer=new GanttTaskRenderer();
		updateUI();
		//TODO unregister
		
		
		setBackground(Color.WHITE);
		setOpaque(true);
		setDoubleBuffered(true);
		setLayout(null);
	}
	
	
	public GraphUI getUI(){
		return (GraphUI)ui;
	}
	
    public NodeModelCache getCache() {
        return model.getCache();
    }
     public void setCache(NodeModelCache cache) {
        model.setCache(cache);
    }
     
	public GraphModel getModel() {
		return model;
	}
	
	public Project getProject() {
		return project;
	}
	public void cleanUp() {
		if (this.model!=null)
			model.removeGraphListener(this);

	}
	public void setModel(GraphModel model) {
		if (this.model!=null) model.removeGraphListener(this);
		this.model = model;
		model.addGraphListener(this);
	}

     
	/**
	 * @return Returns the barStyles.
	 */
	public BarStyles getBarStyles() {
		return barStyles;
	}
	/**
	 * @param barStyles The barStyles to set.
	 */
	public void setBarStyles(BarStyles barStyles) {
		this.barStyles = barStyles;
        model.setBarStyles(barStyles);
	}
	
	

	private String getStringValue(Field field,Node node){
	    WalkersNodeModel wmodel=model.getCache().getWalkersModel();
		Object value=field.getValue(node,wmodel,null);
		return FieldConverter.toString(value,value.getClass(),null);
	}
	

		
    public void updateGraph(GraphEvent e){
    	update(e.getNodes());
    }
    public void update(List nodes){
    	((GraphUI)ui).updateShapes(nodes);
    	repaint();
    }
	
	
    //to override
	public void updateUI(){}

	
	public Rectangle getDrawingBounds(){
		return getVisibleRect();
	}
	
	protected GraphicConfiguration config=GraphicConfiguration.getInstance();
	public GraphicConfiguration getConfiguration(){
		return config;
	}
	public void setConfiguration(GraphicConfiguration config){
		this.config=config;
	}

	
}
