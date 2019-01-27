// Export Workitem-Types as Excel File
// Version: v1.0.0
// Author: Matthias Wegner

import org.rogatio.circlead.model.WorkitemType

// Create Excel-Files
R.writeExcel("Mitarbeiterliste", WorkitemType.PERSON, null);
R.writeExcel("Rollen", WorkitemType.ROLE, null);
R.writeExcel("Rollengruppen", WorkitemType.ROLEGROUP, null);
R.writeExcel("Teams", WorkitemType.TEAM, null);

return "sucessfully used workitem.excel.export"