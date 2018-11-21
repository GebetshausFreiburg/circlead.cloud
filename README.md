# circlead.cloud

Circlead is a system which operates for an evolutionary role model. The is use for circular leadership for people and organisations. Circular leadership is a structural system (or software) and a set of principles based on the verse in the bible 1. peter 4,10.

This is a version written in java to create the role model logic on a minimal dataset. A dataset can be used as
* a cloud or dedicated version of atlassian confluence https://de.atlassian.com/software/confluence (or)
* a flat file repository of json-files.

This application synchronizes and merge all the data and write back simple html-files or confluence-pages. 

The main-layers for the application are
* Repository: Repository is a Singleton-Class. Its the backbone of the application. Contains all loaded data as workitems. A workitem contains logic of evolutionary model to handle minimal loaded data of dataitem. Each workitem contains validator-interface and rendering-interface. The dataitem-interface is exactly the POJO of the represented data. A dataitem can have more than one id, because each synchronizer (and data-storage) defines the unique id.
* Connector: The connector contains all synchronizers. Enables loading, updating, deleting and adding of workitems. The synchronizer contains also a report-handler. RenderEngine of synchronizer allows to render each workitem specifically for system which is handled with synchronizer.
  * FileSynchronizer: Read json-files from storage and writes back html-files. Id of files is UUID-v4.
  * AtlassianSynchronizer: Read data from Confluence Pages in page-property-table (over rest with json) and writes back rendered confluence-content to same page appended after page-properties
* Storage: Hold all data, content and reports. Default space in confluence is space 'circlead'. Default folder for Filesynchronizer is 'data'.

![circlead-api](https://github.com/GebetshausFreiburg/circlead.cloud/blob/master/ressources/circlead-api.jpg)

To understood the philosophy (and model) of circlead read the blog (only in german) at https://circleadonline.wordpress.com/.

To understood the API see https://gebetshausfreiburg.github.io/circlead.cloud/index.html?overview-summary.html and https://veniversum.me/git-visualizer/?owner=GebetshausFreiburg&repo=circlead.cloud
