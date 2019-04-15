// Version: v1.0.0
// Author: Matthias Wegner

String category = PropertyUtil.getInstance().getApplicationDefaultTeamcategory()

// Name of report
name = "Team Category Json Export"

// Description of report
description = "Exportiert alle 'öffentlichen' Teams der Kategorie '"+category+"' in eine Json-Datei."
	
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dmfs.rfc5545.Weekday;
import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.CircleadRecurrenceRule;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.AtlassianSynchronizer;
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer;
import org.rogatio.circlead.model.work.Team;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.util.PropertyUtil;
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.renderer.ISynchronizerRendererEngine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.synchronizer.DefaultSynchronizer;
import org.rogatio.circlead.control.synchronizer.SynchronizerException;
import org.rogatio.circlead.control.synchronizer.SynchronizerFactory;
import org.rogatio.circlead.control.synchronizer.SynchronizerResult;
import org.rogatio.circlead.control.validator.IValidator;
import org.rogatio.circlead.control.validator.ValidationMessage;
import org.rogatio.circlead.model.WorkitemType;
import org.rogatio.circlead.model.data.ActivityDataitem;
import org.rogatio.circlead.model.data.CompetenceDataitem;
import org.rogatio.circlead.model.data.HowTo;
import org.rogatio.circlead.model.data.PersonDataitem;
import org.rogatio.circlead.model.data.RoleDataitem;
import org.rogatio.circlead.model.data.RolegroupDataitem;
import org.rogatio.circlead.model.data.TeamDataitem;
import org.rogatio.circlead.model.work.Activity;
import org.rogatio.circlead.model.work.Competence;
import org.rogatio.circlead.model.work.IWorkitem;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Rolegroup;
import org.rogatio.circlead.model.work.Team;
import org.rogatio.circlead.util.FileUtil;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.util.PropertyUtil;
import org.rogatio.circlead.view.renderer.FileRendererEngine;
import org.rogatio.circlead.view.renderer.ISynchronizerRendererEngine;
import org.rogatio.circlead.view.renderer.IWorkitemRenderer;
import org.rogatio.circlead.view.report.IReport;
import org.rogatio.circlead.view.report.IndexCirclead;
import org.rogatio.circlead.view.report.IndexRbs;
import org.rogatio.circlead.view.report.IndexRrgs;
import org.rogatio.circlead.view.report.IndexWorkitems;
import org.jsoup.nodes.Element;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

class Hours {
  private List<Hour> hours = new ArrayList<Hour>();
  
  public void addHour(Hour hour) {
    hours.add(hour);
  }
		
  public void setHours(List<Hour> hours) {
    this.hours = hours;
  }
  
   public List<Hour> getHours() {
    return hours;
  }
  
}

class Day {
  private List<Hour> hours = new ArrayList<Hour>();
  private int id;
  private String nameOfDay;
 
  public List<Hour> getHours() {
    return hours;
  }
	
  public void setId(int id) {
    this.id = id;                          
  }

  public int getId() {
    return id;               
  }
	
  public void addHour(Hour hour) {
    hours.add(hour);
  }
		
  public void setHours(List<Hour> hours) {
    this.hours = hours;
  }
  
  public String getNameOfDay() {
    return nameOfDay;
  }
  
  public void setNameOfDay(String nameOfDay) {
    this.nameOfDay = nameOfDay;
  }
		
}
	
class Hour {
  private int hour;
  private int duration;
  private String title;
  private String subtitle;
  private int idOfDay;
  private String nameOfDay;
  
  public String getNameOfDay() {
    return nameOfDay;
  }
  
  public void setNameOfDay(String nameOfDay) {
    this.nameOfDay = nameOfDay;
  }
  
  public int getIdOfDay() {
    return idOfDay;
  }
  
  public void setIdOfDay(int idOfDay) {
    this.idOfDay = idOfDay;
  }
  
  public int getHour() {
    return hour;
  }
  public void setHour(int hour) {
    this.hour = hour;
  }
  public int getDuration() {
    return duration;
  }
  public void setDuration(int duration) {
    this.duration = duration;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getSubtitle() {
    return subtitle;
  }
  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }
		
}

class Week {

  private List<Day> days = new ArrayList<Day>();
	
  public List<Day> getDays() {
    return days;
  }
	
  public Day getDay(int id) {
    for(Day day : days) {
	  if (day.getId()==id) {
	    return day;
	  }
	}
  }
	
  public void addDay(Day day) {
    days.add(day);
  }
	
  public void setDays(List<Day> days) {
    this.days = days;
  }
	
}

List<Team> teams = R.getTeamsWithCategory(category);
if (!ObjectUtil.isListNotNullAndEmpty(teams)) {
  Element element = new Element("");
  element.appendText("Report not created, because no team found with category '"+category+"'");
  return element;
}		
	
boolean[][] writeable = ObjectUtil.createMatrix(24,8)
for (int i = 0; i < 24; i++) {
  for (int j = 1; j <= 7; j++) {
    writeable[i][j] = true;
  }
}

Hours hours = new Hours();
Week week = new Week();
    for (int j = 1; j <= 7; j++) {
		Day day = new Day();
		day.setId(j);
		if (j==1) {
		  day.setNameOfDay("Montag");
		}
		if (j==2) {
		  day.setNameOfDay("Dienstag");
		}
		if (j==3) {
		  day.setNameOfDay("Mittwoch");
		}
		if (j==4) {
		  day.setNameOfDay("Donnerstag");
		}
		if (j==5) {
		  day.setNameOfDay("Freitag");
		}
		if (j==6) {
		  day.setNameOfDay("Samstag");
		}
		if (j==7) {
		  day.setNameOfDay("Sonntag");
		}

		week.addDay(day);
	}

		for (int i = 0; i < 24; i++) {
			for (int j = 1; j <= 7; j++) {
			        Day day = week.getDay(j);
			
					boolean found = false;
					for (Team team : teams) {
						if (StringUtil.isNotNullAndNotEmpty(team.getRecurrenceRule())) {
							CircleadRecurrenceRule crr = new CircleadRecurrenceRule(team.getRecurrenceRule());

							Weekday wd = crr.getWeekday();
							int hour = crr.getHour();
							int pos = CircleadRecurrenceRule.WEEKDAY2DAYOFWEEK.get(wd);

							if (writeable[i][j] && (j == pos && i == hour)) {
                                 
                                if (crr.getDuration() == 3) {
									writeable[i + 1][j] = false;
									writeable[i + 2][j] = false;
								} else if (crr.getDuration() == 2) {
									writeable[i + 1][j] = false;
								} 

                               
								String appendix = "";
								if (crr.isRecurrenceOdd() != null) {
									if (crr.isRecurrenceOdd() == true) {
										appendix = " (uKW)";
									} else {
										appendix = " (gKW)";
									}
								}
								
								Hour hourobj = new Hour();
                               hourobj.setHour(hour);
                               hourobj.setIdOfDay(day.getId());
                               hourobj.setNameOfDay(day.getNameOfDay());
                               hourobj.setDuration(crr.getDuration());
                               hourobj.setTitle(team.getTeamType()+appendix);
                               hourobj.setSubtitle(team.getTeamSubtype());
								
								if (team.getTeamSize() == 1&&(!team.isSpecialized())) {
								} else {
								   day.addHour(hourobj);
								   hours.addHour(hourobj);
								}
								
								found = true;
							}
						}
					}
					if (writeable[i][j] && !found) {
						Hour hourobj = new Hour();
                               hourobj.setHour(i);
                               hourobj.setIdOfDay(day.getId());
                               hourobj.setNameOfDay(day.getNameOfDay());
                               hourobj.setDuration(1);
                               hourobj.setTitle("Unbesetzt");
                              day.addHour(hourobj);
                              hours.addHour(hourobj);
					}
				}
			}
		

File f = new File("exports" + File.separatorChar + "teamcategories.json");
if (f.exists()) {
	f.delete();
}
f.createNewFile();

ObjectMapper mapper = new ObjectMapper();
mapper.setSerializationInclusion(Include.NON_NULL);
mapper.enable(SerializationFeature.INDENT_OUTPUT);
mapper.writeValue(f, hours);
