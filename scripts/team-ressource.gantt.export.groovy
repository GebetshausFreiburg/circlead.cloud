// Exports Gantt-Chart from Teams
// Version: v1.0.0
// Author: Matthias Wegner

GanttUtil.write("exports"+File.separatorChar+"Teams.gan", R.getTeams(), R.getRoles(), R.getPersons());

return "sucessfully used team-ressource-gantt.export"