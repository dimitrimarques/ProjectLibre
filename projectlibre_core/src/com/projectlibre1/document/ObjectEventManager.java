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
package com.projectlibre1.document;

import java.util.Iterator;

import com.projectlibre1.field.Field;
import com.projectlibre1.pm.assignment.Assignment;
import com.projectlibre1.pm.task.NormalTask;
import com.projectlibre1.undo.NodeUndoInfo;


/**
 *
 */
public class ObjectEventManager {
//	 Create the listener list
    protected javax.swing.event.EventListenerList listenerList =
        new javax.swing.event.EventListenerList();

    // This methods allows classes to register for ObjectEvents
    public void addListener(ObjectEvent.Listener listener) {
        listenerList.add(ObjectEvent.Listener.class, listener);
    }

    // This methods allows classes to unregister for ObjectEvents
    public void removeListener(ObjectEvent.Listener listener) {
        listenerList.remove(ObjectEvent.Listener.class, listener);
    }



    public void fireCreateEvent(Object source, Object object) {
    	fire(source,object,ObjectEvent.CREATE,null);
    }
	
    public void fireCreateEvent(Object source, Object object, NodeUndoInfo info) {
    	fire(source,object,ObjectEvent.CREATE,info);
    }
    
    public void fireDeleteEvent(Object source, Object object) {
    	fire(source,object,ObjectEvent.DELETE,null);
    }

    public void fireDeleteEvent(Object source, Object object, NodeUndoInfo info) {
    	fire(source,object,ObjectEvent.DELETE,info);
    }

    
    public void fireUpdateEvent(Object source, Object object) {
    	fire(source,object,ObjectEvent.UPDATE,null);
    }
    
    public void fireUpdateEvent(Object source, Object object, NodeUndoInfo info) {
    	fire(source,object,ObjectEvent.UPDATE,info);
    }

    public void fireUpdateEvent(Object source, Object object, Field field) {
    	ObjectEvent evt = ObjectEvent.getInstance(source,object,ObjectEvent.UPDATE,null);
    	evt.setField(field);
    	fire(evt);
    	
    	if (object instanceof NormalTask && field.isApplicable(Assignment.class)) { // fix for bug 258
    		Iterator i = ((NormalTask)object).getAssignments().iterator();
    		while (i.hasNext()) {
    			fireUpdateEvent(source,i.next(),field);
    		}
    	}
    }
	
	
    
    private void fire(Object source, Object object, int eventType, NodeUndoInfo info) {
    	ObjectEvent evt = ObjectEvent.getInstance(source,object,eventType,info);
    	fire(evt);
    }
    
    public void fire(ObjectEvent evt) {    	
        Object[] listeners = listenerList.getListenerList();
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
        for (int i=0; i<listeners.length; i+=2) {
            if (listeners[i]==ObjectEvent.Listener.class) {
//            	if (evt.isUpdate()) System.out.println("ObjectEvent update: object="+evt.getObject()+", field="+evt.getField()+", source="+evt.getSource()+", info="+evt.getInfo()+", listener="+listeners[i+1]);
//            	else if (evt.isCreate()) System.out.println("ObjectEvent create: object="+evt.getObject()+", field="+evt.getField()+", source="+evt.getSource()+", info="+evt.getInfo()+", listener="+listeners[i+1]);
//            	else if (evt.isDelete()) System.out.println("ObjectEvent delete: object="+evt.getObject()+", field="+evt.getField()+", source="+evt.getSource()+", info="+evt.getInfo()+", listener="+listeners[i+1]);
//            	else System.out.println("ObjectEvent: object="+evt.getObject()+", field="+evt.getField()+", source="+evt.getSource()+", info="+evt.getInfo()+", listener="+listeners[i+1]);
                ((ObjectEvent.Listener)listeners[i+1]).objectChanged(evt);
            }
        }
        evt.recycle();
    }
}
