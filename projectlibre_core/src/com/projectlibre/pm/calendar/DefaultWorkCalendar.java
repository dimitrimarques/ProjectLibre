/*
The contents of this file are subject to the Common Public Attribution License 
Version 1.0 (the "License"); you may not use this file except in compliance with 
the License. You may obtain a copy of the License at 
http://www.projectlibre.com/license . The License is based on the Mozilla Public 
License Version 1.1 but Sections 14 and 15 have been added to cover use of 
software over a computer network and provide for limited attribution for the 
Original Developer. In addition, Exhibit A has been modified to be consistent 
with Exhibit B. 

Software distributed under the License is distributed on an "AS IS" basis, 
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for the 
specific language governing rights and limitations under the License. The 
Original Code is OpenProj and ProjectLibre.
The Original Developer is the Initial Developer and is both Projity, Inc and 
ProjectLibre Inc. All portions of the code written by Projity are Copyright (c) 
2006, 2008. All Rights Reserved. All portions of the code written by ProjectLibre 
are Copyright (c) 2012. All Rights Reserved. Contributors Projity, Inc. and 
ProjectLibre, Inc.

Alternatively, the contents of this file may be used under the terms of the 
ProjectLibre End-User License Agreement (the ProjectLibre License) in which case 
the provisions of the ProjectLibre License are applicable instead of those above. 
If you wish to allow use of your version of this file only under the terms of the 
ProjectLibre License and not to allow others to use your version of this file under 
the CPAL, indicate your decision by deleting the provisions above and replace them 
with the notice and other provisions required by the Project Libre License. If you 
do not delete the provisions above, a recipient may use your version of this file 
under either the CPAL or the ProjectLibre Licenses. 


[NOTE: The text of this Exhibit A may differ slightly from the text of the notices 
in the Source Code files of the Original Code. You should use the text of this 
Exhibit A rather than the text found in the Original Code Source Code for Your 
Modifications.] 
EXHIBIT B. Attribution Information both ProjectLibre and OpenProj required

Attribution Copyright Notice: Copyright (c) 2012, ProjectLibre, Inc.
Attribution Phrase (not exceeding 10 words): ProjectLibre, the updated version of 
OpenProj
Attribution URL: http://www.projectlibre.com
Graphic Image as provided in the Covered Code as file: projectlibre-logo.png with 
alternatives listed on http://www.projectlibre.com/logo 

Display of Attribution Information is required in Larger Works which are defined in 
the CPAL as a work which combines Covered Code or portions thereof with code not 
governed by the terms of the CPAL. However, in addition to the other notice 
obligations, all copies of the Covered Code in Executable and Source Code form 
distributed must, as a form of attribution of the original author, include on each 
user interface screen the "OpenProj"  and "ProjectLibre" logos visible to all users. 
The OpenProj logo should be located horizontally aligned with the menu bar and left 
justified on the top left of the screen adjacent to the File menu. The logo must be 
at least 100 x 25 pixels. When users click on the "OpenProj" logo it must direct them 
back to http://www.projity.com. The ProjectLibre logo should be located horizontally 
aligned immediately above the OpenProj logo and left justified in alignment with the 
OpenProj logo. The logo must be at least 144 x 31 pixels. When users click on the 
"ProjectLibre" logo it must direct them back to http://www.projectlibre.com.

Attribution Copyright Notice: Copyright (c) 2006, 2008 Projity, Inc.
Attribution Phrase (not exceeding 10 words): Powered by OpenProj, an open source 
solution from Projity
Attribution URL: http://www.projity.com
Graphic Image as provided in the Covered Code as file: openproj_logo.png with 
alternatives listed on http://www.projity.com/logo 

Display of Attribution Information is required in Larger Works which are defined in 
the CPAL as a work which combines Covered Code or portions thereof with code not 
governed by the terms of the CPAL. However, in addition to the other notice 
obligations, all copies of the Covered Code in Executable and Source Code form 
distributed must, as a form of attribution of the original author, include on each 
user interface screen the "OpenProj" and "ProjectLibre" logos visible to all users. 
The OpenProj logo should be located horizontally aligned with the menu bar and left 
justified on the top left of the screen adjacent to the File menu. The logo must be 
at least 100 x 25 pixels. When users click on the "OpenProj" logo it must direct them 
back to http://www.projity.com.
*/
package com.projectlibre.pm.calendar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Laurent Chretienneau
 *
 */
public class DefaultWorkCalendar implements WorkCalendar{
	protected WorkCalendar base;
	protected CalendarId id;
	protected String name;
	protected List<WorkCalendarException> exceptions=new ArrayList<WorkCalendarException>();
	protected List<WorkWeek> weeks=new ArrayList<WorkWeek>();
	
	public DefaultWorkCalendar(){
		
	}

	@Override
	public WorkCalendar getBase() {
		return base;
	}

	@Override
	public void setBase(WorkCalendar base) {
		this.base = base;
	}

	@Override
	public List<WorkCalendarException> getExceptions() {
		return exceptions;
	}

	@Override
	public void setExceptions(List<WorkCalendarException> exceptions) {
		this.exceptions = exceptions;
	}

	@Override
	public boolean addException(WorkCalendarException exception) {
		// TODO Auto-generated method stub
		return exceptions.add(exception);
	}

	@Override
	public List<WorkWeek> getWeeks() {
		return weeks;
	}

	@Override
	public void setWeeks(List<WorkWeek> weeks) {
		this.weeks = weeks;
	}

	@Override
	public WorkWeek getWeek() {
		return weeks.size()==0 ? null : weeks.get(0);
	}

	@Override
	public void setWeek(WorkWeek week) {
		if (weeks.size()==0)
			weeks.add(week);
		else weeks.set(0, week);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public CalendarId getId() {
		return id;
	}

	@Override
	public void setId(CalendarId id) {
		this.id = id;
	}

	@Override
	public String toString(String tab){
		StringBuffer s=new StringBuffer();
		s.append(tab).append("DefaultWorkCalendar #").append(getId()).append("-").append(getName());
		return s.toString();
	}
	
	@Override
	public String toLongString(String tab){
		StringBuffer s=new StringBuffer();
		s.append(tab).append("DefaultWorkCalendar #").append(hashCode()).append('\n');
		s.append(tab).append("\t").append("id=").append(id).append('\n');
		s.append(tab).append("\t").append("name=").append(name).append('\n');
		s.append(tab).append("\t").append("base=").append(base==null? null : base.hashCode()).append('\n');

		s.append(tab).append("\t").append("exceptions=\n");
		for (WorkCalendarException exception : exceptions)
			s.append(tab).append("\t\t").append(exception.toString()).append('\n');
		
		s.append(tab).append("\t").append("weeks=\n");
		for (WorkWeek week : weeks){
			if (week==null) s.append(tab).append("\t\t").append("null").append('\n');
			else s.append(week.toString(tab+"\t\t"));

		}
		
		return s.toString();
	}

	@Override
	public String toString(){
		return toString("");
	}
	
	@Override
	public String toLongString(){
		return toLongString("");
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	
	
}
