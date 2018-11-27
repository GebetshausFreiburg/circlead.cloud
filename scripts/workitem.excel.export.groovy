import org.rogatio.circlead.model.WorkitemType

R.writeExcel("Mitarbeiterliste", WorkitemType.PERSON, null);
R.writeExcel("Rollen", WorkitemType.ROLE, null);
R.writeExcel("Rollengruppen", WorkitemType.ROLEGROUP, null);
R.writeExcel("Teams", WorkitemType.TEAM, null);