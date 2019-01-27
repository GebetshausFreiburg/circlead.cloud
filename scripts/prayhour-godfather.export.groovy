// Export Teamhours which are related to one person which are 12 hours later
// Version: v1.0.0
// Author: Matthias Wegner

import org.rogatio.circlead.model.work.Role
import java.util.List
import org.jsoup.nodes.Element
import org.rogatio.circlead.control.synchronizer.ISynchronizer
import org.rogatio.circlead.control.synchronizer.atlassian.AtlassianSynchronizer
import org.rogatio.circlead.control.synchronizer.file.FileSynchronizer
import org.rogatio.circlead.model.work.Person
import org.rogatio.circlead.model.work.Role
import org.rogatio.circlead.model.work.Team
import org.rogatio.circlead.view.renderer.ISynchronizerRendererEngine
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

String content = "";

Role roleSet = R.getRole("Stamm-Mitarbeiter");
List<String> personIdentifiers = roleSet.getPersonIdentifiers();

if (ObjectUtil.isListNotNullAndEmpty(personIdentifiers)) {
	for (int i = 0; i < personIdentifiers.size(); i++) {
		String personIdentifier = personIdentifiers.get(i);

		Person p = R.getPerson(personIdentifier);					
		if (p!=null) {
			content += "\n\n"+p.getTitle().toUpperCase()
			List<Team> teams = R.getTeamsWithMember(p);
			if (teams.size() > 0) {
				for (Team team : teams) {
					StringBuilder sb = new StringBuilder();

					if (team.getCategory().equals("Gebetsstunde")) {
						sb.append("\nGebetsstunde: "+team.getCRRWeekday()+" ")
						sb.append(team.getCRRHour())
						sb.append("h \n")
				
						Integer hour = team.getCRRHour();
						String day = team.getCRRWeekday();
						if (hour!=null) {
							if (hour>=12) {
								hour = team.getCRRHour() - 12;
								day = StringUtil.getNextDay(team.getCRRWeekday());
							} else {
								hour = team.getCRRHour() + 12;
							}
						} 

						Team t = R.getTeam(hour, day);

						if (t!=null) {
							sb.append("Patenstunde: ")
							sb.append(day+" ")
							sb.append(hour)
							sb.append("h")
							sb.append(" "+t.getTeamMembers())
						} else {
							sb.append("Patenstunde: " + day + " " + hour + "h Stunde unbesetzt")
						}

						if (team.getCRRDuration()==2) {
							hour = hour + 1
							t = R.getTeam(hour, day);

							if (t!=null) {
								sb.append("\nPatenstunde: ")
								sb.append(day+" ")
								sb.append(hour)
								sb.append("h")
								sb.append(" "+t.getTeamMembers())
							} else {
								sb.append("\nPatenstunde: " + day + " " + hour + "h Stunde unbesetzt")
							}
						}
				
						content += sb.toString()+"\n"
			
					}
				}
			}
		}
	}
}

File f = new File("exports/prayhour.godfather.export.txt");
		if (!f.exists()) {
			f.getParentFile().mkdirs(); 
			f.createNewFile();
		}
Files.write(Paths.get("exports/prayhour.godfather.export.txt"), content.getBytes(), StandardOpenOption.CREATE);

return "sucessfully used prayhour.godfather.export"
