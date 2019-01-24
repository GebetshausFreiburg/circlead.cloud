import com.dropbox.core.v2.DbxTeamClientV2

DbxTeamClientV2 dbxClient = DropboxUtil.getTeamClientFromAccessToken(PropertyUtil.getInstance().getDropboxAccesstoken());
DropboxUtil.uploadFileToTeamFolder(dbxClient, "exports/Gebetsstundenübersicht.xlsx", "/06_GBH_BO_Gebetstundenorga/Gebetsstundenübersicht.xlsx");
