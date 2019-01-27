// Export teams with category 'Gebetsstunde' to Excel and upload excel to Dropbox
// Version: v1.0.0
// Author: Matthias Wegner

import com.dropbox.core.v2.DbxTeamClientV2
import java.io.File
import org.rogatio.circlead.main.PrayHourExporter

// Create Excel-File with Exporter
PrayHourExporter phe = new PrayHourExporter();
phe.export("Gebetsstundenübersicht");

// Upload Excel-File to Dropbox Team-Folder
DbxTeamClientV2 dbxClient = DropboxUtil.getTeamClientFromAccessToken(PropertyUtil.getInstance().getDropboxAccesstoken());
DropboxUtil.uploadFileToTeamFolder(dbxClient, "exports"+File.separatorChar+"Gebetsstundenübersicht.xlsx", "/06_GBH_BO_Gebetstundenorga/Gebetsstundenübersicht.xlsx");

return "sucessfully used dropbox.excel.export"