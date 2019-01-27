// Copy needed file for File-Synchronizer from ressource-folders
// Version: v1.0.0
// Author: Matthias Wegner

FileUtil.copyFileOrFolder(new File("data" + File.separatorChar + "ressources" + File.separatorChar + "images"),	new File("web" + File.separatorChar + "images"));
FileUtil.copyFileOrFolder(new File("data" + File.separatorChar + "ressources" + File.separatorChar + "javascript"), new File("web" + File.separatorChar + "javascript"));
FileUtil.copyFileOrFolder(new File("data" + File.separatorChar + "howtos"), new File("web" + File.separatorChar + "howtos"));
FileUtil.copyFileOrFolder(new File("data" + File.separatorChar + "ressources" + File.separatorChar + "styles.css"), new File("web" + File.separatorChar + "styles.css"));
FileUtil.copyFileOrFolder(new File("data" + File.separatorChar + "ressources" + File.separatorChar + "stylesCategoryReport.css"), new File("web" + File.separatorChar + "stylesCategoryReport.css"));

return "sucessfully used filesynchronizer.ressources.util"